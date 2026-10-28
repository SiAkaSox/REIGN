package io.ruin.model.entity.npc;

import io.ruin.model.achievements.listeners.hard.ARealChallenge;
import io.ruin.model.inter.utils.Config;
import io.ruin.utility.NumberUtils;
import io.ruin.utility.Random;
import io.ruin.cache.Color;
import io.ruin.cache.NPCDef;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.model.World;
import io.ruin.model.achievements.listeners.elite.NotJustAFragment;
import io.ruin.model.achievements.listeners.master.ChickenKiller;
import io.ruin.model.activities.timerift.TaskKills;
import io.ruin.model.activities.timerift.TimeRiftEvent;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.combat.*;
import io.ruin.model.content.accountperks.Perk;
import io.ruin.model.content.accountperks.PerkManager;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.combattasks.CombatTaskKills;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.GoldCasket;
import io.ruin.model.item.actions.impl.WildernessKey;
import io.ruin.model.item.actions.impl.jewellery.BraceletOfEthereum;
import io.ruin.model.item.actions.impl.jewellery.RingOfWealth;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Graphic;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.actions.impl.dungeons.KourendCatacombs;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.skills.slayer.SlayerMaster;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Broadcast;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.cache.ItemID.VORKATHS_HEAD;
import static io.ruin.model.content.accountperks.Perk.ANIMAL_LOVER;

public abstract class NPCCombat extends Combat {

    protected NPC npc;

    protected npc_combat.Info info;

    protected Stat[] stats;

    private int[] bonuses;

    private boolean allowRespawn = true;

    @Setter
    @Getter
    private boolean allowRetaliate = true;

    public final NPCCombat init(NPC npc, npc_combat.Info info) {
        this.npc = npc;
        this.info = info;
        this.stats = new Stat[]{
                new Stat(info.attack),      //0
                new Stat(info.defence),     //1
                new Stat(info.strength),    //2
                new Stat(info.hitpoints),   //3
                new Stat(info.ranged),      //4
                new Stat(1),           //5 (prayer, not used)
                new Stat(info.magic)        //6
        };
        this.bonuses = new int[]{
                /*
                 * Attack bonuses
                */
                info.stab_attack,
                info.slash_attack,
                info.crush_attack,
                info.magic_attack,
                info.ranged_attack,
                /*
                 * Defence bonuses
                 */
                info.stab_defence,
                info.slash_defence,
                info.crush_defence,
                info.magic_defence,
                info.ranged_defence,
        };
        init();
        return this;
    }

    public void updateInfo(npc_combat.Info newInfo) { // only used when transforming!
        info = newInfo;
        if (stats[0].fixedLevel != newInfo.attack) stats[0] = new Stat(newInfo.attack);
        if (stats[1].fixedLevel != newInfo.defence) stats[1] = new Stat(newInfo.defence);
        if (stats[2].fixedLevel != newInfo.strength) stats[2] = new Stat(newInfo.strength);
        if (stats[3].fixedLevel != newInfo.hitpoints) stats[3] = new Stat(newInfo.hitpoints);
        if (stats[4].fixedLevel != newInfo.ranged) stats[4] = new Stat(newInfo.ranged);
        if (stats[6].fixedLevel != newInfo.magic) stats[6] = new Stat(newInfo.magic);

        this.bonuses = new int[]{ // bonuses cannot be changed so we can just set to the new ones
                /*
                 * Attack bonuses
                */
                info.stab_attack,
                info.slash_attack,
                info.crush_attack,
                info.magic_attack,
                info.ranged_attack,
                /*
                 * Defence bonuses
                */
                info.stab_defence,
                info.slash_defence,
                info.crush_defence,
                info.magic_defence,
                info.ranged_defence,
        };
    }

    public final List<Entity> getPossibleTargets() {
        return this.getPossibleTargets(this.npc, true);
    }

    public final List<Entity> getPossibleTargets(NPC npc, boolean playersOnly) {
        return this.getPossibleTargets(npc, playersOnly, false, true);
    }

