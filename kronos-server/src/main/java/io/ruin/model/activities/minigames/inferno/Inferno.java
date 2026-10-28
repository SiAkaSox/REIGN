package io.ruin.model.activities.minigames.inferno;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.Pet;
import io.ruin.utility.Random;
import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.model.World;
import io.ruin.model.achievements.listeners.elite.TheLavaCapeII;
import io.ruin.model.activities.miscpvm.PassiveCombat;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.entity.shared.listeners.LogoutListener;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Tile;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;

import java.util.*;

import static io.ruin.Server.STARTUP_THREAD;

public class Inferno {

    private static final int HOST = 11247,
            NIBBLER = 7691,
            BAT = 7692,
            BLOB = 7693,
            MELEE = 7697,
            RANGE = 7698,
            MAGE = 7699,
            JAD = 7700,
            HEALER = 7701;

    private static final Position EXIT = new Position(2495, 5112, 0);

    private static final Position[] SPAWN_POSITIONS;
    public static final int PILLAR_ALIVE = 7709;
    public static final int PILLAR_DEAD = 7710;
    public static final int SHIELD = 7707;

    static {
        List<Position> spawns = Arrays.asList(
                new Position(2259, 5353, 0),
                new Position(2279, 5352, 0),
                new Position(2260, 5346, 0),
                new Position(2279, 5346, 0),
                new Position(2272, 5340, 0),
                new Position(2261, 5339, 0),
                new Position(2260, 5332, 0),
                new Position(2271, 5331, 0),
                new Position(2279, 5332, 0)
        );
        Collections.shuffle(spawns); //let's not get predictable ;)
        SPAWN_POSITIONS = spawns.toArray(new Position[0]);
    }

    /**
     * Separator
     */

    private Player p;

    @Expose
    private int wave;

    @Expose
    private boolean practice;

    @Expose
    private boolean[] deadPillars = new boolean[3];

    private DynamicMap map;

    private List<Pillar> pillars;

    private int spawnOffset;

    private boolean logoutRequest;

    private boolean killedZuk;

    private boolean preparingWave;

    public Inferno(Player player, int wave, boolean practice) {
        this.p = player;
        this.wave = wave;
        this.practice = practice;
    }

    public void start(boolean login) {
        if (deadPillars == null)
            deadPillars = new boolean[3];
        if (p.jalNibRek)
            wave = 30;
        p.startEvent(e -> {
            p.lock();

            p.inferno = this;
            p.deathEndListener = (DeathListener.Simple) this::handleDeath;
            p.logoutListener = new LogoutListener().onAttempt(this::allowLogout);
            spawnOffset = Random.get(SPAWN_POSITIONS.length - 1);

            map = new DynamicMap().build(9043, 1);
            pillars = new ArrayList<>(3);
            if (wave < 67) {
                if (!deadPillars[0])
                    pillars.add(new Pillar(30353, map.convertX(2257), map.convertY(5349), Config.INFERNO_PILLAR_DAMAGE_WEST));
                if (!deadPillars[1])
                    pillars.add(new Pillar(30354, map.convertX(2267), map.convertY(5335), Config.INFERNO_PILLAR_DAMAGE_SOUTH));
                if (!deadPillars[2])
                    pillars.add(new Pillar(30355, map.convertX(2274), map.convertY(5351), Config.INFERNO_PILLAR_DAMAGE_EAST));
            }

            if (login) {
                p.getMovement().teleport(map.convertX(2270), map.convertY(5344), 0);
            } else {
                p.animate(6723);
                p.getMovement().force(0, 11, 0, 0, 15, 255, Direction.NORTH);
                e.delay(5);
                p.dialogue(new MessageDialogue("You jump into the fiery cauldron of The Inferno; your heart is pulsating.").lineHeight(24).hideContinue());
                e.delay(2);
                p.getPacketSender().fadeOut();
                e.delay(4);
                p.dialogue(new MessageDialogue("You fall and fall and feel the temperature rising.").hideContinue());
                e.delay(2);
                p.dialogue(new MessageDialogue("Your heart is in your throat...").hideContinue());
                e.delay(2);
                p.dialogue(new MessageDialogue("You hit the ground in the centre of The Inferno.").hideContinue());
                p.getPacketSender().fadeIn();
                p.getMovement().teleport(map.convertX(2270), map.convertY(5344), 0);
                p.animate(4367);
                e.delay(10);
                p.closeDialogue();
            }

            map.assignListener(p).onExit((p, logout) -> stop(logout));
            p.unlock();
            if (login) {
                p.sendMessage(Color.RED.wrap("The next wave will start in 10 seconds."));
                prepareWave(true);
            } else {
                prepareWave(false);
            }

        });
    }

