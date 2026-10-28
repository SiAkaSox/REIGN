package io.ruin.model.inter.teleports;

import io.ruin.Server;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Position;

import java.util.ArrayList;

import static io.ruin.Server.STARTUP_THREAD;

public class TeleportInterface {
    public static final int INTERFACE_ID = 803;

    public static void open(Player player){
        if (player.wildernessLevel > 0) {
            player.sendMessage("You cannot use this feature inside the wilderness.");
            return;
        }
        player.openInterface(InterfaceType.MAIN, INTERFACE_ID);

        player.teleportSelectedCategory = -1;

        player.searchTeleports = null;

        sendCategories(player);
        showTeleports(player, 0);
    }

    private static void sendCategories(Player player) {
        int index = 0;
        for(TeleportList.Category cat : TeleportList.categories){
            player.getPacketSender().sendClientScript(10083, "iiiisi", index, cat.getSpriteId(), cat.getSw(), cat.getSh(), cat.getName(), 0);

            index++;
        }

        player.getPacketSender().sendClientScript(10085, "iii", (803<<16)|7, (803<<16)|8, index*24);
        player.getPacketSender().sendAccessMask(INTERFACE_ID, 7, 0, index*3, 1<<1);
    }

    private static void showTeleports(Player player, int category){
        if(category == player.teleportSelectedCategory && player.searchTeleports == null)
            return;

        player.searchTeleports = null;

        player.getPacketSender().sendClientScript(10086, "i", (803<<16)|13);

        TeleportList.Category cat = TeleportList.categories[category];

        if(player.teleportSelectedCategory != -1){
            TeleportList.Category prev = TeleportList.categories[player.teleportSelectedCategory];

            player.getPacketSender().sendClientScript(10083, "iiiisi", prev.ordinal(), prev.getSpriteId(), prev.getSw(), prev.getSh(), prev.getName(), category);
        }

        player.getPacketSender().sendClientScript(10083, "iiiisi", category, cat.getSpriteId(), cat.getSw(), cat.getSh(), cat.getName(), category);

        if(category == 0){
            if(player.favoriteTeleports.isEmpty()){
                player.getPacketSender().sendString(803, 17, "Your favorite teleports are currently empty.");
            } else {
                for(int i = 0; i < player.favoriteTeleports.size(); i++){
                    TeleportList.Teleport teleport = TeleportList.getTeleportForId(player.favoriteTeleports.get(i));
                    player.getPacketSender().sendClientScript(10084, "isiiii", i, teleport.getName(), teleport.isMulti()?1:0, teleport.isWild()?1:0, 0, player.favoriteTeleports.contains(teleport.getId())?1:0);
                }

                player.getPacketSender().sendClientScript(10085, "iii", (803<<16)|13, (803<<16)|14, player.favoriteTeleports.size()*24);
                player.getPacketSender().sendAccessMask(803, 13, 0, player.favoriteTeleports.size()*5, 1<<1);
                player.getPacketSender().sendString(803, 17, "");
            }
        } else {
            if(cat.getTeleports() == null){
                return;
            }

            int index = 0;
            for(TeleportList.Teleport teleport : cat.getTeleports()){
                player.getPacketSender().sendClientScript(10084, "isiiii", index, teleport.getName(), teleport.isMulti()?1:0, teleport.isWild()?1:0, 0, player.favoriteTeleports.contains(teleport.getId())?1:0);
                index++;
            }

            player.getPacketSender().sendClientScript(10085, "iii", (803<<16)|13, (803<<16)|14, index*24);
            player.getPacketSender().sendAccessMask(803, 13, 0, index*5, 1<<1);

            if(cat.getTeleports().length == 0){
                player.getPacketSender().sendString(803, 17, "There are no teleports for this category.");
            } else {
                player.getPacketSender().sendString(803, 17, "");
            }

        }

        player.teleportSelectedCategory = category;
    }

    static void handleTeleport(Player player, int slot){
        TeleportList.Teleport teleport = getTeleportForSlot(player, slot);

        if(teleport == null)
            return;

        if(teleport.isWild() && player.tpWildWarn){
            int wildLvl = Wilderness.getLevel(teleport.getPosition());
            player.dialogue(new OptionsDialogue("Are you sure you wish to teleport to level " + wildLvl + " wilderness?", new Option("Yes", ()->{
                teleportFinish(player, teleport.getPosition());
            }), new Option("Yes, don't warn me again.", ()->{
                player.tpWildWarn = false;
                teleportFinish(player, teleport.getPosition());
            }), new Option("No")));
        } else {
            teleportFinish(player, teleport.getPosition());
        }
    }