    public final List<Entity> getPossibleTargets(NPC npc, boolean playersOnly, boolean ignoreProjectilePathing, boolean checkAttackBack) {
        ArrayList<Entity> targets = new ArrayList();
        Iterator var6 = npc.localPlayers().iterator();

        while (true) {
            Player localPlayer;
            do {
                do {
                    do {
                        if (!var6.hasNext()) {
                            if (!playersOnly) {
                                var6 = npc.localNpcs().iterator();

                                while (var6.hasNext()) {
                                    NPC n = (NPC) var6.next();
                                    if (n != null && n.getCombat() != null && !n.getCombat().isDead() && this.canAttack(n)) {
                                        targets.add(n);
                                    }
                                }
                            }

                            return targets;
                        }

                        localPlayer = (Player) var6.next();
                    } while (localPlayer == null);
                } while (localPlayer.getCombat().isDead());
            } while (checkAttackBack && !localPlayer.getCombat().canAttack(npc, false));

            if (this.canAttack(localPlayer)) {
                targets.add(localPlayer);
            }
        }
    }

    /**
     * Following
     */
    public final void follow0() {
        checkAggression();
        if (target == null || npc.isLocked()) //why can an npc be locked but still have a target.. hmm..
            return;
        if (!canAttack(target)) {
            reset();
            return;
        }
        follow();
    }

    protected void follow(int distance) {
        DumbRoute.step(npc, target, distance);
    }

    protected boolean withinDistance(int distance) {
        return DumbRoute.withinDistance(npc, target, distance);
    }

    /**
     * Attacking
     */
    public final void attack0() {
        if (target == null || hasAttackDelay() || npc.isLocked() || !attack())
            return;
        updateLastAttack(info.attack_ticks);
    }

    public void tick() {

    }

    public boolean canAttack(Entity target) {
        if (isDead())
            return false;
        if (npc == null)
            return false;
        if (npc.isHidden())
            return false;
        if (npc.isRemoved() || npc.dead())
            return false;
        if (target == null || target.isHidden())
            return false;
        if (target.getCombat() == null)
            return false;
        if (target.getCombat().isDead())
            return false;
        if (!multiCheck(target))
            return false;
        if (npc.targetPlayer == null) {
            if (!npc.getPosition().isWithinDistance(target.getPosition()))
                return false;
            Bounds attackBounds = npc.attackBounds;
            if (attackBounds != null && !npc.getPosition().inBounds(attackBounds)) {
                DumbRoute.route(npc, npc.spawnPosition.getX(), npc.spawnPosition.getY());
                //possibly consider resetting the monster to prevent abusing this mechanic
                return false;
            }
        }
        return true;
    }

    public boolean multiCheck(Entity target) {
        return npc.inMulti() || target.getCombat().allowPj(npc);
    }

    protected Hit basicAttack() {
        return basicAttack(info.attack_animation, info.attack_style, info.max_damage);
    }

    protected Hit basicAttack(int animation, AttackStyle attackStyle, int maxDamage) {
        npc.animate(animation);
        defendAnim();
        Hit hit = new Hit(npc, attackStyle, null).randDamage(maxDamage);
        target.hit(hit);
        return hit;
    }

    protected Hit projectileAttack(Projectile projectile, int animation, AttackStyle attackStyle, int maxDamage) {
        return projectileAttack(projectile, animation, Graphic.builder().id(-1).build(), attackStyle, maxDamage);
    }

