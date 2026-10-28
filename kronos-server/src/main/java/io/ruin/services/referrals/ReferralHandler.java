package io.ruin.services.referrals;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.ruin.Server;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.utility.JsonUtils;
import io.ruin.utility.Misc;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class ReferralHandler {

    public static Map<String, Referral> ACTIVE_GIFTCODES = Maps.newConcurrentMap();

    private static final File DIRECTORY = World.getMainSavePath();

    public static void createGiftCode(Player player) {

        if (!player.isAdminAbove())
            return;

        player.stringInput("Enter a name to base your code from:", (code) -> {
            if (code == null || code.isEmpty()) {
                player.sendMessage("invalid code start.. try again.");
                return;
            }
            setMaxUses(player, code + Misc.random(1_000, 9_999));
        });
    }

    public static void removeGiftCode(Player player) {

        player.stringInput("Enter a codename you would like to delete:", (code) -> {
            if (code == null || code.isEmpty()) {
                player.sendMessage("invalid code start.. try again.");
                return;
            }
            if (ACTIVE_GIFTCODES.containsKey(code)) {
                ACTIVE_GIFTCODES.remove(code);
                player.sendMessage("removed code: " + code + " from the active giftcode list!");
            } else {
                player.sendMessage("this code doesn't exist.");
            }
        });
    }

    private static void setMaxUses(Player player, String code) {
        player.integerInput("How many uses would you like this giftcode to have?", (number) -> {
            if (number < 1 || number > 100) {
                player.sendMessage("Your number can only range from 1-1,000");
                return;
            }
            setReward(player, code, number);
        });
    }

    private static void setReward(Player player, String code, int number) {
        player.integerInput("What reward would you like to give? enter an item number:", (itemId) -> {
            if (ItemDef.get(itemId) == null) {
                player.sendMessage("Invalid item id! it has no defs, try again.");
                return;
            }
            confirmCode(player, code, number, itemId);
        });
    }

    private static void confirmCode(Player player, String code, int number, int itemId) {
        player.dialogue(
                new OptionsDialogue("Code: " + code + " Uses: " + number + " item: 1x " + ItemDef.get(itemId).name,
                        new Option("Yes, create this code.", () -> {
                            ACTIVE_GIFTCODES.put(code, new Referral(code, number, itemId));
                            StringBuilder sb = new StringBuilder();
                            sb.append("\nCode: " + code + "\n")
                                    .append("Uses: " + number + "\n")
                                    .append("Reward: 1x " + ItemDef.get(itemId).name + " (" + itemId + ")");
                            player.dialogue(new MessageDialogue("You have created the giftcode! " + sb.toString().replaceAll("\n", "<br>")));
                            player.sendMessage("You have created the giftcode! " + sb.toString().replaceAll("\n", "<br>"));
                           ;
                        }),
                        new Option("No, I made a mistake.", () -> {

                        })
                ));
    }

    public static void debug() {
        ACTIVE_GIFTCODES.entrySet().stream().forEach(e -> {
            System.err.println(e.getValue().code + " " + e.getValue().remainingUses + " " + e.getValue().maxUses + " ");
            e.getValue().redeemUsers.stream().forEach(u -> System.err.println(u));
        });
    }

    public static void redeem(Player player, String code) {
        Long cooldown = player.get("giftcoderedeem", 0L);
        if (cooldown > System.currentTimeMillis()) {
            player.sendMessage("You can only redeem a code once every 5 seconds.");
            return;
        }
        code.toLowerCase();
        Optional<Map.Entry<String, Referral>> gift = ACTIVE_GIFTCODES.entrySet().stream().filter(c -> Objects.equals(c.getKey(), code)).findAny();
        player.set("giftcoderedeem", System.currentTimeMillis() + 5_000L);
        if (!gift.isPresent()) {
            player.dialogue(new MessageDialogue("This GiftCode doesn't exist or has expired."));
            return;
        }
        Referral referral = gift.get().getValue();
        if (referral.redeemUsers.stream().anyMatch(u -> Objects.equals(player.getUUID(), u))) {
            player.dialogue(new MessageDialogue("You have already redeemed this code.<br>You can only redeem the same code once per person!"));
            return;
        }
        if (referral.remainingUses == 0) {
            player.dialogue(new MessageDialogue("This GiftCode has exceeded the maximum number of uses!"));
            ACTIVE_GIFTCODES.remove(code);
            return;
        }
        referral.remainingUses--;
        referral.redeemUsers.add(player.getUUID());
        player.dialogue(new ItemDialogue().one(referral.rewardId, "You have redeemed the code " + referral.code + "<br>You have been rewarded<br>1x " + ItemDef.get(referral.rewardId).name));
        player.getInventory().addOrDrop(referral.rewardId, 1);
    }

    static {
        load();
    }

    public static void save() {
        CompletableFuture.runAsync(() -> {
            try {
                String json = JsonUtils.GSON_PRETTY.toJson(ACTIVE_GIFTCODES);
                if (!DIRECTORY.exists() && !DIRECTORY.mkdirs())
                    throw new IOException("gift-code could not be created!");
                Files.write(new File(DIRECTORY, "referrals.json").toPath(), json.getBytes());
                log.info("Successfully saved referrals.json");
            } catch (Exception e) {
                Server.logError(e.getMessage());
            }
        });
    }

    public static void load() {
        File file = new File(DIRECTORY, "referrals.json");
        try {
            FileReader fileReader = new FileReader(file);
            Type type = new TypeToken<Map<String, Referral>>() {
            }.getType();
            ACTIVE_GIFTCODES = new Gson().fromJson(fileReader, type);
        } catch (Exception e) {
            e.printStackTrace();
            ACTIVE_GIFTCODES = Maps.newConcurrentMap();
        }
        log.info("Successfully loaded referrals.json. " + ACTIVE_GIFTCODES.size());

    }
}