    private void stop(boolean logout) {
        if (logout) {
            if (practice) {
                p.getMovement().teleport(EXIT);
                p.getCombat().restore();
                p.inferno = null;
            }
        } else {
            int lastWave = wave - 1;
            if (lastWave <= 25) {
                p.dialogue(new NPCDialogue(HOST, "Well I suppose you tried... better luck next time.").animate(610));
            } else if (!practice) {
                int tokkul = 2 + (lastWave * (3 + lastWave));
                if (!killedZuk && lastWave > 25) {
                    if (!p.isSapphire()) {
                        p.dialogue(new NPCDialogue(HOST, "Well done in the cave, here take TokKul as reward.").animate(588));
                        p.getInventory().addOrDrop(6529, tokkul);
                    } else {
                        p.dialogue(new NPCDialogue(HOST, "Well I suppose you tried... better luck next time."));
                    }
                } else {
                    p.dialogue(new NPCDialogue(HOST, "You are very impressive for a JalYt. You managed to defeat TzKal-Zuk! Please accept this cape as a token of appreciation.").animate(615));
                    p.zukKills.increment(p);
                    Item reward = new Item(21295, 1);
                    p.getInventory().addOrDrop(reward);
                    p.getCollectionLog().checkItemReceived(reward);
                    p.getInventory().addOrDrop(6529, 16440);
                    TheLavaCapeII.complete(p);
                    Broadcast.GLOBAL.sendNews(p, p.name() + " has defeated the Inferno!");
                }
            }
            p.inferno = null;
            p.deathEndListener = null;
            p.logoutListener = null;
            p.getNpcUpdater().setMaxDistance(14);
        }
        p = null;
        map.destroy();
        map = null;
    }

    /**
     * Spawning
     */

    private void prepareWave(boolean login) {
        if (preparingWave)
            return;
        preparingWave = true;
        if (!login)
            wave++;
        map.addEvent(event -> {
            if (!practice)
                p.sendMessage(Color.RED.wrap("Wave: " + wave));
            else
                p.sendMessage(Color.RED.wrap("Wave: " + wave + " (Practice mode)"));
            event.delay(login ? 16 : 10);
            beginWave();
            preparingWave = false;
        });
    }

    private void beginWave() {
        if (wave > 69)
            return;
        if (map.getNpcs() != null)
            map.getNpcs().removeIf(n -> n.getCombat().isDead()); // clear dead npcs

        /**
         * Boss waves
         */
        if (wave == 67) { // Single jad
            //Collapse pillars
            pillars.forEach(Pillar::collapse);
            pillars.clear();
            spawn(JAD);
            return;
        }
        if (wave == 68) { // Triple jad wave
            spawn(JAD, new Position(2265, 5347, 0));
            NPC jad2 = spawn(JAD, new Position(2267, 5336, 0));
            jad2.getCombat().updateLastAttack(4);
            NPC jad3 = spawn(JAD, new Position(2274, 5346, 0));
            jad3.getCombat().updateLastAttack(7);
            return;
        }
        if (wave == 69) { // Zuk
            p.getNpcUpdater().setMaxDistance(32);
            spawnZuk();
            return;
        }
        /**
         * Nibbler only waves
         */
        if (wave == 3) {
            spawnNibblers(6);
            return;
        }
        if (wave == 8 || wave == 17 || wave == 34) {
            spawnNibblers(5);
            return;
        }
        /**
         * Regular waves
         */
        int budget = this.wave;
        if (wave > 3)
            budget--;
        if (wave > 8)
            budget--;
        if (wave > 17)
            budget--;
        if (wave > 34)
            budget--;
        while (budget >= 31) {
            budget -= 31;
            spawn(MAGE);
        }
        while (budget >= 15) {
            budget -= 15;
            spawn(RANGE);
        }
        while (budget >= 7) {
            budget -= 7;
            spawn(MELEE);
        }
        while (budget >= 3) {
            budget -= 3;
            spawn(BLOB);
        }
        while (budget > 0) {
            budget--;
            spawn(BAT);
        }
        spawnNibblers(3);
    }