    protected Hit projectileAttack(Projectile projectile, int animation, Graphic hitgfx, AttackStyle attackStyle, int maxDamage) {
        return projectileAttack(projectile, animation, hitgfx, Graphic.builder().id(-1).build(), attackStyle, maxDamage, false);
    }
    protected Hit projectileAttack(Projectile projectile, int animation, Graphic hitgfx, Graphic splashgfx, AttackStyle attackStyle, int maxDamage, boolean ignorePrayer) {
        if (animation != -1)
            npc.animate(animation);
        int delay = projectile.send(npc, target);
        Hit hit = new Hit(npc, attackStyle, null).randDamage(maxDamage).clientDelay(delay);
        hit.afterPostDamage(e -> {
            boolean splash = hit.isBlocked();
            if (target != null) {
                target.graphics(
                        splash ? splashgfx.getId() : hitgfx.getId(),
                        splash ? splashgfx.getHeight() : hitgfx.getHeight(),
                        splash ? splashgfx.getDelay() : hitgfx.getDelay()
                );
                if (splash ? splashgfx.getSoundId() != -1 : hitgfx.getSoundId() != -1) {
                    target.publicSound(
                            splash ? splashgfx.getSoundId() : hitgfx.getSoundId(),
                            splash ? splashgfx.getSoundType() : hitgfx.getSoundType(),
                            splash ? splashgfx.getSoundDelay() : hitgfx.getSoundDelay()
                    );
                }
            }
        });

        target.hit(hit);
        if (attackStyle == null || !attackStyle.isMagicalMelee()) {
            defendAnim(delay);
        }
        return hit;
    }

    /**
     * Reset
     */
    @Override
    public void reset() {
        super.reset();
        npc.faceNone(!isDead());
        TargetRoute.reset(npc);
    }

    private void handleWorldEventKills(NPC npc) {
        Killer killer = getKiller();
        Player player = killer.player;

        if (player != null) {
            WorldEventKills.kills(player, npc.getId());
        }
    }

    private void handleCombatTaskKills(NPC npc) {
        Killer killer = getKiller();
        Player player = killer.player;
        if (player != null) {
            CombatTaskKills.kills(player, npc.getId());
        }
    }

    /**
     * Death
     */
    @Override
    public void startDeath(Hit killHit) {
        setDead(true);

        if (target != null)
            reset();
        Killer killer = getKiller();
        if (npc.deathStartListener != null) {
            npc.deathStartListener.handle(npc, killer, killHit);
            if (npc.isRemoved())
                return;
        }
        npc.startEvent(event -> {
            npc.lock();
            event.delay(1);
            if (info.death_animation != -1)
                npc.animate(info.death_animation);
            if (info.death_ticks > 0)
                event.delay(info.death_ticks);

            if (killer != null && killer.player != null) {
                SlayerMaster.handle(killer.player, npc);
                if (npc.getDef().killCounter != null)
                    npc.getDef().killCounter.apply(killer.player).increment(killer.player);

                if (info.pet != null) {
                    int dropAverage = info.pet.dropAverage;
                    if (PerkManager.isActive(killer.player, ANIMAL_LOVER) && dropAverage > 0)
                        dropAverage /= 2;
                    if (info.pet != null && Random.rollDie(dropAverage))
                        info.pet.unlock(killer.player);
                }
                //handleCombatTaskKills(npc);
                handleWorldEventKills(npc);

                TaskKills.kills(killer.player, npc.getId());

//                DailyTask.checkNPCKill(killer.player, npc);
//                DailyTaskMedium.checkNPCKill(killer.player, npc);
//                DailyTaskHard.checkNPCKill(killer.player, npc);
//                DailyTaskElite.checkNPCKill(killer.player, npc);

                //IndividualTasks.checkNPCKill(killer.player, npc);
                //TeamTasks.checkNPCKill(killer.player, npc);
                dropLarens(killer.player, npc);
               // DemonSlayer.check(killer.player, npc);
                ChickenKiller.check(killer.player, npc);
                dropStrangeCasket(killer.player, npc);
                ARealChallenge.check(killer.player, npc);

            }

            dropItems(killer);

            if (npc.deathEndListener != null) {
                npc.deathEndListener.handle(npc, killer, killHit);
                if (npc.isRemoved())
                    return;
            } else if (info.respawn_ticks < 0) {
                npc.remove();
                return;
            }

            if (info.respawn_ticks > 0)
                npc.setHidden(true);
            if (!allowRespawn())
                return;

            event.delay(info.respawn_ticks);
            respawn();
        });
    }

