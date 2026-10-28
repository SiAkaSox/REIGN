package io.ruin.model.inter;

import io.ruin.Server;
import io.ruin.cache.Color;
import io.ruin.model.achievements.listeners.elite.TheVault;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.greatvaultrewards.*;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.item.Item;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.inter.greatvaultrewards.SerenChestRewards;
import io.ruin.utility.Broadcast;

import java.util.concurrent.TimeUnit;

import static io.ruin.Server.STARTUP_THREAD;

public class TheGreatVault {

    private static final long VAULT_TIMER = TimeUnit.HOURS.toMillis(24);

    static {

            ObjectAction.register(32994, "Search", (player, obj) -> openInter(player));
            InterfaceHandler.register(907, h -> {//Main interface
                h.actions[8] = (SlotAction) (player, slot) -> claimHtfReward(player);
                h.actions[9] = (SlotAction) (player, slot) -> claimCtfReward(player);
                h.actions[10] = (SlotAction) (player, slot) -> SerenChestRewards.openChest(player);
                h.actions[11] = (SlotAction) (player, slot) -> claimGlodRewards(player);
                h.actions[15] = (SlotAction) (player, slot) -> claimPirateShipReward(player);
                h.actions[16] = (SlotAction) (player, slot) -> claimDeadmanChestReward(player);
            });
    }

    public static void openInter(Player p) {
        p.openInterface(InterfaceType.MAIN, 907);
    }

