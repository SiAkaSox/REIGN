package io.ruin.model.entity.player;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.activities.wilderness.events.capturetheflag.CaptureTheFlag;
import io.ruin.model.item.actions.impl.EscapeCrystal;
import io.ruin.utility.*;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.cache.ItemID;
import io.ruin.model.World;
import io.ruin.model.activities.bosses.zulrah.Zulrah;
import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.activities.wilderness.pvp.leaderboard.Leaderboard;
import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.activities.wilderness.BloodyChest;
import io.ruin.model.activities.wilderness.pvp.unrated.Arena1v1Queue;
import io.ruin.model.combat.*;
import io.ruin.model.combat.special.Special;
import io.ruin.model.combat.special.magic.Dawnbringer;
import io.ruin.model.combat.special.magic.StaffOfTheDead;
import io.ruin.model.combat.special.melee.DragonWarhammer;
import io.ruin.model.combat.special.melee.VestasSpear;
import io.ruin.model.combat.special.ranged.DragonThrownaxe;
import io.ruin.model.combat.special.ranged.ToxicBlowpipe;
import io.ruin.model.content.accountperks.Perk;
import io.ruin.model.content.accountperks.PerkManager;
import io.ruin.model.content.upgrade.ItemEffect;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.handlers.EquipmentStats;
import io.ruin.model.inter.handlers.IKOD;
import io.ruin.model.inter.handlers.TabCombat;
import io.ruin.model.inter.journal.toggles.TargetOverlay;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.TransformationRing;
import io.ruin.model.item.actions.impl.chargable.*;
import io.ruin.model.item.actions.impl.combine.SlayerHelm;
import io.ruin.model.item.actions.impl.jewellery.PhoenixNecklace;
import io.ruin.model.item.actions.impl.jewellery.RingOfLife;
import io.ruin.model.item.actions.impl.jewellery.RingOfRecoil;
import io.ruin.model.item.actions.impl.jewellery.RingOfSuffering;
import io.ruin.model.item.actions.impl.skillcapes.DefenceSkillCape;
import io.ruin.model.item.attributes.AttributeExtensions;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.item.lootkey.LootKeyManager;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.route.routes.TargetRoute;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.skills.magic.spells.TargetSpell;
import io.ruin.model.skills.magic.spells.lunar.Vengeance;
import io.ruin.model.skills.prayer.Prayer;
import io.ruin.model.skills.prayer.Redemption;
import io.ruin.model.skills.prayer.Retribution;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.utility.Random;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.ruin.model.content.accountperks.Perk.QUICK_DRAW;
import static io.ruin.model.content.accountperks.Perk.SECOND_WIND;
import static io.ruin.model.inter.interfaceutils.Interface.COMBAT_OPTIONS;

@Slf4j
public class PlayerCombat extends Combat {

    private LootKeyManager lootKeyManager;

    public PlayerCombat() {
        lootKeyManager = new LootKeyManager(p);
    }

    private Player p;

    public Entity lastTarget;

    public transient Player lastTargetPrio;

    private int lastTargetTimeoutTicks;

    public void init(Player player) {
        this.p = player;
        player.hitListener = new HitListener()
                //hits to this player
                .preDefend(this::preDefend)
                .postDefend(this::postDefend)
                .preDamage(this::preDamage)
                .postDamage(this::postDamage)
                //hits from this player
                .preTargetDefend(this::preTargetDefend)
                .postTargetDefend(this::postTargetDefend)
                .preTargetDamage(this::preTargetDamage)
                .postTargetDamage(this::postTargetDamage);
    }

    public void start() { //different than init because player isn't online yet when init happens
        setLevel();
        if (highRiskSkull)
            p.getAppearance().setSkullIcon(KillingSpree.overheadId(p));
        else if (skullDelay > 0)
            p.getAppearance().setSkullIcon(KillingSpree.overheadId(p));
    }


    /**
     * Target
     */

    @Override
    public void setTarget(Entity target) {
        updateLastTarget(target);
        super.setTarget(target);
        TargetOverlay.set(p, target);
        if (target.player != null)
            Config.PLAYER_PRIORITY.set(p, target.getIndex());
    }

    private void updateLastTarget(Entity target) {
        lastTarget = target;
        lastTargetTimeoutTicks = 5;
        if (lastTarget != null && lastTarget.isPlayer()) {
            Player targ = (Player) lastTarget;
            boolean noNully = targ.getCombat().lastTargetPrio != null;
            boolean same = noNully && Objects.equals(p.getUUID(), targ.getCombat().lastTargetPrio.getUUID());
            if (targ.getCombat().lastTargetPrio == null || !same) {
                targ.getCombat().lastTargetPrio = p;
            }
        }
    }

    private void checkLastTarget() {
        if (lastTargetTimeoutTicks > 0 && --lastTargetTimeoutTicks == 0)
            lastTarget = null;
    }

    /**
     * Pre-attack
     */

    public void preAttack() {
        depleteTb();
        depleteSkull();
        depleteCharge();
        checkLastTarget();
        checkGraniteMaul();
        if (p.dragonfireShieldSpecial)
            if (canAttack(target, true))
                DragonfireShield.specialAttack(p, target);
            else
                p.dragonfireShieldSpecial = false;
        if (target != null) {
            if (!canAttack(target, true))
                reset();
            else
                TargetRoute.set(p, target, useSpell() || (wearsNightmareStaff(p) && specialActive != null) ? 10 :
                        Math.min((weaponType.maxDistance + (getAttackType() == AttackType.LONG_RANGED ? 2 : 0)), 10));
        }
    }

    public void combatSound(int id) {
        if (id == -1)
            return;
        p.privateSound(id);
        if (target == null)
            return;
        if (target instanceof Player) {
            target.player.privateSound(id);
        }
    }

    private boolean wearsNightmareStaff(Player player) {
        final Item item = player.getEquipment().get(3);
        if (item != null) {
            int weaponId = item.getId();
            return weaponId == 24422 || weaponId == 24424 || weaponId == 24425;
        }
        return false;
    }

    public boolean hasDawnbringer() {
        return p.getEquipment().getId(Equipment.SLOT_WEAPON) == 22516;
    }

    /**
     * Attack checks
     */

    public boolean canAttack(Entity target, boolean message) {
        if (isDead()) {
            System.err.println("is dead.. cannot attack "+this.isDead()+" "+p.name());
            return false;
        }
        if (target == null || target.isHidden()) {
            System.err.println("is bugged or hidden.. cannot attack "+(target == null));
            return false;
        }
        if (Arena1v1Queue.inArena)
            return true;
        if (target.isNPC()) {
            NPC n = (NPC) target;
            if (n.getId() == 9025)//tornados hunleff
                return false;
        }
        if (target.getCombat() == null) {
            System.err.println("2 is bugged npc has no cb script combatNull="+(target == null));
            if (message)
                p.sendMessage("You can't attack that npc.");
            return false;
        }
        if (target.getCombat().isDead()) {
            System.err.println("is bugged npc has no cb script "+(target == null)+" dead="+target.getCombat().isDead()+" ");
            return false;
        }
        if (p.isStunned()) {
            if (message)
                p.sendMessage("You're stunned!");
            return false;
        }
        if (!multiCheck(target, message))
            return false;
        if (target.player != null) {
            if (p.riskDef > 0 && !target.player.inMulti() && !p.inMulti()) {
                if (IKOD.getRiskAmount(target.player) < p.riskDef) {
                    p.sendMessage("You cannot attack " + target.player.name() + ". They don't risk enough for you to attack them.");
                    return false;
                }
            }
            if (target.player.riskDef > 0 && !p.inMulti() && target.player.inMulti()) {
                if (IKOD.getRiskAmount(p) < target.player.riskDef) {
                    target.player.sendMessage("You cannot attack " + p.name() + ". You need a risk of atleast " + NumberUtils.formatNumber(target.player.riskDef) + ".");
                    return false;
                }
            }
            if (Wilderness.isAlreadyWildernessSameRegion(p)) {
                p.sendMessage("<col=ff0000>Warning: You cannot attack players while having multiple accounts in the Wilderness<br><col=ff0000>HOWEVER, you can still be attacked!</col>");
                return false;
            }
            if (target.player.getCombat().lastTargetPrio != null) {
                if (!Objects.equals(p.name(), target.player.getCombat().lastTargetPrio.name()) && Objects.equals(target.player.getCombat().lastTargetPrio.getUUID(), p.getUUID())) {
                    p.sendMessage("<col=ff0000>WARNING: You cannot attack players from different accounts in the Wilderness in a short duration of time.");
                    return false;
                }
            }
            if (p.attackPlayerListener != null) {
                if (!p.attackPlayerListener.allow(p, target.player, message))
                    return false;
            } else {
                if (message)
                    p.sendMessage("You can't attack players from where you're standing.");
                return false;
            }
        } else {
            if (target.npc.getCombat().getInfo().slayer_level > 0 && p.getStats().get(StatType.Slayer).currentLevel < target.npc.getCombat().getInfo().slayer_level) {
                if (message)
                    p.sendMessage("You need a Slayer level of at least " + target.npc.getCombat().getInfo().slayer_level + " to attack this monster.");
                return false;
            }
            boolean isKrakenMonster = Arrays.asList(496, 5535, 493, 494, 5534).stream().anyMatch(n -> n.intValue() == target.npc.getId());
            if (isKrakenMonster) {
                System.err.println("is kraken and cannot attack "+(target == null));
                if (Config.SLAYER_TASK.get(p) != 92) {
                    p.sendMessage(Color.RED.wrap("You must have a Cave Kraken task to attack this monster!"));
                    return false;
                } else {
                    return true;
                }
            }

            if (target.npc.id == 4127)//cannot attack troll spectators.
                return false;

            if (target.npc.hasTarget() && !target.npc.isTargeting(p)) {
                if (message)
                    p.sendMessage("That monster isn't after you!");
                return false;
            }
            if (target.npc.attackNpcListener != null && !target.npc.attackNpcListener.allow(p, target.npc, message)) {
                System.err.println("npc listen says we cannot attack "+(target == null));
                return false;
            }
            if (p.attackNpcListener != null && !p.attackNpcListener.allow(p, target.npc, message)) {
                System.err.println("222 npc listen says we cannot attack "+(target == null));
                return false;
            }
        }
        return true;
    }