    private void dropStrangeCasket(Player player, NPC npc) {
        int hitpoints = npc.getMaxHp();
        int combatLevel = npc.getDef().combatLevel;
        double hitpointsFactor = hitpoints * 0.1;
        double combatLevelFactor = combatLevel * 0.2;
        double dropChance = hitpointsFactor + combatLevelFactor;
        double odds = (dropChance / 10_000D);
        boolean hit = Math.random() <= odds;
        if (hit) {
            new GroundItem(23184, 1).owner(player).position(npc.getPosition()).spawn();
            player.sendMessage("<col=ff0000>Untradable drop: Strange Casket");
        }
    }

    private void dropLarens(Player player, NPC npc) {//zombie pirates only.
        List<Integer> zombie_pirate = Arrays.asList(563, 564, 565, 566, 567, 568, 569, 570, 571, 572, 573, 574, 575, 576, 577, 578, 579, 580, 581, 582, 583, 584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597, 598, 599, 613, 614, 615, 616, 617, 618);
        if (!zombie_pirate.stream().anyMatch(z -> z.intValue() == npc.getId()))
            return;
        double chance = Math.random();
        double probability = 1.0 / 362;
        if (chance <= probability) {
            new GroundItem(23490, 1).owner(player).position(npc.getPosition()).spawn();
            player.sendMessage("<col=ff0000>Untradable drop: Larran's key");
        }
    }

    public final void respawn() {
        npc.animate(info.spawn_animation);
        npc.getPosition().copy(npc.spawnPosition);
        resetKillers();
        restore();
        setDead(false);
        npc.setHidden(false);
        if (npc.respawnListener != null)
            npc.respawnListener.onRespawn(npc);
        npc.unlock();
    }

    public void setAllowRespawn(boolean allowRespawn) {
        this.allowRespawn = allowRespawn;
    }

    public boolean allowRespawn() {
        return allowRespawn;
    }

    public void dropItems(Killer killer) {
        NPCDef def = npc.getDef();
        Position dropPosition = getDropPosition();
        Player pKiller = killer == null ? null : killer.player;
        if (pKiller == null
                || pKiller.getPosition().inBounds(TimeRiftEvent.ACTIVE.bounds)
                || pKiller.getPosition().regionId() == 11335) {
            return;
        }

        /*
         * Drop table loots
         */
        LootTable t = def.lootTable;
        if (t != null) {
            int rolls = getRandomDropCount();
            for (int i = 0; i < rolls; i++) {
                List<Item> items = t.rollItems(i == 0);
                if (items != null) {
                    handleDrop(killer, dropPosition, pKiller, items);
                }
            }
        }

        checkPerk(pKiller, npc);
        /*
         * Handle giving player vorkaths head after 50 kills.
         */
        vorkathHead(dropPosition, pKiller);

        serenKills(dropPosition, pKiller);

        /*
         * Gives players PVM Points
         */
        //PvmPoints.addPoints(pKiller, npc);
        /*
         * Casket loots
         */
        GoldCasket.drop(pKiller, npc, dropPosition);
        /*
         * Summer Loot
         */
        //SummerTokens.npcKill(pKiller, npc, dropPosition);
        /*
         * Catacombs loot
         */
        KourendCatacombs.drop(pKiller, npc, dropPosition);

        if (npc.getId() == 6610 || npc.getId() == 6609 || npc.getId() == 6615 || npc.getId() == 6611 || npc.getId() == 7858
                || npc.getId() == 7859 || npc.getId() == 7860) {
            if (Random.rollDie(20, 1)) {
                int amount = Random.get(15, 40);
                pKiller.getInventory().addOrDrop(13307, amount * pKiller.babyLavaDragBonus);
                pKiller.sendMessage("You notice <col=FF0000>x" + amount + " Blood Money</col> drop from the monster.");
            }
        }

        if (npc.getId() == 11246) {
            pKiller.getInventory().addOrDrop(21813, 1);
            pKiller.getInventory().addOrDrop(11158, 1);
        }

        /*
         * Roll for OSRS wilderness key
         */
        if (World.wildernessKeyEvent)
            WildernessKey.rollForWildernessBossKill(pKiller, npc);

        /*
         * PvP Item loots
         */
        Wilderness.rollPvPItemDrop(pKiller, npc, dropPosition);

        /*
         * Roll for wilderness clue key
         */
        Wilderness.rollClueKeyDrop(pKiller, npc, dropPosition);

        //Roll for looting bag
        Wilderness.rollLootingBag(pKiller, npc);

        /*
         * Blood Money
         */
        //Wilderness.bloodMoneyDrop(pKiller, npc);

        /*
         * Resource packs
         */
        //Wilderness.resourcePackWithBoss(pKiller, npc);

    }

