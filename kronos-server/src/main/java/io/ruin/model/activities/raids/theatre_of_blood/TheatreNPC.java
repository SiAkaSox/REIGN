package io.ruin.model.activities.raids.theatre_of_blood;

import com.google.common.collect.Lists;
import io.ruin.model.World;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.SpawnListeners;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.object.GameObject;

import java.util.ArrayList;
import java.util.List;

public abstract class TheatreNPC extends NPC {

    private List<TheatreNPC> nodes;

    protected Position base;

    public TheatreNPC(int id, Position base) {
        super(id);
        this.base = base.copy();
        init();
    }

    public void destroy() {
        remove();
    }

    @Override
    public boolean inMulti() {
        return true;
    }

    public ArrayList<Entity> getPossibleTargets() {
        return getPossibleTargets(24, true, false);
    }

    public ArrayList<Entity> getPossibleTargets(int ratio, boolean players, boolean npcs) {
        List<Entity> possibleTargets = Lists.newArrayList();
        if (players) {
            for (Player player : World.players) {

                if (player == null)
                    continue;

                boolean tobDead = false;

                if (TheatrePartiesManager.getRaid(player.getPosition()) != null) {
                    tobDead = TheatrePartiesManager.getRaid(player.getPosition()).isDead(player);
                }

                if (tobDead || player.getCombat().isDead() || player.getPosition().distance(this.getCentrePosition()) > ratio) {
                    continue;
                }

                possibleTargets.add(player);
            }
        }
        if (npcs) {
            for (NPC npc : World.npcs) {
                if (npc == null || npc == this || npc.getCombat() == null || npc.getCombat().isDead() || npc.getCentrePosition().distance(this.getCentrePosition()) > ratio) {
                    continue;
                }
                possibleTargets.add(npc);
            }
        }
        return (ArrayList<Entity>) possibleTargets;
    }

    public Position getSpawnLocation() {
        return base;
    }

    public void register() {
        spawn(base.copy());
    }

    @Override
    public NPC spawn(int x, int y, int z, Direction spawnDirection, int walkRange) {
        init();
        this.position = new Position(x, y, z);
        this.lastPosition = position.copy();
        this.spawnPosition = new Position(x, y, z);
        this.spawnDirection = spawnDirection;
        this.walkRange = walkRange;
        if(walkRange > 0)
            walkBounds = new Bounds(spawnPosition, walkRange);
        if(setCombat()) {
            attackBounds = new Bounds(spawnPosition, getCombat().getAttackBoundsRange());
            getCombat().customAttackBounds();
        }
        setIndex(World.npcs.add(this, 0));
        Tile.occupy(this);
        this.setHp(3_500);
        this.setMaxHp(3_500);
        SpawnListeners.INSTANCE.runFor(this);
        return this;
    }

    public List<TheatreNPC> getNodes() {
        return nodes;
    }

    public void setNodes(List<TheatreNPC> nodes) {
        this.nodes = nodes;
        if (!nodes.contains(this)) {
            nodes.add(this);
        }
    }
}
