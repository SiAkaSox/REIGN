package io.ruin.model.activities.godwars.combat.impl.zaros;

import io.ruin.Server;
import io.ruin.utility.Random;
import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.handlers.EquipmentStats;
import io.ruin.model.map.*;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.utility.Common;
import io.ruin.utility.Misc;

import java.util.*;
import java.util.stream.Collectors;

import static io.ruin.Server.STARTUP_THREAD;

public class Nex extends NPCCombat {

    private Phase phase = Phase.SPAWN; // current phase
    private int soulSplitDamage; // soul split dmg
    private int damageHealsDelay; // blood siphon delay
    private List<NPC> minions = new ArrayList<>(); // list of active minions
    private List<NPC> bloodReavers = new ArrayList<>(); // list of active blood reavers
    private List<GameObject> icicles = new ArrayList<>(); // list of active icicles
    private List<GameObject> stalagmites = new ArrayList<>(); // list of active stalagmites
    // forcing nex to use attacks below. We do it this way so that it's only called when the attack delay is up.
    private boolean forceShadowSmash = false;
    private boolean forceBloodSiphonAttack = false;
    private boolean forceBloodSiphonHeal = false;
    private boolean forceContainmentAttack = false;
    private boolean forceSoulSplitAttackWithReflect = false;
    private boolean forceSoulSplitAttackOnly = false;
    private boolean forceChokeAttack = false;
    private boolean forceDeflectMelee = false;
    private boolean forceDragAttack = false;
    private boolean forceBloodSacrificeAttack = false;
    private boolean forceIcePrisonAttack = false;
    private boolean forceMelee = false; // force melee is prioritized last, after all special attacks
    public static final Bounds NEX_BOUNDS = new Bounds(2909, 5187, 2941, 5220, 0);
    private NPC FUMUS_SPAWN;
    private NPC UMBRA_SPAWN;
    private NPC CRUOR_SPAWN;
    private NPC GLACIES_SPAWN;
    private int regularAttackCount = 0;
    private boolean hasHealedInZarosForm = false;

    public Nex() {

    }

    /**
     * Nex has to be not null, alive, and not removed for this boolean to be true.
     *
     * @return
     */
    private boolean nexIsAllGucci() {
        if (npc == null) {
            // System.out.println("NPC is null.");
            return false;
        }
        if (!npc.alive()) {
            // System.out.println("NPC is not alive.");
            return false;
        }
        if (npc.isRemoved()) {
            // System.out.println("NPC is removed.");
            return false;
        }
        return true;
    }

    @Override
    public void init() {
        // System.out.println("Init started!");
        World.startEvent(event -> {
            // System.out.print("Started Nex's persistable event");
            event.delay(3);
            while (phase.equals(Phase.SPAWN)) {
                // System.out.print(".");
                event.delay(1);
            }
            // System.out.println("Finished spawn event delay.");

            boolean keepGoing = nexIsAllGucci();
            while (keepGoing) {
                if (nexIsAllGucci()) { // while nex has not null, alive, has not been removed...
                    everyTick();
                    event.delay(1);
                } else {
                    keepGoing = false;
                    // System.out.println("Set keepGoing to false");
                }
            }
            // System.out.println("Event stopped.");
        });
        npc.hitListener = new HitListener().preDefend(this::preDefend).postTargetDefend(this::postTargetDefend).postDamage(this::postDamage); // bind these
        npc.deathStartListener = (entity, killer, killHit) -> {
            if (npc.getId() != Forms.WRATH_NEX) {
                npc.transform(Forms.WRATH_NEX);
            }
            wrathAttack(npc.getPosition().copy());
            restoreHook();
        };

        //npc.dropListener
        restoreHook();

        if (phase.equals(Phase.SPAWN)) {
            handleIntro();
            //skipIntro();
        }
    }

    private void printShouldPulls(boolean... shouldPullFrom) {
        // System.out.print("ShouldPullFrom values: ");
        // System.out.print("0: " + shouldPullFrom[0] + ", ");
        // System.out.print("1: " + shouldPullFrom[1] + ", ");
        // System.out.print("2: " + shouldPullFrom[2] + ", ");
        // System.out.print("3: " + shouldPullFrom[3] + "");
        // System.out.println(".");
    }

    // @todo remove prints
    private void dragAttack() {
        /*// Get a random player who we could hit with a projectile
        final Player pull = Random.get(npc.localPlayers().stream().filter(t -> ProjectileRoute.allow(npc, t)).collect(Collectors.toList()));

        // Face eachother
        pull.faceTemp(npc);
        npc.faceTemp(pull);

        // copy positions
        final Position npos = npc.getPosition().copy();
        final Position ppos = pull.getPosition().copy();

        // These booleans represent each direction a player could be pulled from.
        boolean[] shouldPullFrom = new boolean[]{false, false, false, false};

        if (ppos.getX() > (npos.getX() + 1)) { // East
            shouldPullFrom[0] = true;
        }
        if (ppos.getX() < (npos.getX() + 1)) { // West
            shouldPullFrom[1] = true;
        }
        if (ppos.getY() > (npos.getY() + 1)) { // North
            shouldPullFrom[2] = true;
        }
        if (ppos.getY() < (npos.getY() + 1)) { // South
            shouldPullFrom[3] = true;
        }

        //printShouldPulls(shouldPullFrom);

        // if we shouldn't pull from any direction, return
        if (!shouldPullFrom[0] && !shouldPullFrom[1] && !shouldPullFrom[2] && !shouldPullFrom[3]) {
            return;
        }

        // for each boolean (each direction)
        for (int i = 0; i < shouldPullFrom.length; i++) {
            List<Position> t = NexUtils.getPossiblePullPositions(npc, i); // get pull positions
            if (t.equals(null)) { // if clipping is bad for any given tile
                shouldPullFrom[i] = false; // we should not pull from that tile
                continue;
            }
            for (Position somePos : t) { // for all tiles in the clipping list
                if (!Common.clippingIsEmpty(somePos) || !somePos.inBounds(NEX_BOUNDS)) { // if any tile has bad clipping, or out of bounds
                    shouldPullFrom[i] = false; // set shouldPullFrom to false
                }
            }
        }

        //printShouldPulls(shouldPullFrom);

        Map<Integer, List<Position>> map = new HashMap<>(); // Create a new Map which will contain only valid entries.
        int newCount = 0; // Create a new key for map key pairs
        for (int count = 0; count < shouldPullFrom.length; count++) { // Loop through all the valid tiles' pull positions
            if (shouldPullFrom[count]) { // if a given direction is valid
                // System.out.println("Count: " + count + ", put new positions.");
                map.put(newCount, NexUtils.getPossiblePullPositions(npc, count)); // get position
                newCount++; // increment our loop
            }
        }
        // System.out.println("Map.size: " + map.size() + ", newCount-1 = " + (newCount - 1));
        // It's important we use newCount -1. Because arrays start at 0.
        final int choice = newCount > 1 ? Random.get(0, newCount - 1) : 0;
        final List<Position> chosenTiles = map.get(choice);
        // System.out.println("newCount: " + newCount);
        if (chosenTiles == null) { // if chosenTiles is null return
            // System.out.println("chosenTiles equals null. Returning");
            return;
        } // if
        if (chosenTiles.isEmpty()) {
            // System.out.println("ChosenTiles is empty");
            return;
        }

        if (map.size() > 0) { // if our map has an entry
            pull.addEvent(e -> {
                pull.lock(LockType.FULL_CANT_ATTACK); // block the player from attacking
                List<Position> temp = map.get(choice); // get the temp positions
                for (Position position : temp) { // for each of our temp positions
                    pull.animate(1501); // anim8
                    pull.getMovement().teleport(position); // tele
                    e.delay(1); // delay 1 tick
                }
                if (pull.isLocked()) {
                    pull.unlock();
                }
                pull.hit(new Hit(npc, AttackStyle.MAGICAL_RANGED, null).randDamage(20, 30).ignoreDefence().ignorePrayer()); // finally, hit the player
            });
            World.startEvent(event -> {
                for (int i = 0; i < 5; i++) {
                    if (i <= 3) {
                        if (pull.isLocked()) {
                            // System.out.println("Pull is locked.");
                            pull.unlock();
                        }
                    }
                    event.delay(1);
                    i++;
                }
            });
        }*/
    }