    public boolean multiCheck(Entity target, boolean message) {
        if (target.inMulti())
            return true;
        if (target.player != null && BloodyChest.hasBloodyKey(target.player))
            return true;
        //if(entity == player.getBountyHunter().target)
        //    return true;
        //^This is how RS works, but people weren't liking this.. And it's kind of bad for a smaller pk scene.
        if (!target.getCombat().allowPj(p)) {
            if (message)
                p.sendMessage("That " + (target.player != null ? "player" : "monster") + " is already in combat!");
            return false;
        }
        if (!allowPj(target)) {
            if (lastAttacker != null && (lastAttacker.getHp() == 0 || lastAttacker.getCombat().isDead()))
                return true;
            if (message)
                p.sendMessage("You are already in combat!");
            return false;
        }
        return true;
    }

    /**
     * Attacking
     */

    public void attack() {

        if (target == null || !p.getRouteFinder().targetRoute.withinDistance)
            return;
        if (p.mageArena && attackSet.style != AttackStyle.MAGIC) {
            p.sendMessage("You can only use magic inside the arena!");
            reset();
            return;
        }
        if (!hasAttackDelay() && wearsNightmareStaff(p) && specialActive != null) {
            if (handleSpecial(AttackStyle.MAGIC, AttackType.ACCURATE, 0))
                updateLastAttack(weaponType.attackTicks);
            if (p.getCombat().getAttackStyle() != AttackStyle.MAGIC) {
                p.getCombat().reset();
            }
            return;
        } else if (useSpell()) {
            if (!hasAttackDelay())
                attackWithMagic();
            return;

        } else if (attackSet.style == AttackStyle.RANGED) {
            if (!hasAttackDelay() || (specialActive instanceof DragonThrownaxe && Config.SPECIAL_ENERGY.get(p) >= 250))
                attackWithRanged();
        } else if (attackSet.style == AttackStyle.MAGIC) {
            if (!hasAttackDelay())
                attackWithMagicWeapon();
        } else {
            if (target.isNpc() && target.npc.getId() == 15016) { //brutal lava dragon flying
                p.sendMessage("You can't attack this npc with melee.");
                updateLastAttack(4);
                return;
            }
            if (!specialGraniteMaul() && !hasAttackDelay()) {
                attackWithMelee();
            }
        }
    }

    private void attackWithMagicWeapon() {
        ItemDef weaponDef = p.getEquipment().getDef(Equipment.SLOT_WEAPON);
        boolean swamp = weaponDef.id == 12899;
        if (weaponDef.id == 24423) {
            updateLastAttack(3);
        }
        if (weaponDef.id == TridentOfTheSeas.UNCHARGED || weaponDef.id == 12900) {
            p.sendMessage("Your trident has no charges left!");
            updateLastAttack(4);
            return;
        }
        if (weaponDef.id == SanguinestiStaff.UNCHARGED) {
            p.sendMessage("Your Sanguinesti staff has no charges left!");
            updateLastAttack(4);
            return;
        }
        if (weaponDef.id == CorruptedStaff.UNCHARGED) {
            p.sendMessage("Your Corrupted staff has no charges left!");
            updateLastAttack(4);
            return;
        }
        if (swamp || weaponDef.id == TridentOfTheSeas.CHARGED || weaponDef.id == TridentOfTheSeas.FULLY_CHARGED) {
            if (target.player != null) {
                p.sendMessage(Color.RED.wrap("This staff's spell cannot be used against other players."));
                return;
            }
            p.animate(1167);
            p.graphics(swamp ? 665 : 1251, 92, 0);
            int duration = new Projectile(swamp ? 1040 : 1252, 30, 0, 51, 56, 10, 16, 11).send(p, target);
            Hit hit = new Hit(p, AttackStyle.MAGIC, getAttackType()).randDamage(getTridentMaxDamage(swamp)).clientDelay(duration).setAttackWeapon(weaponDef);
            int damage = target.hit(hit);
            if (damage > 0) {
                target.graphics(swamp ? 1042 : 1253, 90, duration);
                if (swamp && Random.rollDie(4, 1))
                    target.envenom(6);
            } else {
                hit.nullify();
                target.graphics(85, 92, duration);
            }
            updateLastAttack(4);
        }
        if (weaponDef.id == 23899 || weaponDef.id == 23900) {
            if (target.player != null) {
                p.sendMessage(Color.RED.wrap("This staff's spell cannot be used against other players."));
                return;
            }
            p.animate(1167);
            p.graphics(1719);
            int duration = new Projectile(1720, 30, 0, 51, 56, 10, 16, 11).send(p, target);
            Hit hit = new Hit(p, AttackStyle.MAGIC, getAttackType()).randDamage(weaponDef.id == 23899 ? 31 : weaponDef.id == 23900 ? 39 : 31).clientDelay(duration).setAttackWeapon(weaponDef);
            int damage = target.hit(hit);
            if (damage > 0) {
                target.graphics(1721, 90, duration);
            } else {
                hit.nullify();
                target.graphics(85, 92, duration);
            }
            updateLastAttack(4);
        }
        if (weaponDef.id == HolySanguinestiStaff.CHARGED) {
            if (target.player != null) {
                p.sendMessage(Color.RED.wrap("This staff's spell cannot be used against other players."));
                return;
            }
            p.animate(1167);
            p.graphics(1900, 92, 0);
            int duration = new Projectile(1899, 30, 0, 51, 56, 10, 16, 11).send(p, target);
            Hit hit = new Hit(p, AttackStyle.MAGIC, getAttackType()).randDamage(34).clientDelay(duration).setAttackWeapon(weaponDef);
            int damage = target.hit(hit);
            if (damage > 0) {
                if (Random.rollDie(6, 1)) {
                    p.incrementHp(hit.damage / 2);
                    target.graphics(1902, 90, duration);
                } else {
                    target.graphics(1901, 90, duration);
                }
            } else {
                hit.nullify();
                target.graphics(85, 92, duration);
            }
            updateLastAttack(4);
        }
        if (weaponDef.id == SanguinestiStaff.CHARGED) {
            if (target.player != null) {
                p.sendMessage(Color.RED.wrap("This staff's spell cannot be used against other players."));
                return;
            }
            p.animate(1167);
            p.graphics(1540, 92, 0);
            int duration = new Projectile(1539, 30, 0, 51, 56, 10, 16, 11).send(p, target);
            Hit hit = new Hit(p, AttackStyle.MAGIC, getAttackType()).randDamage(34).clientDelay(duration).setAttackWeapon(weaponDef);
            int damage = target.hit(hit);
            if (damage > 0) {
                if (Random.rollDie(6, 1)) {
                    p.incrementHp(hit.damage / 2);
                    target.graphics(1542, 90, duration);
                } else {
                    target.graphics(1541, 90, duration);
                }
            } else {
                hit.nullify();
                target.graphics(85, 92, duration);
            }
            updateLastAttack(4);
        }
        if (weaponDef.id == ThammaronsSceptre.CHARGED) {
            if (target.player != null) {
                return;
            }
            if (p.wildernessLevel <= 0) {
                p.sendMessage(Color.RED.wrap("You cannot use this staff outside of the wilderness"));
                return;
            }
            p.animate(1167);
            p.graphics(1540, 92, 0);
            int duration = new Projectile(1539, 30, 0, 51, 56, 10, 16, 11).send(p, target);
            Hit hit = new Hit(p, AttackStyle.MAGIC, getAttackType()).randDamage(34).clientDelay(duration).setAttackWeapon(weaponDef);
            int damage = target.hit(hit);
            if (damage > 0) {
                target.graphics(1541, 90, duration);
            } else {
                hit.nullify();
                target.graphics(85, 92, duration);
            }
            updateLastAttack(4);
        }
    }

    public int getCorruptedStaffMaxDamage() {
        int base = 24;
        return (int) Math.round((Math.max(base, base + (Math.max(0, p.getStats().get(StatType.Magic).currentLevel - 77)) / 3)) * (1 + (p.getEquipment().bonuses[EquipmentStats.MAGIC_DAMAGE] / 100.0)));
    }

    public int getTridentMaxDamage(boolean swamp) {
        int base = 20;
        if (swamp)
            base += 3;
        return (int) Math.round((Math.max(base, base + (Math.max(0, p.getStats().get(StatType.Magic).currentLevel - 75)) / 3)) * (1 + (p.getEquipment().bonuses[EquipmentStats.MAGIC_DAMAGE] / 100.0)));
    }

    private void attackAnim() {
        if (attackSet.attackAnimation != null)
            p.animate(attackSet.attackAnimation);
        else
            p.animate(weaponType.attackAnimation);
        if (weaponType.attackSound != -1) {
            p.publicSound(weaponType.attackSound, 1, 1);
        }
    }

    /**
     * Melee
     */

    private void attackWithScythe(Player player, Entity target) {
        AttackStyle style = attackSet.style;
        AttackType type = attackSet.type;
        int maxDamage = CombatUtils.getMaxDamage(player, style, type);
        updateLastAttack(weaponType.attackTicks);
        if (handleSpecial(style, type, maxDamage))
            return;
        attackAnim();
        ItemDef weapon = player.getEquipment().getDef(Equipment.SLOT_WEAPON);
        final int secondHitDmg = (int) (maxDamage * 0.5f);
        final int thirdHitDmg = (int) (maxDamage * 0.25f);
        final Hit firstHit = new Hit(player, style, type).randDamage(maxDamage).setAttackWeapon(player.getEquipment().getDef(Equipment.SLOT_WEAPON));
        final Hit secondHit = new Hit(player, style, type).randDamage(secondHitDmg).setAttackWeapon(player.getEquipment().getDef(Equipment.SLOT_WEAPON));
        final Hit thirdHit = new Hit(player, style, type).randDamage(thirdHitDmg).setAttackWeapon(player.getEquipment().getDef(Equipment.SLOT_WEAPON));
        player.graphics(1898, 100, 20);
        if (target.getSize() >= 2) {
            target.hit(firstHit, secondHit, thirdHit);
        } else {
            target.hit(firstHit);
        }
    }