    public static void claimHtfReward(Player p) {
        if (!p.getInventory().contains(3460)) {
            p.sendMessage("You need a <col=8B0000>Hold The Fort Key</col> to claim this reward.");
        } else {
            p.startEvent(event -> {
                        p.lock(LockType.FULL_NULLIFY_DAMAGE);
                        p.dialogue(new MessageDialogue("Opening The Great Vault, searching for a reward..").hideContinue());
                        event.delay(1);
                        p.animate(881);
                        event.delay(2);
                        p.getInventory().remove(3460, 1);
                        HtfChestRewards.claimReward(p);
                        TheVault.complete(p);
                        if (p.greatvaultBoost > 0) {
                            p.dialogue(new MessageDialogue("Your great vault boost allows you to open the chest again!").hideContinue());
                            event.delay(1);
                            p.animate(881);
                            event.delay(2);
                            HtfChestRewards.greatvaultBoostReward(p);
                        }
                        p.unlock();
            });
        }
    }
    public static void claimCtfReward(Player p) {
        if (!p.getInventory().contains(3461)) {
            p.sendMessage("You need a <col=8B0000>Capture The Flag Key</col> to claim this reward.");
        } else {
            p.startEvent(event -> {
                p.lock(LockType.FULL_NULLIFY_DAMAGE);
                p.dialogue(new MessageDialogue("Opening The Great Vault, searching for a reward..").hideContinue());
                event.delay(1);
                p.animate(881);
                event.delay(2);
                p.getInventory().remove(3461, 1);
                CtfChestRewards.claimReward(p);
                TheVault.complete(p);
                if (p.greatvaultBoost > 0) {
                    p.dialogue(new MessageDialogue("Your great vault boost allows you to open the chest again!").hideContinue());
                    event.delay(1);
                    p.animate(881);
                    event.delay(2);
                    CtfChestRewards.greatvaultBoostReward(p);
                }
                p.unlock();
            });
        }
    }
    public static void claimPirateShipReward(Player p) {
        if (!p.getInventory().contains(3464)) {
            p.sendMessage("You need a <col=8B0000>Pirate's Ship Key</col> to claim this reward.");
        } else {
            p.startEvent(event -> {
                p.lock(LockType.FULL_NULLIFY_DAMAGE);
                p.dialogue(new MessageDialogue("Opening The Great Vault, searching for a reward..").hideContinue());
                event.delay(1);
                p.animate(881);
                event.delay(2);
                p.getInventory().remove(3464, 1);
                PirateShipChestRewards.claimReward(p);
                TheVault.complete(p);
                if (p.greatvaultBoost > 0) {
                    p.dialogue(new MessageDialogue("Your great vault boost allows you to open the chest again!").hideContinue());
                    event.delay(1);
                    p.animate(881);
                    event.delay(2);
                    PirateShipChestRewards.greatvaultBoostReward(p);
                }
                p.unlock();
            });
        }
    }
    public static void claimDeadmanChestReward(Player p) {
        if (!p.getInventory().contains(3463)) {
            p.sendMessage("You need a <col=8B0000>Deadman's Chest Key</col> to claim this reward.");
        } else {
            p.startEvent(event -> {
                p.lock(LockType.FULL_NULLIFY_DAMAGE);
                p.dialogue(new MessageDialogue("Opening The Great Vault, searching for a reward..").hideContinue());
                event.delay(1);
                p.animate(881);
                event.delay(2);
                p.getInventory().remove(3463, 1);
                DeadmanChestRewards.claimReward(p);
                TheVault.complete(p);
                if (p.greatvaultBoost > 0) {
                    p.dialogue(new MessageDialogue("Your great vault boost allows you to open the chest again!").hideContinue());
                    event.delay(1);
                    p.animate(881);
                    event.delay(2);
                    DeadmanChestRewards.greatvaultBoostReward(p);
                }
                p.unlock();
            });
        }
    }
    public static void claimGlodRewards(Player p) {
        if (!p.getInventory().contains(25430)) {
            p.sendMessage("You need a <col=8B0000>Glod Great Vault Key</col> to claim this reward.");
        } else {
            p.startEvent(event -> {
                p.lock(LockType.FULL_NULLIFY_DAMAGE);
                p.dialogue(new MessageDialogue("Opening The Great Vault, searching for a reward..").hideContinue());
                event.delay(1);
                p.animate(881);
                event.delay(2);
                p.getInventory().remove(25430, 1);
                GlodChestRewards.claimReward(p);
                TheVault.complete(p);
                if (p.greatvaultBoost > 0) {
                    p.dialogue(new MessageDialogue("Your great vault boost allows you to open the chest again!").hideContinue());
                    event.delay(1);
                    p.animate(881);
                    event.delay(2);
                    GlodChestRewards.greatvaultBoostReward(p);
                }
                p.unlock();
            });
        }
    }
    public static void claimPvPReward(Player p) {
        if(p.pvpVaultTimer > System.currentTimeMillis()) {
            p.dialogue(
                    new ItemDialogue().one(744, "You must wait " + Color.COOL_BLUE.wrap(getRemainingTime(p)) + " until you can claim another reward.").lineHeight(24));
            return;
        }
        if (p.pvpVaultActivities < 10) {
            p.dialogue(
                    new MessageDialogue("You must complete at least 10 Arenas or Battlegrounds today before claiming a reward.")
            );
            return;
        }
        p.startEvent(event -> {
            p.lock(LockType.FULL_NULLIFY_DAMAGE);
            p.dialogue(new MessageDialogue("Opening the vault, searching for a reward.."));
            p.animate(536);
            event.delay(2);
            p.unlock();
            giveReward(p);
            TheVault.complete(p);
        }
        );
    }

    public static void giveReward(Player p) {
        Item reward;
        reward = MYSTERY_BOX_TABLE.rollItem();
        p.conquestPoints += 100;
        p.pvpVaultTimer = System.currentTimeMillis() + VAULT_TIMER;
        p.pvpVaultActivities = 0;
        p.getInventory().addOrDrop(reward);
        p.dialogue(
                new ItemDialogue().one(reward.getId(), "Congratulations, you've won a "+ Color.COOL_BLUE.wrap(reward.getDef().name) +"!")
        );
    }