    private void rollSpecialAttack() {
        if (regularAttackCount >= 5) { // + Random.get(0, 2)) { // between 5 - 7 attacks
            // System.out.println("Time for special attack! phase: " + phase.toString().toLowerCase() + ", count: " + regularAttackCount);
            regularAttackCount = 0;
            switch (phase) {
                case SMOKE_LOCKED:
                case SMOKE:
                    int roll = Random.get(1, 2);
                    if (roll == 1) {
                        forceChokeAttack = true;
                    }
                    //smokeDash();
                    break;
                case SHADOW_LOCKED:
                case SHADOW:
                    forceShadowSmash = true;
                    break;
                case BLOOD_LOCKED:
                case BLOOD:
                    int theySeeMeRolling = Random.get(1, 2);
                    if (theySeeMeRolling == 1) {
                        forceBloodSiphonAttack = true;
                    } else {
                        forceBloodSacrificeAttack = true;
                    }
                    break;
                case ICE_LOCKED:
                case ICE:
                    int iceRoll = Random.get(1, 2);
                    if (iceRoll == 1) {
                        forceContainmentAttack = true;
                    } else {
                        forceIcePrisonAttack = true;
                    }
                    break;
                case ZAROS:
                    int rng = Random.get(1, 2);
                    if (rng == 1) {
                        forceSoulSplitAttackOnly = true;
                    } else if (rng == 2) {
                        forceDeflectMelee = true;
                    }
                    break;
            }
        }
    }

    protected Entity findAggressionTarget() {
        if (npc.localPlayers().isEmpty())
            return null;
        if (npc.hasTarget())
            return null;
        List<Player> targets = npc.localPlayers().stream()
                .filter(this::canAggro)
                .collect(Collectors.toList());
        if (targets.isEmpty())
            return null;
        return Random.get(targets);
    }

    private void maintainPlayerTarget() {
        target = findAggressionTarget();
    }

    private void printTargetInfo(NPC npc) {
        String info = "Target: "+npc.getCombat().getTarget().player.name()+", hasTarget: "+npc.hasTarget()+", isAggro: "+npc.getCombat().isAggressive()+", retaliating: "+npc.getCombat().retaliating+", isDefending(5000): "+npc.getCombat().isDefending(5000);
        // System.out.println(info);
    }

    /**
     * This method runs ticks every tick
     */
    private void everyTick() {
        // If no players in nex, clear the room out.
        if (!NexUtils.anyPlayerInNex()) {
            // System.out.println("Nex room is empty, clearing out.");
            npc.remove();
            restoreHook();
            return;
        }

        try {
            if (target == null) {
                target = npc.localPlayers().get(0);
            }
            printTargetInfo(npc);
        } catch (Exception e) {
            // System.out.println("Attempted to target null player. Handled it. Error below:");
            e.printStackTrace();
        }
        if (npc.isLocked()) { // if npc is locked, stop
            return;
        }
        if (damageHealsDelay > 0) { // if we have a heal delay, de-increment it.
            damageHealsDelay = damageHealsDelay - 1;
        }

        checkPhase(); // check the phase has to run every tick.

        if (wrathHook()) { // wrath hook. If npc hp < 7.5% activate wrath.
            npc.transform(Forms.WRATH_NEX); // change to wrath form.
        }
        if (this.canMeleeAttack() && Random.get(1, 5) == 1) { // if can attack and 20% chance to hit, force a melee
            forceMelee = true;
        }
    }

    /**
     * Checks for Wrath, returns true when < 7.5% hp
     *
     * @return
     */
    private boolean wrathHook() {
        if (npc.getId() == Forms.WRATH_NEX) { // If we're already in wrath form, stop.
            return false;
        }
        if (phase != Phase.ZAROS) { // if we're not in zaros phase, stop.
            return false;
        }
        if (!hasHealedInZarosForm) { // if we have healed already, stop. (This can only occur once)
            return false;
        }
        final double currentHP = npc.getHp() / (double) npc.getMaxHp(); // calculate hp %
        if (currentHP <= 0.075) { // if npc is lower than 0.075%
            return true;
        }
        return false;
    }

    /**
     * Handles blocking damage when relevant or healing procs.
     *
     * @param hit
     */
    private void preDefend(Hit hit) {
        // When heal delay and locked, heal for all incoming dmg.
        if (damageHealsDelay > 0 && hit.type != HitType.HEAL && npc.isLocked()) {
            hit.type = HitType.HEAL;
        }
        // Stop here for heals. Only dmg below.
        if (hit.type == HitType.HEAL) {
            return;
        }
        // handle reflecting melee damage back to melee attackers.
        if (npc.getId() == Forms.REFLECT_MELEE_NEX && hit.attackStyle.isMelee()) {
            Entity opponent = hit.attacker;

            if (opponent == null) { // if opponent is null, do not continue
                return;
            }

            // idk why it can't get hit.damage but this is a way to simulate melee damage.
            final int simulatedAtkDmg = opponent.player.getCombat().simulateAttackWithMelee(false, false, false, AttackStyle.SLASH, 0);

            // System.out.println("Deflected " + simulatedAtkDmg + " dmg to: " + opponent.player);
            opponent.player.hit(new Hit(npc, AttackStyle.MAGICAL_MELEE, null).fixedDamage(simulatedAtkDmg).ignorePrayer().ignoreDefence()); // hit the attacker thru prayer
            hit.block(); // block the incoming hit.
            return; // stop here
        }

        // If you've attacked a locked nex, block all dmg.
        switch (phase) {
            case SPAWN: // handle the spawn phase. Should not be attack-able.
            case SMOKE_LOCKED:
            case SHADOW_LOCKED:
            case BLOOD_LOCKED:
            case ICE_LOCKED:
                hit.block();
                break;
        }
    }

    /**
     * Handles spawning soul split projectiles
     *
     * @param hit
     * @param entity
     */
    private void postTargetDefend(Hit hit, Entity entity) {
        if (npc.getId() == Forms.SOUL_SPLIT_NEX) { // we're in soulsplit form
            if (hit.damage > 0) {
                soulSplitDamage += hit.damage; // add damage to a counter
                npc.addEvent(e -> {
                    e.delay(1);
                    Projectile p = Attacks.SOUL_SPLIT_PROJ; // do the soul split projectile
                    p.send(entity, npc);
                });
            }
        }
    }

    /**
     * In soul split form, heal for damage after a 3 tick delay.
     *
     * @param hit
     */
    private void postDamage(Hit hit) {
        if (npc.getId() == Forms.SOUL_SPLIT_NEX) { // if we're in soul split form
            if (soulSplitDamage > 0) { // and dealt dmg
                int damage = soulSplitDamage;
                soulSplitDamage = 0; // reset counter
                npc.addEvent(e -> {
                    e.delay(3); // wait 3 seconds
                    npc.hit(new Hit(HitType.HEAL).fixedDamage(damage)); // heal
                });
            }
        }
    }