    private void spawnZuk() {
        p.addEvent(event -> {
            event.delay(6);
            p.getPacketSender().fadeOut();
            p.getPacketSender().sendMapState(2);
            p.dialogue(new MessageDialogue("A great power is starting to shake the cavern...").hideContinue());
            event.delay(2);
            p.lock();
            //Need to teleport the player to z 1 because we have to change a few objects there
            p.getMovement().teleport(map.convertX(2271), map.convertY(5356), 1);
            GameObject.forObj(30356, map.convertX(2267), map.convertY(5368), 1, GameObject::remove);
            GameObject.spawn(30346, map.convertX(2268), map.convertY(5364), 1, 10, 3);
            GameObject.spawn(30345, map.convertX(2273), map.convertY(5364), 1, 10, 3);
            event.delay(1);
            //Back to z 0
            p.getMovement().teleport(p.getPosition().copy().translate(0, 0, -1));
            event.delay(2);
            int wallX = map.convertX(2270);
            int wallY = map.convertY(5363);
            GameObject.forObj(30338, wallX, wallY, 0, GameObject::remove);
            NPC shield = spawn(SHIELD, new Position(2270, 5363, 0));
            GameObject.forObj(30337, map.convertX(2268), map.convertY(5364), 0, obj -> {
                obj.setId(30344);
                World.startEvent(e -> {
                    e.delay(2);
                    p.getPacketSender().sendObjectAnimation(map.convertX(2268), map.convertY(5364), 0, 10, 3, 7561);
                    e.delay(2);
                    obj.remove();
                });
            });
            GameObject.forObj(30336, map.convertX(2273), map.convertY(5364), 0, obj -> {
                obj.setId(30343);
                World.startEvent(e -> {
                    e.delay(2);
                    p.getPacketSender().sendObjectAnimation(map.convertX(2273), map.convertY(5364), 0, 10, 3, 7561);
                    e.delay(2);
                    obj.remove();
                });
            });
            GameObject.forObj(30332, map.convertX(2275), map.convertY(5364), 0, obj -> obj.setId(30339));
            GameObject.spawn(30340, map.convertX(2267), map.convertY(5364), 0, 10, 1);
            GameObject.spawn(30342, map.convertX(2267), map.convertY(5366), 0, 10, 1);
            GameObject.spawn(30341, map.convertX(2275), map.convertY(5366), 0, 10, 3);
            event.delay(1);
            p.getPacketSender().fadeIn();
            p.getPacketSender().sendMapState(0);
            p.getPacketSender().moveCameraToLocation(map.convertX(2276), map.convertY(5349), 1000, 10, 100);
            p.getPacketSender().turnCameraToLocation(map.convertX(2271), map.convertY(5365), 1000, 10, 100);
            p.getPacketSender().shakeCamera(0, 10);
            p.getPacketSender().shakeCamera(1, 10);
            p.getPacketSender().shakeCamera(2, 10);
            p.addEvent(camEvent -> {
                int tickToDelay = 14;
                camEvent.delay(tickToDelay);
                p.getPacketSender().resetCamera();
            });
            NPC zuk = spawn(7706, new Position(2268, 5364, 0));
            zuk.lock();
            shield.addEvent(e -> {
                e.delay(3);
                shield.step(0, -1, StepType.FORCE_WALK);
                e.delay(1);
                shield.step(0, -1, StepType.FORCE_WALK);
            });
            p.dialogue(new NPCDialogue(7690, "Oh no! TzKal-Zuk's prison is breaking down. This not meant to have happened. There's nothing I can do for you now JalYt!").animate(615).hideContinue());
            event.delay(9);
            p.closeDialogue();
            p.getPacketSender().resetCamera();
            p.unlock();
            zuk.unlock();
        });
    }


    private void spawn(int id) {
        Position pos = SPAWN_POSITIONS[spawnOffset];
        if (++spawnOffset >= SPAWN_POSITIONS.length)
            spawnOffset = 0;
        NPC npc = new NPC(id).spawn(map.convertX(pos.getX()), map.convertY(pos.getY()), 0);
        registerNPC(npc);
    }

    public NPC spawn(int id, Position pos) {
        return spawn(id, pos, true);
    }