    private static final LootTable MYSTERY_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(4708, 1, 50), //Ahrim's hood
            new LootItem(4712, 1, 50), //Ahrim's robetop
            new LootItem(4714, 1, 50), //Ahrim's robeskirt
            new LootItem(4716, 1, 40), //Dharok's helm
            new LootItem(4720, 1, 40), //Dharok's platebody
            new LootItem(4722, 1, 40), //Dharok's platelegs
            new LootItem(4718, 1, 40), //Dharok's greataxe
            new LootItem(4736, 1, 50), //Karil's leathertop
            new LootItem(13442, 100, 250, 65), //100 Anglerfish
            new LootItem(11235, 1, 45), //Dark bow
            new LootItem(8927, 1, 25), //Bandana eyepatch
            new LootItem(2643, 1, 35), //Dark cavalier
            new LootItem(12301, 1, 25), //Blue headband
            new LootItem(2577, 1, 15), //Ranger boots
            new LootItem(12639, 1, 25), //Guthix halo
            new LootItem(12430, 1, 25), //Afro
            new LootItem(12245, 1, 25), //Beanie
            new LootItem(12638, 1, 25), //Zamorak halo
            new LootItem(12637, 1, 25), //Saraodmin halo
            new LootItem(11899, 1, 25), //Decorative platebody (Melee)
            new LootItem(11900, 1, 25), //Decorative platelegs (Melee)
            new LootItem(11898, 1, 25), //Decorative hat (Magic)
            new LootItem(11896, 1, 25), //Decorative robe top (Magic)
            new LootItem(11897, 1, 25), //Decorative bottoms (Magic)
            new LootItem(12375, 1, 25), //Black cane
            new LootItem(12377, 1, 25), //Adamant cane
            new LootItem(12365, 1, 25), //Iron dragon mask
            new LootItem(12367, 1, 25), //Steel dragon mask
            new LootItem(12369, 1, 25), //Mithril dragon mask
            new LootItem(12518, 1, 25), //Green dragon mask
            new LootItem(12522, 1, 25), //Red dragon mask
            new LootItem(12524, 1, 25), //Black dragon mask
            new LootItem(12763, 1, 25), //White dark bow paint
            new LootItem(12761, 1, 25), //Yellow dark bow paint
            new LootItem(12759, 1, 25), //Green dark bow paint
            new LootItem(12757, 1, 25), //Blue dark bow paint
            new LootItem(12769, 1, 25), //Frozen whip mix
            new LootItem(12771, 1, 25), //Volcanic whip mix
            new LootItem(12829, 1, 25), //Spirit shield
            new LootItem(6922, 1, 25), //Infinity gloves
            new LootItem(6918, 1, 25), //Infinity hat
            new LootItem(6916, 1, 25), //Infinity top
            new LootItem(6924, 1, 25), //Infinity bottoms
            new LootItem(6528, 1, 25), //Tzhaar-ket-om
            new LootItem(6525, 1, 25), //Toktz-xil-ek
            new LootItem(4151, 1, 25), //Abyssal whip
            new LootItem(4153, 1, 25), //Granite maul
            new LootItem(6920, 1, 25), //Infinity boots
            new LootItem(11128, 1, 25),   //Berseker necklace
            new LootItem(12696, 200, 250, 25), //250 Super combat potions
            new LootItem(13442, 200, 250, 25), //250 Anglerfish
            new LootItem(12696, 400, 500, 25), //500 Super combat potions
            new LootItem(13442, 450, 650, 25), //500 Anglerfish
            new LootItem(1037, 1, 25), //Bunny ears
            new LootItem(6666, 1, 25), //Flippers
            new LootItem(4566, 1, 25), //rubber chicken
            new LootItem(13182, 1, 25), //Bunny feat
            new LootItem(12006, 1, 25), //Abyssal tentacle
            new LootItem(6665, 1, 25), //Mudskipper hat
            new LootItem(11919, 1, 15), //Cow mask
            new LootItem(12956, 1, 15), //Cow top
            new LootItem(12957, 1, 15), //Cow bottoms
            new LootItem(12958, 1, 15), //Cow gloves
            new LootItem(12959, 1, 15), //Cow boots
            new LootItem(12002, 1, 15), //Occult necklace
            new LootItem(6585, 1, 15), //Amulet of fury
            new LootItem(11283, 1, 15), //Dragonfire shield
            new LootItem(12902, 1, 15), //Toxic staff of the dead
            new LootItem(11791, 1, 15), //Staff of the dead
            new LootItem(4224, 1, 15), //New crystal shield
            new LootItem(12831, 1, 15), //Blessed spirit shield
            new LootItem(11926, 1, 15), //Odium ward
            new LootItem(11924, 1, 15), //Malediction ward
            new LootItem(12379, 1, 15), //Rune cane
            new LootItem(12373, 1, 15), //Dragon cane
            new LootItem(12363, 1, 15), //Bronze dragon mask
            new LootItem(12371, 1, 15), //Lava dragon mask
            new LootItem(12931, 1, 5), //Serpentine helm
            new LootItem(13235, 1, 5), //Eternal boots
            new LootItem(13237, 1, 5), //Pegasian boots
            new LootItem(13239, 1, 5), //Primordial boots
            new LootItem(11828, 1, 5), //Armadyl chestplate
            new LootItem(11830, 1, 5), //Armadyl chainskirt
            new LootItem(11826, 1, 5), //Armadyl helmet
            new LootItem(11834, 1, 5), //Bandos tassets
            new LootItem(11832, 1, 5), //Bandos chestplate
            new LootItem(11808, 1, 5), //Zamorak godsword
            new LootItem(11806, 1, 5), //Saradomin godsword
            new LootItem(11804, 1, 5), //Bandos godsword
            new LootItem(6737, 1, 5), //Berserker ring
            new LootItem(6735, 1, 5), //Warriors ring
            new LootItem(6733, 1, 5), //Archers ring
            new LootItem(6731, 1, 5), //Seers ring
            new LootItem(11773, 1, 5), //Berserker ring (i)
            new LootItem(11772, 1, 5), //Warriors ring (i)
            new LootItem(11771, 1, 5), //Archers ring (i)
            new LootItem(11770, 1, 5), //Seers ring (i))
            new LootItem(20517, 1, 5), //Elder chaos top
            new LootItem(20520, 1, 5), //Elder chaos bottom
            new LootItem(20595, 1, 5), //Elder chaos hood
            new LootItem(12696, 800, 1000, 1), //1000 Super combat potions
            new LootItem(13442, 800, 1000, 1), //1000 Anglerfish
            new LootItem(12696, 1300, 1500, 1), //1500 Super combat potions
            new LootItem(13442, 1200, 1500, 1), //1500 Anglerfish
            new LootItem(11806, 1, 1), //Armadyl godsword
            new LootItem(12825, 1, 1), //Arcane spirit shield
            new LootItem(12821, 1, 1).broadcast(Broadcast.WORLD), //Spectral spirit shield
            new LootItem(13271, 1, 1), //Abyssal dagger
            new LootItem(12791, 1, 1), //Rune pouch
            new LootItem(11785, 1, 1), //Armadyl crossbow
            new LootItem(13576, 1, 1), //Dragon warhammer
            new LootItem(2581, 1, 1), //Robin hood hat
            new LootItem(22981, 1, 1), //Ferocious gloves
            new LootItem(22975, 1, 1), //Brimstone ring
            new LootItem(22978, 1, 1), //Dragon hunter lance
            new LootItem(12596, 1, 1), //Rangers' tunic
            new LootItem(1053, 1, 1).broadcast(Broadcast.WORLD), //Green halloween mask
            new LootItem(1055, 1, 1).broadcast(Broadcast.WORLD), //Blue halloween mask
            new LootItem(1057, 1, 1).broadcast(Broadcast.WORLD), //Red halloween mask
            new LootItem(11847, 1, 1).broadcast(Broadcast.WORLD), //Black halloween mask
            new LootItem(1050, 1, 1).broadcast(Broadcast.WORLD), //Santa hat
            new LootItem(13343, 1, 1).broadcast(Broadcast.WORLD), //Black santa hat
            new LootItem(13344, 1, 1).broadcast(Broadcast.WORLD), //Inverted santa hat
            new LootItem(12696, 4000, 5000, 1), //5000 Super combat potions
            new LootItem(13442, 4000, 5000, 1), //5000 Anglerfish
            new LootItem(1038, 1, 1).broadcast(Broadcast.WORLD), //Red party hat
            new LootItem(1040, 1, 1).broadcast(Broadcast.WORLD), //Yellow party hat
            new LootItem(1042, 1, 1).broadcast(Broadcast.WORLD), //Blue party hat
            new LootItem(1044, 1, 1).broadcast(Broadcast.WORLD), //Green party hat
            new LootItem(1046, 1, 1).broadcast(Broadcast.WORLD), //Purple party hat
            new LootItem(1048, 1, 1).broadcast(Broadcast.WORLD), //White  party hat
            new LootItem(11862, 1, 1).broadcast(Broadcast.WORLD), //Black party hat
            new LootItem(11863, 1, 1).broadcast(Broadcast.WORLD), //Rainbow party hat
            new LootItem(12399, 1, 1).broadcast(Broadcast.WORLD), //Partyhat & specs
            new LootItem(962, 1, 1).broadcast(Broadcast.WORLD), // Xmas cracker
            new LootItem(1050, 1, 1).broadcast(Broadcast.WORLD), // Santa hat
            new LootItem(1419, 1, 1), // Scythe
            new LootItem(1037, 1, 1), // Bunny ears
            new LootItem(12422, 1, 1).broadcast(Broadcast.WORLD), // 3rd age wand
            new LootItem(12424, 1, 1).broadcast(Broadcast.WORLD), // 3rd age bow
            new LootItem(12426, 1, 1).broadcast(Broadcast.WORLD), // 3rd age sword
            new LootItem(12437, 1, 1).broadcast(Broadcast.WORLD), // 3rd age cloak
            new LootItem(10330, 1, 1).broadcast(Broadcast.WORLD), // 3rd age range top
            new LootItem(10332, 1, 1).broadcast(Broadcast.WORLD), // 3rd age range legs
            new LootItem(10334, 1, 1).broadcast(Broadcast.WORLD), // 3rd age range coif
            new LootItem(10336, 1, 1).broadcast(Broadcast.WORLD), // 3rd age range vanbraces
            new LootItem(10338, 1, 1).broadcast(Broadcast.WORLD), // 3rd age robe top
            new LootItem(10340, 1, 1).broadcast(Broadcast.WORLD), // 3rd age robe
            new LootItem(10342, 1, 1).broadcast(Broadcast.WORLD), // 3rd age mage hat
            new LootItem(10344, 1, 1).broadcast(Broadcast.WORLD), // 3rd age amulet
            new LootItem(10346, 1, 1).broadcast(Broadcast.WORLD), // 3rd age platelegs
            new LootItem(10348, 1, 1).broadcast(Broadcast.WORLD), // 3rd age platebody
            new LootItem(10350, 1, 1).broadcast(Broadcast.WORLD), // 3rd age fullhelm
            new LootItem(10352, 1, 1).broadcast(Broadcast.WORLD), // 3rd age kiteshield
            new LootItem(19988, 1, 1), // Blacksmith's helm
            new LootItem(19991, 1, 1), // Bucket helm
            new LootItem(20059, 1, 1), // Bucket helm (g)
            new LootItem(13652, 1, 1), // Dragon claws
            new LootItem(20050, 1, 1), // Obsidian cape (f)
            new LootItem(20008, 1, 1), // Fancy tiara
            new LootItem(20110, 1, 1), // Bowl wig
            new LootItem(20020, 1, 1), // Lesser demon mask
            new LootItem(20023, 1, 1), // Greater demon mask
            new LootItem(20026, 1, 1), // Black demon mask
            new LootItem(20029, 1, 1), // Old demon mask
            new LootItem(20032, 1, 1) // Jungle demon mask
    );

    private static String getRemainingTime(Player player) {
        long ms = player.pvpVaultTimer - System.currentTimeMillis();
        long hours = TimeUnit.MILLISECONDS.toHours(ms);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
        return (hours >= 1 ? (hours + " hour" + (hours > 1 ? "s" : "") + " ") : "") +
                Math.max((minutes - TimeUnit.HOURS.toMinutes(hours)), 1) + " min" +
                ((minutes - TimeUnit.HOURS.toMinutes(hours)) > 1 ? "s" : "");
    }

}