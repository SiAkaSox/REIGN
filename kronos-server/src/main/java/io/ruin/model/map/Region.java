package io.ruin.model.map;

import com.google.common.collect.Maps;
import io.ruin.Server;
import io.ruin.network.io.InBuffer;
import io.ruin.cache.filestore.IndexFile;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.containers.bank.BankActions;
import io.ruin.model.map.object.*;
import io.ruin.model.map.object.Location;
import io.ruin.model.map.route.RouteFinder;
import io.ruin.model.skills.construction.House;

import java.util.*;

public class Region {

    public static final int CLIENT_SIZE = 104;

    public static final Region[] LOADED = new Region[Short.MAX_VALUE + 1];

    /**
     * Separator
     */

    public final int id;

    public final int baseX, baseY;

    public final Bounds bounds;

    public final ArrayList<Player> players;

    public final ArrayList<Tile> activeTiles;

    private House house; // house instance that this region currently belongs to

    public Region(int id) {
        this.id = id;
        this.baseX = (id >> 8) * 64;
        this.baseY = (id & 0xff) * 64;
        this.bounds = Bounds.fromRegion(id);
        this.players = new ArrayList<>();
        this.activeTiles = new ArrayList<>();
    }

    /**
     * Data
     */

    public int[] keys;

    public Tile[][][] tiles;

    public int[][][][] dynamicData;

    public int dynamicIndex = -1;

    public int dynamicRegionBaseX, dynamicRegionBaseY;

    public boolean empty;

    public void init() { //not my favorite design, let's come back to this one day..
        byte[][][] tileData = new byte[4][64][64];
        byte[] mapData = getMapData();
        if(mapData != null) {
            InBuffer mapIn = new InBuffer(mapData);
            for(int z = 0; z < 4; z++) {
                for(int x = 0; x < 64; x++) {
                    for(int y = 0; y < 64; y++) {
                        for(; ; ) {
                            int i = mapIn.readByteUnsafe() & 0xFF;
                            if(i == 0)
                                break;
                            if(i == 1) {
                                mapIn.skipByte();
                                break;
                            }
                            if(i <= 49)
                                mapIn.skipByte();
                            else if(i <= 81)
                                tileData[z][x][y] = (byte) (i - 49);
                        }
                    }
                }
            }
            for(int z = 0; z < 4; z++) {
                for(int x = 0; x < 64; x++) {
                    for(int y = 0; y < 64; y++) {
                        if((tileData[z][x][y] & 0x1) == 1) {
                            int height = z;
                            if((tileData[1][x][y] & 0x2) == 2)
                                height--;
                            if(height >= 0) {
                                int absX = baseX + x;
                                int absY = baseY + y;
                                Tile tile = getTile(absX, absY, z, true);
                                tile.flagUnmovable();
                                tile.defaultClipping = tile.clipping;
                            }
                        }
                        if((tileData[z][x][y] & 0x4) != 0) {
                            int absX = baseX + x;
                            int absY = baseY + y;
                            Tile tile = getTile(absX, absY, z, true);
                            tile.roofExists = true;
                        }
                    }
                }
            }
        }
        byte[] landscapeData = null;
        boolean invalidKeys = false;
        try {
            landscapeData = getLandscapeData();
        } catch(Throwable t) {
            //System.err.println("Invalid Map Keys for Region (" + id + "): base=(" + baseX + ", " + baseY + ") keys=" + Arrays.toString(keys));
            invalidKeys = true;
        }
        if(landscapeData != null) {
            InBuffer landIn = new InBuffer(landscapeData);
            int objectId = -1;
            for(; ; ) {
                int idOffset = landIn.readUnsignedIntSmartShortCompat();
                if(idOffset == 0)
                    break;
                objectId += idOffset;
                int position = 0;
                for(; ; ) {
                    int positionOffset = landIn.readUnsignedIntSmartShortCompat();
                    if(positionOffset == 0)
                        break;
                    position += positionOffset - 1;
                    int localY = position & 0x3f;
                    int localX = (position >> 6) & 0x3f;
                    int height = position >> 12;

                    int attributes = landIn.readUnsignedByte();
                    int type = attributes >> 2;
                    int direction = attributes & 0x3;

                    if((tileData[1][localX][localY] & 0x2) == 2)
                        height--;
                    if(height >= 0) {
                        int absX = baseX + localX;
                        int absY = baseY + localY;
                        GameObject obj = new GameObject(objectId, absX, absY, height, type, direction);
                        getTile(absX, absY, height, true).addObject(obj);
                        BankActions.markTiles(obj);
                    }
                }
            }
        }
        empty = !invalidKeys && mapData == null && landscapeData == null;
    }