    public NPC spawn(int id, Position pos, boolean attackTarget) {
        NPC npc = new NPC(id).spawn(map.convertX(pos.getX()), map.convertY(pos.getY()), 0);
        registerNPC(npc, attackTarget);
        return npc;
    }

    private void registerNPC(NPC npc) {
        registerNPC(npc, true);
    }

    private void registerNPC(NPC npc, boolean attackTarget) {
        npc.set("INFERNO", this);
        npc.getCombat().setAllowRespawn(false);
        npc.deathEndListener = (DeathListener.Simple) () -> handleDeath(npc);
        npc.targetPlayer(p, false);
        if (attackTarget)
            npc.attackTargetPlayer();
        map.addNpc(npc);
    }

    private void spawnNibblers(int amount) {
        int skips = 9 - amount;
        int point = 0;
        int baseX = map.convertX(2265);
        int baseY = map.convertY(5345);
        Entity target = pillars.isEmpty() ? p : Random.get(pillars).npc;
        for (int i = 0; i < amount; i++) {
            /**
             * Calculate point inside of 3x3 square to spawn.
             */
            if (skips > 0 && Random.rollDie(2, 1)) {
                skips--;
                point++;
            }
            int pointX = point / 3;
            int pointY = point;
            if (point >= 6)
                pointY -= 6;
            else if (point >= 3)
                pointY -= 3;
            point++;
            /**
             * Spawn the nibblers!
             */
            NPC npc = new NPC(NIBBLER).spawn(baseX + pointX, baseY + pointY, 0);
            npc.face(target);
            npc.getCombat().setTarget(target);
            npc.targetPlayer(p, false);
            npc.deathEndListener = (DeathListener.Simple) () -> handleDeath(npc);
            npc.getCombat().setAllowRespawn(false);
            map.addNpc(npc);
        }
    }

    public static Inferno getInstance(NPC npc) {
        return npc.get("INFERNO");
    }

    /**
     * Death handlers
     */

    private void handleDeath() {
        p.getMovement().teleport(EXIT);
        p.sendMessage("You have been defeated!");
    }

    private void handleDeath(NPC npc) {
        npc.setHidden(true); // we don't remove the npc here so that the magers can find dead ones and revive them
        if (p.getCombat().isDead()) {
            //nothing should happen cause you failed
            return;
        }
        if (npc.getId() == 7706) {
            killedZuk = true;
            p.getMovement().teleport(EXIT);
            return;
        }
        if (npc.getId() == BLOB) {
            spawnBloblets(npc);
            return;
        }
        if (map.getNpcs().stream().allMatch(n -> n.getId() == PILLAR_ALIVE || n.getId() == PILLAR_DEAD || n.getCombat().isDead())) {
            if (!finishPractice()) {
                if (logoutRequest) {
                    p.sendMessage("<col=ef1020>The Inferno has been paused. You may now log out.");
                    return;
                }
                prepareWave(false);
            }
        }
    }

    public DynamicMap getMap() {
        return map;
    }

    private void spawnBloblets(NPC npc) {
        LinkedList<Position> spawnPoints = new LinkedList<>();
        Bounds b = new Bounds(npc.getPosition(), npc.getPosition().relative(npc.getSize(), npc.getSize()), npc.getPosition().getZ());
        b.forEachPos(pos -> {
            if (Tile.get(pos).clipping == 0)
                spawnPoints.add(pos);
        });
        Collections.shuffle(spawnPoints);
        for (int bloblet = 7694; bloblet <= 7696; bloblet++) {
            Position pos = spawnPoints.pop();
            NPC n = new NPC(bloblet).spawn(pos);
            registerNPC(n);
        }
    }

    /**
     * Misc
     */

    private boolean finishPractice() {
        if (!practice)
            return false;
        p.getMovement().teleport(EXIT);
        p.getCombat().restore();
        return true;
    }

    private boolean allowLogout() {
        if (practice)
            return true;
        if (!logoutRequest) {
            p.sendMessage("<col=ef1020>Your logout request has been received. The minigame will be paused at the end of this wave.<br><col=ef1020>If you try to log out before that, you will have to repeat this wave.");
            logoutRequest = true;
            return false;
        }
        return true;
    }

    public int getWave() {
        return wave;
    }

