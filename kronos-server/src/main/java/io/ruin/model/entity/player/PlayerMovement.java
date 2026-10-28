package io.ruin.model.entity.player;

import io.ruin.model.combat.SetEffect;
import io.ruin.model.content.accountperks.Perk;
import io.ruin.model.content.accountperks.PerkManager;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.Movement;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.stat.StatType;
import io.ruin.process.event.EventConsumer;

import java.util.List;

import static io.ruin.model.map.Direction.getDirection;

public class PlayerMovement extends Movement {

    private Player player;

    public int followX = -1, followY = -1;

    public int lastFollowX = -1, lastFollowY = -1;

    public Player following;

    public PlayerMovement(Player player) {
        this.player = player;
    }

    /**
     * Energy
     */

    private void restoreEnergy() {
        if (player.energyUnits >= 10000) {
            return;
        }
        int oldEnergy = getEnergy();
        double restore = 12 + (player.getStats().get(StatType.Agility).currentLevel / 6);
        if (player.wildernessLevel > 0 && player.beginnerParkourEnergyBoost && player.storeAmountSpent < 50)
            restore *= 1.25;
        if (SetEffect.GRACEFUL.hasPieces(player))
            restore *= 1.3;
        if (player.wildernessLevel > 0 && player.storeAmountSpent >= 50) {
            restore *= 100;
        }
        player.energyUnits = Math.min(player.energyUnits + restore, 10000);
        sendEnergy(oldEnergy);
    }

    public void restoreEnergy(int percent) {
        int oldEnergy = getEnergy();
        double restore = percent * 100D;
        player.energyUnits = Math.min(player.energyUnits + restore, 10000);
        sendEnergy(oldEnergy);
    }

    public void drainEnergy(int percent) {
        int oldEnergy = getEnergy();
        double drain = percent * 100D;
        player.energyUnits = Math.max(0, player.energyUnits - drain);
        sendEnergy(oldEnergy);
    }

    private void drainEnergy() {
        int oldEnergy = getEnergy();
        double weight = player.getInventory().weight + player.getEquipment().weight;
        if (weight < 0D)
            weight = 0D;
        else if (weight > 64D)
            weight = 64D;
        double drain = ((weight / 100D) + 0.15) * 100D;
        if (player.staminaTicks > 0) {
            /* reduces drain by 70% */
            drain *= 0.30;
        }
        if (player.morrigansAxeSpecial.isDelayed()) {
            drain *= 6;
        }
        boolean perk = PerkManager.isActive(player, Perk.CARDIO_MASTER);
        if (!perk) {
            player.energyUnits -= drain;
        }
        if (player.energyUnits <= 0) {
            player.energyUnits = -100;
            Config.RUNNING.set(player, 0);
            ctrlRun = false;
        }
        if (!perk) {
            sendEnergy(oldEnergy);
        }
    }

    public void sendEnergy(int oldEnergy) {
        int newEnergy = getEnergy();
        if (newEnergy == oldEnergy)
            return;
        player.getPacketSender().sendRunEnergy(newEnergy);
    }

    private int getEnergy() {
        int percent = (int) Math.ceil(player.energyUnits / 100D);
        return Math.max(0, percent);
    }

    /**
     * Running
     */

    private boolean ctrlRun;

    public void toggleRunning() {
        if (Config.RUNNING.toggle(player) == 1 && player.energyUnits <= 0) {
            Config.RUNNING.set(player, 0);
            player.sendMessage("You don't have enough run energy left to run!");
        }
    }

    public void setCtrlRun(boolean ctrlRun) {
        this.ctrlRun = ctrlRun;
    }

    private boolean isRunning() {
        return player.energyUnits > 0 && (ctrlRun || Config.RUNNING.get(player) == 1);
    }

    /**
     * Misc methods
     */

    public void force(int stepsX, int stepsY, int speed1, int speed2, Direction faceDirection) {
        player.resetActions(false, true, true);
        force(stepsX, stepsY, 0, 0, speed1, speed2, faceDirection);
    }