    public Tile getTile(int x, int y, int z, boolean create) {
        int localX = x - baseX;
        int localY = y - baseY;
        if(tiles == null) {
            if(!create)
                return null;
            tiles = new Tile[4][64][64];
        }
        Tile tile = tiles[z][localX][localY];
        if(tile == null && create)
            tile = tiles[z][localX][localY] = new Tile(this);
        return tile;
    }

    private byte[] getMapData() {
        IndexFile index = Server.fileStore.get(5);
        int mapArchiveId = index.getArchiveId("m" + ((baseX >> 3) / 8) + "_" + ((baseY >> 3) / 8));
        return mapArchiveId == -1 ? null : index.getFile(mapArchiveId, 0);
    }

    public byte[] getLandscapeData() {
        if(keys == null)
            return null;
        IndexFile index = Server.fileStore.get(5);
        int landArchiveId = index.getArchiveId("l" + ((baseX >> 3) / 8) + "_" + ((baseY >> 3) / 8));
        return landArchiveId == -1 ? null : index.getFile(landArchiveId, 0, keys[0] == 0 && keys[1] == 0 && keys[2] == 0 && keys[3] == 0 ? null : keys);
    }

    public static Region get(int regionId) {
        return LOADED[regionId];
    }

    public static Region get(int absX, int absY) {
        return get(getId(absX, absY));
    }

    public static int getId(int absX, int absY) {
        return ((absX >> 6) << 8) | absY >> 6;
    }

    public static int getClipping(int x, int y, int z) {
        Region region = Region.get(x, y);
        if(region.empty)
            return RouteFinder.UNMOVABLE_MASK;
        Tile tile = region.getTile(x, y, z, false);
        return tile == null ? 0 : tile.clipping;
    }

    /**
     * Updating
     */

    public static void update(Player player) {
        player.getPacketSender().clearChunks();
        for(Region region : player.getRegions()) {
            for(Tile tile : region.activeTiles)
                tile.update(player);
        }
    }