    /**
     * Resets all static, class level variables around nex.
     */
    private void removeAllForceAttacks() {
        forceContainmentAttack = false;
        forceSoulSplitAttackWithReflect = false;
        forceSoulSplitAttackOnly = false;
        forceChokeAttack = false;
        forceDeflectMelee = false;
        forceShadowSmash = false;
        forceDragAttack = false;
        forceBloodSiphonAttack = false;
        forceBloodSiphonHeal = false;
        forceBloodSacrificeAttack = false;
        forceIcePrisonAttack = false;
        // and melee.
        forceMelee = false;
    }

    /**
     * Called when Nex dies and spawns, reset all static things that could be out of sync.
     */
    public void restoreHook() {
        //phase = Phase.SPAWN
        hasHealedInZarosForm = false;
        regularAttackCount = 0;
        damageHealsDelay = 0;
        removeAllForceAttacks();
        NexUtils.removeNpc(bloodReavers);
        NexUtils.removeObjects(icicles);
        NexUtils.removeObjects(stalagmites);
        NexUtils.removeNpc(minions);
        bloodReavers.clear();
        minions.clear();
        icicles.clear();
        stalagmites.clear();
    }

    private void wrathAttack(Position pos) {
        if (npc.getId() == Forms.WRATH_NEX) {
            List<Player> deathPlayers = npc.localPlayers();
            npc.forceText("Taste my wrath!");
            World.startEvent(e -> {
                e.delay(3);
                List<Position> tiles = NexUtils.getWrathTiles(npc);
                World.sendGraphics(2013, 0, 30, npc.getPosition().getX() + 1, npc.getPosition().getY() + 1, npc.getHeight());
                tiles.forEach(tile -> {
                    if (!tile.inBounds(NEX_BOUNDS)) { // if we're outside of nex bounds, block the animation
                        return;
                    }
                    if (Tile.get(tile).clipping == 0) { // if clipping is good
                        Attacks.WRATH_PROJ.send(npc.getPosition(), tile); // send proj
                        World.sendGraphics(2014, 0, 4, tile); // send gfx
                    }
                });
                e.delay(2);
                deathPlayers.forEach(p -> {
                    if (!p.getPosition().inBounds(Nex.NEX_BOUNDS)) // if player is out of bounds, don't hit them
                        return;
                    final int dist = NexUtils.calculateWrathDistance(p, npc);
                    final int currentDist = Misc.getDistance(pos, p.getPosition());
                    // System.out.print("Wrath dist: " + dist + ", ");
                    if (currentDist <= dist) {
                        p.hit(new Hit(HitType.DAMAGE).randDamage(30, 40).ignorePrayer().ignoreDefence());
                    }
                });
            });
        }
    }

    /**
     * Checks the phase, on every tick.
     */
    private void checkPhase() {
        boolean locked = true;
        if (phase.getMinionId() != -1) {
            for (NPC minion : minions) {
                if (minion == null) {
                    continue;
                }
                if (minion.getId() == phase.getMinionId()) {
                    if (minion.isInvincible() || minion.dead()) {
                        // System.out.print("Minion " + minion.getDef().name);
                        // System.out.println(" is not locking us, set locked to false in checkPhase");
                        locked = false;
                    }
                }
            }
        }
        Phase changedPhase = Phase.getPhase(npc.getHp() / (double) npc.getMaxHp(), locked);
        if (phase == changedPhase || phase.ordinal() >= changedPhase.ordinal()) {
            return;
        }
        // System.out.println("Changed phase to: " + changedPhase.toString().toLowerCase());
        phase = changedPhase;
        if (phase.getMinionId() != -1) {
            for (NPC minion : minions) {
                if (minion == null) {
                    continue;
                }
                if (minion.getId() == phase.getMinionId()) {
                    if (minion.alive()) {
                        minion.setInvincible(false);
                        Attacks.MINION_SPAWN_PROJ.send(minion.getPosition().copy(), npc.getPosition().copy());
                    }
                }
            }
        }
        npc.forceText(phase.getPhrase());
        if (!locked) {
            regularAttackCount = 0;
            npc.getCombat().delayAttack(4);
            // consider reducing delay here if its not procing fast enough. It doesn't "have" to be 4.
            switch (phase) {
                case SHADOW:
                    forceShadowSmash = true;
                    break;
                case BLOOD:
                    npc.addEvent(event -> {
                        event.delay(4);
                        forceBloodSiphonAttack = true;
                    });
                    break;
                case ICE:
                    forceBloodSiphonHeal = true;
                    npc.addEvent(event -> {
                        event.delay(4);
                        forceContainmentAttack = true;
                    });
                    break;
                case ZAROS: {
                    npc.hit(new Hit(HitType.HEAL).fixedDamage(500).ignoreDefence().ignorePrayer());
                    hasHealedInZarosForm = true; // toggle this to true.
                    npc.animate(9179);
                    npc.graphics(2016);
                    npc.addEvent(event -> {
                        event.delay(4);
                        forceSoulSplitAttackWithReflect = true;
                    });
                    break;
                }
            }
        }
    }

    @Override
    protected boolean canAggro(Player player) {
        if (npc.getId() == Forms.UNATTACKABLE_NEX) { // if nex is unattackable form, don't aggro
            return false;
        }
        if (player == null || player.isHidden()) {
            // System.out.println("Nex could not aggro! A1");
            return false;
        }
        if (player.getCombat() == null) {
            // System.out.println("Nex could not aggro! A2");
            return false;
        }
        final boolean canAtk = canAttack(player);
        // System.out.println("canAtk " + canAtk);
        final boolean inPos = player.getPosition().inBounds(NEX_BOUNDS);
        // System.out.println("inPos: " + inPos);
        return inPos && canAtk;
    }

    @Override
    public boolean isAggressive() {
        return true;
    }

    @Override
    public int getAggressionRange() {
        return 40;
    }

    @Override
    public void follow() {
        // tested @ 1 originally, 5 from battlescape scripts
        follow(forceMelee ? 1 : 8);
    }

    /**
     * Handles all forced special attacks. This is processed on the attack tick
     *
     * @return
     */
    private boolean handleForcedSpecialAttack() {
        if (forceShadowSmash && (phase == Phase.SHADOW || phase == Phase.SHADOW_LOCKED)) {
            shadowSmashAttack();
            forceShadowSmash = false;
            return true;
        }
        if (forceBloodSiphonAttack && (phase == Phase.BLOOD || phase == Phase.BLOOD_LOCKED)) {
            bloodSiphonAttack();
            forceBloodSiphonAttack = false;
            return true;
        }
        if (forceBloodSiphonHeal && (phase == Phase.BLOOD || phase == Phase.BLOOD_LOCKED)) {
            bloodSiphonHeal();
            forceBloodSiphonHeal = false;
            return true;
        }
        if (forceBloodSacrificeAttack && (phase == Phase.BLOOD || phase == Phase.BLOOD_LOCKED)) {
            bloodSacrificeAttack();
            forceBloodSacrificeAttack = false;
            return true;
        }
        if (forceContainmentAttack && (phase == Phase.ICE || phase == Phase.ICE_LOCKED)) {
            containmentAttack();
            forceContainmentAttack = false;
            return true;
        }
        if (forceIcePrisonAttack && (phase == Phase.ICE || phase == Phase.ICE_LOCKED)) {
            icePrisonAttack();
            forceIcePrisonAttack = false;
            return true;
        }
        if (forceSoulSplitAttackWithReflect && phase == Phase.ZAROS) {
            soulSplitAttack(true);
            forceSoulSplitAttackWithReflect = false;
            return true;
        }
        if (forceSoulSplitAttackOnly && phase == Phase.ZAROS) {
            soulSplitAttack(false);
            forceSoulSplitAttackOnly = false;
            return true;
        }
        if (forceChokeAttack && (phase == Phase.SMOKE || phase == Phase.SMOKE_LOCKED)) {
            chokeAttack();
            forceChokeAttack = false;
            return true;
        }
        if (forceDeflectMelee && phase == Phase.ZAROS) {
            deflectMeleeAttack();
            forceDeflectMelee = false;
            return true;
        }
        return false;
    }