    public void force(int diffX1, int diffY1, int diffX2, int diffY2, int speed1, int speed2, Direction faceDirection) {
        Position pos = player.getPosition();
        teleport(pos.getX() + (diffX1 + diffX2), pos.getY() + (diffY1 + diffY2), pos.getZ());
        player.forceMovementUpdate.set(-diffX1, -diffY1, -diffX2, -diffY2, speed1, speed2, faceDirection.clientValue);
    }

    public void outOfReach() {
        player.privateSound(154);
        player.sendMessage("I can't reach that!");
    }

    /**
     * Processing
     */

    public void process() {
        handleSpectating();
        player.updateLastPosition();
        walkDirection = runDirection = -1;
        if (player.isVisibleInterface(Interface.WORLD_MAP))
            player.getPacketSender().sendClientScript(1749, "c", player.getPosition().getTileHash());
        if (finishTeleport(player.getPosition())) {
            if (!player.forceMovementUpdate.updated)
                player.teleportModeUpdate.set(127);
            else
                player.movementModeUpdate.set(1);
            followX = followY = -1;
            lastFollowX = lastFollowY = -1;
            player.getPosition().getTile().checkTriggers(player);
        } else {
            if (following != null) {
                if (!following.isOnline() || !player.isLocal(following)) {
                    player.faceNone(false);
                    following = null;
                } else {
                    int destX, destY;
                    if (following.processed && following.getMovement().hasMoved()) {
                        destX = following.getMovement().lastFollowX;
                        destY = following.getMovement().lastFollowY;
                    } else {
                        destX = following.getMovement().followX;
                        destY = following.getMovement().followY;
                    }
                    if (destX == -1 || destY == -1)
                        player.getRouteFinder().routeEntity(following);
                    else if (player.getAbsX() != destX || player.getAbsY() != destY)
                        player.getRouteFinder().routeAbsolute(destX, destY, false);
                }
            }
            if (!step(player)) {
                restoreEnergy();
                return;
            }
            boolean forceRun = stepType == StepType.FORCE_RUN;
            boolean ran = (forceRun || (isRunning() && stepType != StepType.FORCE_WALK)) && step(player);
            int diffX = player.getPosition().getX() - player.getLastPosition().getX();
            int diffY = player.getPosition().getY() - player.getLastPosition().getY();
            if (ran) {
                runDirection = getRunDirection(diffX, diffY);
                if (runDirection == -1)
                    walkDirection = getWalkDirection(diffX, diffY);
                else if (!forceRun)
                    drainEnergy();
                player.movementModeUpdate.set(2);
            } else {
                restoreEnergy();
                walkDirection = getWalkDirection(diffX, diffY);
                player.movementModeUpdate.set(1);
            }
            lastFollowX = followX;
            lastFollowY = followY;
            followX = player.getLastPosition().getX();
            followY = player.getLastPosition().getY();
            if (diffX >= 2)
                followX++;
            else if (diffX <= -2)
                followX--;
            if (diffY >= 2)
                followY++;
            else if (diffY <= -2)
                followY--;
            player.getPosition().getTile().checkTriggers(player);
        }
        player.getUpdater().updateRegion = player.getPosition().updateRegion();
        player.checkMulti();
        Tile.occupy(player);
    }

    private void handleSpectating() {
        if (player.spectators == null)
            return;
        List<Player> spectators = player.spectators;
        int x = player.getAbsX();
        int y = player.getAbsY();
        for (Player sp : spectators) {
            if (sp == null) {
                continue;
            }
            sp.getRouteFinder().routeAbsolute(x, y);
            sp.getMovement().teleport(x, y, player.getHeight());
            if (player.getPosition().distance(sp.getPosition()) > 5)
                sp.getMovement().teleport(x, y, player.getHeight());
        }

    }

    /**
     * Teleporting
     */

