package io.ruin.services;

import io.ruin.model.entity.player.Player;

import java.sql.*;

import io.ruin.model.inter.utils.Config;
import io.ruin.model.stat.StatType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Using this class:
 * To call this class, it's best to make a new thread. You can do it below like so:
 * new Thread(new Donation(player)).start();
 */
public class Hiscores implements Runnable {

    public static final String HOST = "164.90.133.215"; // website ip address
    public static final String USER = "qwnvjvxqpj";
    public static final String PASS = "hwccg33BHe";
    public static final String DATABASE = "qwnvjvxqpj";
    private final Player player;
    private Connection conn;
    private Statement stmt;

    public Hiscores(Player player) {
        this.player = player;
    }

    public boolean connect(String host, String database, String user, String pass, Player player) {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://"+host+":3306"+"/"+database+"?useSSL=false", user, pass);
            return true;
        } catch (SQLException e) {
            player.unlock();
            e.printStackTrace();
            System.out.println("Failing connecting to database! " + e.getErrorCode() + " state: " + e.getSQLState());
            return false;
        }
    }

    /**
     * Disconnects from the MySQL server and destroy the connection
     * and statement instances
     */
    public void destroy() {
        try {
            conn.close();
            conn = null;
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    private void updateECO(ResultSet resultSet) throws SQLException {
        resultSet.updateString("username", player.name());
        resultSet.updateString("rights", player.getPrimaryGroup().toString());
        resultSet.updateString("gamemode", getGameMode());
        resultSet.updateString("exp_mode", player.xpMode.getName());
        resultSet.updateInt("overall_level", player.getStats().totalLevel);
        resultSet.updateLong("overall_exp", player.getStats().totalXp);
        resultSet.updateInt("attack_exp", (int) player.getStats().get(StatType.Attack).experience);
        resultSet.updateInt("defence_exp", (int) player.getStats().get(StatType.Defence).experience);
        resultSet.updateInt("strength_exp", (int) player.getStats().get(StatType.Strength).experience);
        resultSet.updateInt("hitpoints_exp", (int) player.getStats().get(StatType.Hitpoints).experience);
        resultSet.updateInt("ranged_exp", (int) player.getStats().get(StatType.Ranged).experience);
        resultSet.updateInt("prayer_exp", (int) player.getStats().get(StatType.Prayer).experience);
        resultSet.updateInt("magic_exp", (int) player.getStats().get(StatType.Magic).experience);
        resultSet.updateInt("cooking_exp", (int) player.getStats().get(StatType.Cooking).experience);
        resultSet.updateInt("woodcutting_exp", (int) player.getStats().get(StatType.Woodcutting).experience);
        resultSet.updateInt("fletching_exp", (int) player.getStats().get(StatType.Fletching).experience);
        resultSet.updateInt("fishing_exp", (int) player.getStats().get(StatType.Fishing).experience);
        resultSet.updateInt("firemaking_exp", (int) player.getStats().get(StatType.Firemaking).experience);
        resultSet.updateInt("crafting_exp", (int) player.getStats().get(StatType.Crafting).experience);
        resultSet.updateInt("smithing_exp", (int) player.getStats().get(StatType.Smithing).experience);
        resultSet.updateInt("mining_exp", (int) player.getStats().get(StatType.Mining).experience);
        resultSet.updateInt("herblore_exp", (int) player.getStats().get(StatType.Herblore).experience);
        resultSet.updateInt("agility_exp", (int) player.getStats().get(StatType.Agility).experience);
        resultSet.updateInt("thieving_exp", (int) player.getStats().get(StatType.Thieving).experience);
        resultSet.updateInt("slayer_exp", (int) player.getStats().get(StatType.Slayer).experience);
        resultSet.updateInt("farming_exp", (int) player.getStats().get(StatType.Farming).experience);
        resultSet.updateInt("runecrafting_exp", (int) player.getStats().get(StatType.Runecrafting).experience);
        resultSet.updateInt("hunter_exp", (int) player.getStats().get(StatType.Hunter).experience);
        resultSet.updateInt("construction_exp", (int) player.getStats().get(StatType.Construction).experience);
        resultSet.updateInt("kills", Config.PVP_KILLS.get(player));
        resultSet.updateInt("deaths", Config.PVP_DEATHS.get(player));
        resultSet.updateInt("highest_killspree", player.highestKillSpree);
        resultSet.updateInt("pk_rating", player.pkRating);
    }

    private String getGameMode() {
        if(player.getGameMode().isUltimateIronman()) {
            return "Ultimate Iron Man";
        } else if (player.getGameMode().isHardcoreIronman()) {
            return "Hardcore Iron Man";
        } else if (player.getGameMode().isAnyIron()) {
            return "Iron Man";
        } else {
            return "Regular";
        }
    }

    @Override
    public void run() {
        try {
            if (!connect(HOST, DATABASE, USER, PASS, player)) {
                return;
            }

            String name = player.name().replace("_", " ");
            ResultSet rs = executeQuery("SELECT * FROM `hiscores` WHERE `username` = '"+name+"' LIMIT 1");
            if (!rs.next()) {
                rs.moveToInsertRow();
                updateECO(rs);
                rs.insertRow();
            } else {
                updateECO(rs);
                rs.updateRow();
            }
            destroy();
        } catch (Exception e) {
            player.sendMessage("Something went wrong, please try again. If it continues contact staff");
            e.printStackTrace();
        }
    }
}