    public Player getP() {
        return p;
    }

    /**
     * Pillar
     */
    private final class Pillar {

        public final GameObject obj;

        public final NPC npc;

        public final int pillarIndex;

        public Pillar(int objId, int x, int y, Config config) {
            pillarIndex = objId - 30353;
            obj = GameObject.spawn(objId, x, y, 0, 10, 0);
            map.addNpc(npc = new NPC(PILLAR_ALIVE).spawn(x, y, 0));
            npc.hitListener = new HitListener().postDamage(hit -> {
                int damageDealt = npc.getMaxHp() - npc.getHp();
                config.set(p, damageDealt);
            });
            npc.deathStartListener = (DeathListener.Simple) () -> {
                deadPillars[pillarIndex] = true;
                obj.remove();
                npc.transform(PILLAR_DEAD);
                npc.animate(7561);
                if (withinDistance(p))
                    p.hit(wave < 67 ? new Hit().randDamage(10, 25) : new Hit().fixedDamage(49));
                for (NPC n : map.getNpcs()) {
                    if (n.getCombat() != null && !n.getCombat().isDead() && withinDistance(n))
                        n.hit(new Hit().randDamage(10, 25));
                }
                pillars.remove(this);
            };
            npc.deathEndListener = (DeathListener.Simple) () -> map.removeNpc(npc);
            NPCDef.get(PILLAR_ALIVE).ignoreMultiCheck = true;
            NPCDef.get(PILLAR_DEAD).ignoreMultiCheck = true;
        }

        private boolean withinDistance(Entity entity) {
            int centerX = npc.getAbsX() + 1;
            int centerY = npc.getAbsY() + 1;
            return Misc.getDistance(entity.getPosition(), centerX, centerY) <= 2;
        }

        private void collapse() { // forced collapse, wave 67
            npc.addEvent(event -> {
                obj.remove();
                npc.transform(PILLAR_DEAD);
                npc.animate(7561);
                if (withinDistance(p))
                    p.hit(new Hit().fixedDamage(49));
                for (NPC n : map.getNpcs()) {
                    if (n.getCombat() != null && !n.getCombat().isDead() && withinDistance(n))
                        n.hit(new Hit().randDamage(10, 25));
                }
                event.delay(npc.getCombat().getInfo().death_ticks);
                npc.remove();
            });
        }

    }

    static {

        NPCDef def = NPCDef.get(PILLAR_ALIVE);
        def.combatHandlerClass = PassiveCombat.class;
        def.combatInfo = new npc_combat.Info();
        def.combatInfo.hitpoints = 254;
        def.combatInfo.defend_animation = -1;
        def.combatInfo.death_animation = -1;

        NPCDef.get(NIBBLER).ignoreOccupiedTiles = true;

        def = NPCDef.get(SHIELD);
        def.combatHandlerClass = PassiveCombat.class;
        def.combatInfo = new npc_combat.Info();
        def.combatInfo.hitpoints = 600;
        def.combatInfo.defend_animation = 7568;
        def.combatInfo.death_animation = 7569;
        def.combatInfo.spawn_animation = -1;
    }

    /**
     * Actions
     */

    static {
        NPCAction.register(7690, 1, (player, npc) -> openDialogue(player));
    }

    private static void openDialogue(Player p) {
        p.dialogue(
                new OptionsDialogue("Select an option",
                        new Option("Ask about inferno", () -> talktoGuy(p)),
                        new Option("Gamble infernal cape", () -> gambleCape(p)),
                        new Option("Nevermind"))
        );
    }

