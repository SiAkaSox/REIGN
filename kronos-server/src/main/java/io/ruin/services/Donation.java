package io.ruin.services;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerGroup;
import org.json.JSONArray;

import java.sql.*;


/**
 * Using this class:
 * To call this class, it's best to make a new thread. You can do it below like so:
 * new Thread(new Donation(player)).start();
 */
public class Donation implements Runnable {

    public static final String HOST = "164.90.133.215";
    public static final String USER = "qwnvjvxqpj";
    public static final String PASS = "hwccg33BHe";
    public static final String DATABASE = "qwnvjvxqpj";

    private Player player;
    private Connection conn;
    private Statement stmt;

    /**
     * The constructor
     * @param player
     */
    public Donation(Player player) {
        this.player = player;
    }

    public static PlayerGroup getGroup(Player player) {
        if(player.storeAmountSpent >= 500)
            return PlayerGroup.PLATINUM_DONATOR;
        if(player.storeAmountSpent >= 100)
            return PlayerGroup.LEGENDARY_DONATOR;
        if(player.storeAmountSpent >= 50)
            return PlayerGroup.SUPER_DONATOR;
        if(player.storeAmountSpent >= 10)
            return PlayerGroup.DONATOR;
        return null;
    }

    public void run() {
        try {
            player.lock();
            if (!connect(HOST, DATABASE, USER, PASS)) {
                return;
            }

            String name = player.name().replace("_", " ");
            ResultSet rs = executeQuery("SELECT * FROM `hbw_rs_orders` WHERE `username`='"+name+"' AND `claimed` = 'unclaimed'");
            player.sendMessage("Checking items...");
            int itemID;
            while (rs.next()) {
                itemID = rs.getInt("item_id");
                int quantity = rs.getInt("item_amount");
                double paid = rs.getDouble("order_value");

                if (player.getGameMode().isUltimateIronman()) {
                    player.getInventory().addOrDrop(itemID, quantity);
                } else {
                    player.getBank().add(itemID, quantity);
                }

                player.storeAmountSpent += paid;

                rs.updateString("claimed", "claimed"); // do not delete otherwise they can reclaim!
                rs.updateLong("claim_date", System.currentTimeMillis());
                rs.updateString("claim_ip", player.getIp());
                rs.updateRow();
            }
            if (!rs.first()) {
                player.rewardsDialogue("I don't have any items for you to claim "+player.name());
                player.sendMessage("You have no items to claim.");
            } else {
                player.sendMessage("Thank you for supporting OSPK!");
                player.sendMessage("Your items have been added to your bank.");
                player.rewardsDialogue("Thank you for donating and supporting OSPK!<br>Your items have been added to your bank.");
            }
            PlayerGroup group = getGroup(player);
            player.unlock();
            player.set("lastDonationClaim", System.currentTimeMillis() + 10_000L);
            destroy();
        } catch (Exception e) {
            player.unlock();
            player.sendMessage("Something went wrong, please try again. If it continues contact staff");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param host the host ip address or url
     * @param database the name of the database
     * @param user the user attached to the database
     * @param pass the users password
     * @return true if connected
     */
    public boolean connect(String host, String database, String user, String pass) {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database+"?useSSL=false", user, pass);
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

    /**
     * Executes an update query on the database
     * @param query
     * @see {@link Statement#executeUpdate}
     */
    public int executeUpdate(String query) {
        try {
            stmt = conn.createStatement(1005, 1008);
            int results = stmt.executeUpdate(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * Executres a query on the database
     * @param query
     * @see {@link Statement#executeQuery(String)}
     * @return the results, never null
     */
    public ResultSet executeQuery(String query) {
        try {
            stmt = conn.createStatement(1005, 1008);
            ResultSet results = stmt.executeQuery(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}