package io.ruin.services.punishment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.ruin.Server;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.activities.jail.Jail;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerFile;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.utility.JsonUtils;
import io.ruin.utility.StringUtils;
import io.ruin.utility.TimeUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.ruin.services.punishment.PunishmentType.*;

@Slf4j
public class PunishmentManager {

    public static Map<String, Punishment> PENDING_PUNISHMENTS = Maps.newConcurrentMap();

    public static Map<String, Punishment> UUID_PUNISHMENTS = Maps.newConcurrentMap();

    public static Map<String, Punishment> BANNED_ACCOUNTS = Maps.newConcurrentMap();

    public static List<Punishment> ACTIVE_PUNISHMENTS = Lists.newCopyOnWriteArrayList();

    private static final File SAVE_FOLDER = World.getSavePath("punishments");

    static {
        load();
    }

    private static void savePending() {
        CompletableFuture.runAsync(() -> {
            try {
                String json = JsonUtils.GSON_PRETTY.toJson(PENDING_PUNISHMENTS);
                Files.write(new File(SAVE_FOLDER, "pending_punishments.json").toPath(), json.getBytes());
                log.info("Successfully saved pending_punishments.json");
            } catch (Exception e) {
                Server.logError(e.getMessage());
            }
        });
    }

    private static void loadPending() {
        File file = new File(SAVE_FOLDER, "pending_punishments.json");
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                Type type = new TypeToken<Map<String, Punishment>>() {
                }.getType();
                PENDING_PUNISHMENTS = new Gson().fromJson(fileReader, type);
                if (PENDING_PUNISHMENTS != null) {
                    log.info("Successfully loaded pending_punishments.json. Size: " + PENDING_PUNISHMENTS.size());
                } else
                    PENDING_PUNISHMENTS = Maps.newConcurrentMap();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error loading pending_punishments.json", e);
            }
        }
    }

    public static void saveUUIDPunishments() {
        CompletableFuture.runAsync(() -> {
            try {
                String json = JsonUtils.GSON_PRETTY.toJson(UUID_PUNISHMENTS);
                Files.write(new File(SAVE_FOLDER, "uuid_punishments.json").toPath(), json.getBytes());
                log.info("Successfully saved uuid_punishments.json");
            } catch (Exception e) {
                Server.logError(e.getMessage());
            }
        });
    }

    public static void saveAccountBans() {
        CompletableFuture.runAsync(() -> {
            try {
                String json = JsonUtils.GSON_PRETTY.toJson(BANNED_ACCOUNTS);
                Files.write(new File(SAVE_FOLDER, "banned_accounts.json").toPath(), json.getBytes());
                log.info("Successfully saved banned_accounts.json");
            } catch (Exception e) {
                Server.logError(e.getMessage());
            }
        });
    }

    public static void loadUUIDPunishments() {
        File file = new File(SAVE_FOLDER, "uuid_punishments.json");
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                Type type = new TypeToken<Map<String, Punishment>>() {
                }.getType();
                UUID_PUNISHMENTS = new Gson().fromJson(fileReader, type);
                if (UUID_PUNISHMENTS != null)
                    log.info("Successfully loaded uuid_punishments.json. Size: " + UUID_PUNISHMENTS.size());
                else
                    UUID_PUNISHMENTS = Maps.newConcurrentMap();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error loading uuid_punishments.json", e);

            }
        }
    }

    public static void loadBannedAccounts() {
        File file = new File(SAVE_FOLDER, "banned_accounts.json");
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                Type type = new TypeToken<Map<String, Punishment>>() {
                }.getType();
                BANNED_ACCOUNTS = new Gson().fromJson(fileReader, type);
                if (BANNED_ACCOUNTS != null)
                    log.info("Successfully loaded banned_accounts.json. Size: " + BANNED_ACCOUNTS.size());
                else
                    BANNED_ACCOUNTS = Maps.newConcurrentMap();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error loading uuid_punishments.json", e);
            }
        }
    }

    public static void load() {
        loadPending();
        loadUUIDPunishments();
        loadBannedAccounts();
    }

    public static void save() {
        savePending();
        saveUUIDPunishments();
        saveAccountBans();
    }

    private static void addPunishment(Player player, String name, Punishment punishment) {
        Player punish = World.getPlayer(name);
        boolean isBanType = Arrays.asList(PERM_BAN, BAN).stream().anyMatch(c -> punishment.punishmentType == c);
        if (punish != null) {
            boolean canPunish = player.staffRank >= punish.staffRank;
            if (!canPunish) {
                player.sendMessage("You cannot " + StringUtils.capitalizeFirst(punishment.punishmentType.name().toLowerCase().replaceAll("_", " ")) + " someone with higher permissions than you.");
                punish.sendMessage("<col=ff0000>WARNING: " + player.name() + " just tried to " + StringUtils.capitalizeFirst(punishment.punishmentType.name().toLowerCase().replaceAll("_", " ")) + " you.");
                return;
            }
            if (isBanType) {
                handleBan(player, name, punishment);
                return;
            }
            checkPunishment(punish, punishment);
            return;
        }
        boolean saveExists = PlayerFile.saveExists(name.toLowerCase());
        if (!saveExists) {
            player.sendMessage("Warning: " + name + " doesn't exist. punish You cannot somebody who doesn't exist!");
            return;
        }
        if (punishment.punishmentType == KICK) {
            player.sendMessage("You cannot queue a server kick for offline players.");
            return;
        }
        if (isBanType) {
            handleBan(player, name, punishment);
            return;
        }
        player.dialogue(new MessageDialogue("Punishment added to pending punishment queue.<br>" + name + " will be receive what you gave them when they login next."));
        if (PENDING_PUNISHMENTS == null) {
            PENDING_PUNISHMENTS = Maps.newConcurrentMap();
        }
        PENDING_PUNISHMENTS.put(name.toLowerCase(), punishment);
    }

    private static void handleBan(Player player, String name, Punishment punishment) {
        Player ban = World.getPlayer(name);
        name.replaceAll("_", " ").toLowerCase();
        String uid = "";
        boolean offlineLoad = false;
        if (ban == null) {
            if (punishment.punishmentType == BAN) {
                BANNED_ACCOUNTS.put(name, punishment);
            } else {
                ban = PlayerFile.loadOffline(name);
                if (ban != null) {
                    offlineLoad = true;
                    uid = ban.getUUID();
                    BANNED_ACCOUNTS.put(name, punishment);
                    UUID_PUNISHMENTS.put(uid, punishment);
                    checkAssociatedAccounts(uid);
                } else {
                    player.sendMessage("cannot perm offline ban: " + name + " contact administrator");
                    return;
                }
            }
        }

        if (!offlineLoad) {

            switch (punishment.punishmentType) {
                case PERM_BAN:
                    checkAssociatedAccounts(ban.getUUID());
                    BANNED_ACCOUNTS.put(name, punishment);
                    UUID_PUNISHMENTS.put(ban.getUUID(), punishment);
                    break;
                case BAN:
                    ban.forceLogout();
                    BANNED_ACCOUNTS.put(name, punishment);
                    break;
            }
        }
        player.sendMessage("You have <col=ff0000>" + StringUtils.capitalizeFirst(punishment.punishmentType.name().toLowerCase().replaceAll("_", " ")) + "</col> " + name + ".");
    }

    private static void checkAssociatedAccounts(String uid) {
        for (Player p : World.players) {
            if (p == null)
                continue;
            if (Objects.equals(uid, p.getUUID()))
                p.forceLogout();
        }
    }

    public static void checkPunishment(Player player, Punishment onlineRequest) {
        String name = player.name().toLowerCase();
        Punishment punishment = PENDING_PUNISHMENTS.get(name);
        if (punishment == null) {
            punishment = onlineRequest;
        }
        if (punishment == null)
            return;
        PunishmentType punishType = punishment.punishmentType;
        ACTIVE_PUNISHMENTS.add(punishment);
        List<PunishmentType> kickFromActivities = Arrays.asList(PERM_BAN, BAN, JAIL, KICK);
        if (kickFromActivities.stream().anyMatch(pun -> pun == punishType)) {
            if (player.lmsSession != null) {
                player.lmsSession.eliminatePlayer(player);
            }
            if (player.getDuel() != null) {
                player.getDuel().lose(true);
            }
            if (player.tournament != null) {
                player.tournament.eliminate(player, false);
            }
        }
        List<PunishmentType> uuid_punishment = Arrays.asList(PERM_BAN, PERM_MUTE);
        if (uuid_punishment.stream().anyMatch(pun -> pun == punishType)) {
            UUID_PUNISHMENTS.put(player.getUUID(), punishment);
        }
        switch (punishType) {
            case PERM_BAN:
            case BAN:
                if (punishType == PunishmentType.PERM_BAN) {
                    for (Player ban : World.players) {
                        if (ban == null)
                            continue;
                        if (ban.getUUID().equals(player.getUUID())) {
                            ban.forceLogout();
                            ban.setBanned(true);
                        }
                    }
                }
                player.forceLogout();
                break;
            case PERM_MUTE:
            case MUTE:
                player.mutedBy = punishment.punishedBy;
                player.mutedReason = punishment.punishReason;
                player.muteEnd += player.muteEnd > System.currentTimeMillis() ? punishment.punishmentDuration - System.currentTimeMillis() : punishment.punishmentDuration;
                player.sendMessage("You have been muted by: <col=ff0000>" + player.mutedBy + "</col> Reason: <col=ff0000>" + player.mutedReason + "</col> Duration: <col=ff0000>" + (player.muteEnd == -1 ? "Permanent" : TimeUtils.fromMs(player.muteEnd - System.currentTimeMillis(), false)));
                break;
            case JAIL:
                player.jailerName = punishment.punishedBy;
                player.jailerReason = punishment.punishReason;
                player.jailOresAssigned = punishment.requiredOres;
                player.jailOresCollected = 0;
                World.startEvent(e -> {
                    while (player.isLocked())
                        e.delay(1); //wait
                    if (!player.isOnline())
                        return;
                    player.resetActions(true, true, true);
                    Jail.startEvent(player);
                });
                break;

            case UN_JAIL:
                if (player.jailOresAssigned <= 0) {
                    return;
                }
                player.jailerName = null;
                player.jailerReason = null;
                player.jailOresAssigned = player.jailOresCollected = 0;
                player.teleportListener = null;
                player.deathEndListener = null;
                player.getMovement().teleport(3092, 3492, 0);
                player.sendMessage(Color.DARK_RED.tag() + "You have been unjailed by a staff member.");
                break;

            case UN_MUTE:
                player.mutedReason = "";
                player.mutedBy = "";
                player.muteEnd = 0;
                player.sendMessage("You have been unmuted.");
                break;
            case UN_MUTE_PERM:
                Punishment punish = UUID_PUNISHMENTS.get(player.name());
                if (punish == null)
                    return;
                if (punish.punishmentType == PERM_MUTE) {
                    player.mutedReason = "";
                    player.mutedBy = "";
                    player.muteEnd = 0;
                    UUID_PUNISHMENTS.remove(player.getUUID());
                    player.sendMessage("You have been unmuted.");
                    return;
                }
                break;
            case KICK:
                player.resetActions(true, true, true);
                player.forceLogout();
                break;
        }
        PENDING_PUNISHMENTS.remove(name);
        if (onlineRequest == null)
            return;
    }

    public static void auto_jail(Player player, NPC npc, int ores) {
        if (player.lmsSession != null) {
            player.lmsSession.eliminatePlayer(player);
        }
        player.jailerName = npc.getDef().name;
        player.jailOresAssigned = ores;
        player.jailOresCollected = 0;
        World.startEvent(e -> {
            if (!player.isOnline())
                return;
            player.resetActions(true, true, true);
            Jail.startEvent(player);
        });
    }


    public static void unjail(Player p1, Player p2) {
        p2.jailOresAssigned = 0; //this will automatically finish their sentence lol
        p2.jailerReason = null;
        p2.jailerName = null;
        p1.sendMessage("You unjailed " + p2.name() + ".");
    }

    public static void unmute(Player p1, Player p2) {
        if (p2.muteEnd == 0) {
            p1.sendMessage(p2.name() + " isn't muted.");
            return;
        }
        if (p2.shadowMute && !p1.isAdmin()) {
            p1.sendMessage("This player can only be unmuted by an admin.");
            return;
        }
        p2.muteEnd = 0;
        p2.mutedBy = null;
        p2.mutedReason = null;
        if (p2.shadowMute)
            p2.shadowMute = false;
        else
            p2.sendMessage("Your mute has been lifted.");
        p1.sendMessage(p2.name() + " is now unmuted.");
    }

    public static boolean isMuted(Player player) {
        return player.muteEnd == -1 || (player.muteEnd > 0 && System.currentTimeMillis() < player.muteEnd) || isPunished(player.getUUID(), PERM_MUTE);
    }

    public static boolean isPunished(String uuid, PunishmentType type) {
        if (UUID_PUNISHMENTS == null)
            return false;
        return UUID_PUNISHMENTS.entrySet().stream().anyMatch(p -> p.getKey().equals(uuid) && p.getValue().punishmentType == type);
    }

    public static boolean isBanned(String name) {
        if (BANNED_ACCOUNTS == null)
            return false;
        return BANNED_ACCOUNTS.entrySet().stream().anyMatch(p -> p.getKey().equals(name) && p.getValue().punishmentType == BAN);
    }

    public static void handle(Player player) {
        player.stringInput("Enter the name of who you would like to punish:", (input) -> {
            if (input == null || input.isEmpty()) {
                player.dialogue(new MessageDialogue("Invalid input! try enter the name again."));
                return;
            }
            sendPunishmentOptions(player, input);
        });
    }

    public static void unban(Player player) {
        player.stringInput("Enter the name of who you would like to Unban:", (input) -> {
            if (input == null || input.isEmpty()) {
                player.dialogue(new MessageDialogue("Invalid input! try enter the name again."));
                return;
            }
            sendUnbanOptions(player, input);
        });
    }

    private static void sendUnbanOptions(Player player, String punished) {
        OptionsDialogue unban =
                new OptionsDialogue(
                        new Option("Unban from Server", () -> unbanGlobal(player, punished)),
                        new Option("Unban Account", () -> unbanAccount(player, punished))
                );
        player.dialogue(unban);
    }

    private static void unbanAccount(Player player, String punished) {
        Punishment punishment = BANNED_ACCOUNTS.get(punished);
        if (punishment == null) {
            player.sendMessage(punished + " isn't on the account ban list!");
            return;
        }
        if (punishment.staffLevel > player.staffRank) {
            player.sendMessage("You don't have enough permissions to unban this person. You need a level of " + punishment.staffLevel + " or higher.");
            return;
        }
        BANNED_ACCOUNTS.remove(punished);
        player.sendMessage("You have unbanned " + punished + "'s Account. They can login again.");
    }

    private static void unbanGlobal(Player player, String punished) {
        Player off = PlayerFile.loadOffline(punished);
        Player check = World.getPlayer(punished);
        if (check != null) {
            player.sendMessage(punished + " isn't on the global ban list!");
            return;
        }
        if (off == null) {
            player.sendMessage("Error checking account. Contact administrator.");
            return;
        }
        Punishment punishment = UUID_PUNISHMENTS.get(off.getUUID());
        if (punishment == null) {
            player.sendMessage(punished + " isn't on the global ban list!");
            return;
        }
        BANNED_ACCOUNTS.remove(off.name());
        UUID_PUNISHMENTS.remove(off.getUUID());
        player.sendMessage(punished + "'s account has been unbanned, they have also been unglobal banned so they can connect.");
    }

    private static void sendPunishmentOptions(Player player, String punished) {
        OptionsDialogue adminOptions =
                new OptionsDialogue(
                        new Option("Ban Options", () -> player.dialogue(new OptionsDialogue(
                                new Option("Permanent Ban", () -> {
                                    handleBanType(player, punished, true);
                                }),
                                new Option("Account Ban", () -> {
                                    handleBanType(player, punished, false);
                                })))),
                        new Option("Jail", () -> sendJailOptions(player, punished)),
                        new Option("Mute Options", () -> sendMuteOptions(player, punished)),
                        new Option("Kick", () -> {
                            sendKickOption(player, punished);
                        }));

        OptionsDialogue modOptions =
                new OptionsDialogue(
                        new Option("Jail", () -> sendJailOptions(player, punished)),
                        new Option("Mute Options", () -> sendMuteOptions(player, punished)));


        OptionsDialogue supportOptions = new OptionsDialogue(new Option("Mute Options", () -> sendMuteOptions(player, punished)));

        player.dialogue(player.isSupport() ? supportOptions : player.isModerator() ? modOptions : adminOptions);
    }

    private static void handleBanType(Player player, String punished, boolean permBan) {
        punished.replaceAll("_", " ").toLowerCase();
        player.dialogue(
                new OptionsDialogue(permBan ? "Perm Ban " : "Account Ban" + punished + "? (CONFIRM)",
                        new Option("Yes", () -> {
                            addPunishment(player, punished, new Punishment(player, "N/A", permBan ? PERM_BAN : BAN, -1));
                        }),
                        new Option("No", () -> {
                        })
                ));
    }

    private static void sendKickOption(Player player, String punished) {
        player.stringInput("Enter a Kick reason:", (reason) -> addPunishment(player, punished, new Punishment(player, reason, KICK, -1)));
    }

    private static void sendJailOptions(Player player, String punished) {
        player.stringInput("Enter a Jail reason:", (reason) -> addPunishment(player, punished, new Punishment(player, reason, getOresForRights(player))));
    }

    private static int getOresForRights(Player player) {
        if (player.isAdminAbove())
            return 1000;
        else if (player.isAdmin())
            return 850;
        else if (player.isModerator())
            return 500;
        return 250;
    }

    private static void sendMuteOptions(Player player, String punished) {
        if (!player.isStaff())
            return;
        player.dialogue(new OptionsDialogue(
                new Option("Permanent Mute", () -> selectMuteDuration(player, null, punished)),
                new Option("Temporary Mute", () -> player.dialogue(new OptionsDialogue("Select a Duration",
                        new Option("24 Hours", () -> selectMuteDuration(player, MuteDuration.FIFTH, punished)),
                        new Option("12 Hours", () -> selectMuteDuration(player, MuteDuration.FOURH, punished)),
                        new Option("6 Hours", () -> selectMuteDuration(player, MuteDuration.THIRD, punished)),
                        new Option("2 Hour", () -> selectMuteDuration(player, MuteDuration.SECOND, punished)),
                        new Option("1 Hour", () -> selectMuteDuration(player, MuteDuration.FIRST, punished)))))));
    }

    private static void selectMuteDuration(Player player, MuteDuration duration, String punished) {
        long mutedUntil = duration == null ? -1 : duration.duration + System.currentTimeMillis();
        player.stringInput("Enter a mute reason:", (reason) -> addPunishment(player, punished, new Punishment(player, reason.isEmpty() ? "N/A" : reason, duration == null ? PERM_MUTE : PunishmentType.MUTE, mutedUntil)));
    }

    public static void unMute(Player player) {
        if (!player.isStaff())
            return;
        player.stringInput("Enter a name to Unmute:", (name) -> {
            if (name == null || name.isEmpty())
                return;
            player.dialogue(new OptionsDialogue(
                    new Option("Unmute", () -> addPunishment(player, name, new Punishment(player, name, UN_MUTE, 0))),
                    new Option("Un-perm Mute", () -> addPunishment(player, name, new Punishment(player, name, UN_MUTE_PERM, 0)))));
        });
    }

    public static void unjail(Player player) {
        if (!player.isStaff())
            return;
        player.stringInput("Enter a name to Unmute:", (name) -> {
            if (name == null || name.isEmpty())
                return;
            addPunishment(player, name, new Punishment(player, name, UN_JAIL, 0));
        });
    }
}