    private static void gambleCape(Player player) {
        if (player.getInventory().hasId(Pet.TZREK_ZUK.itemId) || player.getBank().hasId(Pet.TZREK_ZUK.itemId) || (player.pet != null && player.pet == Pet.TZREK_ZUK)) {
            player.dialogue(new NPCDialogue(11247, "You already have TzRek-Zuk!"));
            return;
        }
        Item cape = player.getInventory().findItem(21295);
        if (cape == null) {
            player.dialogue(new NPCDialogue(11247, "You no have cape!"));
            return;
        }
        player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Sacrifice your infernal cape for a chance at TzRek-Zuk?", cape, () -> {
            cape.remove();
            if (Random.rollDie(25, 1)) {
                Pet.TZREK_ZUK.unlock(player);
                player.dialogue(new NPCDialogue(11247, "You lucky. Better train him good else TzKal-Zuk find you, JalYt."));
            } else {
                player.dialogue(new NPCDialogue(11247, "You not lucky. Maybe next time, JalYt."));
            }
        }));
    }

    public static void talktoGuy(Player p) {
        if (!p.sacrificedFireCape) {
            sacrificecapeDialogue(p);
        } else {
            p.dialogue(
                    new NPCDialogue(11247, "You can head into The Inferno anytime. We trust you to help us JalYt-Ket-Xo-JalYt-Ket-Xo-" + p.name() + "."),
                    new PlayerDialogue("I'll do my best.")
            );
        }
    }

    public static void sacrificecapeDialogue(Player p) {
        p.dialogue(
                new PlayerDialogue("Can I go down there?"),
                new NPCDialogue(11247, "JalYt, we need help, but this is extremely dangerous. TzHaar not strong enough to take control back."),
                new NPCDialogue(11247, "We are worried about trusting JalYt to take on this task for us."),
                new PlayerDialogue("Let me prove it."),
                new NPCDialogue(11247, "I have one idea, if you could sacrifice your fire cape to me, I can take your word."),
                new PlayerDialogue("Okay, I'd like to sacrifice my fire cape."),
                new NPCDialogue(11247, "Very well, just hand it over."),
                new OptionsDialogue("Really sacrifice your fire cape?",
                        new Option("Yes, I'd like to sacrifice it.", () -> reallysacrificeCape(p)),
                        new Option("No, I'd like to keep it.")
                )
        );
    }

    public static void reallysacrificeCape(Player p) {
        if (!p.getInventory().contains(6570) || !p.getEquipment().contains(6570)) {
            p.dialogue(
                    new PlayerDialogue("I don't have a fire cape on me unfortunately.")
            );
        }
        if (!p.getInventory().contains(6570) && p.getEquipment().contains(6570)) {
            p.dialogue(
                    new PlayerDialogue("Let me just take it off first.")
            );
        }
        if (p.getInventory().contains(6570)) {
            p.getInventory().remove(6570, 1);
            p.sacrificedFireCape = true;
            p.dialogue(
                    new MessageDialogue("You hand over your cape to TzHaar-Ket-Keh."),
                    new NPCDialogue(11247, "Give it your best shot JalYt-Ket-Xo-" + p.name() + ".")
            );
        }
    }

    public static void join(Player player, int wave, boolean practice) {
        if (!player.sacrificedFireCape) {
            player.dialogue(
                    new MessageDialogue("You must sacrifice a fire cape before entering The Inferno.")
            );
        }
        if (player.inferno == null && player.sacrificedFireCape) //probably will always be true, but just to be safe?
            new Inferno(player, wave - 1, practice).start(false);
    }

    private static void exit(Player player) {
        //map listener will stop your session the second you leave the map.
        player.getMovement().teleport(EXIT);
    }

    private static int getStartingWave(Player player) {
        if (player.storeAmountSpent >= 500) {
            return 69;
        }
        return 67;
    }

    static {
        GameObject.forObj(30352, 2493, 5124, 0, obj -> obj.walkTo = new Position(2496, 5115, 0));
        ObjectAction.register(30352, actions -> {
            actions[1] = (player, obj) -> {
                player.dialogue(new OptionsDialogue(
                        new Option("Enter the Inferno", () -> join(player, getStartingWave(player), false)),
                        new Option("Practice a specific wave", () -> player.integerInput("Enter the wave you'd like to practice: (1-69)", wave -> {
                            if (wave < 0 || wave > 69) {
                                player.retryIntegerInput("Invalid wave, enter the wave you'd like to practice: (1-69)");
                                return;
                            }
                            join(player, wave, true);
                        })),
                        new Option("Cancel")));
            };
        });

        LoginListener.register(p -> {
            if (p.inferno != null) {
                p.inferno.p = p;
                p.inferno.start(true);
            }
        });

        ObjectAction.register(30283, "exit", (player, obj) -> player.dialogue(
                new OptionsDialogue("Really leave?",
                        new Option("Yes - really leave.", () -> exit(player)),
                        new Option("No, I'll stay.", player::closeDialogue)
                )
        ));

        ObjectAction.register(30283, "quick-exit", (player, obj) -> exit(player));
    }

}