    private void forceTesting(final Phase testPhase, boolean forceAttackToTest) {
        if (phase != testPhase)
            phase = testPhase;
        if (!forceAttackToTest)
            forceAttackToTest = true;
    }

    @Override
    public boolean attack() {
        if (npc.isLocked()) {
            return false;
        }
        if (target == null) {
            return false;
        }

        //forceTesting(Phase.ICE, forceIcePrisonAttack);

        rollSpecialAttack(); // roll for special attack.
        // if we have a pending attack do that then return
        if (handleForcedSpecialAttack()) { // handle any forced special attacks here
            return true;
        }

        // every time a non-special attack is used, increment this variable
        regularAttackCount++;

        // handle specials 1st, melee 2nd
        if (forceMelee) { //if we have a pending melee attack, do that then return
            npc.startEvent(e -> {
                e.delay(4);
                if (forceMelee) {
                    forceMelee = false;
                }
            });
            meleeAttack();
            //forceMelee = false;
            return true;
        }

        // do some type of attack dependant on the phase
        switch (phase) {
            case SMOKE:
            case SMOKE_LOCKED:
                smokeMagicAttack();
                return true;
            case SHADOW:
            case SHADOW_LOCKED:
                shadowRangedAttack();
                return true;
            case BLOOD:
            case BLOOD_LOCKED:
                bloodMagicAttack();
                return true;
            case ICE:
            case ICE_LOCKED:
                iceMagicAttack();
                return true;
            case ZAROS:
                zarosMagicAttack();
                return true;
            default:
                // System.out.println("Default attack handler");
                meleeAttack();
                return true;
        }
        //return false;
    }

    public void process() {

    }

    /**
     * Target 1 player who must move at least 7 squares away from nex or get SMACKED.
     * Random other players take prayer draining damage.
     */
    private void bloodSacrificeAttack() {
        final Optional<Player> closePlayer = npc.localPlayers().stream().filter(p -> Misc.getDistance(p.getPosition().copy(), npc.getPosition().copy()) < 7).findFirst();
        final Player chosen = closePlayer.orElseGet(() -> Random.get(npc.localPlayers())); // attempt to choose a player within 7 squares or else get a random player.

        npc.forceText(Attacks.BLOOD_SACRIFICE);
        chosen.addEvent(event -> {
            chosen.graphics(Attacks.BLOOD_SACRIFICE_GFX_SM);
            event.delay(2);
            chosen.graphics(Attacks.BLOOD_SACRIFICE_GFX_MED);
            event.delay(2);
            chosen.graphics(Attacks.BLOOD_SACRIFICE_GFX_LG);
            event.delay(2);

            final int distance = Misc.getDistance(chosen.getPosition().copy(), npc.getPosition().copy());
            // System.out.println("BloodSac dist: " + distance);
            if (distance < 7) { // if closer than 7
                chosen.graphics(2015);
                final int damage = Random.get(1, 50);
                Hit hit = new Hit(npc, AttackStyle.MAGICAL_MELEE, null).randDamage(damage).ignorePrayer().ignoreDefence();
                hit.postDamage((t) -> {
                    chosen.getPrayer().drain(NexUtils.hasSpectral(chosen) ? (int) (damage * 0.2) : (int) (damage * 0.25));
                    chosen.sendFilteredMessage(Attacks.PRAYER_DRAIN);
                    if (hit.damage > 0) {
                        chosen.privateSound(105, 1, 0);
                        chosen.graphics(377, 0, 0);
                        npc.incrementHp((int) (hit.damage * 0.25)); // heal 1/4 of dmg done
                    } else {
                        chosen.privateSound(227, 1, 0);
                        chosen.graphics(85, 124, 0); // splash
                        hit.hide();
                    }
                });
                chosen.hit(hit);
                Hit healhit = new Hit(HitType.HEAL).fixedDamage(25).hide();
                npc.hit(healhit);
            }
        });
        /*.setCancelCondition(() -> { // basically, if either player dies, is null, or is offline, this attack should not go off.
            if (chosen == null) { // if the target is null
                // System.out.println("Chosen is null");
                return true;
            }
            if (!chosen.isOnline()) { // if the target is offline
                // System.out.println("Is offline");
                return true;
            }
            if (!target.alive()) { //if the target is dead
                // System.out.println("Is dead.");
                return true;
            }
            if (!target.getPosition().inBounds(NEX_BOUNDS)) { // target is not in nex bounds
                // System.out.println("Is out of bounds");
                return true;
            }
            if (!nexIsAllGucci()) { //nex is some kinda dead
                // System.out.println("Is not gucci");
                return true;
            }
            return false;
        });*/

        List<Player> targets = npc.localPlayers();
        targets.forEach(p -> {
            if (p == chosen || Random.get(1, 2) == 1) {
                // System.out.println("Skipped for attack" + (p == chosen ? " p was chosen" : "p was a random choice") + ".");
                return;
            }
            int delay = Attacks.BLOOD_MAGIC_PROJECTILE.send(npc, p);
            Hit hit = new Hit(npc, AttackStyle.MAGIC, null)
                    .randDamage(12)
                    .ignorePrayer()
                    .ignoreDefence()
                    .clientDelay(delay);
            hit.postDamage((t) -> {
                t.player.getPrayer().drain(NexUtils.hasSpectral(t.player) ? 3 : 4);
                t.player.sendFilteredMessage(Attacks.PRAYER_DRAIN);
                if (hit.damage > 0) {
                    t.privateSound(105, 1, 0);
                    t.graphics(377, 0, 0);
                    npc.incrementHp((int) (hit.damage * 0.25)); // heal 1/4 of dmg done
                } else {
                    t.privateSound(227, 1, 0);
                    t.graphics(85, 124, 0); // splash
                    hit.hide();
                }
            });
            p.hit(hit);
        });
    }

    private void containmentAttack() {
        npc.forceText(Attacks.CONTAIN);
        npc.animate(9186);
        npc.getCombat().delayAttack(4);
        World.startEvent(e -> {
            e.delay(4);
            for (Position offSetPos : NexUtils.getContainmentTiles(npc)) {
                final Tile tile = Tile.get(offSetPos);
                if (tile == null || tile.clipping != 0) {
                    continue;
                }
                final GameObject gameObject = new GameObject(Attacks.UNATTACKABLE_STALAGMITE, offSetPos.copy(), 10, Attacks.STALAGMITE_DIRECTION).spawn();
                icicles.add(gameObject);
            }
            npc.localPlayers().forEach(p -> {
                final Bounds containmentBounds = NexUtils.getContainmentBounds(npc);
                if (p.getPosition().inBounds(containmentBounds)) { // dude is within containment bounds
                    p.hit(new Hit(HitType.DAMAGE).randDamage(1, 60).ignorePrayer().ignoreDefence());
                    p.getPrayer().slashPrayers();
                }

            });
            e.delay(10);
            NexUtils.removeObjects(icicles);
            icicles.clear();
        });
    }