    static void handleFavorite(Player player, int slot){
        TeleportList.Teleport teleport = getTeleportForSlot(player, slot);

        if(teleport == null)
            return;

        if(player.favoriteTeleports.contains(teleport.getId())){
            player.favoriteTeleports.remove((Object)teleport.getId());
            player.sendMessage("You have removed " + teleport.getName() + " from your favorite teleports.");
        } else {
            player.favoriteTeleports.add(teleport.getId());
            player.sendMessage("You have added " + teleport.getName() + " to your favorite teleports.");
        }

        if(player.teleportSelectedCategory == 0){
            player.teleportSelectedCategory = -1;
            showTeleports(player, 0);
            return;
        }

        player.getPacketSender().sendClientScript(10084, "isiiii", slot, teleport.getName(), teleport.isMulti()?1:0, teleport.isWild()?1:0, 0, player.favoriteTeleports.contains(teleport.getId())?1:0);
    }

    static TeleportList.Teleport getTeleportForSlot(Player player, int slot){
        if(player.searchTeleports != null && slot >= 0 && slot < player.searchTeleports.size()){
            return player.searchTeleports.get(slot);
        } else if(player.teleportSelectedCategory == 0 && slot >= 0 && slot < player.favoriteTeleports.size()){
            return TeleportList.getTeleportForId(player.favoriteTeleports.get(slot));
        } else {
            if(player.teleportSelectedCategory >= 0 && player.teleportSelectedCategory < TeleportList.categories.length &&
                    slot >= 0 && slot < TeleportList.categories[player.teleportSelectedCategory].getTeleports().length){
                return TeleportList.categories[player.teleportSelectedCategory].getTeleports()[slot];
            }
        }

        return null;
    }


    private static void teleportFinish(Player player, Position position) {
        player.previousTeleportX = position.getX();
        player.previousTeleportY = position.getY();
        player.previousTeleportZ = position.getZ();
        teleport(player, position);
    }

    public static void teleport(Player player, Position position) {
        player.resetActions(true, true, true);
        player.lock(LockType.FULL_NULLIFY_DAMAGE); //keep lock outside of event!
        player.startEvent(e -> {
            player.getPacketSender().fadeOut();
            e.delay(1);
            player.getMovement().teleport(position);
            player.getPacketSender().clearFade();

            PlayerCounter.TELEPORT_PORTAL_USES.increment(player, 1);
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

    public static void search(Player player, String input){
        player.searchTeleports = new ArrayList<>();

        for(TeleportList.Teleport t : TeleportList.teleports){
            if(t.getName().toLowerCase().contains(input.toLowerCase())) {
                player.searchTeleports.add(t);
                continue;
            }
            for(String tag : t.getTags()){
                if(tag.toLowerCase().contains(input.toLowerCase())){
                    player.searchTeleports.add(t);
                    break;
                }
            }
        }

        TeleportList.Category cat = TeleportList.categories[player.teleportSelectedCategory];
        player.getPacketSender().sendClientScript(10083, "iiiisi", player.teleportSelectedCategory, cat.getSpriteId(), cat.getSw(), cat.getSh(), cat.getName(), -1);

        player.getPacketSender().sendClientScript(10086, "i", (803<<16)|13);

        if(player.searchTeleports.isEmpty()){
            player.getPacketSender().sendString(803, 17, "No results found!");
        } else {
            for(int i = 0; i < player.searchTeleports.size(); i++){
                TeleportList.Teleport teleport = player.searchTeleports.get(i);

                player.getPacketSender().sendClientScript(10084, "isiiii", i, teleport.getName(), teleport.isMulti()?1:0, teleport.isWild()?1:0, 0, player.favoriteTeleports.contains(teleport.getId())?1:0);
            }

            player.getPacketSender().sendString(803, 17, "");
        }

        player.getPacketSender().sendClientScript(10085, "iii", (803<<16)|13, (803<<16)|14, player.searchTeleports.size()*24);
        player.getPacketSender().sendAccessMask(803, 13, 0, player.searchTeleports.size()*5, 1<<1);
    }

    static {
        
            InterfaceHandler.register(INTERFACE_ID, h -> {
                h.actions[7] = (SlotAction) (player, slot) -> showTeleports(player, slot / 3);
                h.actions[13] = (SlotAction) (player, slot) -> {
                    if ((slot - 1) % 5 == 0) {
                        handleTeleport(player, (slot - 1) / 5);
                    } else if ((slot - 2) % 5 == 0) {
                        handleFavorite(player, (slot - 2) / 5);
                    }
                };
                h.actions[15] = (SimpleAction) (p) -> {
                    p.stringInput("Search teleports (name, npc, location):", s -> {
                        search(p, s);
                    });
                };
            });
    }
}

