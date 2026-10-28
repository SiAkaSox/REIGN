package io.ruin.data.impl.npcs;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.utility.JsonUtils;
import io.ruin.cache.NPCDef;
import io.ruin.data.DataFile;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.item.actions.impl.Pet;

import java.util.List;

public class npc_combat extends DataFile {

    /*
    public static void main(String[] args) throws Exception {
        Server.fileStore = new FileStore("%HOME%/jagexcache/oldschool/LIVE");
        Server.dataFolder = FileUtils.get("%HOME%/Dropbox/Runite/Server/data");
        ItemDef.load();
        NPCDef.load();
        AnimDef.load();
        DataFile.load(new npc_combat()); //needed for parsing
        DataFile.load(new slayer_tasks()); //needed for parsing
        //dumpAll();
        //toJson();
    }
    */

    @Override
    public String path() {
        return "npcs/combat/*.json";
    }

    @Override
    public int priority() {
        return 5;
    }

    @Override
    public Object fromJson(String fileName, String json) {
        List<Info> list = JsonUtils.fromJson(json, List.class, Info.class);

        if (!Server.dataOnlyMode) {
            list.forEach(info -> {
                for (int id : info.ids) {
                    NPCDef def = NPCDef.get(id);
                    if (def.combatInfo != null) {
                        /*if (Config.CONSOLE_MESSAGES) {
                            System.err.println("@@@@ " + def.id + ": " + def.name + " already has combat info set!");
                        }*/
                    }
                    def.combatInfo = info;
                    if (info.handler != null && !info.handler.isEmpty()) {
                        try {
                            //noinspection unchecked
                            def.combatHandlerClass = (Class<? extends NPCCombat>) Class.forName(info.handler);
                        } catch (ClassNotFoundException e) {
                            //just in case someone hasn't commit, we'll print a little warning and move on!
                            System.err.println("Warning, combat handler class not found: " + info.handler + " for npc #" + id + " \"" + def.name + "\"!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //if(info.slayer_task != null && info.slayer_task.equalsIgnoreCase("NONE"))
                    //    info.slayer_task = null;
                }
            });
        }

        return list;
    }

    public static final class Info {

        @Expose public int[] ids;

        @Expose public String handler;

        @Expose public Pet pet;

        /**
         * Combat Info
         */

        @Expose public int hitpoints;

        @Expose public int aggressive_level;

        @Expose public int max_damage;

        @Expose public AttackStyle attack_style;

        /**
         * Slayer Info
         */

        @Expose public int slayer_level;

        @Expose public double slayer_xp;

        @Expose public String[] slayer_tasks;


        /**
         * Combat Stats
         */

        @Expose public int attack, strength, defence, ranged, magic;

        /**
         * Aggressive Stats
         */

        @Expose public int stab_attack, slash_attack, crush_attack, magic_attack, ranged_attack;

        /**
         * Defensive Stats
         */

        @Expose public int stab_defence, slash_defence, crush_defence, magic_defence, ranged_defence;

        /**
         * Immunities
         */

        @Expose public boolean poison_immunity, venom_immunity;

        /**
         * Speeds
         */

        @Expose public int attack_ticks, death_ticks, respawn_ticks;

        /**
         * Anims
         */

        @Expose public int spawn_animation, attack_animation, defend_animation, death_animation;

        /**
         * Sounds
         */

        @Expose public Integer attack_sound, defend_sound, death_sound;

        /**
         * Broadcast loot to all nearby players
         */
        @Expose public boolean local_loot = false;

        /**
         * Combat XP modifier.
         * Combat XP earned through hitting this monster will be multiplied by this value
         */

        @Expose public double combat_xp_modifier = 1.0;

        @Expose public int random_drop_count = 1;

    }
}