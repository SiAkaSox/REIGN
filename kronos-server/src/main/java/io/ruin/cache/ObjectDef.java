package io.ruin.cache;

import com.google.common.collect.Maps;
import io.ruin.Server;
import io.ruin.network.io.InBuffer;
import io.ruin.cache.filestore.IndexFile;
import io.ruin.utility.StringUtils;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static io.ruin.cache.ObjectID.PORTAL_OF_CHAMPIONS;

public class ObjectDef {

    public static boolean aBool1550 = false;
    private static byte[] EMPTY_BUFFER = new byte[]{0};

    public static Map<Integer, ObjectDef> LOADED = Maps.newConcurrentMap();
    public static ObjectDef[] LOADED_EXTRA = new ObjectDef[10];

    @SuppressWarnings("Duplicates")
    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        int size = index.getLastFileId(6) + 1;
        for (int id = 0; id < size; id++) {
            byte[] data = index.getFile(6, id);
            if (data != null) {
                ObjectDef def = new ObjectDef();
                def.id = id;
                def.decode(new InBuffer(data));
                if (def.hollow) {
                    def.interactType = 0;
                    def.blockProjectile = false;
                }
                if (StringUtils.vowelStart(def.name))
                    def.descriptiveName = "an " + def.name;
                else
                    def.descriptiveName = "a " + def.name;
                LOADED.put(id, def);
            }
        }
    }

    public static void forEach(Consumer<ObjectDef> consumer) {
        for (ObjectDef def : LOADED.values()) {
            if (def != null)
                consumer.accept(def);
        }
    }

    public static ObjectDef get(int id) {
        return LOADED.get(id);
    }


    /**
     * Custom data
     */

    public String descriptiveName;

    public ObjectAction[] defaultActions;

    public HashMap<Integer, ItemObjectAction> itemActions;

    public ItemObjectAction defaultItemAction;

    public boolean bank;

    /**
     * Door data
     */

    public boolean gateType;

    public boolean longGate;

    public int doorOppositeId = -1;

    public boolean doorReversed, doorClosed;

    public int doorOpenSound = -1, doorCloseSound = -1;

    public boolean reversedConstructionDoor;

    /**
     * Separator
     */

    public int id;
    public int wallOrDoor = -1;
    public int supportsItems = -1;
    public String name = "null";
    public int xLength = 1;
    public int yLength = 1;
    public int interactType = 2;
    public boolean blockProjectile = true;
    public boolean modelClipped = false;
    public int animationId = -1; // idle animation
    public int decorDisplacement = 16;
    public int mapMarkerId = -1;
    public boolean shadow = true;
    public int spriteId = -1;
    public boolean obstructsGround = false;
    public int ambientSoundId = -1;
    public int ambientSoundDistance = 0;
    public int ambientSoundChangeTicksMin = 0;
    public int ambientSoundChangeTicksMax = 0;
    public int[] ambientSoundIds;
    public int[] showIds;
    public String[] options = new String[5];
    public int[] modelTypes;
    public int[] modelIds;
    int countouredGround = -1;
    boolean mergeNormals = false;
    int ambient = 0;
    int contrast = 0;
    public short[] originalModelColors;
    public short[] modifiedModelColors;
    short[] retextureToFind;
    short[] textureToReplace;
    public boolean rotated = false;
    public int modelSizeX = 128;
    public int modelSizeHeight = 128;
    public int modelSizeY = 128;
    int offsetX = 0;
    int offsetHeight = 0;
    int offsetY = 0;
    public boolean hollow = false;
    public int varpBitId = -1;
    public int varpId = -1;
    public int randomizeAnimStart;
    public int mapAreaId;

    public int blockingMask;
    public Map<Integer, Object> params;

    private void decode(InBuffer in) {
        for (; ; ) {
            int opcode = in.readUnsignedByte();
            if (opcode == 0)
                break;
            decode(in, opcode);
        }
        if (id == ObjectID.GRAND_EXCHANGE_BOOTH) {
            name = "Trading Post";
            options[0] = "Open";
            options[1] = "Coffer";
            options[2] = "Guide";
        } else if (id == 50001) {
            //custom home altar
            options[0] = "Pray-at";
            options[1] = "Spellbook";
        } else if (id == 33523) {
            options[0] = "Use";
            options[1] = "Exchange";
        } else if (id == 23311) {
            //custom home altar
            options[0] = "Teleport";
            options[1] = "Previous Teleport";
        } else if (id == 11833) {
            //fight caves entrance
            options[1] = "Practice";
        } else if (id == 32758) {
            //rfd chest
            name = "Loyalty Chest";
            options[0] = "Loot";
            options[1] = "About";
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if (id == 32759) {
            name = "Loyalty Chest";
            options[0] = null;
            options[1] = null;
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if (id == 40449) {
            name = "Alternative Reality";
            options[0] = "Enter";
            options[1] = null;
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if (id == 41730) {
            name = "Scoreboard";
            options[0] = "Edge PvP";
            options[1] = "Deep Wild PvP";
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if (id == 10061) {
            options[0] = "Open";
        } else if (id == 31379) {
            //uhh i hope 31379 isn't ever used
            name = "Donation table";
            wallOrDoor = 1;
            xLength = yLength = 2;
            obstructsGround = false;
            supportsItems = 1;
            showIds = null;
            modelIds = new int[]{32153};
            varpBitId = -1;
        } else if (id == 31380) {
            //uhh i hope 31380 isn't ever used
            name = "Rejuvenation pool";
            wallOrDoor = 1;
            xLength = yLength = 2;
            animationId = 7304;
            ambientSoundDistance = 3;
            blockProjectile = false;
            options[0] = "Drink";
            ambientSoundId = 2149;
            interactType = 1;
            obstructsGround = false;
            showIds = null;
            ambient = 40;
            modelIds = new int[]{32101};
            varpBitId = -1;
        } else if (id == PORTAL_OF_CHAMPIONS) {
            name = "Teleporter";
            options[0] = "Teleport";
            options[1] = "Teleport-previous";
        } else if (id == 25203) {
            //decapitated elvarg corpse
            options[0] = "Loot";
        } else if (id == 29226) {
            //pet list
            name = "Pet list";
            options[4] = null;
        } else if (id == 6045) {
            //jail mine cart
            options[0] = "Dump-ore";
        } else if (id == 31622) {
            name = "Tournament Portal";
        } else if (id == 3581) {
            //tournament ticket exchange
            name = "Ticket exchange";
            options[0] = "Use";
        } else if (id == 15477) {
            //raids mumbo jumbo
            name = "Chambers of Xeric";
            options[1] = null;
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if (id == 26714) {
            //mounted max cape
            options[0] = options[3] = null;
        } else if (id == 11508 || id == 11509) {
            //curtain
            interactType = 0;
        } else if (id == 30352) {
            name = "test";
            options[1] = "Practice";
        } else if (id == 539) {
            name = "Donator Area";
        } else if (id == 33114) {
            name = "PvP Supply Chest";
            options[0] = "Check-timer";
        } else if (id == 33115) {
            name = "PvP Supply Chest";
        } else if (id == 31583) {
            name = "PvP Supply Chest";
        } else if (id == 32572) {
            name = "Bloody Chest";
            options[1] = "Information";
        } else if (id == 31621) {
            name = "Wilderness Portal";
        } else if (id == 31626) {
            name = "Tournament Entrance";
        } else if (id == 32573) {
            name = "Bloody Chest";
            options[0] = null;
            options[1] = null;
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if (id == 30169) { // Dagannoth kings crack
            options[0] = "Instance";
            options[1] = "Peek";
        } else if (id == 1816) { // KBD Lever
            options[1] = "Instance";
            options[2] = "Commune";
        } else if (id == 535) { // Thermonuclear smoke devil crevice
            options[1] = "Instance";
            options[2] = "Peek";
        } else if (id == 23104) { // Cerberus iron winch
            options[1] = "Instance";
            options[2] = "Peek";
        } else if (id == 29705) { // KQ Crack
            options[0] = "Instance";
            options[1] = "Peek";
        } else if (id >= 26502 && id <= 26505) { // GWD boss doors
            options[1] = "Instance";
            options[2] = "Peek";
        } else if (id == 4407) { // pvm instance portal
            name = "Boss instance portal";
            options[0] = "Use";
        } else if (id == 19038) { //christmas tree
            name = "Christmas tree";
            options[0] = "Grab-present";
        } else if (id == 29709) {
            name = "Snowball Exchange";
            options[0] = "Open";
            options[1] = "Information";
        } else if (id == 40000) {
            copy(32546);
            name = "Giveaway booth";
        } else if (id == 40001) {
            copy(4525);
            options[0] = "Exit";
            options[1] = null;
            options[2] = null;
        } else if (id == 40002) {
            copy(32424);
            name = "Consumables";
        } else if (id == 40003) {
            copy(32425);
            name = "Equipment";
        } else if (id == 2654) {
            name = "Bloody Fountain";
            options[0] = "Drink";
            options[1] = null;
            modelSizeHeight = 200;
            modelSizeX = 200;
            modelSizeY = 200;
            xLength = 3;
            yLength = 3;
        } else if (id == 40004) {
            copy(29241);
            options[4] = null;
        } else if (id == 40005) {
            copy(31858);
            options[0] = "Pray-at";
            options[1] = "Spellbook";
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if (id == 40006) {
            copy(4537);
            options[4] = null;
        } else if (id == 40007) {
            copy(13619);
            name = "Fun PVP Portal";
            options[4] = null;
        } else if (id == 40008) {
            copy(20839);
            name = "Tournament Barrier";
            options[0] = "Use";
        } else if (id == 40009) {
            copy(26714);
            name = "Mounted Max Cape";
            options[0] = null;
            options[1] = null;
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if (id == 31846) {
            name = "Tournament Information";
            options[0] = "Read";
        } else if (id == 29087) {
            name = "Ticket Exchange";
            options[0] = "Use";
        } else if (id == 4451 || id == 14321 || id == 14329 || id == 14324 || id == 14333) { // Asset non-projectile clip for pc portal decortations.
            interactType = 0;
            blockProjectile = false;
        }
    }

    private void decode(InBuffer buffer, int opcode) {
        if (opcode == 1) {
            int length = buffer.readUnsignedByte();
            if (length > 0) {
                if (modelIds == null || aBool1550) {
                    modelTypes = new int[length];
                    modelIds = new int[length];
                    for (int index = 0; index < length; index++) {
                        modelIds[index] = buffer.readUnsignedShort();
                        modelTypes[index] = buffer.readUnsignedByte();
                    }
                } else
                    buffer.skip(length * 3);
            }
        } else if (opcode == 2) {
            name = buffer.readString();
        } else if (opcode == 5) {
            int length = buffer.readUnsignedByte();
            if (length > 0) {
                if (modelIds == null || aBool1550) {
                    modelTypes = null;
                    modelIds = new int[length];
                    for (int index = 0; index < length; index++)
                        modelIds[index] = buffer.readUnsignedShort();
                } else buffer.skip(length * 2);
            }
        } else if (opcode == 14)
            xLength = buffer.readUnsignedByte();
        else if (opcode == 15)
            yLength = buffer.readUnsignedByte();
        else if (opcode == 17) {
            interactType = 0;
            blockProjectile = false;
        } else if (opcode == 18)
            blockProjectile = false;
        else if (opcode == 19)
            wallOrDoor = buffer.readUnsignedByte();//type22int
        else if (opcode == 21)
            countouredGround = 0;
        else if (opcode == 22)
            mergeNormals = true;
        else if (opcode == 23)
            modelClipped = true;
        else if (opcode == 24) {
            animationId = buffer.readUnsignedShort();
            if (animationId == 65535)
                animationId = -1;
        } else if (opcode == 27)
            interactType = 1;
        else if (opcode == 28)
            decorDisplacement = buffer.readUnsignedByte();
        else if (opcode == 29)
            ambient = buffer.readByte();
        else if (opcode == 39)
            contrast = buffer.readByte() * 25;
        else if (opcode >= 30 && opcode < 35) {
            options[opcode - 30] = buffer.readString();
            if (options[opcode - 30].equalsIgnoreCase("Hidden"))
                options[opcode - 30] = null;
        } else if (opcode == 40) {
            int length = buffer.readUnsignedByte();
            originalModelColors = new short[length];
            modifiedModelColors = new short[length];
            for (int index = 0; index < length; index++) {
                originalModelColors[index] = (short) buffer.readUnsignedShort();
                modifiedModelColors[index] = (short) buffer.readUnsignedShort();
            }
        } else if (opcode == 41) {
            int length = buffer.readUnsignedByte();
            retextureToFind = new short[length];
            textureToReplace = new short[length];
            for (int index = 0; index < length; index++) {
                retextureToFind[index] = (short) buffer.readUnsignedShort();
                textureToReplace[index] = (short) buffer.readUnsignedShort();
            }
        } else if (opcode == 60) //this was removed
            mapMarkerId = buffer.readUnsignedShort();
        else if (opcode == 61) // category
            buffer.readUnsignedShort();
        else if (opcode == 62)
            rotated = true;
        else if (opcode == 64)
            shadow = false;
        else if (opcode == 65)
            modelSizeX = buffer.readUnsignedShort();
        else if (opcode == 66)
            modelSizeHeight = buffer.readUnsignedShort();
        else if (opcode == 67)
            modelSizeY = buffer.readUnsignedShort();
        else if (opcode == 68)
            spriteId = buffer.readUnsignedShort();
        else if (opcode == 69)
            blockingMask = buffer.readUnsignedByte();
        else if (opcode == 70)
            offsetX = buffer.readShort();
        else if (opcode == 71)
            offsetHeight = buffer.readShort();
        else if (opcode == 72)
            offsetY = buffer.readShort();
        else if (opcode == 73)
            obstructsGround = true;//type22boolean
        else if (opcode == 74)
            hollow = true;
        else if (opcode == 75)
            supportsItems = buffer.readUnsignedByte();
        else if (opcode == 77 || opcode == 92) {
            varpBitId = buffer.readUnsignedShort();
            if (varpBitId == 65535)
                varpBitId = -1;
            varpId = buffer.readUnsignedShort();
            if (varpId == 65535)
                varpId = -1;
            int i_17_ = -1;
            if (opcode == 92) {
                i_17_ = buffer.readUnsignedShort();
                if (i_17_ == 65535)
                    i_17_ = -1;
            }
            int i_18_ = buffer.readUnsignedByte();
            showIds = new int[i_18_ + 2];
            for (int i_19_ = 0; i_19_ <= i_18_; i_19_++) {
                showIds[i_19_] = buffer.readUnsignedShort();
                if (showIds[i_19_] == 65535)
                    showIds[i_19_] = -1;
            }
            showIds[i_18_ + 1] = i_17_;
        } else if (opcode == 78) {
            ambientSoundId = buffer.readUnsignedShort();
            ambientSoundDistance = buffer.readUnsignedByte();
        } else if (opcode == 79) {
            ambientSoundChangeTicksMin = buffer.readUnsignedShort();
            ambientSoundChangeTicksMax = buffer.readUnsignedShort();
            ambientSoundDistance = buffer.readUnsignedByte();
            int length = buffer.readUnsignedByte();
            ambientSoundIds = new int[length];
            for (int index = 0; index < length; index++)
                ambientSoundIds[index] = buffer.readUnsignedShort();
        } else if (opcode == 81)
            countouredGround = buffer.readUnsignedByte() * 256;
        else if (opcode == 82)
            mapAreaId = buffer.readUnsignedShort(); //mapAreaId
        else if (opcode == 89)
            buffer.readUnsignedShort();
        else if (opcode == 249) {
            int size = buffer.readUnsignedByte();
            if (params == null) {
                params = Maps.newConcurrentMap();
            }
            for (int index = 0; index < size; ++index) {
                boolean isString = buffer.readUnsignedByte() == 1;
                int key = buffer.readMedium();
                if (isString) {
                    params.put(key, buffer.readString());
                } else {
                    params.put(key, buffer.readInt());
                }
            }
        } else {
            int[] ignore = new int[]{9, 35, 84, 101, 108, 111, 112, 114, 116, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 146, 147, 162, 164, 165, 214};
            boolean ignoreWarning = false;
            for (int i : ignore) {
                if (opcode == i)
                    ignoreWarning = true;
            }
            if (!ignoreWarning)
                throw new RuntimeException("Error.. missing opcode " + opcode + " for cache id="+id+" Object Definitions!");
        }
    }


    public boolean isClippedDecoration() {
        return wallOrDoor != 0 || interactType == 1 || obstructsGround;
    }

    public boolean hasOption(String... searchOptions) {
        return getOption(searchOptions) != -1;
    }

    public int getOption(String... searchOptions) {
        if (options != null) {
            for (String s : searchOptions) {
                for (int i = 0; i < options.length; i++) {
                    String option = options[i];
                    if (s.toLowerCase().equalsIgnoreCase(option))
                        return i + 1;
                }
            }
        }
        return -1;
    }

    private void copy(int id) {
        if (this.id < id) {
            System.err.println("Unable to copy Object where target has lower id.");
            return;
        }

        ObjectDef from = LOADED.get(id);

        try {
            wallOrDoor = from.wallOrDoor;
            supportsItems = from.supportsItems;
            name = from.name;
            xLength = from.xLength;
            yLength = from.yLength;
            interactType = from.interactType;
            blockProjectile = from.blockProjectile;
            modelClipped = from.modelClipped;
            animationId = from.animationId;
            decorDisplacement = from.decorDisplacement;
            mapMarkerId = from.mapMarkerId;
            shadow = from.shadow;
            spriteId = from.spriteId;
            obstructsGround = from.obstructsGround;
            ambientSoundId = from.ambientSoundId;
            ambientSoundDistance = from.ambientSoundDistance;
            ambientSoundChangeTicksMin = from.ambientSoundChangeTicksMin;
            ambientSoundChangeTicksMax = from.ambientSoundChangeTicksMax;
            ambientSoundIds = from.ambientSoundIds == null ? null : Arrays.copyOf(from.ambientSoundIds, from.ambientSoundIds.length);
            showIds = from.showIds == null ? null : Arrays.copyOf(from.showIds, from.showIds.length);
            options = from.options == null ? null : Arrays.copyOf(from.options, from.options.length);
            modelTypes = from.modelTypes == null ? null : Arrays.copyOf(from.modelTypes, modelTypes.length);
            modelIds = from.modelIds == null ? null : Arrays.copyOf(from.modelIds, from.modelIds.length);
            countouredGround = from.countouredGround;
            mergeNormals = from.mergeNormals;
            ambient = from.ambient;
            contrast = from.contrast;
            originalModelColors = from.originalModelColors == null ? null : Arrays.copyOf(from.originalModelColors, from.originalModelColors.length);
            modifiedModelColors = from.modifiedModelColors == null ? null : Arrays.copyOf(from.modifiedModelColors, from.modifiedModelColors.length);
            retextureToFind = from.retextureToFind == null ? null : Arrays.copyOf(from.retextureToFind, from.retextureToFind.length);
            textureToReplace = from.textureToReplace == null ? null : Arrays.copyOf(from.textureToReplace, from.textureToReplace.length);
            rotated = from.rotated;
            modelSizeX = from.modelSizeX;
            modelSizeHeight = from.modelSizeHeight;
            modelSizeY = from.modelSizeY;
            offsetX = from.offsetX;
            offsetHeight = from.offsetHeight;
            offsetY = from.offsetY;
            hollow = from.hollow;
            varpBitId = from.varpBitId;
            varpId = from.varpId;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