    public List<GameObject> getAllObjects() {
        List<GameObject> objects = new ArrayList<>();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                for (int k = 0; k < tiles[i][j].length; k++) {
                    if (tiles[i][j][k].gameObjects == null) continue;
                    objects.addAll(tiles[i][j][k].gameObjects);
                }
            }
        }
        return objects;
    }

    /**
     * Destroy
     */

    public void destroy() {
        players.clear();
        if(!activeTiles.isEmpty()) {
            for(Tile tile : activeTiles)
                tile.destroy();
            activeTiles.clear();
        }
        tiles = null;
        dynamicIndex = -1;
        dynamicData = null;
        empty = true;
        house = null;
    }

    public boolean isEmpty() {
        return activeTiles.isEmpty();
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    private static Map<Integer, Region> regions = Maps.newConcurrentMap();

    public static Optional<io.ruin.model.map.object.Location> findFreeRegion(int width, int length) {
        for(int x = 160;x<220;x++) {
            for(int y = 160;y<220;y++) {
                int regionId = (x << 8) + y;
                boolean regionExists = regions.containsKey(regionId);
                if(!regionExists) {
                    boolean regionFree = true;
                    for (int x2 = x + 1; x2 <= x + width; x2++) {
                        for (int y2 = y + 1; y2 <= y + length; y2++) {
                            if (!regionFree)
                                break;
                            int nextRegionId = (x2 << 8) + y2;
                            if (regions.containsKey(nextRegionId)) {
                                regionFree = false;
                                break;
                            }
                        }
                    }
                    if(regionFree)
                        return Optional.of(new io.ruin.model.map.object.Location(x * 32, y * 32));
                }
            }
        }
        return Optional.empty();
    }

    public static Region getRegion(int x, int y) {
        int regionX = x >> 3;
        int regionY = y >> 3;
        int regionId = (regionX / 8 << 8) + regionY / 8;
        Region region = regions.get(regionId);
        if(region == null) {
            region = new Region(regionId);
        }
        return region;
    }

    private static HashMap<Integer, ArrayList<WorldObject>> worldObjects = new HashMap<>();

    public static Optional<WorldObject> getWorldObject(int id, int x, int y, int height, int type) {
        Region region = getRegion(x, y);
        if (region == null) {
            return Optional.empty();
        }
        Collection<WorldObject> regionObjects = worldObjects.get(region.id);
        if (regionObjects == null) {
            return Optional.empty();
        }
        Optional<WorldObject> exists = regionObjects.stream().filter(object -> (type == -1 || object.type == type) && object.id == id && object.x == x && object.y == y && object.getHeight() == height).findFirst();
        if(!exists.isPresent())
            exists = regionObjects.stream().filter(object -> (type == -1 || object.type == type) && object.id == id && object.x == x && object.y == y && object.getHeight() == (height & 3)).findFirst();

        return exists;
    }

    public static boolean isBlocked(int x, int y, int z) {
        return getClipping(x, y, z) != 0;
    }

    public static boolean solidObjectExists(int x, int y, int height) {
        Region region = getRegion(x, y);
        if (region == null) {
            return false;
        }
        Collection<WorldObject> regionObjects = worldObjects.get(region.id);
        if (regionObjects == null) {
            return false;
        }
        Optional<WorldObject> exists = regionObjects.stream().filter(object -> object.type != 22 && object.x == x && object.y == y && object.height == height).findFirst();
        if(!exists.isPresent())
            exists = regionObjects.stream().filter(object ->  object.type != 22 && object.x == x && object.y == y && object.height == (height & 3)).findFirst();

        return exists.isPresent();
    }

    public static void deleteRegion(int regionId, int width, int length) {
        int regionX = regionId >> 8;
        int regionY = regionId & 0xff;
        for (int rX = 0; rX < width; rX++) {
            for (int rY = 0; rY < length; rY++) {
                int newRegionId = ((regionX + rX) << 8) + (regionY + rY);
                Region region = regions.get(newRegionId);
                if (region != null) {
                    region.destroy();
                   // World.getWorld().getGlobalObjects().destroyObjectsAt(region.getBoundary().streamWithHeight().collect(Collectors.toList()));
                }
            }
        }
    }

    public static void loadPalette(io.ruin.model.map.object.Location minimum, Palette palette) {
        byte[][][] tileFlags = new byte[palette.getHeight()][palette.getWidth() * 8][palette.getLength() * 8];
        for(int z = 0;z<palette.getHeight();z++) {
            for(int x = 0;x<palette.getWidth();x++) {
                for(int y = 0;y<palette.getLength();y++) {
                    PaletteTile tile = palette.getTile(x, y, z);
                    if(tile != null) {
                        int tileZ = tile.getZ();
                        int tileX = tile.getX();
                        int tileY = tile.getY();
                        int rotation = tile.getRotation();
                        int regionId = (tileX / 8 << 8) + tileY / 8;
                        Optional<RegionData> data = MapIndexLoader.lookup(regionId);
                        if(data.isPresent()) {
                            ByteStream lsData = MapIndexLoader.getLandscapeData(data);
                            ByteStream objData = MapIndexLoader.getObjectData(data);
                            decodeConstructedMapsLandscape(tileFlags, lsData, tileZ, rotation, x * 8, (tileX & 7) * 8, (tileY & 7) * 8, z, y * 8);
                            decodeConstructedMapObjects(minimum, tileFlags, tileZ, x * 8, (tileY & 7) * 8, z, objData, (tileX & 7) * 8, rotation, y * 8);
                        }
                    }
                }
            }
        }

        for(int z = 0;z<4;z++) {
            for(int x = 0;x<tileFlags[z].length;x++) {
                for(int y = 0;y<tileFlags[z][x].length;y++) {
                    if ((tileFlags[z][x][y] & 1) == 1) {
                        int height = z;
                        if ((tileFlags[1][x][y] & 2) == 2) {
                            height--;
                        }
                        if (height >= 0 && height <= 3) {
                            addClipping(minimum.getX() + x, minimum.getY() + y, height, 0x200000);
                        }
                    }
                }
            }
        }
    }

    public static final void decodeConstructedMapObjects(Location baseLocation, byte[][][] tileFlags, int tileZ, int baseX, int tileY, int plane, ByteStream stream, int tileX, int rotation, int baseY) {
        label0: {
            int objectId = -1;
            do {
                int incr = stream.readUnsignedIntSmartShortCompat();
                if (incr == 0)
                    break label0;
                objectId += incr;
                int location = 0;
                do {
                    int incr2 = stream.getUSmart();
                    if (incr2 == 0)
                        break;
                    location += incr2 - 1;
                    int localY = location & 0x3f;
                    int localX = location >> 6 & 0x3f;
                    int height = location >> 12;
                    int objectData = stream.getUByte();
                    int type = objectData >> 2;
                    int orientation = objectData & 3;
                    if (height == tileZ && localX >= tileX && localX < tileX + 8 && localY >= tileY && localY < tileY + 8) {
                        ObjectDef definition = ObjectDef.forID(objectId);
                        int size1;
                        int size2;
                        if (orientation == 1 || orientation == 3) {
                            size1 = definition.length;//objectSizeY
                            size2 = definition.width;//objectSizeX
                        } else {
                            size1 = definition.width;
                            size2 = definition.length;
                        }
                        int locX = baseX + MapTileUtils.getRotatedMapChunkX(rotation, size2, localX & 7, localY & 7, size1);
                        int locY = baseY + MapTileUtils.getRotatedMapChunkY(localY & 7, size2, rotation, size1, localX & 7);
                        if (locX > 0 && locY > 0 && locX < tileFlags[0].length && locY < tileFlags[0].length) {

                            int l4 = height;
                            if ((tileFlags[1][locX][locY] & 2) == 2)
                                l4--;
                            //if(objectId == 36101 || objectId == 36102)
                            //log.info("Spawning object {} at {} {} {}", objectId, baseLocation.getX() + locX, baseLocation.getY() + locY, l4);

                            addObject(objectId, baseLocation.getX() + locX, baseLocation.getY() + locY, l4, type, orientation + rotation & 3);
                            addWorldObject(objectId, baseLocation.getX() + locX, baseLocation.getY() + locY, l4, type, orientation + rotation & 3);
                        }
                    }
                } while (true);
            } while (true);
        }
    }

    public static void addWorldObject(int id, int x, int y, int height, int type, int face) {
        //log.info("Spawning {} at {}, {}, {}", id, x, y, height);
        Region region = getRegion(x, y);
        if (region == null) {
            return;
        }
        int regionId = region.id;
        if (worldObjects.containsKey(regionId)) {
            if (objectExists(regionId, id, x, y, height, type)) {
                return;
            }
            worldObjects.get(regionId).add(new WorldObject(id, x, y, height, type, face));
        } else {
            ArrayList<WorldObject> object = new ArrayList<>(1);
            object.add(new WorldObject(id, x, y, height, type, face));
            worldObjects.put(regionId, object);
        }
    }

    private static boolean objectExists(int region, int id, int x, int y, int height, int type) {
        List<WorldObject> objects = worldObjects.get(region);
        for (WorldObject object : objects) {
            if (object == null) {
                continue;
            }
            if (object.getId() == id && object.getType() == type && object.getX() == x && object.getY() == y && (object.getHeight() == (height & 3) || object.getHeight() == height)) {
                return true;
            }
        }
        return false;
    }

    public static void addObject(int objectId, int x, int y, int height, int type, int direction) {
        ObjectDef def = ObjectDef.forID(objectId);
        if (def == null) {
            return;
        }
        int xLength;
        int yLength;
        if (direction != 1 && direction != 3) {
            xLength = def.width;
            yLength = def.length;
        } else {
            xLength = def.length;
            yLength = def.width;
        }
        if (type == 22) {
            if (def.hasActions && def.solid) {
                addClipping(x, y, height, 0x200000);
                if (def.impenetrable) {
                    addProjectileClipping(x, y, height, 0x200000);
                }
            }
        } else if (type >= 9) {
            if (def.solid) {
                addClippingForSolidObject(x, y, height, xLength, yLength, def.solid);
                if (def.impenetrable) {
                    addProjectileClippingForSolidObject(x, y, height, xLength, yLength, true);
                }
            }
        } else if (type >= 0 && type <= 3) {
            if (def.solid) {
                addClippingForVariableObject(x, y, height, type, direction, def.solid);
                if (def.impenetrable) {
                    setProjectileClippingForVariableObject(x, y, height, type, direction, def.solid, false);
                }
            }
        }
    }

    private static void setProjectileClippingForVariableObject(int x, int y, int height,
                                                               int type, int direction, boolean flag, boolean negative) {
        if (type == 0) {
            if (direction == 0) {
                addProjectileClipping(x, y, height, negative ? -128 : 128);
                addProjectileClipping(x - 1, y, height, negative ? -8 : 8);
            } else if (direction == 1) {
                addProjectileClipping(x, y, height, negative ? -2 : 2);
                addProjectileClipping(x, y + 1, height, negative ? -32 : 32);
            } else if (direction == 2) {
                addProjectileClipping(x, y, height, negative ? -8 : 8);
                addProjectileClipping(x + 1, y, height, negative ? -128 : 128);
            } else if (direction == 3) {
                addProjectileClipping(x, y, height, negative ? -32 : 32);
                addProjectileClipping(x, y - 1, height, negative ? -2 : 2);
            }
        } else if (type == 1 || type == 3) {
            if (direction == 0) {
                addProjectileClipping(x, y, height, negative ? -1 : 1);
                addProjectileClipping(x - 1, y + 1, height, negative ? -16 : 16);//wrong method217(16, x - 1, y + 1);
            } else if (direction == 1) {
                addProjectileClipping(x, y, height, negative ? -4 : 4);
                addProjectileClipping(x + 1, y + 1, height, negative ? -64 : 64);
            } else if (direction == 2) {
                addProjectileClipping(x, y, height, negative ? -16 : 16);
                addProjectileClipping(x + 1, y - 1, height, negative ? -1 : 1);
            } else if (direction == 3) {
                addProjectileClipping(x, y, height, negative ? -64 : 64);
                addProjectileClipping(x - 1, y - 1, height, negative ? -4 : 4);
            }
        } else if (type == 2) {
            if (direction == 0) {
                addProjectileClipping(x, y, height, 130);
                addProjectileClipping(x - 1, y, height, negative ? -8 : 8);
                addProjectileClipping(x, y + 1, height, negative ? -32 : 32);
            } else if (direction == 1) {
                addProjectileClipping(x, y, height, negative ? -10 : 10);
                addProjectileClipping(x, y + 1, height, negative ? -32 : 32);
                addProjectileClipping(x + 1, y, height, negative ? -128 : 128);
            } else if (direction == 2) {
                addProjectileClipping(x, y, height, negative ? -40 : 40);
                addProjectileClipping(x + 1, y, height, negative ? -128 : 128);
                addProjectileClipping(x, y - 1, height, negative ? -2 : 2);
            } else if (direction == 3) {
                addProjectileClipping(x, y, height, negative ? -160 : 160);
                addProjectileClipping(x, y - 1, height, negative ? -2 : 2);
                addProjectileClipping(x - 1, y, height, negative ? -8 : 8);
            }
        }
        if (flag) {
            if (type == 0) {
                if (direction == 0) {
                    addProjectileClipping(x, y, height, negative ? -0x10000 : 0x10000);
                    addProjectileClipping(x - 1, y, height, negative ? -4096 : 4096);
                } else if (direction == 1) {
                    addProjectileClipping(x, y, height, negative ? -1024 : 1024);
                    addProjectileClipping(x, y + 1, height, negative ? -16384 : 16384);
                } else if (direction == 2) {
                    addProjectileClipping(x, y, height, negative ? -4096 : 4096);
                    addProjectileClipping(x + 1, y, height, negative ? -0x10000 : 0x10000);
                } else if (direction == 3) {
                    addProjectileClipping(x, y, height, negative ? -16384 : 16384);
                    addProjectileClipping(x, y - 1, height, negative ? -1024 : 1024);
                }
            }
            if (type == 1 || type == 3) {
                if (direction == 0) {
                    addProjectileClipping(x, y, height, negative ? -512 : 512);
                    addProjectileClipping(x - 1, y + 1, height, negative ? -8192 : 8192);
                } else if (direction == 1) {
                    addProjectileClipping(x, y, height, negative ? -2048 : 2048);
                    addProjectileClipping(x + 1, y + 1, height, negative ? -32768 : 32768);
                } else if (direction == 2) {
                    addProjectileClipping(x, y, height, negative ? -8192 : 8192);
                    addProjectileClipping(x + 1, y + 1, height, negative ? -512 : 512);
                } else if (direction == 3) {
                    addProjectileClipping(x, y, height, negative ? -32768 : 32768);
                    addProjectileClipping(x - 1, y - 1, height, negative ? -2048 : 2048);
                }
            } else if (type == 2) {
                if (direction == 0) {
                    addProjectileClipping(x, y, height, negative ? -0x10400 : 0x10400);
                    addProjectileClipping(x - 1, y, height, negative ? -4096 : 4096);
                    addProjectileClipping(x, y + 1, height, negative ? -16384 : 16384);
                } else if (direction == 1) {
                    addProjectileClipping(x, y, height, negative ? -5120 : 5120);
                    addProjectileClipping(x, y + 1, height, negative ? -16384 : 16384);
                    addProjectileClipping(x + 1, y, height, negative ? -0x10000 : 0x10000);
                } else if (direction == 2) {
                    addProjectileClipping(x, y, height, negative ? -20480 : 20480);
                    addProjectileClipping(x + 1, y, height, negative ? -0x10000 : 0x10000);
                    addProjectileClipping(x, y - 1, height, negative ? -1024 : 1024);
                } else if (direction == 3) {
                    addProjectileClipping(x, y, height, negative ? -81920 : 81920);
                    addProjectileClipping(x, y - 1, height, negative ? -1024 : 1024);
                    addProjectileClipping(x - 1, y, height, negative ? -4096 : 4096);
                }
            }
        }
    }

    private static void addClippingForVariableObject(int x, int y, int height, int type, int direction, boolean flag) {
        if (type == 0) {
            if (direction == 0) {
                addClipping(x, y, height, 128);
                addClipping(x - 1, y, height, 8);
            } else if (direction == 1) {
                addClipping(x, y, height, 2);
                addClipping(x, y + 1, height, 32);
            } else if (direction == 2) {
                addClipping(x, y, height, 8);
                addClipping(x + 1, y, height, 128);
            } else if (direction == 3) {
                addClipping(x, y, height, 32);
                addClipping(x, y - 1, height, 2);
            }
        } else if (type == 1 || type == 3) {
            if (direction == 0) {
                addClipping(x, y, height, 1);
                addClipping(x - 1, y, height, 16);
            } else if (direction == 1) {
                addClipping(x, y, height, 4);
                addClipping(x + 1, y + 1, height, 64);
            } else if (direction == 2) {
                addClipping(x, y, height, 16);
                addClipping(x + 1, y - 1, height, 1);
            } else if (direction == 3) {
                addClipping(x, y, height, 64);
                addClipping(x - 1, y - 1, height, 4);
            }
        } else if (type == 2) {
            if (direction == 0) {
                addClipping(x, y, height, 130);
                addClipping(x - 1, y, height, 8);
                addClipping(x, y + 1, height, 32);
            } else if (direction == 1) {
                addClipping(x, y, height, 10);
                addClipping(x, y + 1, height, 32);
                addClipping(x + 1, y, height, 128);
            } else if (direction == 2) {
                addClipping(x, y, height, 40);
                addClipping(x + 1, y, height, 128);
                addClipping(x, y - 1, height, 2);
            } else if (direction == 3) {
                addClipping(x, y, height, 160);
                addClipping(x, y - 1, height, 2);
                addClipping(x - 1, y, height, 8);
            }
        }
        if (flag) {
            if (type == 0) {
                if (direction == 0) {
                    addClipping(x, y, height, 65536);
                    addClipping(x - 1, y, height, 4096);
                } else if (direction == 1) {
                    addClipping(x, y, height, 1024);
                    addClipping(x, y + 1, height, 16384);
                } else if (direction == 2) {
                    addClipping(x, y, height, 4096);
                    addClipping(x + 1, y, height, 65536);
                } else if (direction == 3) {
                    addClipping(x, y, height, 16384);
                    addClipping(x, y - 1, height, 1024);
                }
            }
            if (type == 1 || type == 3) {
                if (direction == 0) {
                    addClipping(x, y, height, 512);
                    addClipping(x - 1, y + 1, height, 8192);
                } else if (direction == 1) {
                    addClipping(x, y, height, 2048);
                    addClipping(x + 1, y + 1, height, 32768);
                } else if (direction == 2) {
                    addClipping(x, y, height, 8192);
                    addClipping(x + 1, y + 1, height, 512);
                } else if (direction == 3) {
                    addClipping(x, y, height, 32768);
                    addClipping(x - 1, y - 1, height, 2048);
                }
            } else if (type == 2) {
                if (direction == 0) {
                    addClipping(x, y, height, 66560);
                    addClipping(x - 1, y, height, 4096);
                    addClipping(x, y + 1, height, 16384);
                } else if (direction == 1) {
                    addClipping(x, y, height, 5120);
                    addClipping(x, y + 1, height, 16384);
                    addClipping(x + 1, y, height, 65536);
                } else if (direction == 2) {
                    addClipping(x, y, height, 20480);
                    addClipping(x + 1, y, height, 65536);
                    addClipping(x, y - 1, height, 1024);
                } else if (direction == 3) {
                    addClipping(x, y, height, 81920);
                    addClipping(x, y - 1, height, 1024);
                    addClipping(x - 1, y, height, 4096);
                }
            }
        }
    }

    private static void addProjectileClippingForSolidObject(int x, int y, int height,
                                                            int xLength, int yLength, boolean flag) {
        int clipping = 256;
        if (flag) {
            clipping += 0x20000;
        }
        for (int i = x; i < x + xLength; i++) {
            for (int i2 = y; i2 < y + yLength; i2++) {
                addProjectileClipping(i, i2, height, clipping);
            }
        }
    }

    private static void addClippingForSolidObject(int x, int y, int height, int xLength, int yLength, boolean flag) {
        int clipping = 256;
        if (flag) {
            clipping += 0x20000;
        }
        for (int i = x; i < x + xLength; i++) {
            for (int i2 = y; i2 < y + yLength; i2++) {
                addClipping(i, i2, height, clipping);
            }
        }
    }

    private static void addProjectileClipping(int x, int y, int height, int shift) {
        Region region = getRegion(x, y);
        if (region != null) {
            if (shift > 0) {
                region.addProjectileClip(x, y, height, shift);
            } else {
                region.removeProjectileClip(x, y, height, shift);
            }
        }
    }

    private Map<Integer, int[][]> shootable = Maps.newConcurrentMap();

    private void addProjectileClip(int x, int y, int height, int shift) {
        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (!shootable.containsKey(height)) {
            shootable.put(height, new int[64][64]);
        }
        shootable.get(height)[x - regionAbsX][y - regionAbsY] |= shift;
    }

    private void removeProjectileClip(int x, int y, int height, int shift) {
        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (shootable.get(height) == null) {
            shootable.put(height, new int[64][64]);
        }
        shootable.get(height)[x - regionAbsX][y - regionAbsY] += shift;
    }

    public static final void decodeConstructedMapsLandscape(byte[][][] tileFlags, ByteStream stream, int tileZ, int rotation, int baseX, int tileX, int tileY, int plane, int baseY) {
        for (int l2 = 0; l2 < 4; l2++) {
            for (int i3 = 0; i3 < 64; i3++) {
                for (int j3 = 0; j3 < 64; j3++)
                    if (l2 == tileZ && i3 >= tileX && i3 < tileX + 8 && j3 >= tileY && j3 < tileY + 8 )
                        decodeLandscape(tileFlags, baseY + MapTileUtils.getRotatedLandscapeChunkY(j3 & 7, rotation, i3 & 7), 0, stream,
                                baseX + MapTileUtils.getRotatedLandscapeChunkX(rotation, j3 & 7, i3 & 7), plane, rotation, 0);
                    else
                        decodeLandscape(tileFlags, -1, 0, stream, -1, 0, 0, 0);
            }
        }
    }
    private static void decodeLandscape(byte[][][] tileFlags, int i, int j, ByteStream stream, int k, int l, int i1, int baseX) {
        try {
            if (k >= 0 && k < tileFlags[0].length && i >= 0 && i < tileFlags[0].length) {
                int absX = (baseX + k);
                int absY = (j + i);
                int absZ = (i1 + l);
                tileFlags[l][k][i] = 0;
                do {
                    int l1 = stream.getUByte();
                    if (l1 == 0)
                        if (l == 0) {
                            return;
                        } else {

                            return;
                        }
                    if (l1 == 1) {
                        int j2 = stream.getUByte();
                        if (j2 == 1)
                            j2 = 0;
                        if (l == 0) {
                            return;
                        } else {
                            return;
                        }
                    }

                    if (l1 <= 49) {

                    } else if (l1 <= 81)
                        tileFlags[l][k][i] = (byte) (l1 - 49);
                } while (true);
            }
            do {
                int i2 = stream.getUByte();
                if (i2 == 0)
                    break;
                if (i2 == 1) {
                    stream.getUByte();
                    return;
                }
                if (i2 <= 49)
                    stream.getUByte();
            } while (true);
        } catch (Exception e) {
        }
    }

    private static void addClipping(int x, int y, int height, int shift) {
        Region region = getRegion(x, y);
        if(region != null) {
            if(shift > 0) {
                region.addClip(x, y, height, shift);
            } else {
                region.removeClip(x, y, height, shift);
            }
        }
    }

    private Map<Integer, int[][]> clips = Maps.newConcurrentMap();

    private void removeClip(int x, int y, int height, int shift) {
        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (clips.get(height) == null) {
            clips.put(height, new int[64][64]);
        }
        clips.get(height)[x - regionAbsX][y - regionAbsY] += shift;
    }

    private void addClip(int x, int y, int height, int shift) {
        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (clips.get(height) == null) {
            clips.put(height, new int[64][64]);
        }
        clips.get(height)[x - regionAbsX][y - regionAbsY] |= shift;
    }
}