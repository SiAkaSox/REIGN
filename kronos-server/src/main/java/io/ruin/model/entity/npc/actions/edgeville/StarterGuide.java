package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.Server;
import io.ruin.cache.NPCDef;
import io.ruin.data.impl.Help;
import io.ruin.model.World;
import io.ruin.model.achievements.listeners.easy.IntroToEnchanting;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.XpMode;
import io.ruin.model.entity.player.clanchat.ClanChat;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.entity.shared.listeners.SpawnListener;
import io.ruin.model.inter.DonatorUpgrade;
import io.ruin.model.inter.GuideBookInter;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.interfaceutils.Interface;
import io.ruin.model.inter.interfaceutils.InterfaceAction;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.XpCounter;
import io.ruin.model.inter.pvpinter.LeagueManager;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;
import io.ruin.model.skills.slayer.master.Turael;
import io.ruin.model.stat.StatType;
import io.ruin.network.incoming.handlers.CommandHandler;
import io.ruin.utility.Broadcast;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.model.entity.player.clanchat.ClanChat.DEFAULT_CC;
import static io.ruin.model.inter.enchanting.Enchanting.clearAll;
import static io.ruin.model.inter.enchanting.armor.EnchantArmorData.sendArmorItemList;

@Slf4j
public class StarterGuide {

    private static final int GUIDE = 306;

    static {

        NPCDef.get(307).ignoreOccupiedTiles = true;
        NPCAction.register(GUIDE, "talk-to", (player, npc) -> guideDialogue(player));
        NPCAction.register(GUIDE, 3, (player, npc) -> guideDialogue(player));
        NPCAction.register(GUIDE, 4, (player, npc) -> openGuideBook(player));
        NPCAction.register(GUIDE, 5, (player, npc) -> ShopManager.openIfExists(player, "4d16cff7-a3a8-4351-9aae-d69kf2f7e037"));
        LoginListener.register(player -> {
            if (player.playTime == 0) {
                XpCounter.select(player, 1);
                ecoTutorial(player);
            }
        });
    }

    public static void guideDialogue(Player p) {
        p.dialogue(
                new NPCDialogue(GUIDE, "Hello " + p.name() + ", and welcome to OSPK! I am able to guide you around the home area, show you some quick guides, and also sell you some items from my Daily Achievements shop!"),
                new OptionsDialogue("What would you like to do?",
                        new Option("Show me around the home area", () -> p.sendMessage("Work in progress!")),
                        new Option("View some useful guides", () -> openGuideBook(p)),
                        new Option("Open the Daily Achievements shop", () -> ShopManager.openIfExists(p, "4d16cff7-a3a8-4351-9aae-d69kf2f7e037")),
                        new Option("Nevermind")
                )
        );
    }

    public static void openGuideBook(Player p) {
        GuideBookInter.openInter(p);
    }

    private static void optionsDialogue(Player player, NPC npc) {
        player.dialogue(new NPCDialogue(npc, "Hello " + player.name() + ", is there something I could assist you with?"),
                new OptionsDialogue(
                        new Option("View help pages", () -> Help.open(player)),
                        new Option("Replay tutorial", () -> ecoTutorial(player)),
                        new Option("Change home point", () -> {
                            npc.startEvent(event -> {
                                if (!player.edgeHome) {
                                    player.dialogue(new NPCDialogue(npc, "I can move your spawn point and <br>" +
                                                    "home teleport location to Edgeville.<br>" +
                                                    "it will cost 5,000,000 GP.<br>" +
                                                    "Would you like to do this?"),
                                            new OptionsDialogue(
                                                    new Option("No.. I like this home.", player::closeDialogue),
                                                    new Option("Yes! I want to respawn in Edgeville!", () -> {
                                                        if (player.getInventory().hasItem(995, 5000000)) {
                                                            player.getInventory().remove(995, 5000000);
                                                            player.edgeHome = true;
                                                            player.dialogue(new NPCDialogue(npc, "Your spawn point has been changed<br>" +
                                                                    "to Edgeville! If you'd like to change<br>" +
                                                                    "it back, just speak to me again."));
                                                        } else {
                                                            player.dialogue(new NPCDialogue(npc, "I'm sorry but it doesn't look like<br>" +
                                                                    "you can afford this.."));
                                                        }
                                                    })));
                                } else {
                                    player.dialogue(
                                            new NPCDialogue(npc, "Are you wanting to move your<br>" +
                                                    "spawn point back to home? It will cost<br>" +
                                                    "another 5,000,000 GP."),
                                            new OptionsDialogue(
                                                    new Option("No thanks.", player::closeDialogue),
                                                    new Option("Yes please!", () -> {
                                                        if (player.getInventory().hasItem(995, 5000000)) {
                                                            player.getInventory().remove(995, 5000000);
                                                            player.edgeHome = false;
                                                            player.dialogue(new NPCDialogue(npc, "Your spawn point has been changed<br>" +
                                                                    "back to home. If you'd like it changed,<br>" +
                                                                    "just speak to me again!"));
                                                        } else {
                                                            player.dialogue(new NPCDialogue(npc, "I'm sorry but it doesn't look like<br>" +
                                                                    "you can afford this.."));
                                                        }
                                                    })
                                            ));
                                }
                            });
                        })));
    }

