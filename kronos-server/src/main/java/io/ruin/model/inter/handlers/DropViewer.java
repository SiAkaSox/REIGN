package io.ruin.model.inter.handlers;

import io.ruin.cache.NPCDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.interfaceutils.InterfaceHandler;
import io.ruin.model.inter.interfaceutils.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.journal.bestiary.Bestiary;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.List;

public class DropViewer {
    public static final int WIDGET_ID = 909;
    private final Player player;

    public String searchString;
    public String updatedSearch;

    private List<NPCDef> npcs = new ArrayList<>();

    public DropViewer(Player player){
        this.player = player;
    }

    public static void open(Player player){
        player.openInterface(InterfaceType.MAIN, WIDGET_ID);

        player.consumerString = player.dropViewer::searchNpcs;
    }

    public void searchNpcs(String name){
        String search = formatForSearch(name);

        searchString = search;
    }

    public void updateDropviewer(){
        updatedSearch = searchString;

        if (searchString == null) {
            return;
        }

        if(searchString.length() < 4){
            if(searchString.length() <= 1){
                player.getPacketSender().sendString(WIDGET_ID, 12, "Search for an npc.");
                player.getPacketSender().sendClientScript(10004, "is",0, "");
                return;
            }

            player.getPacketSender().sendString(WIDGET_ID, 12, "No npcs found! Input is too short.");
            player.getPacketSender().sendClientScript(10004, "is",0, "");
            return;
        }

        npcs.clear();

        NPCDef.forEach(def -> {
            if(def != null && formatForSearch(def.name).contains(updatedSearch) && def.lootTable != null){
                npcs.add(def);
            }
        });

        if(npcs.isEmpty()){
            player.getPacketSender().sendString(WIDGET_ID, 12, "No npcs found! Make sure you spelled it correctly.");
            player.getPacketSender().sendClientScript(10004, "is",0, "");
            return;
        }

        player.getPacketSender().sendString(WIDGET_ID, 12, "");

        String npc_line = "";

        int count = 0;
        for(NPCDef def : npcs) {
            npc_line += getNpcSearchString(def);

            if(count < npcs.size()-1) {
                npc_line += "|";
            }
            count++;
        }

        player.getPacketSender().sendClientScript(10004, "is", npcs.size(), npc_line);
        //player.getPacketSender().sendAccessMask(WIDGET_ID, 10, 0, npcs.size(), 1<<1);
    }

    public String getNpcSearchString(NPCDef def) {
        String s = def.name + " ";

        s += getColTags(def.combatLevel, player.getCombat().getLevel());

        s += "(lvl-"+def.combatLevel+")";

        return s;
    }

    public String getColTags(int cb1, int cb2) {
        int dif = cb2 - cb1;

        String tag;

        if(dif < -9) {
            tag = Misc.getColTags(16711680);
        } else if(dif < -6) {
            tag = Misc.getColTags(16723968);
        } else if(dif < -3) {
            tag = Misc.getColTags(16740352);
        } else if(dif < 0) {
            tag = Misc.getColTags(16756736);
        } else if(dif > 9) {
            tag = Misc.getColTags(65280);
        } else if(dif > 6) {
            tag = Misc.getColTags(4259584);
        } else if(dif > 3) {
            tag = Misc.getColTags(8453888);
        } else if(dif > 0) {
            tag = Misc.getColTags(12648192);
        } else {
            tag = Misc.getColTags(16776960);
        }

        return tag;
    }

    private static String formatForSearch(String string) {
        return string.replace("'", "")
                .toLowerCase()
                .trim();
    }

    static {
        InterfaceHandler.register(WIDGET_ID, h -> {
            h.closedAction = (p, i) ->{
                p.dropViewer.searchString = null;
            };
            h.actions[15] = (SimpleAction) player -> {
                player.stringInput("Enter NPC Name:", (s) -> {
                    Bestiary.search(player, s, true);
                    player.dropViewer.updateDropviewer();
                });
            };
            h.actions[16] = (SimpleAction) player -> {
                player.stringInput("Enter Item Name:", (s) -> {
                    Bestiary.search(player, s, false);
                    player.dropViewer.updateDropviewer();
                });
            };
        });
    }
}