    public int simulateAttackWithMelee(boolean blockOtherStyles, boolean animate, boolean updateLastAttack, AttackStyle bonusStyle, int bonusDamage) {
        AttackStyle style = attackSet.style;
        AttackType type = attackSet.type;
        if (blockOtherStyles) {
            if (style == AttackStyle.RANGED || style == AttackStyle.MAGIC) {
                p.sendMessage("You can't attack that with " + Common.formatNoun(style.toString()) + "!");
                return -1;
            }
        }
        int maxDamage = CombatUtils.getMaxDamage(p, style, type);
        if (bonusStyle != null) {
            if (style.equals(bonusStyle)) {
                maxDamage += bonusDamage; // increasing the maxDamage does give you a better chance of winning this simulated roll.
                //System.out.println("Bonus awarded for: " + Common.formatNoun(bonusStyle.toString()));
            }
        }
        if (animate) {
            attackAnim();
        }
        if (updateLastAttack) {
            updateLastAttack(weaponType.attackTicks);
        }

        int randomDamage = Random.get(0, maxDamage);

        return randomDamage;
    }

    private void attackWithMelee() {
        ItemDef weapon = p.getEquipment().getDef(Equipment.SLOT_WEAPON);
        AttackStyle style = attackSet.style;
        AttackType type = attackSet.type;
        int maxDamage = CombatUtils.getMaxDamage(p, style, type);
        int attackSpeed = weaponType.attackTicks;
        if (weapon != null) {
            if (PerkManager.isActive(p, Perk.GUTHANS_BLESSING) && target != null && target instanceof NPC) {
                List<Integer> guthans_warspears = Arrays.asList(ItemID.GUTHANS_WARSPEAR, ItemID.GUTHANS_WARSPEAR_100, ItemID.GUTHANS_WARSPEAR_75, ItemID.GUTHANS_WARSPEAR_50, ItemID.GUTHANS_WARSPEAR_25);
                if (guthans_warspears.stream().anyMatch(w -> w.intValue() == weapon.id)) {
                    attackSpeed--;
                }
            }
        }
        updateLastAttack(attackSpeed);
        defendAnim();
        if (handleSpecial(style, type, maxDamage))
            return;
        if (weapon == null && weaponType == WeaponType.UNARMED) {
            attackAnim();
            target.hit(new Hit(p, style, type).randDamage(maxDamage).setAttackWeapon(p.getEquipment().getDef(Equipment.SLOT_WEAPON)));
        } else {
            boolean hasScytheEquipped = p.getEquipment().contains(new Item(22325)) || p.getEquipment().contains(new Item(25739));
            if (hasScytheEquipped && target.isNpc()) {
                attackWithScythe(p, target);
            } else if (weaponType != WeaponType.UNARMED) {
                attackAnim();
                target.hit(new Hit(p, style, type).randDamage(maxDamage).setAttackWeapon(p.getEquipment().getDef(Equipment.SLOT_WEAPON)));
            }
        }
    }

    /**
     * Ranged
     */

    public RangedData rangedData;

    private void attackWithRanged() {
        Item wep = p.getEquipment().get(Equipment.SLOT_WEAPON);
        if (wep == null) {
            /* obviously should never happen */
            return;
        }
        ItemDef wepDef = wep.getDef();
        RangedWeapon rangedWep = wepDef.rangedWeapon;
        if (rangedWep == null) {
            p.sendMessage("Unhandled ranged weapon: " + wepDef.name);
            reset();
            return;
        }
        if (rangedWep == RangedWeapon.TOXIC_BLOWPIPE || rangedWep == RangedWeapon.MAGMA_BLOWPIPE) {
            attackWithBlowpipe(wep);
            return;
        }
        if (rangedWep == RangedWeapon.TOXIC_BLOWPIPE_E) {

        }
        if (rangedWep == RangedWeapon.CORRUPTED_JAVELIN) {
            attackWithCorruptedJavelin(wep);
            return;
        }

        Item ammo = null;
        RangedAmmo rangedAmmo = null;
        boolean fireDouble = false;
        if ((rangedData = rangedWep.data) != null) {
            /**
             * Crystal bow, Knifes, Darts, etc
             */
            if (rangedWep != RangedWeapon.CRYSTAL_BOW
                    && rangedWep != RangedWeapon.CRAWS_BOW
                    && rangedWep != RangedWeapon.CRYSTAL_BOW_HUNLLEF
                    && rangedWep != RangedWeapon.BOW_OF_FAERDHINEN
                    && rangedWep != RangedWeapon.BOW_OF_FAERDHINEN_BLUE
                    && rangedWep != RangedWeapon.BOW_OF_FAERDHINEN_RED
                    && rangedWep != RangedWeapon.BOW_OF_FAERDHINEN_YELLOW
                    && rangedWep != RangedWeapon.BOW_OF_FAERDHINEN_WHITE
                    && rangedWep != RangedWeapon.BOW_OF_FAERDHINEN_CYAN
                    && rangedWep != RangedWeapon.BOW_OF_FAERDHINEN_GREEN
                    && rangedWep != RangedWeapon.BOW_OF_FAERDHINEN_BLACK
                    && rangedWep != RangedWeapon.BOW_OF_FAERDHINEN_PURPLE)
                ammo = wep;
        } else {
            /**
             * Shortbows, Longbows, Crossbows, etc
             */
            ammo = p.getEquipment().get(Equipment.SLOT_AMMO);
            if (ammo == null) {
                p.sendMessage("You are all out of ammo.");
                reset();
                return;
            }
            ItemDef ammoDef = ammo.getDef();
            if ((rangedAmmo = ammoDef.rangedAmmo) == null || !rangedWep.allowAmmo(rangedAmmo)) {
                p.sendMessage("Your weapon can't fire that ammo.");
                reset();
                return;
            }
            if (wepDef.rangedLevel < ammoDef.rangedLevel) {
                p.sendMessage("Your weapon is not strong enough to fire that ammo.");
                reset();
                return;
            }
            rangedData = rangedAmmo.data;
            if (rangedWep == RangedWeapon.DARK_BOW && ammo.getAmount() >= 2)
                fireDouble = true;
        }
        AttackStyle style = attackSet.style;
        AttackType type = attackSet.type;
        int maxDamage = CombatUtils.getMaxDamage(p, style, type);
        int attackTicks = type == AttackType.RAPID_RANGED ? weaponType.attackTicks - 1 : weaponType.attackTicks;
        if (rangedWep.isCrossbow()) {
            if (target != null && target.isNPC()) {
                if (PerkManager.isActive(p, QUICK_DRAW)) {
                    if (attackTicks > 1)
                        attackTicks--;
                }
            }
        }
        updateLastAttack(attackTicks);
        if (handleSpecial(style, type, maxDamage))
            return;
        attackAnim();
        if (!fireDouble) {
            boolean chins = wep.getId() == 10033 || wep.getId() == 10034 || wep.getId() == 11959;
            if (rangedData.drawbackId != -1)
                p.graphics(rangedData.drawbackId, 96, 0);
            int delay = rangedData.projectiles[0].send(p, target);
            Hit hit = new Hit(p, style, type).randDamage(maxDamage).clientDelay(delay).setAttackWeapon(wepDef);
            if (ammo != null) {
                // Chins dont roll for chance of saving ammo or drop on ground so just decrement.
                if (chins) {
                    ammo.incrementAmount(-1);
                } else {
                    removeAmmo(ammo, hit);
                }

                hit.setRangedAmmo(ammo.getDef());
            }
            if (rangedAmmo != null && rangedAmmo.effect != null && rangedAmmo.effect.apply(target, hit))
                return;
            defendAnim(delay);
            target.hit(hit);
            if (chins) {
                target.graphics(157, 100, delay);
                if (target.inMulti()) {
                    int entityIndex = p.getClientIndex();
                    int targetIndex = target.getClientIndex();
                    int targetCount = 0;
                    for (Player plr : target.localPlayers()) {
                        int playerIndex = plr.getClientIndex();
                        if (playerIndex == entityIndex || playerIndex == targetIndex)
                            continue;
                        if (!plr.getPosition().isWithinDistance(target.getPosition(), 1))
                            continue;
                        if (!p.getCombat().canAttack(plr, false))
                            continue;
                        plr.hit(new Hit(p, style, type).randDamage(maxDamage).clientDelay(delay).setAttackWeapon(wepDef));
                        if (++targetCount >= 9)
                            break;
                    }
                    for (NPC npc : target.localNpcs()) {
                        int npcIndex = npc.getClientIndex();
                        if (npcIndex == entityIndex || npcIndex == targetIndex)
                            continue;
                        if (!npc.getPosition().isWithinDistance(target.getPosition(), 1))
                            continue;
                        if (npc.getDef().ignoreMultiCheck)
                            continue;
                        if (!p.getCombat().canAttack(npc, false))
                            continue;
                        npc.hit(new Hit(p, style, type).randDamage(maxDamage).clientDelay(delay).setAttackWeapon(wepDef));
                        if (++targetCount >= 9)
                            break;
                    }
                }
            }
        } else {
            if (rangedData.doubleDrawbackId != -1)
                p.graphics(rangedData.doubleDrawbackId, 96, 0);
            int delay1 = rangedData.projectiles[1].send(p, target);
            int delay2 = rangedData.projectiles[2].send(p, target);
            defendAnim(delay1);
            Hit[] hits = {
                    new Hit(p, style, type).randDamage(maxDamage).clientDelay(delay1).setRangedAmmo(ammo.getDef()).setAttackWeapon(wepDef),
                    new Hit(p, style, type).randDamage(maxDamage).clientDelay(delay2).setRangedAmmo(ammo.getDef()).setAttackWeapon(wepDef),
            };
            removeAmmo(ammo, hits);
            target.hit(hits);
        }
    }