    @SneakyThrows
    private static void begin(Player player) {
        boolean actuallyNew = player.newPlayer;
        player.startEvent(event -> {
            event.delay(1);
            player.getMovement().teleport(3090, 3505, 0);
            //player.lock(LockType.MOVEMENT);
//            if (actuallyNew) {
//                player.openInterface(InterfaceType.MAIN, Interface.APPEARANCE_CUSTOMIZATION);
//                while (player.isVisibleInterface(Interface.APPEARANCE_CUSTOMIZATION)) {
//                    event.delay(1);
//                }
//            }
            if (actuallyNew) {
                //XpMode.setXpMode(player, XpMode.EASY);
                player.lock(LockType.FULL);
                //Config.LOCK_CAMERA.set(player, 1);
                player.dialogue(
                        new NPCDialogue(307, "We've been expecting you, " + player.name + "! Before you begin your journey on " + World.type.getWorldName() + ", let's choose your game mode!"),
                        new ActionDialogue(() -> {
                            GameMode.openSelection(player);
                        })
                );
            }
        });
    }

    public static void continueTut(Player player) {
        player.startEvent(event -> {
            boolean startTutorial = false;
            String text = "Normal mode with semi-spawning abilities, great!";
            if (player.getGameMode() == GameMode.IRONMAN) {
                text = "Iron Man, huh? Self-sufficiency is quite a challenge, good luck!";
            } else if (player.getGameMode() == GameMode.HARDCORE_IRONMAN) {
                text = "Hardcore?! You only live once... make it count!";
            } else if (player.getGameMode() == GameMode.ULTIMATE_IRONMAN) {
                text = "Ultimate Iron Man... Up for quite the challenge, aren't you?";
            }
            if (player.getGameMode().isAnyIron()) {
                player.dialogue(new NPCDialogue(307, text),
                        new NPCDialogue(307, "You've chosen to play the " + player.getGameMode().toString().toLowerCase() + " mode! Here's some items to get you started on your journey.") {
                            @Override
                            public void open(Player player) {
                                giveIronmanModesStarter(player);
                                player.newPlayer = false;
                                super.open(player);
                            }
                        });
            }
            if (player.getGameMode().isStandard() && !player.ispvpMode && !player.isRealism) {
                player.dialogue(new NPCDialogue(307, "Great! You've chosen to play the Standard Game mode with no account restrictions."),
                        new NPCDialogue(307, "Here are some items to get you started on your journey in " + World.type.getWorldName() + ".") {
                            @Override
                            public void open(Player player) {
                                giveEcoStarter(player);
                                player.newPlayer = false;
                                super.open(player);
                            }
                        });
            }
            if (player.isRealism) {
                player.dialogue(new NPCDialogue(307, "We have a true gamer, here! Realism mode will surely offer you many great challenges and rewards."),
                        new NPCDialogue(307, "Here are some items to get you started on your journey in " + World.type.getWorldName() + ".") {
                            @Override
                            public void open(Player player) {
                                giveRealismStarter(player);
                                player.newPlayer = false;
                                super.open(player);
                            }
                        });
            }
            event.waitForDialogue(player);
            int amount;
            if (player.isRealism) {
                amount = 250;
            } else {
                amount = 50000;
            }
            player.dialogue(
                    new NPCDialogue(307, "Would you like to earn an extra " + amount + " coins for going through the tutorial?"),
                    new OptionsDialogue("Start the tutorial?",
                            new Option("Yes please!", () -> startTutorial(player)),
                            new Option("No, thanks.", () -> finishTutorial(player))));
        });
    }