    public boolean startTeleport(EventConsumer eventConsumer) {
        return startTeleport(20, eventConsumer);
    }

    public boolean startTeleport(int maxWildernessLevel, EventConsumer eventConsumer) {
        if (player.isLocked())
            return false;
        if (player.teleportListener != null && !player.teleportListener.allow(player))
            return false;
        if (maxWildernessLevel != -1 && player.wildernessLevel > maxWildernessLevel) {
            player.sendMessage("You cannot teleport from over " + maxWildernessLevel + " wilderness.");
            return false;
        }
        Bounds bossBounds = new Bounds(3160, 4114, 3177, 4126, 0);
        if (player.getPosition().inBounds(bossBounds)) {
            player.sendMessage("A magical force prevents you from teleporting out of this area.");
            return false;
        }
        if (player.getCombat().checkTb())
            return false;
        player.lock(LockType.FULL_NULLIFY_DAMAGE);
        /** Fixed accepting the consumer via entity/player **/
        player.addEvent(eventConsumer);
        player.unlock();
        /** Another event is needed to reset the unlock/freeze ect **/
        player.resetActions(true, true, true); //closes things properly like trade
        player.resetStun();
        player.resetFreeze();
        return true;
    }

    /**
     * Misc
     */

    private static int getWalkDirection(int diffX, int diffY) {
        if (diffX < 0) {
            if (diffY < 0)
                return 0;
            else if (diffY > 0)
                return 5;
            else
                return 3;
        } else if (diffX > 0) {
            if (diffY < 0)
                return 2;
            else if (diffY > 0)
                return 7;
            else
                return 4;
        } else {
            if (diffY < 0)
                return 1;
            else if (diffY > 0)
                return 6;
        }
        return -1;
    }

    private static int getRunDirection(int diffX, int diffY) {
        if (diffX == -2) {
            if (diffY == -2)
                return 0;
            else if (diffY == -1)
                return 5;
            else if (diffY == 0)
                return 7;
            else if (diffY == 1)
                return 9;
            else if (diffY == 2)
                return 11;
        } else if (diffX == -1) {
            if (diffY == -2)
                return 1;
            else if (diffY == 2)
                return 12;
        } else if (diffX == 0) {
            if (diffY == -2)
                return 2;
            else if (diffY == 2)
                return 13;
        } else if (diffX == 1) {
            if (diffY == -2)
                return 3;
            else if (diffY == 2)
                return 14;
        } else if (diffX == 2) {
            if (diffY == -2)
                return 4;
            else if (diffY == -1)
                return 6;
            else if (diffY == 0)
                return 8;
            else if (diffY == 1)
                return 10;
            else if (diffY == 2)
                return 15;
        }
        return -1;
    }

    protected Direction[] directions;
    public int lastX;
    public int lastY;

    public Direction[] getDirections() {
        if (this.directions == null) {
            this.directions = new Direction[50];
        }

        return this.directions;
    }

    public boolean addStep(int absX, int absY) {
        Direction[] directions = this.getDirections();
        if (this.writeOffset >= directions.length) {
            return false;
        } else {
            int diffX = absX - this.lastX;
            int diffY = absY - this.lastY;
            int max = Math.max(Math.abs(diffX), Math.abs(diffY));

            for (int i = 0; i < max && this.writeOffset < directions.length; ++i) {
                if (diffX < 0) {
                    ++diffX;
                } else if (diffX > 0) {
                    --diffX;
                }

                if (diffY < 0) {
                    ++diffY;
                } else if (diffY > 0) {
                    --diffY;
                }

                Direction dir = getDirection(absX - diffX - this.lastX, absY - diffY - this.lastY);
                if (dir != null) {
                    this.lastX = absX - diffX;
                    this.lastY = absY - diffY;
                    directions[this.writeOffset++] = dir;
                }
            }

            this.lastX = absX;
            this.lastY = absY;
            return true;
        }
    }

}