    private void attackWithCorruptedJavelin(Item wep) {
        if (AttributeExtensions.getCharges(wep) <= 0) {
            p.sendMessage("Your javelin isn't charged with any essence.");
            reset();
            return;
        }
        AttackStyle style = attackSet.style;
        AttackType type = attackSet.type;
        int maxDamage = CombatUtils.getMaxDamage(p, style, type);
        int attackTicks = type == AttackType.RAPID_RANGED ? weaponType.attackTicks - 1 : weaponType.attackTicks;
        if (target.npc != null)
            attackTicks--;
        updateLastAttack(attackTicks);
        Hit hit = new Hit(p, style, type)
                .setAttackWeapon(wep.getDef())
                .setRangedAmmo(ItemDef.get(11230));
        p.graphics(5006, 96, 0);
        attackAnim();
        Projectile proj = Projectile.javelin(5019)[0];
        int delay = proj.send(p, target);
        hit.randDamage(maxDamage).clientDelay(delay);
        defendAnim(delay);
        target.hit(hit);
    }

    private void attackWithBlowpipe(Item blowpipe) {
        Blowpipe.Dart dart = Blowpipe.getDart(blowpipe);
        int dartAmount = Blowpipe.getDartAmount(blowpipe);
        if (dart == Blowpipe.Dart.NONE || dartAmount == 0) {
            p.sendMessage("Your blowpipe isn't loaded with any darts.");
            reset();
            return;
        }
        int scalesAmount = Blowpipe.getScalesAmount(blowpipe);
        if (scalesAmount == 0) {
            p.sendMessage("Your blowpipe isn't charged with any scales.");
            reset();
            return;
        }
        AttackStyle style = attackSet.style;
        AttackType type = attackSet.type;
        int maxDamage = CombatUtils.getMaxDamage(p, style, type);
        int attackTicks = type == AttackType.RAPID_RANGED ? weaponType.attackTicks - 1 : weaponType.attackTicks;
        if (target.npc != null)
            attackTicks--;
        updateLastAttack(attackTicks);
        Hit hit = new Hit(p, style, type)
                .setAttackWeapon(blowpipe.getDef())
                .setRangedAmmo(ItemDef.get(dart.id));
        attackAnim();
        if (handleSpecial(style, type, maxDamage)) {
            int delay = ToxicBlowpipe.SIPHON_PROJECTILE.send(p, target);
            defendAnim(delay);
            hit.randDamage(maxDamage).boostDamage(0.50).clientDelay(delay);
            int damage = target.hit(hit);
            if (damage >= 2) {
                if (p.getEquipment().hasId(30049)) {
                    p.incrementHp(damage);
                }
                p.incrementHp(damage / 2);
            }
        } else {
            int delay = dart.rangedData.projectiles[0].send(p, target);
            hit.randDamage(maxDamage).clientDelay(delay);
            target.hit(hit);
            defendAnim(delay);
        }
        if (!hasAvaDevice() || !rollAvaChance())
            dartAmount--;
        if (Random.rollDie(3, 2))
            scalesAmount--;
        Blowpipe.update(blowpipe, dart, dartAmount, scalesAmount);
    }

    public void removeAmmo(Item ammo, Hit... hits) {
        if (rangedData.alwaysBreak) {
            ammo.remove(hits.length);
            return;
        }
        boolean ava = hasAvaDevice();
        for (Hit hit : hits) {
            if (ava && rollAvaChance())
                continue;
            ammo.incrementAmount(-1);
            if (!ava)
                hit.drop(new GroundItem(ammo.getId(), 1).owner(p).position(updatedTile(target)));
        }
    }

    private Position updatedTile(Entity entity) {
        Position position = entity.getPosition().copy();
        int x = position.getX();
        int y = position.getY();
        if (entity instanceof NPC) {
            NPC npc = (NPC) entity;
            if (npc.isZulrah()) {
                Optional<Zulrah.Location> test = Arrays.stream(Zulrah.Location.values()).filter(off -> off.localX == npc.getPosition().getBaseLocalX() && off.localY == npc.getPosition().getBaseLocalY()).findFirst();
                if (test.isPresent()) {
                    Zulrah.Location loc = test.get();
                    position.set(x + loc.offsetX, y + loc.offsetY);
                }
            }
        }
        return position;
    }

    private boolean hasAvaDevice() {
        int capeID = p.getEquipment().getId(Equipment.SLOT_CAPE);
        return capeID == 10499 || capeID == 21898 || capeID == 13337 || capeID == 22109 || capeID == 9756 || capeID == 9757;
    }

    private boolean rollAvaChance() {
        return Random.rollPercent(p.getEquipment().getId(Equipment.SLOT_CAPE) == 22109 ? 80 : 72); //72% chance according to wiki
    }

    /**
     * Magic
     */

    public TargetSpell queuedSpell, autocastSpell;

    public boolean useSpell() {
        return (specialActive == null && hasDawnbringer()) || queuedSpell != null || autocastSpell != null;
    }

    public void queueSpell(TargetSpell spell, Entity target) {
        queuedSpell = spell;
        setTarget(target);
    }

    private void attackWithMagic() {
        TargetSpell spell;
        boolean autocast;
        if (hasDawnbringer()) {
            queuedSpell = Dawnbringer.INSTANCE;
        }
        if (queuedSpell == null) {
            spell = autocastSpell;
            autocast = true;
        } else {
            spell = queuedSpell;
            autocast = false;
        }
        if (!spell.cast(p, target)) {
            reset();
            return;
        }
        updateLastAttack(weaponType.attackTicks);
        if (!autocast)
            reset();
    }

    /**
     * Reset
     */

    @Override
    public void reset() {
        updateLastTarget(target);
        super.reset();
        queuedSpell = null;
        rangedData = null;
        p.faceNone(!isDead());
        TargetRoute.reset(p);
    }

    /**
     * Retliate
     */

    @Override
    public boolean allowRetaliate(Entity attacker) {
        return target == null && p.getMovement().isAtDestination() && Config.AUTO_RETALIATE.get(p) == 0 && !p.isLocked();
    }

    @Override
    public int getDefendAnimation() {
        if (p.recentlyEquipped.isDelayed())
            return -1;
        ItemDef shieldDef = p.getEquipment().getDef(Equipment.SLOT_SHIELD);
        if (shieldDef != null && shieldDef.shieldType != null)
            return shieldDef.shieldType.animationId;
        return weaponType.defendAnimation;
    }

    /**
     * Hits to this player
     */