    private static void finishTutorial(Player player) {
        player.unlock();
        Turael.assignTask(player);
        LeagueManager.updatePlayerLeague(player);
        ClanChat.join(player, DEFAULT_CC, false, true, true);
    }

    public static void startTutorial(Player p) {
        p.lock(LockType.FULL_ALLOW_LOGOUT);
        p.inTutorial = true;
        p.startEvent(event -> {
            p.getPacketSender().sendClientScript(39, "i", 100);
            //Config.LOCK_CAMERA.set(p, 1);
            event.delay(1);
            event.waitForMovement(p);
            p.animate(-1);
            event.waitForDialogue(p);
            p.getAppearance().update();

            p.getPacketSender().fadeOut();
            event.delay(2);
            p.getMovement().teleport(3090, 3505, 0);
            p.getPacketSender().moveCameraToLocation(3090, 3503, 1500, 100, 100);
            p.getPacketSender().turnCameraToLocation(3090, 3503, 1400, 100, 100);
            event.delay(1);
            p.getPacketSender().fadeIn();
            p.dialogue(new NPCDialogue(307, "Welcome to OSPK's newly rebuilt Edgeville! Most of your basic needs can" +
                    " be found right here at home."));
            event.waitForDialogue(p);
            p.dialogue(new NPCDialogue(307, "Surrounding us you can find the <col=800000>teleport portal</col>, a <col=800000>bank</col> to north, a <col=800000>fairy ring</col> to the east, and our only <col=800000>thieving stall</col> to your left."));
            event.waitForDialogue(p);
            p.getPacketSender().sendHintIcon(3083, 3509);
            p.dialogue(new NPCDialogue(307, "Additionally, you can find our Fire of Sacrifice here! Players can sacrifice items here in exchange for points to unlock powerful account perks!"));
            event.waitForDialogue(p);
            p.dialogue(new NPCDialogue(307, "Let's start by checking out the <col=800000>Shops</col> building!"));
            p.getPacketSender().resetHintIcon(false);
            event.waitForDialogue(p);

            p.getPacketSender().fadeOut();
            event.delay(2);
            p.getMovement().teleport(3078, 3510, 0);
            p.getPacketSender().moveCameraToLocation(3078, 3506, 1300, 100, 100);
            p.getPacketSender().turnCameraToLocation(3078, 3506, 700, 100, 100);
            event.delay(1);
            p.getPacketSender().fadeIn();
            p.getPacketSender().sendHintIcon(3075, 3510);
            p.dialogue(new NPCDialogue(307, "This is OSPK's one-stop-shop building for most of your shopping" +
                    " needs! The indicated <col=800000>Shop Manager</col> contains the combat, consumable, skilling shops and more."));
            event.waitForDialogue(p);
            p.getPacketSender().resetHintIcon(false);

            p.getPacketSender().sendHintIcon(3075, 3511);
            p.dialogue(new NPCDialogue(307, "Next to the Shop Manager is our <col=800000>Vote/Donator Manager</col>. Speaking" +
                    " to this npc gives access to voting and donating information, as well as their respected shops!"));
            event.waitForDialogue(p);
            p.getPacketSender().resetHintIcon(false);

            p.getPacketSender().sendHintIcon(3075, 3509);
            p.dialogue(new NPCDialogue(307, "To our left is the <col=800000>Ironman-only shop</col>. This shop will offer ironman" +
                    " accounts basic items to get them started on their journey!"));
            event.waitForDialogue(p);
            p.dialogue(new NPCDialogue(307, "Let me show your our <col=800000>Trading Post</col> area next!"));
            event.waitForDialogue(p);
            p.getPacketSender().resetHintIcon(false);

            p.getPacketSender().fadeOut();
            event.delay(2);
            p.getMovement().teleport(3082, 3500, 0);
            p.getPacketSender().moveCameraToLocation(3082, 3496, 1300, 100, 100);
            p.getPacketSender().turnCameraToLocation(3082, 3496, 700, 100, 100);
            event.delay(1);
            p.getPacketSender().fadeIn();
            p.getPacketSender().sendHintIcon(3078, 3500);
            p.dialogue(new NPCDialogue(307, "To your left, you will find the <col=800000>Trading Post</col>. This allows you" +
                    " to view items that are for sale by other players, or sell your own item to others!"));
            event.waitForDialogue(p);
            p.dialogue(new NPCDialogue(307, "Upon opening the Trading Post, you will see the offers currently " +
                    "available from other players. To post your own items for sale, simply click the " +
                    "<col=800000>My Offers</col> button, and select the item from your inventory"));
            event.waitForDialogue(p);
            p.dialogue(new NPCDialogue(307, "you wish to sell. To collect GP from your sold items, simply click the <col=800000>Coffer</col> button!"));
            event.waitForDialogue(p);
            p.dialogue(new NPCDialogue(307, "Next, let's move on to another area of interest: <col=800000>Slayer!</col>"));
            event.waitForDialogue(p);
            p.getPacketSender().resetHintIcon(false);
            p.closeInterfaces();

            p.getPacketSender().fadeOut();
            event.delay(2);
            p.getMovement().teleport(3082, 3487, 0);
            p.getPacketSender().moveCameraToLocation(3082, 3484, 1300, 100, 100);
            p.getPacketSender().turnCameraToLocation(3082, 3484, 700, 100, 100);
            event.delay(1);
            p.getPacketSender().fadeIn();
            p.dialogue(new NPCDialogue(307, "All of the slayer masters can be found in this building! Wilderness slayer is" +
                    " a very populer money making method on OSPK. If you would like to try out wilderness tasks, you"));
            event.waitForDialogue(p);
            p.dialogue(new NPCDialogue(307, "can speak with Krystilia to get started!"));
            event.waitForDialogue(p);
            p.dialogue(new NPCDialogue(307, "Why don't we go take a look at the AFK Zone next?"));
            event.waitForDialogue(p);

            p.getPacketSender().fadeOut();
            p.getMovement().teleport(3124, 3480, 0);
            p.getPacketSender().moveCameraToLocation(3124, 3478, 1400, 5, 5);
            p.getPacketSender().turnCameraToLocation(3124, 3478, 600, 5, 5);
            p.getPacketSender().fadeIn();
            event.delay(2);
            p.dialogue(new NPCDialogue(307, "Down here to the south-east part of town, you can find our <col=800000>AFK Zone</col>." +
                    " This area allows you to keep your player logged in and gaining slow xp, while earning <col=800000>AFK Dust</col> to buy cosmetic" +
                    " skilling outfits and more!"));
            event.waitForDialogue(p);
            p.dialogue(new NPCDialogue(307, "While AFK'ing here, you gain <col=800000>AFK Dust</col>, which you can spend in the <col=800000>AFK Shop</col> located in this zone!"));
            p.dialogue(new NPCDialogue(307, "The next are I'd like to show you is quite exciting, so make sure" +
                    " to pay close attention to it!"));
            event.waitForDialogue(p);

            p.getPacketSender().fadeOut();
            p.getMovement().teleport(3111, 3508, 0);
            p.getPacketSender().moveCameraToLocation(3111, 3505, 1400, 5, 5);
            p.getPacketSender().turnCameraToLocation(3111, 3505, 600, 5, 5);
            p.getPacketSender().fadeIn();
            event.delay(2);
            p.dialogue(new NPCDialogue(307, "To the North of the AFK Zone, you will find a plethora of exciting features, " +
                    "such as..."));
            event.waitForDialogue(p);
            //
            p.getMovement().teleport(3113, 3503, 0);
            p.getPacketSender().moveCameraToLocation(3113, 3501, 1400, 5, 5);
            p.getPacketSender().turnCameraToLocation(3113, 3501, 600, 5, 5);
            p.getPacketSender().sendHintIcon(3115, 3503);
            p.dialogue(new ItemDialogue().one(3462, "<col=800000>--The Great Vault--</col><br>" +
                    "Any key that you receive from competing in World Events around OSPK can be opened here!" +
                    " Simply open the chest, and select the event that you possess a key for, and loot away!"));
            event.waitForDialogue(p);
            p.getPacketSender().resetHintIcon(false);
            p.closeInterfaces();
            //
            p.getPacketSender().sendHintIcon(3111, 3503);
            sendArmorItemList(p);
            clearAll();
            p.dialogue(new ItemDialogue().one(30183, "<col=800000>--Altar of Enchantment--</col><br>" +
                    "Players can visit the Altar of Enchantment to give some extra bonuses or unique abilities to" +
                    " some of their items! The enchantments only require the base item + some GP to upgrade."));
            event.waitForDialogue(p);
            p.dialogue(new ItemDialogue().one(30183, "Note: Enchanted items do NOT work in PvP scenarios."));
            event.waitForDialogue(p);
            p.getPacketSender().resetHintIcon(false);
            p.closeInterfaces();
            //
            p.getMovement().teleport(3121, 3509, 0);
            p.getPacketSender().moveCameraToLocation(3121, 3507, 1400, 5, 5);
            p.getPacketSender().turnCameraToLocation(3121, 3507, 600, 5, 5);
            p.getPacketSender().sendHintIcon(3121, 3510);
            p.dialogue(new ItemDialogue().one(13069, "<col=800000>--Achievement Diary Rewards--</col><br>" +
                    "Ironmen rejoice! Players are able to visit this building to be rewarded with Achievement " +
                    "Diary rewards, such as the Ardougne Cloak, Karamja Gloves and all others!"));
            event.waitForDialogue(p);
            p.getPacketSender().resetHintIcon(false);
            //
            p.getMovement().teleport(3105, 3506, 0);
            p.getPacketSender().moveCameraToLocation(3105, 3504, 1400, 5, 5);
            p.getPacketSender().turnCameraToLocation(3105, 3504, 600, 5, 5);
            p.getPacketSender().sendHintIcon(3105, 3503);
            p.dialogue(new ItemDialogue().one(25859, "<col=800000>--The Gauntlet--</col><br>" +
                    "The infamous red prison. Luckily, on OSPK, we made it a tad easier by skipping all of" +
                    " the prep and going straight to testing your luck against Hunllef! Players may choose " +
                    "between normal or the corrupted version."));
            event.waitForDialogue(p);
            p.dialogue(new ItemDialogue().one(25859, "Keep in mind, rare drop rates are <col=800000>doubled</col> in the corrupted gauntlet!"));
            event.waitForDialogue(p);
            p.getPacketSender().resetHintIcon(false);
            //
            p.getMovement().teleport(3101, 3512, 0);
            p.getPacketSender().moveCameraToLocation(3101, 3509, 1400, 5, 5);
            p.getPacketSender().turnCameraToLocation(3101, 3509, 600, 5, 5);
            p.getPacketSender().sendHintIcon(3101, 3513);
            p.dialogue(new ItemDialogue().one(11802, "<col=800000>--PvP Zone--</col><br>" +
                    "And finally, we have our dedicated PvP area that offers convenience too all of you " +
                    "who are pvp'ers! In this area you can quickly switch between magic books, recharge your" +
                    " spec and health, enter a tournament,"));
            event.waitForDialogue(p);
            p.dialogue(new ItemDialogue().one(11802, "turn emblems into the Emblem Trader, and queue up for <col=800000>1v1 Arenas</col> to earn PvP Rating for the season!"));
            event.waitForDialogue(p);
            p.getPacketSender().resetHintIcon(false);

            p.getPacketSender().fadeOut();
            p.getMovement().teleport(3090, 3505, 0);
            p.getPacketSender().moveCameraToLocation(3090, 3505, 1200, 5, 5);
            p.getPacketSender().turnCameraToLocation(3090, 3505, 600, 5, 5);
            p.getPacketSender().fadeIn();
            event.delay(2);
            p.dialogue(new NPCDialogue(307, "I highly advise you to start your Easy achievements in order to" +
                    " get more in-depth information about all of the features of the home area! " +
                    "Completing these achievements also gives you a better head start on GP to"));
            event.waitForDialogue(p);
            p.dialogue(new NPCDialogue(307, "start your journey even quicker! Leaving this tutorial, you will be in our Help clan chat, where there are staff members available to answer any " +
                    "additional questions you may have!"));
            event.waitForDialogue(p);
            p.dialogue(new NPCDialogue(307, "Congratulations, you've made it through the tutorial! Welcome to OSPK!"));
            event.waitForDialogue(p);
            p.getPacketSender().resetCamera();
            p.closeDialogue();
            p.inTutorial = false;
            p.logoutListener = null;
            p.setTutorialStage(0);
            int amount;
            if (p.isRealism) {
                amount = 250;
            } else {
                amount = 50000;
            }
            p.getInventory().add(995, amount);
            p.unlock();
            p.getInventory().add(995, 50000);
            finishTutorial(p);
        }).setCancelCondition(() -> !p.getChannel().isActive() || !p.isOnline());
    }