    private void icePrisonAttack() {
        final Player prisoner = Random.get(npc.localPlayers().stream().filter(t -> ProjectileRoute.allow(npc, t)).collect(Collectors.toList()));
        npc.forceText(Attacks.ICE_PRISON);
        npc.animate(9186);
        npc.getCombat().delayAttack(4);
        World.startEvent(e -> {
            prisoner.lock();
            e.delay(4);
            for (Position prisonPos : NexUtils.getIcePrisonPositions(prisoner)) {
                final Tile tile = Tile.get(prisonPos);
                if (tile == null || tile.clipping != 0) {
                    continue;
                }
                final GameObject gameObject = new GameObject(Attacks.ATTACKABLE_STALAGMITE, prisonPos.copy(), 10, Attacks.STALAGMITE_DIRECTION).spawn();
                stalagmites.add(gameObject);
            } // freeze prison and disable prot prayer
            prisoner.publicSound(168, 1, 0);
            prisoner.graphics(369, 0, 0);
            prisoner.getPrayer().slashPrayers();
            prisoner.sendMessage("You have been frozen and you prayer has been slashed!");
            final int firstStalagCount = Common.countNonNullSpawnedObjects(stalagmites);
            e.delay(7); // 4 seconds
            final int currentStalagCount = Common.countNonNullSpawnedObjects(stalagmites);
            // System.out.println("Stalag status: " + currentStalagCount + "/" + firstStalagCount);
            if (currentStalagCount == firstStalagCount) {
                // System.out.println("Attempting to hit players inside the stalag bounds");
                npc.localPlayers().stream().filter(p -> Misc.getDistance(prisoner.getPosition().copy(), p.getPosition().copy()) <= 5 && !p.dead()).forEach(p -> {
                    // System.out.println("A1");
                    final Bounds iceBounds = NexUtils.getIcePrisonBounds(npc);
                    if (p.getPosition().inBounds(iceBounds)) { // dude is within containment bounds
                        // System.out.println("Hit: " + p.getName());
                        p.hit(new Hit(HitType.DAMAGE).randDamage(1, 75).ignorePrayer().ignoreDefence());
                        p.getPrayer().slashPrayers();
                    }
                });
            }
            e.delay(9);
            prisoner.unlock();
            NexUtils.removeObjects(stalagmites);
            stalagmites.clear();
        });
    }

    /**
     * Kills reavers, heals nex for their remaining hp
     */
    private void bloodSiphonHeal() {
        if (bloodReavers.isEmpty()) {
            return;
        }
        int remainingHitpoints = 0;
        for (NPC bloodReaver : bloodReavers) {
            if (bloodReaver.isLocked()) {
                continue;
            }
            remainingHitpoints += bloodReaver.getHp();
        }
        NexUtils.removeNpc(bloodReavers);
        bloodReavers.clear();
        if (remainingHitpoints == 0) {
            return;
        }
        npc.hit(new Hit(HitType.HEAL).fixedDamage(remainingHitpoints).ignoreDefence().ignorePrayer());
    }

    //  During this, all damage she receives will instead heal her. When a new siphon is used, the previous siphon's reavers, if any, will die and heal Nex for their remaining health.
    private void bloodSiphonAttack() {
        bloodSiphonHeal();
        npc.forceText(Attacks.SIPHON);
        npc.animate(9183);
        damageHealsDelay = Attacks.SIPHON_DURATION;
        npc.lock();

        npc.addEvent(e -> {
            int reaverCount = Math.max(2, npc.localPlayers().size() / 10);
            for (int i = 0; i < reaverCount; i++) {
                for (int tries = 0; tries < 64; tries++) {
                    Bounds b = new Bounds(npc.getSpawnPosition(), reaverCount * 2);
                    Position p = b.randomPosition();
                    if (p.getX() < 2910) {
                        p = new Position(2910, p.getY(), p.getZ());
                    }
                    Tile t = Tile.get(p);
                    if (t == null || t.clipping != 0) { //if (t.clipping != 0 || !t.allowStandardEntrance()) {
                        continue;
                    }
                    NPC spawn = new NPC(BloodReaver.LEVEL_161).spawn(p);
                    bloodReavers.add(spawn);
                    break;
                }
            }
            e.delay(Attacks.SIPHON_DURATION);
            npc.unlock();
        });
    }


    private void soulSplitAttack(boolean deflectAfter) {
        if (npc.getId() != Forms.DEFAULT_NEX) {
            // System.out.println("Tried to do soulsplit attack on non-default nex. " + npc.getId());
            return;
        }
        npc.transform(Forms.SOUL_SPLIT_NEX);
        World.startEvent(event -> {
            event.delay(18);
            if (!nexIsAllGucci()) {
                // System.out.println("Nex was so dead we didn't have time to change form.");
                return;
            }
            npc.transform(Forms.DEFAULT_NEX);
            if (deflectAfter) {
                deflectMeleeAttack();
            }
        });
    }

    private void deflectMeleeAttack() {
        if (npc.getId() != Forms.DEFAULT_NEX) {
            // System.out.println("Tried to do deflect melee attack on non-default nex. " + npc.getId());
            return;
        }
        npc.transform(Forms.REFLECT_MELEE_NEX);
        World.startEvent(event -> {
            event.delay(18);
            npc.transform(Forms.DEFAULT_NEX);
        });
    }

    private boolean canMeleeAttack() {
        Optional<Player> target = npc.localPlayers().stream().filter(t -> Misc.getDistance(t.getPosition().copy(), npc.getPosition().copy()) == 1).findFirst();
        if (target.isPresent()) {
            forceMelee = true;
            return true;
        }
        return false;
    }