    private void checkPerk(Player player, NPC npc) {
        if (npc.getDef() == null || !PerkManager.isActive(player, Perk.SARADOMIN_BLESSING))
            return;
        int restore = (int) Math.min(npc.getDef().combatLevel * 0.015, 4);
        player.restorePrayerPoints(restore);
        player.sendFilteredMessage("Your Saradomins Blessing perk effect uses the power of Saradomin and restores " + restore + " prayer points into you.");

    }

    private void vorkathHead(Position dropPosition, Player pKiller) {
        if (pKiller.vorkathKills.getKills() == 50 && !pKiller.obtained50KCVorkathHead) {
            Item item = new Item(VORKATHS_HEAD);
            GroundItem groundItem = new GroundItem(item).position(dropPosition);
            groundItem.spawn();
            pKiller.obtained50KCVorkathHead = true;
        }
    }

    private void revMaledictus(Position dropPosition, Player pKiller) {
        Item item = new Item(21813);
        Item mbox = new Item(11158);
        GroundItem groundItem = new GroundItem(item).position(dropPosition);
        groundItem.spawn();
        GroundItem groundItem1 = new GroundItem(mbox).position(dropPosition);
        groundItem1.spawn();
    }

    public static int SEREN_SHARD = 31100;

    private void serenKills(Position dropPosition, Player pKiller) {
        if (pKiller.serenKills.getKills() >= 25 && !pKiller.obtained25KCSeren) {
            Item item = new Item(SEREN_SHARD);
            GroundItem groundItem = new GroundItem(item).position(dropPosition);
            groundItem.spawn();
            pKiller.obtained25KCSeren = true;
            NotJustAFragment.complete(pKiller);
        }
    }