    public static void ecoTutorial(Player player) {
        // player.inTutorial = true;
        player.startEvent(event -> {
            //event.delay(1);
//            player.getPacketSender().resetCamera();
//            player.lock(LockType.FULL);
//            event.waitForMovement(player);
//            player.animate(-1);
//            event.waitForDialogue(player);
//            player.getAppearance().update();
//            player.getPacketSender().fadeOut();
//
//            event.delay(2);
//            player.getMovement().teleport(3104, 3465, 0);
//            player.getPacketSender().moveCameraToLocation(3104, 3506, 750, 2, 2);
//            player.getPacketSender().turnCameraToLocation(3104, 3506, 750, 2, 2);
//            event.delay(1);
//            player.getPacketSender().fadeIn();
//            event.delay(4);
//            player.openInterface(InterfaceType.MAIN, 876);
//            event.delay(12);
//            player.getPacketSender().fadeOut();
//            event.delay(1);
//            player.getMovement().teleport(3101, 3503, 0);
//            player.closeInterface(InterfaceType.MAIN);
//            event.delay(1);
//            player.getPacketSender().resetCamera();
//            player.getPacketSender().fadeIn();
            player.lock(LockType.FULL_ALLOW_LOGOUT);
            begin(player);
            //event.delay(1);
            //player.getMovement().teleport(3101, 3503, 0);
            /*player.getPacketSender().moveCameraToLocation(1636, 4839, 750, 2, 2);
            player.getPacketSender().turnCameraToLocation(1636, 4839, 750, 2, 2);
            event.delay(8);
            player.getPacketSender().fadeOut();*/

            /*event.delay(2);
            player.getMovement().teleport(2494, 5094, 0);
            event.delay(1);
            player.getPacketSender().fadeIn();
            player.getPacketSender().moveCameraToLocation(2494, 5131, 750, 2, 2);
            player.getPacketSender().turnCameraToLocation(2494, 5131, 750, 2, 2);
            event.delay(8);
            player.getPacketSender().fadeOut();*/

            /*event.delay(2);
            player.getMovement().teleport(3100, 3505, 0);
            player.getPacketSender().moveCameraToLocation(3100, 3497, 750, 5, 5);
            player.getPacketSender().turnCameraToLocation(3100, 3497, 750, 5, 5);
            player.getPacketSender().fadeIn();
            event.delay(2);
            player.dialogue(new NPCDialogue(307, "Congratulations, you've made it through the tutorial!"));
            event.waitForDialogue(player);
            player.getPacketSender().resetCamera();
            player.closeDialogue();
            player.inTutorial = false;
            player.logoutListener = null;
            player.setTutorialStage(0);
            player.unlock();*/
        }).setCancelCondition(() -> !player.getChannel().isActive() || !player.isOnline());
    }