    private void meleeAttack() {
        boolean drainPrayer = phase == Phase.ZAROS;
        Optional<Player> target = npc.localPlayers().stream().filter(t -> Misc.getDistance(t.getPosition().copy(), npc.getPosition().copy()) == 1).findFirst();
        if (target.isPresent()) {
            Player p = target.get();
            npc.targetPlayer(p, false);
            npc.getCombat().setTarget(p);
            npc.face(p);
            npc.animate(info.attack_animation);
            boolean prayer = p.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE);
            Hit hit = new Hit(npc, AttackStyle.SLASH)
                    .randDamage(prayer ? (int) (Attacks.MELEE_MAX_HIT * Attacks.MELEE_PRAYER_EFFECTIVENESS) : Attacks.MELEE_MAX_HIT)
                    .ignorePrayer()
                    .preDefend(e -> {
                        forceMelee = false;
                    });
            hit.postDamage(entity -> {
                if (drainPrayer && hit.damage > 0) {
                    if (entity != null) {
                        if (entity.isPlayer()) {
                            entity.player.getPrayer().drain(hit.damage / 2);
                            entity.player.sendFilteredMessage(Attacks.PRAYER_DRAIN);
                            // System.out.println("Drained " + entity.player.getName() + "'s prayer.");
                        }
                    }
                }
            });

            npc.getCombat().getTarget().hit(hit);
            ///basicAttack();
        } else {
            forceMelee = false;
        }
    }

    private void smokeMagicAttack() {
        npc.animate(9181);
        npc.face(target);
        List<Player> targets = npc.localPlayers().stream().filter(t -> ProjectileRoute.allow(npc, t)).collect(Collectors.toList());
        targets.forEach(p -> {
            p.privateSound(183, 1, 0);
            int delay = Attacks.SMOKE_MAGIC_PROJECTILE.send(npc, p);
            boolean prayer = p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC);
            Hit hit = new Hit(npc, AttackStyle.MAGIC)
                    .randDamage(prayer ? (int) (Attacks.SMOKE_MAGIC_MAX_HIT * Attacks.SMOKE_MAGIC_PRAYER_EFFECTIVENESS) : Attacks.SMOKE_MAGIC_MAX_HIT)
                    .ignorePrayer()
                    .clientDelay(delay);
            hit.postDamage(t -> {
                if (hit.damage > 0) {
                    t.privateSound(185, 1, 0);
                    t.graphics(385, 124, 0);
                    t.poison(8);
                } else {
                    t.privateSound(227, 1, 0);
                    t.graphics(85, 124, 0); // splash
                    hit.hide();
                }
            });
            p.hit(hit);
        });
    }

    private void shadowRangedAttack() {
        npc.animate(9181);
        npc.face(target);
        // statdrain prayer
        List<Player> targets = npc.localPlayers().stream().filter(t -> ProjectileRoute.allow(npc, t)).collect(Collectors.toList());
        targets.forEach(p -> {
            int delay = Attacks.SHADOW_RANGE_PROJECTILE.send(npc, p);
            boolean prayer = p.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES);
            Hit hit = new Hit(npc, AttackStyle.RANGED)
                    .randDamage(prayer ? (int) (Attacks.SHADOW_RANGE_MAX_HIT * Attacks.SHADOW_RANGE_PRAYER_EFFECTIVENESS) : Attacks.SHADOW_RANGE_MAX_HIT)
                    .ignorePrayer()
                    .clientDelay(delay)
                    .postDamage((t) -> {
                        t.player.getPrayer().drain(NexUtils.hasSpectral(t.player) ? Random.get(1, Attacks.SHADOW_MAX_PRAYER_DRAIN - 1) : Random.get(1, Attacks.SHADOW_MAX_PRAYER_DRAIN));
                        t.player.sendFilteredMessage(Attacks.PRAYER_DRAIN);
                    });
            p.hit(hit);
        });
    }

    private void bloodMagicAttack() {
        npc.animate(9181);
        npc.face(target);
        List<Player> targets = npc.localPlayers().stream().filter(t -> ProjectileRoute.allow(npc, t)).collect(Collectors.toList());
        targets.forEach(p -> {
            p.privateSound(106, 1, 0);
            int delay = Attacks.BLOOD_MAGIC_PROJECTILE.send(npc, p);
            boolean prayer = p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC);
            Hit hit = new Hit(npc, AttackStyle.MAGIC)
                    .randDamage(prayer ? (int) (Attacks.BLOOD_MAGIC_MAX_HIT * Attacks.BLOOD_MAGIC_PRAYER_EFFECTIVENESS) : Attacks.BLOOD_MAGIC_MAX_HIT)
                    .ignorePrayer()
                    .clientDelay(delay);
            hit.postDamage(t -> {
                if (hit.damage > 0) {
                    t.privateSound(105, 1, 0);
                    t.graphics(377, 0, 0);
                    npc.incrementHp((int) (hit.damage * 0.25)); // heal 1/4 of dmg done
                } else {
                    t.privateSound(227, 1, 0);
                    t.graphics(85, 124, 0); // splash
                    hit.hide();
                }
            });
            p.hit(hit);
        });
    }

    private void iceMagicAttack() {
        npc.animate(9181);
        npc.face(target);
        List<Player> targets = npc.localPlayers().stream().filter(t -> ProjectileRoute.allow(npc, t)).collect(Collectors.toList());
        targets.forEach(p -> {
            p.privateSound(171, 1, 0);
            int delay = Attacks.ICE_MAGIC_PROJECTILE.send(npc, p);
            boolean prayer = p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC);
            Hit hit = new Hit(npc, AttackStyle.MAGIC)
                    .randDamage(prayer ? (int) (Attacks.ICE_MAGIC_MAX_HIT * Attacks.ICE_MAGIC_PRAYER_EFFECTIVENESS) : Attacks.ICE_MAGIC_MAX_HIT)
                    .ignorePrayer()
                    .clientDelay(delay);
            hit.postDamage(t -> {
                if (hit.damage > 0) {
                    t.privateSound(168, 1, 0);
                    t.graphics(369, 0, 0);
                    t.player.getPrayer().drain(NexUtils.hasSpectral(t.player) ? (int) (hit.damage * Attacks.ICE_MAGIC_PRAYER_DRAIN_SPECTRAL) : (int) (hit.damage * Attacks.ICE_MAGIC_PRAYER_DRAIN));
                    t.player.sendFilteredMessage(Attacks.PRAYER_DRAIN);
                    if (!prayer) {
                        if (!t.hasFreezeImmunity() && !t.isFrozen()) {
                            t.freeze(Attacks.ICE_MAGIC_FREEZE_DURATION, npc);
                            t.player.sendMessage("You have been frozen.");
//                            target.player.getPacketSender().sendWidget(Widget.BARRAGE, Attacks.ICE_MAGIC_FREEZE_DURATION);
                        }
                    }
                } else {
                    t.privateSound(227, 1, 0);
                    t.graphics(85, 124, 0);
                    hit.hide();
                }
            });
            p.hit(hit);
        });
    }

    private void zarosMagicAttack() {
        npc.animate(9181);
        npc.face(target);
        List<Player> targets = npc.localPlayers().stream().filter(t -> ProjectileRoute.allow(npc, t)).collect(Collectors.toList());
        targets.forEach(p -> {
            p.privateSound(171, 1, 0);
            int delay = Attacks.ZAROS_MAGIC_PROJECTILE.send(npc, p);
            boolean prayer = p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC);
            Hit hit = new Hit(npc, AttackStyle.MAGIC)
                    .randDamage(prayer ? (int) (Attacks.ZAROS_MAGIC_MAX_HIT * Attacks.ZAROS_MAGIC_PRAYER_EFFECTIVENESS) : Attacks.ZAROS_MAGIC_MAX_HIT)
                    .ignorePrayer()
                    .clientDelay(delay);
            hit.postDamage(t -> {
                if (hit.damage > 0) {
                    t.privateSound(3012, 1, 0);
                    t.graphics(2008, 124, 0);
                    t.player.getPrayer().drain(NexUtils.hasSpectral(t.player) ? (int) (Attacks.ZAROS_MAGIC_PRAYER_DRAIN * Attacks.ZAROS_MAGIC_PRAYER_DRAIN_SPECTRAL) : Attacks.ZAROS_MAGIC_PRAYER_DRAIN);
                    t.player.sendFilteredMessage(Attacks.PRAYER_DRAIN);
                } else {
                    t.privateSound(3015, 1, 0);
                    t.graphics(85, 124, 0); // splash
                    hit.hide();
                }
            });
            p.hit(hit);
        });
    }

    private void shadowSmashAttack() {
        npc.forceText(Attacks.SHADOW_SMASH);
        npc.animate(9186);
        npc.faceTemp(target);
        List<Player> targets = npc.localPlayers();
        targets.forEach(p -> {
            final Position pos = p.getPosition().copy();
            final GameObject shadow = new GameObject(Attacks.SHADOW_SMASH_OBJECT_ID, p.getPosition().copy(), 10, 1).spawn();
            shadow.graphics(Attacks.SHADOW_SMASH_GFX);
            Tile tile = Tile.get(pos.getX(), pos.getY(), pos.getZ(), true);
            World.startEvent(event -> {
                event.delay(Attacks.SHADOW_SMASH_DELAY);
                if (tile.playerCount > 0) {
                    targets.stream().filter(t -> t.getPosition().equals(pos)).forEach(noob -> {
                        Hit hit = new Hit(npc, AttackStyle.MAGIC)
                                .fixedDamage(Attacks.SHADOW_SMASH_MAX_HIT)
                                .ignoreDefence()
                                .ignorePrayer();
                        noob.hit(hit);
                        // System.out.println("Shadow Smash hit: " + noob.getName() + " with " + p.getName() + "'s shadow.");
                    });
                }
                if (shadow != null) {
                    if (shadow.isSpawned()) {
                        shadow.graphics(-1);
                        shadow.remove();
                    }
                }
            });
        });
    }

    private void chokeAttack() {
        Player infectedPlayer = null;
        int lowestMagicDef = Integer.MAX_VALUE;
        List<Player> targets = npc.localPlayers();
        for (Player p : targets) {
            int bonus = p.getEquipment().bonuses[EquipmentStats.MAGIC_DEFENCE];
            if (bonus >= lowestMagicDef) {
                continue;
            }
            lowestMagicDef = bonus;
            infectedPlayer = p;
        }
        npc.getCombat().delayAttack(4);
        npc.forceText(Attacks.CHOKE);
        npc.faceTemp(infectedPlayer);
        npc.animate(9186);

        // System.out.println("Infected lowest magic def:" + infectedPlayer.getName() + " @ " + lowestMagicDef + " magic def");

        final Player finalInfectedPlayer = infectedPlayer;
        NexUtils.plagueEffect(finalInfectedPlayer, false);  // make them contagious
        NexUtils.spreadInfection(finalInfectedPlayer);               // make sure they are coughing a good cough.
    }

    /**
     * @todo make a quick-enter?? Or remove.
     */
    private void skipIntro() {
        if (npc.getId() != Forms.UNATTACKABLE_NEX) {
            npc.transform(Forms.UNATTACKABLE_NEX);
        }
        npc.lock(LockType.FULL);

        // talk to minion noobs
        FUMUS_SPAWN = (new NPC(Fumus.ID).spawn(2913, 5215, npc.getPosition().getZ(), Direction.SOUTH_EAST, 0));
        FUMUS_SPAWN.setInvincible(true);
        //FUMUS_SPAWN.deathEndListener = (DeathListener.Simple) () -> minionDeathRemover(FUMUS_SPAWN);
        minions.add(FUMUS_SPAWN);
        Attacks.MINION_SPAWN_PROJ.send(FUMUS_SPAWN.getPosition(), new Position(Attacks.CENTER_POSITION_X, Attacks.CENTER_POSITION_Y, npc.getPosition().getZ()));
        // System.out.println("Spawned fumus. Is invincible.");

        UMBRA_SPAWN = new NPC(Umbra.ID).spawn(2937, 5215, npc.getPosition().getZ(), Direction.SOUTH_WEST, 0);
        UMBRA_SPAWN.setInvincible(true);
        //UMBRA_SPAWN.deathEndListener = (DeathListener.Simple) () -> minionDeathRemover(UMBRA_SPAWN);
        minions.add(UMBRA_SPAWN);
        Attacks.MINION_SPAWN_PROJ.send(UMBRA_SPAWN.getPosition(), new Position(Attacks.CENTER_POSITION_X, Attacks.CENTER_POSITION_Y, npc.getPosition().getZ()));
        // System.out.println("Spawned umbra. Is invincible.");

        CRUOR_SPAWN = new NPC(Cruor.ID).spawn(2937, 5191, npc.getPosition().getZ(), Direction.NORTH_WEST, 0);
        CRUOR_SPAWN.setInvincible(true);
//        CRUOR_SPAWN.deathEndListener = (DeathListener.Simple) () -> minionDeathRemover(CRUOR_SPAWN);
        CRUOR_SPAWN.deathEndListener = (DeathListener.Simple) () -> {
            phase = Phase.ICE;
            npc.forceText(Phase.ICE.getPhrase());
        };
        minions.add(CRUOR_SPAWN);
        Attacks.MINION_SPAWN_PROJ.send(CRUOR_SPAWN.getPosition(), new Position(Attacks.CENTER_POSITION_X, Attacks.CENTER_POSITION_Y, npc.getPosition().getZ()));
        // System.out.println("Spawned cruor. Is invincible.");

        GLACIES_SPAWN = new NPC(Glacies.ID).spawn(2913, 5191, 0, Direction.NORTH_EAST, 0);
        GLACIES_SPAWN.setInvincible(true);
        //GLACIES_SPAWN.deathEndListener = (DeathListener.Simple) () -> minionDeathRemover(GLACIES_SPAWN);
        minions.add(GLACIES_SPAWN);
        Attacks.MINION_SPAWN_PROJ.send(GLACIES_SPAWN.getPosition(), new Position(Attacks.CENTER_POSITION_X, Attacks.CENTER_POSITION_Y, npc.getPosition().getZ()));
        // System.out.println("Spawned glacies. Is invincible.");

        World.startEvent(event -> {
            event.delay(4);
            npc.transform(Forms.DEFAULT_NEX);
            phase = Phase.SMOKE;
            npc.unlock();
        });
    }

    private void handleIntro() {
        if (npc.getId() != Forms.UNATTACKABLE_NEX) {
            npc.transform(Forms.UNATTACKABLE_NEX);
        }
        npc.lock(LockType.FULL);
        npc.publicSound(3950, 1, 0);
        npc.startEvent(event -> {
            npc.face(Direction.EAST);
            npc.forceText(phase.getPhrase());
            event.delay(5);

            // talk to minion noobs
            npc.face(Direction.NORTH_WEST);
            npc.animate(9188);
            npc.forceText("Fumus!");
            FUMUS_SPAWN = new NPC(Fumus.ID).spawn(2913, 5215, npc.getPosition().getZ(), Direction.SOUTH_EAST, 0);
            FUMUS_SPAWN.setInvincible(true);
            minions.add(FUMUS_SPAWN);
            Attacks.MINION_SPAWN_PROJ.send(FUMUS_SPAWN.getPosition(), new Position(Attacks.CENTER_POSITION_X, Attacks.CENTER_POSITION_Y, npc.getPosition().getZ()));
            // System.out.println("Spawned fumus. Is invincible.");
            event.delay(4);

            npc.face(Direction.NORTH_EAST);
            npc.animate(9188);
            npc.forceText("Umbra!");
            UMBRA_SPAWN = new NPC(Umbra.ID).spawn(2937, 5215, npc.getPosition().getZ(), Direction.SOUTH_WEST, 0);
            UMBRA_SPAWN.setInvincible(true);
            minions.add(UMBRA_SPAWN);
            Attacks.MINION_SPAWN_PROJ.send(UMBRA_SPAWN.getPosition(), new Position(Attacks.CENTER_POSITION_X, Attacks.CENTER_POSITION_Y, npc.getPosition().getZ()));
            // System.out.println("Spawned umbra. Is invincible.");
            event.delay(4);

            npc.face(Direction.SOUTH_EAST);
            npc.animate(9188);
            npc.forceText("Cruor!");
            CRUOR_SPAWN = new NPC(Cruor.ID).spawn(2937, 5191, npc.getPosition().getZ(), Direction.NORTH_WEST, 0);
            CRUOR_SPAWN.setInvincible(true);
            CRUOR_SPAWN.deathEndListener = (DeathListener.Simple) () -> {
                phase = Phase.ICE;
                npc.forceText(Phase.ICE.getPhrase());
            };
            minions.add(CRUOR_SPAWN);
            Attacks.MINION_SPAWN_PROJ.send(CRUOR_SPAWN.getPosition(), new Position(Attacks.CENTER_POSITION_X, Attacks.CENTER_POSITION_Y, npc.getPosition().getZ()));
            // System.out.println("Spawned cruor. Is invincible.");
            event.delay(4);

            npc.face(Direction.SOUTH_WEST);
            npc.animate(9188);
            npc.forceText("Glacies!");
            GLACIES_SPAWN = new NPC(Glacies.ID).spawn(2913, 5191, 0, Direction.NORTH_EAST, 0);
            GLACIES_SPAWN.setInvincible(true);
            minions.add(GLACIES_SPAWN);
            Attacks.MINION_SPAWN_PROJ.send(GLACIES_SPAWN.getPosition(), new Position(Attacks.CENTER_POSITION_X, Attacks.CENTER_POSITION_Y, npc.getPosition().getZ()));
            // System.out.println("Spawned glacies. Is invincible.");
            event.delay(4);

            phase = Phase.SMOKE;
            npc.unlock();
            npc.transform(Forms.DEFAULT_NEX);
        });
    }

    /**
     * This is a config class mostly for nex and contains static things that aren't really about to be changing.
     * Also see NexUtils.java for
     */
    private static class Attacks {
        private static final int STALAGMITE_MIN_DAMAGE = 10;
        private static final int STALAGMITE_BONUS_DAMAGE = 10;
        static final String PRAYER_DRAIN = "Your prayer has been drained slightly.";
        //special attacks below
        static final String BLOOD_SACRIFICE = "I demand a blood sacrifice!";
        static final int BLOOD_SACRIFICE_GFX_SM = 1986;
        static final int BLOOD_SACRIFICE_GFX_MED = 1987;
        static final int BLOOD_SACRIFICE_GFX_LG = 1988;
        static final int BLOOD_SACRIFICE_GFX_POP = 1911;
        static final String CONTAIN = "Contain this!";
        static final String ICE_PRISON = "Die now, in a prison of ice!";
        static final int SIPHON_DURATION = 7; // 7 ticks
        static final String SIPHON = "A siphon will solve this!";
        static final String CHOKE = "Let the virus flow through you!";

        static final String SHADOW_SMASH = "Fear the shadow!";
        static final int SHADOW_SMASH_GFX = 383;
        static final int SHADOW_SMASH_DELAY = 3; // ticks
        static final int SHADOW_SMASH_MAX_HIT = 50;
        static final int SHADOW_SMASH_OBJECT_ID = 13721;

        //Stalagmite
        static final int UNATTACKABLE_STALAGMITE = 42943;
        static final int ATTACKABLE_STALAGMITE = 42944;
        static final int STALAGMITE_DIRECTION = 1;

        //Projectiles
        static final Projectile SOUL_SPLIT_PROJ = new Projectile(2009, 0, 43, 41, 46, 7, 16, 64);
        static final Projectile MINION_SPAWN_PROJ = new Projectile(2010, 43, 31, 51, 56, 10, 16, 64);
        static final Projectile WRATH_PROJ = new Projectile(2012, 43, 0, 51, 56, 10, 16, 64);

        //normal attacks below
        static final int MELEE_MAX_HIT = 30;
        static final double MELEE_PRAYER_EFFECTIVENESS = 0.8;

        static final int SMOKE_MAGIC_MAX_HIT = 32;
        static final Projectile SMOKE_MAGIC_PROJECTILE = new Projectile(1997, 43, 31, 51, 56, 10, 16, 64);
        static final double SMOKE_MAGIC_PRAYER_EFFECTIVENESS = 0.8;
        static final Projectile SHADOW_RANGE_PROJECTILE = new Projectile(1999, 43, 31, 51, 56, 10, 16, 64);
        static final int SHADOW_RANGE_MAX_HIT = 60;
        static final double SHADOW_RANGE_PRAYER_EFFECTIVENESS = 0.5;
        static final int SHADOW_MAX_PRAYER_DRAIN = 4;

        static final Projectile BLOOD_MAGIC_PROJECTILE = new Projectile(2000, 43, 31, 51, 56, 10, 16, 64);
        static final int BLOOD_MAGIC_MAX_HIT = 32;
        static final double BLOOD_MAGIC_PRAYER_EFFECTIVENESS = 0.8;

        static final Projectile ICE_MAGIC_PROJECTILE = new Projectile(2004, 43, 31, 51, 56, 10, 16, 64);
        static final int ICE_MAGIC_MAX_HIT = 32;
        static final double ICE_MAGIC_PRAYER_EFFECTIVENESS = 0.8;
        static final double ICE_MAGIC_PRAYER_DRAIN = 0.5; // 0.5 * damage
        static final double ICE_MAGIC_PRAYER_DRAIN_SPECTRAL = 0.3; // 0.3 * damage
        static final int ICE_MAGIC_FREEZE_DURATION = 30;

        static final Projectile ZAROS_MAGIC_PROJECTILE = new Projectile(2007, 43, 31, 51, 56, 10, 16, 64);
        static final int ZAROS_MAGIC_MAX_HIT = 32;
        static final double ZAROS_MAGIC_PRAYER_EFFECTIVENESS = 0.8;
        static final int ZAROS_MAGIC_PRAYER_DRAIN = 5; // 0.5 * damage
        static final double ZAROS_MAGIC_PRAYER_DRAIN_SPECTRAL = 0.8;

        private static final int CENTER_POSITION_X = 2925;
        private static final int CENTER_POSITION_Y = 5203;

        static {
            
                ObjectAction.register(Attacks.ATTACKABLE_STALAGMITE, "attack", (player, obj) -> {
                    final int simulatedAtkDmg = player.getCombat().simulateAttackWithMelee(true, true, true, AttackStyle.CRUSH, Attacks.STALAGMITE_BONUS_DAMAGE);
                    if (simulatedAtkDmg > -1) {
                        player.lock();
                        player.addEvent(event -> {
                            if (simulatedAtkDmg >= Attacks.STALAGMITE_MIN_DAMAGE) {
                                event.delay(1);
                                player.sendFilteredMessage("Your hit smashes the stalagmite to pieces!");
                                if (!obj.isRemoved()) {
                                    obj.remove();
                                }
                            } else {
                                player.sendFilteredMessage("Your hit " + Color.DARK_RED.wrap("wasn't") + " hard enough to shatter the Stalagmite.");
                            }
                            player.unlock();
                        });
                    }
                });
        }
    }

    static class Forms {
        static final int DEFAULT_NEX = 11278;
        static final int UNATTACKABLE_NEX = 11279;
        static final int SOUL_SPLIT_NEX = 11280;
        static final int REFLECT_MELEE_NEX = 11281;
        static final int WRATH_NEX = 11282;

        static boolean isNex(int npcId) {
            if (npcId == DEFAULT_NEX)
                return true;
            if (npcId == SOUL_SPLIT_NEX) {
                return true;
            }
            if (npcId == REFLECT_MELEE_NEX) {
                return true;
            }
            if (npcId == WRATH_NEX) {
                return true;
            }
            if (npcId == UNATTACKABLE_NEX) {
                return true;
            }
            return false;
        }

        static {
            NPCDef def1 = NPCDef.get(Forms.DEFAULT_NEX);
            def1.ignoreMultiCheck = true;

            NPCDef def2 = NPCDef.get(Forms.UNATTACKABLE_NEX);
            def2.ignoreMultiCheck = true;

            NPCDef def3 = NPCDef.get(Forms.SOUL_SPLIT_NEX);
            def3.ignoreMultiCheck = true;

            NPCDef def4 = NPCDef.get(Forms.WRATH_NEX);
            def4.ignoreMultiCheck = true;

            NPCDef def5 = NPCDef.get(Forms.REFLECT_MELEE_NEX);
            def5.ignoreMultiCheck = true;
        }
    }

}