    private void handleDrop(Killer killer, Position dropPosition, Player pKiller, List<Item> items) {
        for (Item item : items) {
            if (item != null && pKiller != null) {
                pKiller.getCollectionLog().checkItemReceived(item);
            }
            if (pKiller != null) {
                if (pKiller.isEasyModeWithEasyXP() && item.getDef() != null && item.getDef().free) {
                    continue;
                }
            }
            if (item.getId() == 21820) {
                if (BraceletOfEthereum.handleEthereumDrop(pKiller, item)) {
                    continue;
                }
            }
            if (item.getId() == 12934) {
                assert pKiller != null;
                if (PerkManager.isActive(pKiller, Perk.SERPENT_HUNTER)) {
                    item.setAmount(item.getAmount() + 250);
                }
            }
            final int[] CLUES = {2677, 2801, 2722, 12073, 19835};
            for (int clues : CLUES) {
                if (item.getId() == clues) {
                    assert pKiller != null;
                    if (PerkManager.isActive(pKiller, Perk.TREASURE_HUNTER)) {
                        item.setAmount(2);
                    }
                }
            }
            if (item.getId() == COINS_995) {
                if (RingOfWealth.check(pKiller, item)) {
                    pKiller.getInventory().addOrDrop(item);
                    continue;
                }
            }
            boolean dropItem = true;
            for (Item equipment : pKiller.getEquipment().getItems()) {
                if (equipment != null && equipment.getDef() != null) {
                    List<String> upgrades = AttributeExtensions.getEffectUpgrades(equipment);
                    boolean hasEffect = upgrades != null;
                    if (hasEffect) {
                        for (String s : upgrades) {
                            try {
                                if (s.equalsIgnoreCase("empty"))
                                    continue;
                                ItemEffect effect = ItemEffect.valueOf(s);
                                dropItem = effect.getUpgrade().modifyDroppedItem(pKiller, item);
                            } catch (Exception ex) {
                                System.err.println("Unknown upgrade { " + s + " } found!");
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }

            //CollectionLog.CollectionLogNpcCombatDrop(pKiller, item);

            /*
             * Donator Benefit: [Noted dragon bones in wilderness]
             */
            if (item.getId() == 534 || item.getId() == 536 || item.getId() == 6812 || item.getId() == 11943 || item.getId() == 22124) {
                if (pKiller.isSapphire() && pKiller.wildernessLevel > 0) {
                    item.setId(item.getDef().notedId);
                }
            }

            /*
             * Donator Benefit: [Noted herbs in wilderness]
             */
            if (item.getDef().name.toLowerCase().contains("grimy")) {
                if (pKiller.isDiamond() && pKiller.wildernessLevel > 0) {
                    if (item.getDef().notedId > -1)
                        item.setId(item.getDef().notedId);
                }
            }

            if (item.getId() == 2359 && npc.getId() == 2919) {
                if (Config.UNLOCK_DULY_NOTED.get(pKiller) == 1) {
                    item.setId(item.getDef().notedId);
                }
            }

            if (item.getDef().name.toLowerCase().contains("statius") ||
                    item.getDef().name.toLowerCase().contains("vesta") ||
                    item.getDef().name.toLowerCase().contains("zuriel")) {
                pKiller.sendMessage("You have been high-risk skulled and tele-blocked because of your loot!");
                pKiller.getCombat().skullNormal();
                pKiller.getCombat().teleblock();
            }

            /*
             * Modify drop for specific npc
             */
            if (npc.dropListener != null)
                npc.dropListener.dropping(killer, item);

            /*
             * Global Broadcast
             */
            if (item.lootBroadcast != null || item.getDef().dropAnnounce) {
                getRareDropAnnounce(pKiller, item);
            }

            /*
             * Local Broadcast!
             */
            if (info.local_loot) {
                getLocalAnnounce(pKiller, item);
            }


            /*
             * Spawn the item on the ground.
             */
            if (dropItem)
                new GroundItem(item).position(dropPosition).owner(pKiller).spawn();
        }
    }

    private void getRareDropAnnounce(Player pKiller, Item item) {
        int amount = item.getAmount();
        String message = pKiller.name() + " has just received <col=8B0000>";
        if (amount > 1)
            message += NumberUtils.formatNumber(amount) + " x " + item.getDef().name;
        else
            message += item.getDef().descriptiveName;
        if (item.lootBroadcast != null) {
            item.lootBroadcast.sendLoot(pKiller, message + " <col=000000>from <col=8B0000>" + npc.getDef().descriptiveName + "!");
        } else {
            Broadcast.GLOBAL.sendLoot(pKiller, message + " <col=000000>from <col=8B0000>" + npc.getDef().descriptiveName + "!");
        }
    }

    private void getLocalAnnounce(Player pKiller, Item item) {
        npc.localPlayers().forEach(p -> p.sendMessage(Color.DARK_GREEN.wrap(pKiller.name() + " received a drop: " + NumberUtils.formatNumber(item.getAmount()) + " x " + item.getDef().name)));
    }

    public Position getDropPosition() {
        return npc.getPosition();
    }

    public void restore() {
        for (Stat stat : stats)
            stat.restore();
        npc.resetFreeze();
        npc.cureVenom(0);
    }

    /**
     * Misc
     */
    @Override
    public boolean allowRetaliate(Entity attacker) {
        if (npc.targetPlayer != null && attacker != npc.targetPlayer) //npc has a designated target
            return false;
        if (npc.isLocked())
            return false;
        if (!allowRetaliate)
            return false;
        if (target != null && target.getCombat().getTarget() == npc) { //npc is being attacked by target
            boolean prioritizePlayer = target.npc != null && attacker.player != null; //target is an npc and attacker is a player
            if (!prioritizePlayer)
                return false;
        }
        return true;
    }

    @Override
    public AttackStyle getAttackStyle() {
        return info.attack_style;
    }

    @Override
    public AttackType getAttackType() {
        return null;
    }

    @Override
    public double getLevel(StatType statType) {
        int i = statType.ordinal();
        return i >= stats.length ? 0 : stats[i].currentLevel;
    }

    public Stat getStat(StatType statType) {
        return stats[statType.ordinal()];
    }

    @Override
    public double getBonus(int bonusType) {
        return bonusType >= bonuses.length ? 0 : bonuses[bonusType];
    }

    @Override
    public Killer getKiller() {
        if (npc.targetPlayer != null) {
            Killer killer = new Killer();
            killer.player = npc.targetPlayer;
            return killer;
        }
        //Player's didn't like this mechanic so we removed it.
       /* if (killers.entrySet().stream().filter(e -> e.getValue().player != null).anyMatch(e -> e.getValue().player.getGameMode().isIronMan()) // ironman did damage
                && killers.entrySet().stream().filter(e -> e.getValue().player != null).anyMatch(e -> !e.getValue().player.getGameMode().isIronMan())) { // but so did a non-ironman :(
            killers.entrySet().removeIf(e -> e.getValue().player != null && e.getValue().player.getGameMode().isIronMan()); // remove all iron men from potential killer list
        }*/
        return super.getKiller();
    }

    @Override
    public int getDefendAnimation() {
        return info.defend_animation;
    }

    public int getMaxDamage() {
        return info.max_damage;
    }

    public npc_combat.Info getInfo() {
        return info;
    }

    @Override
    public double getDragonfireResistance() {
        return 0;
    }

    public final void checkAggression() {
        if (npc == null || npc.isRemoved() || npc.dead() || npc.isHidden())
            return;
        if (target == null && isAggressive()) {
            target = findAggressionTarget();
            if (target != null)
                faceTarget();
        }
    }

    protected Entity findAggressionTarget() {
        if (npc == null)
            return null;
        if (npc.isRemoved() || npc.dead() || npc.isHidden())
            return null;
        if (npc.localPlayers().isEmpty())
            return null;
        if (npc.hasTarget())
            return null;
        List<Player> targets = npc.localPlayers().stream()
                .filter(this::canAggro)
                .collect(Collectors.toList()); // i don't mind if this is done in a different way as long as it picks a RANDOM target that passes the canAggro check
        if (targets.isEmpty())
            return null;
        return Random.get(targets);
    }

    protected int getAggressiveLevel() {
        if (npc.wildernessSpawnLevel > 0)
            return Integer.MAX_VALUE;
        else if (info.aggressive_level == -1)
            return npc.getDef().combatLevel * 2;
        else
            return info.aggressive_level;
    }

    public boolean isAggressive() {
        return getInfo().aggressive_level != 0;
    }

    protected boolean canAggro(Player player) {
        int aggroLevel = getAggressiveLevel();
        return player.getCombat().getLevel() <= aggroLevel // in level range
                && canAttack(player) // can attack
                && !player.isIdle // player isn't idle
                && (player.inMulti() || !player.getCombat().isDefending(12))
                && DumbRoute.withinDistance(npc, player, getAggressionRange()) // distance and line of sight
                && (npc.inMulti() || (StreamSupport.stream(npc.localNpcs().spliterator(), false)
                .noneMatch(n -> n.getCombat() != null && n.getCombat().getTarget() == player && !n.getCombat().isAttacking(10) && !n.getMovement().isAtDestination()))) // not 100% sure how i feel about this check, but it ensures multiple npcs don't try to go for the same player at the same time in a single-way zone since they wouldn't be able to attack upon reaching
                && (npc.aggressionImmunity == null || !npc.aggressionImmunity.test(player));

    }

    public int getAggressionRange() {
        if (npc.getId() == 4127)
            return 12;//troll spectator..
        return npc.wildernessSpawnLevel > 0 ? 2 : 4;
    }

    public int getAttackBoundsRange() {
        return 12;
    }

    @Override
    public void faceTarget() {
        npc.face(target);
    }

    /**
     * Handler functions
     */
    public abstract void init();

    public abstract void follow();

    public abstract boolean attack();

    public int getRandomDropCount() {
        return info.random_drop_count;
    }

    public void customAttackBounds() {
    }

}