    private void preDefend(Hit hit) {
        for (Item item : p.getEquipment().getItems()) {
            if (item != null && item.getDef() != null) {
                item.getDef().preDefend(p, item, hit);
                if (hit.attacker != null && hit.attacker.isNpc()) {
                    List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                    boolean hasEffect = upgrades != null;
                    if (hasEffect) {
                        for (String s : upgrades) {
                            try {
                                if (s.equalsIgnoreCase("empty"))
                                    continue;
                                ItemEffect effect = ItemEffect.valueOf(s);
                                effect.getUpgrade().preDefend(p, hit.attacker, item, hit);
                            } catch (Exception ex) {
                                System.err.println("Unknown upgrade { " + s + " } found!");
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param hit
     */

    private void postDefend(Hit hit) {
        if (hit.attackStyle != null && !hit.prayerIgnored) {
            if (hit.attackStyle.isMelee() || hit.attackStyle.isMagicalMelee()) {
                if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)) {
                    if (hit.attacker != null && hit.attacker.player != null)
                        hit.damage *= 0.60;
                    else
                        hit.damage = 0;
                }
            } else if (hit.attackStyle.isRanged() || hit.attackStyle.isMagicalRanged()) {
                if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES)) {
                    if (hit.attacker != null && hit.attacker.player != null)
                        hit.damage *= 0.60;
                    else
                        hit.damage = 0;
                }
            } else if (hit.attackStyle.isMagic()) {
                if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
                    if (hit.attacker != null && hit.attacker.player != null)
                        hit.damage *= 0.60;
                    else
                        hit.damage = 0;
                }
            }
        }
        if (hit.attacker != null) {
            /* elysian spirit shield */
            if (p.getEquipment().getId(Equipment.SLOT_SHIELD) == 12817 && Random.rollPercent(70)) {
                hit.damage *= 0.75;
                p.graphics(321);
            }
            /* divine spirit shield */
            if (p.getEquipment().getId(Equipment.SLOT_SHIELD) == 30191) {
                hit.damage *= 0.70;
                p.graphics(5024);
                int drain = (int) (Math.ceil(hit.damage * 0.3) / 2);
                if (p.getStats().get(StatType.Prayer).currentLevel > drain) {
                    p.getPrayer().drain(drain);
                }
            }
            if (p.sotdDelay.isDelayed() && hit.attackStyle != null && hit.attackStyle.isMelee()) {
                hit.damage *= 0.50;
            }
        }
        if (p.vestasSpearSpecial.isDelayed() && hit.attackStyle != null && hit.attackStyle.isMelee()) {
            hit.block();
        }
    }

    private void preDamage(Hit hit) {
        TransformationRing.check(p);
    }

    private void postDamage(Hit hit) {
        Vengeance.check(p, hit);
        RingOfRecoil.check(p, hit);
        RingOfSuffering.check(p, hit);
        Redemption.check(p);
        PhoenixNecklace.check(p);
        RingOfLife.check(p);
        EscapeCrystal.check(p);
        DefenceSkillCape.check(p);
        for (Item item : p.getEquipment().getItems()) {
            if (item != null && item.getDef() != null) {
                item.getDef().postDamage(p, item, hit);
                if (target != null && target.isNpc()) {
                    List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                    boolean hasEffect = upgrades != null;
                    if (hasEffect) {
                        for (String s : upgrades) {
                            try {
                                if (s.equalsIgnoreCase("empty"))
                                    continue;
                                ItemEffect effect = ItemEffect.valueOf(s);
                                effect.getUpgrade().postPlayerDamage(p, target, item, hit);
                            } catch (Exception ex) {
                                System.err.println("Unknown upgrade { " + s + " } found!");
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public static List<String> UNDEAD_NPCS = Arrays.asList("Aberrant spectre", "Ankou",
            "Banshee", "Crawling Hand", "Ghast", "Ghost", "Mummy", "Revenant", "Shade",
            "Skeleton", "Skogre", "Summoned Zombie", "Tortured Soul", "Undead Chicken",
            "Undead Cow", "Undead One", "Zogre", "Zombified Spawn", "Zombie", "Vet'ion",
            "Pestilent Bloat", "Tree spirit", "Mi-Gor", "Treus Dayth", "Nazastarool",
            "Slash Bash", "Ulfric", "Vorkath");


    private boolean salveAmuletBoost(Player player, Hit hit, Entity entity) {
        final int salveAmulet = 4081;
        final int salveAmuletImbued = 12017;
        final int salveAmuletEI = 12018;

        if (hit.attackStyle != null && target != null && target.isNpc()) {
            Item amulet = player.getEquipment().get(Equipment.SLOT_AMULET);
            if (amulet == null) {
                return false;
            }

            Optional<String> npc = UNDEAD_NPCS.stream().filter(Objects::nonNull).filter(s -> s.equalsIgnoreCase(entity.npc.getDef().name) || Utils.containsIgnoreCase(entity.npc.getDef().name, s)).findAny();
            if (!npc.isPresent()) {
                return false;
            }

            if (hit.attackStyle.isMelee() && amulet.getId() == salveAmulet) {
                hit.boostAttack(0.15);
                hit.boostDamage(0.15);
                return true;
            } else if (amulet.getId() == salveAmuletImbued) {
                hit.boostAttack(0.15);
                hit.boostDamage(0.15);
                return true;
            } else if (amulet.getId() == salveAmuletEI) {
                hit.boostAttack(0.20);
                hit.boostDamage(0.20);
                return true;
            }
        }
        return false;
    }

    /**
     * Hits from this player
     */

    private void preTargetDefend(Hit hit, Entity target) {
        boolean slayerHelmEffectActive = SlayerHelm.boost(p, target, hit);
        //If slayer helmet effect isn't active try salve amulet
        if (!slayerHelmEffectActive) {
            salveAmuletBoost(p, hit, target);
        }
        //boolean crystalEffectActive = SetEffect.CRYSTAL_ARMOR.checkAndApply(player, target, hit);
        boolean dharoksEffectActive = SetEffect.DHAROK.checkAndApply(p, target, hit);
        boolean veracsEffectActive = SetEffect.VERAC.checkAndApply(p, target, hit);
        boolean karilsEffectActive = SetEffect.KARILS.checkAndApply(p, target, hit);
        boolean berserkerNecklaceEffectActive = SetEffect.BERSERKER_NECKLACE.checkAndApply(p, target, hit);
        boolean obsidianEffectActive = SetEffect.OBSIDIAN_ARMOUR.checkAndApply(p, target, hit);

        if (target.npc != null && target.npc.getDef().dragon && hit.attackStyle != null) {
            //dragon hunter crossbow
            if (hit.attackStyle.isRanged() && p.getEquipment().hasId(ItemID.DRAGON_HUNTER_CROSSBOW))
                hit.boostAttack(0.3).boostDamage(0.3);
            //dragon hunter lance
            if (hit.attackStyle.isMelee() && p.getEquipment().hasId(ItemID.DRAGON_HUNTER_LANCE))
                hit.boostAttack(0.2).boostDamage(0.2);

            if (hit.attacker != null && hit.attacker instanceof Player) {
                if (PerkManager.isActive(hit.attacker.player, Perk.DRAGON_HUNTER)) {
                    hit.boostAttack(0.1).boostDamage(0.1);
                }
            }
        }
        // crystal armor bonuses
        if (p.getEquipment().hasId(25865)
                /**
                 * Bows
                 */
                || p.getEquipment().hasId(25888)
                || p.getEquipment().hasId(25886)
                || p.getEquipment().hasId(25884)
                || p.getEquipment().hasId(25890)
                || p.getEquipment().hasId(25884)
                || p.getEquipment().hasId(25867)
                || p.getEquipment().hasId(25892)
                || p.getEquipment().hasId(25896)
                || p.getEquipment().hasId(25894)) {
            if (p.getEquipment().hasId(23971)) {
                hit.boostDamage(0.025).boostAttack(0.05);
            }
            if (p.getEquipment().hasId(23975)) {
                hit.boostDamage(0.075).boostAttack(0.15);
            }
            if (p.getEquipment().hasId(23979)) {
                hit.boostDamage(0.05).boostAttack(0.10);
            }
        }

        /* Void Set Bonuses */
        boolean voidMelee = p.getEquipment().contains(11665) && p.getEquipment().contains(8839) && p.getEquipment().contains(8840) && p.getEquipment().contains(8842);
        boolean voidRange = p.getEquipment().contains(11664) && p.getEquipment().contains(8839) && p.getEquipment().contains(8840) && p.getEquipment().contains(8842);
        boolean voidMage = p.getEquipment().contains(11663) && p.getEquipment().contains(8839) && p.getEquipment().contains(8840) && p.getEquipment().contains(8842);
        boolean elitevoidMage = p.getEquipment().contains(11663) && p.getEquipment().contains(13072) && p.getEquipment().contains(13073) && p.getEquipment().contains(8842);
        boolean elitevoidRange = p.getEquipment().contains(11664) && p.getEquipment().contains(13072) && p.getEquipment().contains(13073) && p.getEquipment().contains(8842);

        if (voidMelee && p.getCombat().getAttackStyle().isMelee()) {
            hit.boostDamage(0.10);
        }
        if (voidRange && p.getCombat().getAttackStyle().isRanged()) {
            hit.boostDamage(0.10).boostAttack(10);
        }
        if (voidMage && p.getCombat().getAttackStyle().isMagic()) {
            hit.boostAttack(0.45);
        }
        if (elitevoidMage && p.getCombat().getAttackStyle().isMagic()) {
            hit.boostDamage(0.05).boostAttack(0.45);
        }
        if (elitevoidRange && p.getCombat().getAttackStyle().isRanged()) {
            hit.boostDamage(0.15).boostAttack(10);
        }

        if (p.getEquipment().hasId(25867) || p.getEquipment().hasId(24551)) {
            if (p.getEquipment().hasId(23842) && p.getEquipment().hasId(23845) && p.getEquipment().hasId(23848)) {
                hit.boostAttack(30).boostDamage(0.20);
                if (Random.rollPercent(5)) {
                    p.graphics(1811, 0, 0);
                }
            }
        }
        if (p.getEquipment().hasId(25867)) {
            if (p.getEquipment().hasId(23842) && p.getEquipment().hasId(23845) && p.getEquipment().hasId(23848)) {
                hit.boostAttack(30).boostDamage(0.20);
                if (Random.rollPercent(5)) {
                    p.incrementHp((int) (hit.damage * 0.05));
                    p.sendFilteredMessage("Your armor set heals you for 5% of the damage you dealt!");
                }
            }
            if (PerkManager.isActive(p, Perk.CRYSTAL_AIM)) {
                hit.boostAttack(30).boostDamage(0.15);
            }
        }

        /* twisted bow */
        if (hit.attackStyle != null && hit.attackStyle.isRanged() && (p.getEquipment().hasId(20997))) {
            double magicLevel = target.getCombat().getLevel(StatType.Magic) - 20;
            double accuracy = 140D + ((3 * magicLevel - 10D) / 80D) - ((Math.pow(3 * magicLevel / 10D - 100D, 2) / 100D));
            accuracy = Math.min(140, accuracy);
            double damage = 260D + ((4 * magicLevel - 14D) / 100D) - (Math.pow(3 * magicLevel / 10 - 140, 2) / 100D);
            damage = (damage - 100) / 100;
            accuracy = (accuracy - 100) / 70;
            if (damage > 0)
                hit.boostDamage(damage);
            if (accuracy > 0)
                hit.boostAttack(accuracy + 0.15);
        }

        //Enchanted armadyl set
        if (getAttackStyle().isRanged() && p.getEquipment().hasId(31004) && p.getEquipment().hasId(31005) && p.getEquipment().hasId(31006)) {
            if (Random.rollPercent(3)) {
                hit.boostDamage(0.35).boostAttack(0.35);
                p.sendMessage("Your armor gives you a temporary ranged bonus");
            }
        }
        //Enchanted fury
        if (p.getEquipment().hasId(31029)) {
            if (Random.rollPercent(3) && !target.isPlayer()) {
                if (p.getStats().get(StatType.Prayer).currentLevel < p.getStats().get(StatType.Prayer).fixedLevel) {
                    Stat stat = p.getStats().get(StatType.Prayer);
                    stat.restore(12);
                    p.sendMessage("Your amulet restored up to 12 prayer points");
                }
            }
        }
        //Enchanted blood fury
//        if (p.getEquipment().hasId(31045)) {
//            if (Random.rollPercent(3) && !target.isPlayer()) {
//                p.addEvent(event -> {
//                    for (int i = 0; i < 5; i++) {
//                        p.graphics(560);
//                        Stat stat = p.getStats().get(StatType.Hitpoints);
//                        stat.restore(5);
//                        event.delay(1);
//                    }
//                });
//            }
//        }
//        //Enchanted bofa
//        if (p.getEquipment().hasId(25867) && !target.isPlayer()) {
//            if (Random.rollPercent(5)) {
//                p.addEvent(event -> {
//                    for (int i = 0; i < 5; i++) {
//                        p.graphics(560);
//                        target.hit(new Hit(p, null, null).fixedDamage(5).ignoreDefence());
//                        event.delay(1);
//                    }
//                });
//            }
//        }
//        //Enchanted blade of saeldor
//        if (p.getEquipment().hasId(24551) && !target.isPlayer()) {
//            if (Random.rollPercent(5)) {
//                p.addEvent(event -> {
//                    for (int i = 0; i < 5; i++) {
//                        p.graphics(560);
//                        target.hit(new Hit(p, null, null).fixedDamage(5).ignoreDefence());
//                        event.delay(1);
//                    }
//                });
//            }
//        }
        //Enchanted ghrazi rapier
        if (p.getEquipment().hasId(25734) && !target.isPlayer()) {
            if (Random.rollPercent(5)) {
                hit.boostDamage(0.50);
            }
        }
        //Enchanted sanguinesti staff
        if (p.getEquipment().hasId(25731) && !target.isPlayer()) {
            if (Random.rollPercent(5)) {
                hit.boostDamage(0.50);
            }
        }
        //Enchanted scythe of vitur
        if (p.getEquipment().hasId(25739) && !target.isPlayer()) {
            if (Random.rollPercent(5)) {
                hit.boostDamage(0.50);
            }
        }
        //Enchanted kodai wand
        if (p.getEquipment().hasId(30181) && ChambersOfXeric.isRaiding(p)) {
            hit.boostDamage(0.10).boostAttack(0.10);
        }
        //Enchanted occult
        if (p.getEquipment().hasId(31046) && !target.isPlayer()) {
            if (Random.rollPercent(2) && !target.isPlayer()) {
                target.freeze(10, hit.attacker);
                target.graphics(369);
                p.sendMessage("Your amulet freezes your target!");
            }
        }
        //Enhanced dark ancestral set
        boolean fullDarkAncestral = p.getEquipment().hasId(31001)
                && p.getEquipment().hasId(31002)
                && p.getEquipment().hasId(31003);
        if (getAttackStyle().isMagic() && fullDarkAncestral && !target.isPlayer()) {
            hit.boostAttack(0.05);
            p.sendMessage("Your armor gives you a temporary mage bonus");
        }
        //Enchanted bcp + tassets
        if (getAttackStyle().isMelee() && p.getEquipment().hasId(31007) && p.getEquipment().hasId(31008) && !target.isPlayer()) {
            if (Random.rollPercent(5)) {
                hit.boostDamage(0.35).boostAttack(0.35);
                p.sendMessage("Your armor gives you a temporary strength bonus");
            }
        }
        //Enhanced elite void set
        boolean voidHelm = p.getEquipment().hasId(11663)
                || p.getEquipment().hasId(11664)
                || p.getEquipment().hasId(11665);
        if (p.getEquipment().contains(26469) && p.getEquipment().contains(26471)
                && p.getEquipment().contains(8842) && voidHelm && !target.isPlayer()) {
            hit.boostDamage(0.13);
        }
//        //Enhanced crystal set
//        boolean enchantedCrystalSet = p.getEquipment().contains(23842)
//                && p.getEquipment().contains(23845)
//                && p.getEquipment().contains(23848);
//        if (enchantedCrystalSet && !target.isPlayer() && target.npc.getDef().id != 2668) {
//            hit.boostDamage(0.05);
//            if (Random.rollPercent(5)) {
//                p.addEvent(event -> {
//                    for (int i = 0; i < 5; i++) {
//                        p.graphics(560);
//                        Stat stat = p.getStats().get(StatType.Hitpoints);
//                        stat.restore(5);
//                        target.hit(new Hit(p, null, null).fixedDamage(5).ignoreDefence());
//                        event.delay(1);
//                    }
//                });
//            }
//        }

        for (Item item : p.getEquipment().getItems()) {
            if (item != null && item.getDef() != null) {
                item.getDef().preTargetDefend(p, item, hit, target);
                if (target != null && target.isNpc()) {
                    List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                    boolean hasEffect = upgrades != null;
                    if (hasEffect) {
                        for (String s : upgrades) {
                            try {
                                if (s.equalsIgnoreCase("empty"))
                                    continue;
                                ItemEffect effect = ItemEffect.valueOf(s);
                                effect.getUpgrade().preTargetDefend(p, target, item, hit);
                            } catch (Exception ex) {
                                System.err.println("Unknown upgrade { " + s + " } found!");
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

    }

    private void postTargetDefend(Hit hit, Entity target) {
        for (Item item : p.getEquipment().getItems()) {
            if (item != null && item.getDef() != null) {
                item.getDef().postTargetDefend(p, item, hit, target);
                if (target != null && target.isNpc()) {
                    List<String> upgrades = AttributeExtensions.getEffectUpgrades(item);
                    boolean hasEffect = upgrades != null;
                    if (hasEffect) {
                        for (String s : upgrades) {
                            try {
                                if (s.equalsIgnoreCase("empty"))
                                    continue;
                                ItemEffect effect = ItemEffect.valueOf(s);
                                effect.getUpgrade().postTargetDefend(p, target, item, hit);
                            } catch (Exception ex) {
                                System.err.println("Unknown upgrade { " + s + " } found!");
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        if (target.npc != null && ChambersOfXeric.isRaiding(p)) {
            ChambersOfXeric.addDamagePoints(p, target.npc, hit.damage);
        }
    }

    private void preTargetDamage(Hit hit, Entity target) {
        TransformationRing.check(p);
    }

    private void postTargetDamage(Hit hit, Entity target) {
        //If you modify damage inside this method it's absolutely pointless. ("POST" DAMAGE)
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\\

        if (p.getPrayer().isActive(Prayer.SMITE)) {
            if (target.player != null)
                if (target.player.getEquipment().contains(31024)) {
                    target.player.getPrayer().drain(hit.damage / 8);
                } else {
                    target.player.getPrayer().drain(hit.damage / 4);
                }
        }

        if (target != null && target.isNPC()) {

            if (PerkManager.isActive(p, Perk.RAID_MASTER)) {
                if (p.raidsParty != null)
                    hit.boostDamage(0.10);
            }

            if (PerkManager.isActive(p, Perk.THE_LEECHER)) {
                if (p.getHp() < p.getMaxHp()) {
                    double heal = hit.damage * 0.05;
                    if (heal >= 1.0) {
                        p.incrementHp((int) heal);
                        p.graphics(1811, 0, 0);
                        p.sendFilteredMessage("Your perk allows you to heal 5% of the damage you dealt!");
                    }
                }
            }

            if (PerkManager.isActive(p, SECOND_WIND)) {
                if (p.getHp() <= (p.getMaxHp() * .3)) {
                    hit.boostDamage(0.2);
                    p.sendFilteredMessage("Your Second Wind perk effect boosts your damage by 20% as you risk your life! ");
                }
            }
        }

        SetEffect.GUTHAN.checkAndApply(p, target, hit);

        for (Item item : p.getEquipment().getItems()) {
            if (item != null && item.getDef() != null)
                item.getDef().postTargetDamage(p, item, hit, target);
        }

    }

    /**
     * Death
     */

    public static int valueOne = 0;
    public static int valueTwo = 0;
    public static int valuethree = 0;
    public static int valuefour = 0;
    public static int valuefive = 0;
    public static int valuesix = 0;
    public static int valueseven = 0;
    public static int valueeight = 0;
    public static int valuenine = 0;
    public static int valueten = 0;
    public static int valueeleven = 0;

    @Override
    public void startDeath(Hit killHit) {
        p.lock();
        setDead(true);
        if (target != null) {
            reset();
        }
        Killer killer = getKiller();
        if (p.deathStartListener != null) {
            p.deathStartListener.handle(p, killer, killHit);
        }
        int[] excludedItems = new int[]{
                2434, 385, 12695, 391};
        p.resetActions(true, true, true);
        p.startEvent(event -> {
            event.delay(1);
            p.animate(836);
            Retribution.check(p, killer);
            event.delay(4);
            p.resetAnimation();
            setTruelyDead(true);
            if (p.deathEndListener != null) {
                p.deathEndListener.handle(p, killer, killHit);
            } else {
                Player pKiller = killer != null ? killer.player : null;

                Leaderboard.addKill(pKiller);
                Leaderboard.addDeath(p);

                /**
                 * Rewards (Keep before items lost on death!)
                 */
                boolean bhTarget = p.getBountyHunter().deathByTarget(pKiller);
                if (pKiller != null) {
                    killer.reward(p, bhTarget);
                    Config.PVP_DEATHS.increment(p, 1);
                    p.currentKillSpree = 0;
                    pKiller.player.resetFreeze();
                }

                CaptureTheFlag.checkDeath(p);

                /**
                 * Items lost on death
                 */
                boolean useDeathStorage = pKiller == null && p.wildernessLevel == 0;
                if (useDeathStorage) {
                    p.getDeathStorage().death(killer);
                } else {
                    handleItemsOnDeath(killer, pKiller);
                }
                /**
                 * Drop bones (Keep after all other item drops!)
                 */
                if (pKiller == null)
                    new GroundItem(526, 1).owner(p).position(p.getPosition()).spawn(2);
                else
                    new GroundItem(526, 1).owner(pKiller).position(p.getPosition()).spawn();
                /**
                 * Misc
                 */
                p.face(Direction.NORTH);
                p.sendMessage("Oh dear, you are dead!");
                if (p.wildernessLevel > 15)
                    p.presetDelay.delaySeconds(10);

                if (p.currentInstance != null) {
                    p.getMovement().teleport(p.currentInstance.getType().getExitPosition());
                } else if (p.edgeHome) {
                    p.getMovement().teleport(World.EDGEHOME);
                } else {
                    p.getMovement().teleport(World.EDGEHOME);
                }


                /**
                 * HCIM - revoke status
                 */
                if (Config.IRONMAN_MODE.get(p) == 3) {
                    GameMode.hardcoreDeath(p, killHit);
                }

                if (useDeathStorage) {
                    p.sendMessage(Color.DARK_RED.wrap("Info: Lost items are sent to the Death retrieval chest. (outside of the bank at home)"));
                }
            }
            resetSkull();
            resetSkullers();
            restore();
            event.delay(1);
            setTruelyDead(false);
            setDead(false);
            p.unlock();
        });
    }

    public static ArrayList<Item> keyOneItems = new ArrayList<>();

    /**
     * Restore
     */

    public void restore() {
        p.getStats().restore(true);
        p.overloadBoostActive = false;
        p.prayerEnhanceBoostActive = false;
        p.divineAttackBoostActive = false;
        p.divineStrBoostActive = false;
        p.divineDefBoostActive = false;
        p.divineSuperCmbBoostActive = false;
        p.divineMagicBoostActive = false;
        p.divineRangingBoostActive = false;
        p.getMovement().restoreEnergy(100);
        p.getPrayer().deactivateAll();
        p.resetFreeze();
        p.cureVenom(0);
        restoreSpecial(100);
        if (specialActive != null)
            deactivateSpecial();
        TargetOverlay.reset(p);
    }

    private void handleItemsOnDeath(Killer killer, Player pKiller) {

        /** Loot keys **/
        if (pKiller != null && pKiller.getLootKeyManager().canReceive(killer, p))
            return;

        IKOD.forLostItem(p, killer, item -> {
            ItemDef def = item.getDef();
            if (p.isEasyModeWithEasyXP() && item.getDef().free) {
                item.remove();
                return;
            }
            if (def.id == 12746 || def.id == 12748) { //tier 1-2 emblems
                if (pKiller != null)
                    new GroundItem(12746, 1).owner(pKiller).position(p.getPosition()).spawnPrivate(); //Never want emblem to appear for anyone else.
                return;
            }
            if (def.id == 8970) {
                if (pKiller != null)
                    new GroundItem(8970, 1).owner(pKiller).position(p.getPosition()).spawn();
                return;
            }
            if (def.id >= 12749 && def.id <= 12756) { //tier 3-10 emblems
                if (pKiller != null)
                    new GroundItem(def.id - 1, 1).owner(pKiller).position(p.getPosition()).spawnPrivate(); //Never want emblem to appear for anyone else.
                return;
            }
            if (def.id == 21807 || def.id == 21810 || def.id == 21813) { // ancient emblem, totem and statuette
                if (pKiller != null)
                    new GroundItem(def.id, 1).owner(pKiller).position(p.getPosition()).spawnPrivate(); //Never want emblem to appear for anyone else.
                return;
            }
            if (pKiller == null || !def.tradeable) {
                new GroundItem(item).owner(p).position(p.getPosition()).spawn(3);
            } else if (pKiller.getGameMode().isAnyIron()) {
                new GroundItem(item).owner(p).position(p.getPosition()).diedToIron(p).spawn(3);
            } else {
                new GroundItem(item).owner(pKiller).position(p.getPosition()).spawn(3);
            }
            //new GroundItem(item).owner(pKiller).position(p.getPosition()).spawn();
        });
    }

    /**
     * Level
     */

    private int level;

    private boolean updateLevel = true;

    public void updateLevel() {
        updateLevel = true;
    }

    public void checkLevel() {
        if (updateLevel) {
            updateLevel = false;
            setLevel();
        }
    }

    protected void setLevel() {
        int newLevel = CombatUtils.getCombatLevel(p);
        if (level != newLevel) {
            level = newLevel;
            p.getAppearance().update();
            p.getPacketSender().sendString(COMBAT_OPTIONS, 3, "Combat Lvl: " + level);
        }
    }

    public void updateCombatLevel() {
        p.getPacketSender().sendString(COMBAT_OPTIONS, 3, "Combat Lvl: " + level);
    }

    public int getLevel() {
        return level;
    }

    /**
     * Weapon
     */

    public WeaponType weaponType;

    private AttackSet attackSet;

    public void updateWeapon(boolean fromAutocast) {
        ItemDef wepDef = p.getEquipment().getDef(Equipment.SLOT_WEAPON);
        WeaponType newWeapon = (wepDef == null || wepDef.weaponType == null) ? WeaponType.UNARMED : wepDef.weaponType;
        boolean login = weaponType == null;
        if (weaponType != newWeapon) {
            weaponType = newWeapon;
            p.privateSound(weaponType.equipSound);
            updateAttackSet();
            Config.WEAPON_TYPE.set(p, weaponType.config);
        }
        if (!fromAutocast) {
            TabCombat.updateAutocast(p, login);
            if (p.attackPlayerListener == null)
                TabCombat.loadOldAutocast(p);

        }
        if (specialActive != null && (wepDef == null || wepDef.special != specialActive))
            deactivateSpecial();
        p.getPacketSender().sendString(COMBAT_OPTIONS, 1, wepDef == null ? "Unarmed" : wepDef.name);
        if (p.sotdDelay.isDelayed() && (wepDef == null || !(wepDef.special instanceof StaffOfTheDead)))
            p.sotdDelay.reset();
        if (p.vestasSpearSpecial.isDelayed() && (wepDef == null || !(wepDef.special instanceof VestasSpear)))
            p.vestasSpearSpecial.reset();
    }

    private void updateAttackSet() {
        int setIndex = Config.ATTACK_SET.get(p);
        if ((attackSet = weaponType.attackSets[setIndex]) == null) {
            for (int i = setIndex; i >= 0; i--) {
                if (weaponType.attackSets[i] != null) {
                    attackSet = weaponType.attackSets[i];
                    Config.ATTACK_SET.set(p, i);
                    break;
                }
            }
        }
    }

    public void changeAttackSet(int newSetIndex) {
        boolean invalidAttackSet = weaponType.attackSets[newSetIndex] == null;
        attackSet = weaponType.attackSets[invalidAttackSet ? 0 : newSetIndex];
        Config.ATTACK_SET.set(p, invalidAttackSet ? 0 : newSetIndex);
        TabCombat.resetAutocast(p);
    }

    @Override
    public AttackStyle getAttackStyle() {
        return attackSet.style;
    }

    @Override
    public AttackType getAttackType() {
        return attackSet.type;
    }

    /**
     * Special
     */

    private Special specialActive;

    private void deactivateSpecial() {
        specialActive = null;
        Config.SPECIAL_ACTIVE.set(p, 0);
    }

    public void toggleSpecial() {
        ItemDef wepDef = p.getEquipment().getDef(Equipment.SLOT_WEAPON);
        if (specialActive != null) {
            deactivateSpecial();
            if (wepDef != null && wepDef.graniteMaul)
                queueGraniteMaulSpecial();
            return;
        }
        if (wepDef != null && wepDef.special != null) {
            if (DuelRule.NO_SPECIALS.isToggled(p)) {
                p.sendMessage("Special attacks have been disabled for this duel!");
                return;
            }
            if (!wepDef.special.handleActivation(p)) {
                if (wepDef.graniteMaul)
                    queueGraniteMaulSpecial();
                specialActive = wepDef.special;
                Config.SPECIAL_ACTIVE.set(p, 1);
            }
        }
    }

    public double getRegularMagicDamageBoost(boolean skipNightmareStaff) {
        double bonus = 1;
        Equipment equipment = p.getEquipment();
        if (equipment.wearsAmuletOfTheDamned() && equipment.wearsAhrimRobes()) {
            bonus += 0.3;
        }
        if (equipment.wearsTormentedBracelet()) {
            bonus += 0.05;
        }
        if (equipment.wearsOcculNecklace()) {
            bonus += 0.1;
        }
        if (equipment.wearsStaffOfTheDead() || equipment.wearsStaffOfLight() || equipment.wearsStaffOfBalance()) {
            bonus += 0.15;
        }
        if (equipment.wearsAncestralHat()) {
            bonus += 0.02;
        }
        if (equipment.wearsAncestralTop()) {
            bonus += 0.02;
        }
        if (equipment.wearsAncestralBottom()) {
            bonus += 0.02;
        }
        if (equipment.wearsKodaiWand()) {
            bonus += 0.15;
        }
        if (equipment.wearsAhrimStaff()) {
            bonus += 0.05;
        }
        if (equipment.wearsEliteVoid()) {
            bonus += 0.025;
        }
        if (equipment.wearsImbuedGodCape()) {
            bonus += 0.02;
        }
        if (!skipNightmareStaff && equipment.wearsNightmareStaff()) {
            bonus += 0.15;
        }
        if (SpellBook.MODERN.isActive(p) && equipment.wearsSmokeBattleStaff()) {
            bonus += 0.1;
        }
        return bonus;
    }

    private boolean handleSpecial(AttackStyle attackStyle, AttackType attackType, int maxDamage) {
        Special special = specialActive;
        if (special == null)
            return false;
        int amount = special.getDrainAmount();
        if (special instanceof DragonWarhammer) {
            if (target != null && target instanceof NPC) {
                if (PerkManager.isActive(p, Perk.HEAVY_STRIKE)) {
                    amount = 30;
                }
            }
        }
        int energy = Config.SPECIAL_ENERGY.get(p);
        if (amount > energy / 10) {
            p.sendMessage("You need at least " + amount + "% special attack energy to use this.");
            specialActive = null;
            Config.SPECIAL_ACTIVE.set(p, 0);
            return false;
        }

        if (!special.handle(p, target, attackStyle, attackType, maxDamage)) {
            specialActive = null;
            Config.SPECIAL_ACTIVE.set(p, 0);
            return false;
        }

        if (target != null && target.player != null) {
            p.specTeleportDelay.delaySeconds(5);
        }

        boolean ignoresSpecialDrain = (target != null && target.isNpc() && target.npc.getId() == 2668);
        if (!ignoresSpecialDrain) {
            Config.SPECIAL_ENERGY.set(p, energy - (amount * 10)); //drain special energy
        }

        specialActive = null;
        Config.SPECIAL_ACTIVE.set(p, 0);
        return true;
    }

    public void restoreSpecial(int percent) {
        int energy = Config.SPECIAL_ENERGY.get(p);
        int newEnergy = Math.min(1000, energy + (percent * 10));
        if (energy != newEnergy)
            Config.SPECIAL_ENERGY.set(p, newEnergy);
    }

    public boolean useSpecialEnergy(int amount) {
        int energy = Config.SPECIAL_ENERGY.get(p);
        if (amount > energy / 10) {
            p.sendMessage("You need at least " + amount + "% special attack energy to use this.");
            return false;
        }
        Config.SPECIAL_ENERGY.set(p, energy - (amount * 10));
        return true;
    }

    /**
     * Special - Granite maul
     * player.sendMessage("Warning: Since the maul's special is an instant attack, it will be wasted when used on a first strike.");
     * ^ todo? (There's really no good place to put it..:c lol)
     */

    private int graniteMaulSpecials;

    private int graniteMaulTimeoutTicks;

    private void queueGraniteMaulSpecial() {
        graniteMaulSpecials++;
        graniteMaulTimeoutTicks = 5;
    }

    private void checkGraniteMaul() {
        if (graniteMaulTimeoutTicks > 0) {
            if (--graniteMaulTimeoutTicks == 0)
                graniteMaulSpecials = 0;
            else if (graniteMaulTimeoutTicks == 4) //1 tick less than 5 because it was subtracted
                autoAttackGraniteMaul();
        }
    }

    private void autoAttackGraniteMaul() {
        if (target != null || lastTarget == null)
            return;
        if (p.getHeight() != lastTarget.getHeight())
            return;
        int x = p.getAbsX();
        int y = p.getAbsY();
        if (lastTarget.getSize() == 1) {
            int targetX = lastTarget.getAbsX();
            int targetY = lastTarget.getAbsY();
            int diffX = Math.abs(x - targetX);
            int diffY = Math.abs(y - targetY);
            if ((diffX + diffY) != 1)
                return;
        } else {
            Position closestPos = Misc.getClosestPosition(p, lastTarget);
            int targetX = closestPos.getX();
            int targetY = closestPos.getY();
            int diffX = Math.abs(x - targetX);
            int diffY = Math.abs(y - targetY);
            if (diffX > 1 || diffY > 1)
                return;
        }
        p.face(lastTarget);
        setTarget(lastTarget);
    }

    private boolean specialGraniteMaul() {
        int graniteMaulSpecials = this.graniteMaulSpecials;
        if (graniteMaulSpecials == 0)
            return false;
        this.graniteMaulSpecials = 0;

        ItemDef wep = p.getEquipment().getDef(Equipment.SLOT_WEAPON);
        if (wep == null || !wep.graniteMaul)
            return false;

        int energy = Config.SPECIAL_ENERGY.get(p);
        graniteMaulSpecials = Math.min(graniteMaulSpecials, energy / 500);
        if (graniteMaulSpecials == 0)
            return false;

        AttackStyle style = getAttackStyle();
        AttackType type = getAttackType();
        int maxDamage = CombatUtils.getMaxDamage(p, style, type);
        for (int i = 0; i < graniteMaulSpecials; i++) {
            wep.special.handle(p, target, style, type, maxDamage);
            if (target != null && target.player != null) {
                p.specTeleportDelay.delaySeconds(5);
            }
            energy -= 500;
        }
        Config.SPECIAL_ENERGY.set(p, energy);
        if (specialActive != null)
            deactivateSpecial();
        return true;
    }

    /**
     * Dragonfire shield special attacks
     */


    /**
     * Skulling
     */

    private HashSet<Integer> skullers;

    @Expose
    public int skullDelay;

    @Expose
    public boolean highRiskSkull;

    public void skull(Player pTarget) {
        if (p.wildernessLevel == 0 && !p.pvpAttackZone)
            return;
        if (skullers != null && skullers.contains(pTarget.getUserId()) && pTarget.getCombat().isSkulled())
            return;
        if (pTarget.getCombat().skullers == null)
            pTarget.getCombat().skullers = new HashSet<>(5);
        pTarget.getCombat().skullers.add(p.getUserId());
        if (!highRiskSkull)
            skullNormal();
    }

    public void skullNormal() {
        skullDelay = 1000;
        highRiskSkull = false;
        if (p.getLootKeyManager().hasSkull())
            return;
        p.getAppearance().setSkullIcon(KillingSpree.overheadId(p));
    }

    public void skullHighRisk() {
        if (p.getLootKeyManager().hasSkull()) {
            p.sendMessage("You cannot get a high risk skull while being marked with lootkeys.");
            return;
        }
        skullDelay = 1000;
        highRiskSkull = true;
        p.getAppearance().setSkullIcon(KillingSpree.overheadId(p));
        if (p.getPrayer().isActive(Prayer.PROTECT_ITEM))
            p.getPrayer().deactivate(Prayer.PROTECT_ITEM);
        p.sendMessage(Color.ORANGE_RED.wrap("Warning:") + " The Protect Item prayer is disabled when marked with a high-risk skull.");
    }

    private void depleteSkull() {
        if (!highRiskSkull && skullDelay > 0 && --skullDelay == 0)
            p.getAppearance().setSkullIcon(-1);
    }

    private void resetSkullers() {
        if (skullers != null)
            skullers.clear();
    }

    public void resetSkull() {
        skullDelay = 0;
        highRiskSkull = false;
        if (p.getAppearance().getSkullIcon() != -1)
            p.getAppearance().setSkullIcon(-1);
    }

    public boolean isSkulled() {
        return highRiskSkull || skullDelay > 0;
    }

    /**
     * Teleblock
     */

    @Expose
    public int tbTicks, tbImmunityTicks;

    public void teleblock() {
        if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC)) {
            tbTicks = 250;
            p.getPacketSender().sendWidget(Widget.TELEBLOCK, 150);
            p.sendMessage("<col=4f006f>A teleblock spell has been cast on you. It will expire in 2 minutes, 30 seconds.");
        } else {
            tbTicks = 500;
            p.getPacketSender().sendWidget(Widget.TELEBLOCK, 300);
            p.sendMessage("<col=4f006f>A teleblock spell has been cast on you. It will expire in 5 minutes.");
        }
    }

    private void depleteTb() {
        if (tbTicks > 0 && --tbTicks == 0) {
            p.sendMessage("<col=4f006f>The teleblock spell cast on you fades away."); //custom lul
            tbImmunityTicks = 100;
        }
        if (tbImmunityTicks > 0)
            --tbImmunityTicks;
    }

    public void resetTb() {
        tbTicks = 0;
        tbImmunityTicks = 0;
        p.getPacketSender().sendWidget(Widget.TELEBLOCK, 0);
    }

    public boolean checkTb() {
        if (tbTicks == 0)
            return false;
        long ms = Server.tickMs() * tbTicks;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(minutes);
        if (minutes == 0) {
            if (seconds <= 1)
                p.sendMessage("A teleport block has been cast on you. It should wear off in 1 second.");
            else
                p.sendMessage("A teleport block has been cast on you. It should wear off in " + seconds + " seconds.");
        } else if (minutes == 1) {
            if (seconds == 0)
                p.sendMessage("A teleport block has been cast on you. It should wear off in 1 minute.");
            else if (seconds == 1)
                p.sendMessage("A teleport block has been cast on you. It should wear off in 1 minute, 1 second.");
            else
                p.sendMessage("A teleport block has been cast on you. It should wear off in 1 minute, " + seconds + " seconds.");
        } else {
            if (seconds == 0)
                p.sendMessage("A teleport block has been cast on you. It should wear off in " + minutes + " minutes.");
            else if (seconds == 1)
                p.sendMessage("A teleport block has been cast on you. It should wear off in " + minutes + " minutes, 1 second.");
            else
                p.sendMessage("A teleport block has been cast on you. It should wear off in " + minutes + " minutes, " + seconds + " seconds.");
        }
        return true;
    }

    /**
     * Charge
     */

    @Expose
    public int chargeTicks;

    public boolean charge() {
        if (chargeTicks > 0) {
            p.sendMessage("You can't recast that yet, your current Charge is too strong.");
            return false;
        }
        p.animate(811);
        p.graphics(111, 130, 3);
        chargeTicks = 100;
        return true;
    }

    private void depleteCharge() {
        if (chargeTicks > 0)
            chargeTicks--;
    }

    /**
     * Misc
     */

    @Override
    public double getLevel(StatType stat) {
        return p.getStats().get(stat).currentLevel;
    }

    @Override
    public double getBonus(int bonusType) {
        return p.getEquipment().bonuses[bonusType];
    }

    @Override
    public double getDragonfireResistance() {
        int shieldId = p.getEquipment().getId(Equipment.SLOT_SHIELD);
        /**
         * Dragon fire
         */
        double absorbDamage = 0.0;
        if (p.superAntifireTicks > 0)
            absorbDamage += 1.0;
        else if (p.antifireTicks > 0 || p.princeBlackDragon)
            absorbDamage += 0.8;

        if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC))
            absorbDamage += 0.2;

        if (shieldId == 1540) { // antifire shield
            absorbDamage += 0.8;
        }
        if (shieldId == 31011) { //enchanted dfs
            absorbDamage += 0.98;
        }
        if (p.getEquipment().contains(25918)) { //enchanted dhcb
            absorbDamage = 0.95;
        }
        if (p.getEquipment().contains(31057)) { //enchanted dh lance
            absorbDamage = 0.95;
        }
        if (shieldId == 11283 || shieldId == 11284 || shieldId == 21634 || shieldId == 21633 || // dragonfire shield
                shieldId == 22003 || shieldId == 22002) {
            absorbDamage += 0.8;
        }
        return absorbDamage;
    }

    @Override
    public void faceTarget() {
        p.face(target);
    }

    public Special specialActive() {
        return specialActive;
    }

    public boolean hasSpecialActive() {
        return specialActive != null;
    }

    public static void addItemsToKey(Player p) {
        if (!p.getInventory().contains(26651)) {
            generateKeyOneItems(p);
        }
    }

    public static void generateKeyOneItems(Player p) {
        p.keyonehelmetslot = p.getEquipment().get(Equipment.SLOT_HAT).getId();
        p.keyonecapeslot = p.getEquipment().get(Equipment.SLOT_CAPE).getId();
        p.keyoneamuletslot = p.getEquipment().get(Equipment.SLOT_AMULET).getId();
        p.keyoneammoslot = p.getEquipment().get(Equipment.SLOT_AMMO).getId();
        p.keyoneweaponslot = p.getEquipment().get(Equipment.SLOT_WEAPON).getId();
        p.keyonechestslot = p.getEquipment().get(Equipment.SLOT_CHEST).getId();
        p.keyoneshieldslot = p.getEquipment().get(Equipment.SLOT_SHIELD).getId();
        p.keyonelegsslot = p.getEquipment().get(Equipment.SLOT_LEGS).getId();
        p.keyoneglovesslot = p.getEquipment().get(Equipment.SLOT_HANDS).getId();
        p.keyonebootsslot = p.getEquipment().get(Equipment.SLOT_FEET).getId();
        p.keyoneringslot = p.getEquipment().get(Equipment.SLOT_RING).getId();
    }
}