    private static void introCutscene(NPC guide, Player player) {
        guide.startEvent((e) -> {
            player.getPacketSender().sendClientScript(39, "i", 100);
            Config.LOCK_CAMERA.set(player, 1);
            player.getPacketSender().moveCameraToLocation(2050, 3572, 600, 0, 12);
            player.getPacketSender().turnCameraToLocation(2045, 3577, 400, 0, 25);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "Here in Edgeville, you will find our newly rebuilt home of OSPK!" +
                            " You will find almost everything you need here at your convenience." +
                            " What do you say we take a look around to help you become more" +
                            " familiar with the home area?"));
            e.waitForDialogue(player);

            player.getMovement().teleport(2019, 3577, 0);
            e.delay(1);
            player.getPacketSender().moveCameraToLocation(2015, 3577, 750, 0, 12);
            player.getPacketSender().turnCameraToLocation(2010, 3577, 500, 0, 30);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "This is Kronos bank. It houses the normal bank<br>" +
                            "amenities but also inside are the vote point and<br>" +
                            "donation managers."));
            e.waitForDialogue(player);
            player.getPacketSender().moveCameraToLocation(2013, 3577, 750, 0, 12);
            player.getPacketSender().turnCameraToLocation(2019, 3577, 500, 0, 18);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "Here we have the trading post.<br>" +
                            "You can buy items from other players, much like<br>" +
                            "the grand exchange."));
            e.waitForDialogue(player);

            player.getMovement().teleport(2031, 3577, 0);
            player.getPacketSender().moveCameraToLocation(2026, 3573, 500, 0, 12);
            player.getPacketSender().turnCameraToLocation(2032, 3571, 500, 0, 30);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "Here is the construction portal. Kronos offers full<br>" +
                            "construction! There are also NPC's here to sell you<br>" +
                            "supplies to build your house and remodel it as well."));
            e.waitForDialogue(player);

            player.getMovement().teleport(2031, 3577, 0);
            player.getPacketSender().turnCameraToLocation(2020, 3558, 400, 0, 30);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "All of our basic shops are located in this building<br>" +
                            "The ironman shop is in the small building to<br>" +
                            "the east."));
            e.waitForDialogue(player);

            player.getPacketSender().moveCameraToLocation(2064, 3583, 1000, 0, 12);
            player.getPacketSender().turnCameraToLocation(2062, 3570, 0, 0, 30);
            e.delay(1);
            player.dialogue(new NPCDialogue(guide,
                    "There is a skilling area to the east of home<br>" +
                            "just over the bridge. Fishing is also available<br>" +
                            "to the north."));
            e.delay(10);
            player.getPacketSender().moveCameraToLocation(2064, 3572, 1200, 0, 12);
            player.getPacketSender().turnCameraToLocation(2064, 3590, 0, 0, 32);
            e.waitForDialogue(player);
            Config.LOCK_CAMERA.set(player, 0);
            player.getPacketSender().resetCamera();
            player.setTutorialStage(1);

            guide.getMovement().teleport(2031, 3577, 0);
            player.getMovement().teleport(2032, 3577, 0);
            guide.face(player);
            player.face(guide);
            player.getPacketSender().moveCameraToLocation(2032, 3582, 450, 0, 12);
            player.getPacketSender().turnCameraToLocation(2032, 3577, 400, 0, 30);
            player.dialogue(new NPCDialogue(guide,
                    "If you have any other questions, there are always<br>" +
                            "helpful users in the help clan chat"));
            e.waitForDialogue(player);
            guide.animate(863);
            // player.inTutorial = false;
            player.unlock();
            player.setTutorialStage(0);
            guide.addEvent(evt -> {
                evt.delay(2);
                World.sendGraphics(86, 50, 0, guide.getPosition());
                player.logoutListener = null;
                guide.remove();
            });
            player.getPacketSender().resetCamera();
        });
    }

    private static void giveRealismStarter(Player p) {
        p.getInventory().add(995, 250);
        p.getInventory().add(558, 25);
        p.getInventory().add(556, 25);
        p.getInventory().add(841, 1);
        p.getInventory().add(882, 25);
        p.getInventory().add(303, 1);
        p.getInventory().add(590, 1);
    }

    private static void giveEcoStarter(Player p) {
        p.getInventory().add(COINS_995, 50000); // gp
        p.getInventory().add(558, 100); // Mind Rune
        p.getInventory().add(556, 250); // Air Rune
        p.getInventory().add(554, 250); // Fire Rune
        p.getInventory().add(555, 250); // Water Rune
        p.getInventory().add(557, 250); // Earth Rune
        p.getInventory().add(1381, 1); // Air Staff
        p.getInventory().add(362, 50); // Tuna
        p.getInventory().add(841, 1); // shortbow
        p.getInventory().add(884, 500); // iron arrow
        p.getInventory().add(8013, 10); // house tabs
        p.getInventory().add(1856, 1); // guide book
        CommandHandler.fillBank(p);
        p.sendMessage("Your bank has been filled with basic pvp items!");
    }

    private static void giveIronmanModesStarter(Player p) {
        p.getInventory().add(COINS_995, 25000); // gp
        p.getInventory().add(841, 1); // shortbow
        p.getInventory().add(884, 500); // iron arrow
        p.getInventory().add(556, 250); // air rune
        p.getInventory().add(558, 250); // mind Rune
        p.getInventory().add(1381, 1); // staff of air
        p.getInventory().add(1323, 1); // iron scim
        p.getInventory().add(8013, 10); // house tabs
        p.getInventory().add(1856, 1); // guide book
    }

}
