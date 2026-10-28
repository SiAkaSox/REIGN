package io.ruin.services;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.achievements.listeners.hard.DoingMyPart;
import io.ruin.model.content.vote.VoteManager;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import kilim.tools.P;

import java.sql.*;


/**
 * Using this class:
 * To call this class, it's best to make a new thread. You can do it below like so:
 * new Thread(new Votes(player)).start();
 */
public class Votes implements Runnable {

    public static final String HOST = "164.90.133.215"; // website ip address
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
    public Votes(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {
            if (!connect(HOST, DATABASE, USER, PASS)) {
                System.err.println("cannot connect to DB");
                return;
            }

            String name = player.name().replace("_", " ");
            ResultSet rs = executeQuery("SELECT * FROM hbw_rs_votes WHERE username='"+name+"' AND claimed=0");
            player.sendMessage("Checking for votes...");
            int count = 0;
            int totalSites = 5;
            while (rs.next()) {
                int site_id = rs.getInt("site_id");
                String toplist;

                switch (site_id) {
                    case 1:
                        toplist = "RuneLocus";
                        break;
                    case 2:
                        toplist = "RSPS List";
                        break;
                    case 3:
                        toplist = "TopG";
                        break;
                    case 4:
                        toplist = "Top 100 Arena";
                        break;
                    case 5:
                        toplist = "Arena Top 100";
                        break;
                    default:
                        toplist = "Unknown";
                }

                rs.updateInt("claimed", 1); // do not delete otherwise they can reclaim!
                rs.updateRow();
                count += 1;
                VoteManager.check();
                int amount = count;
                if (player.lilZik || World.doubleVotes) {
                    amount = 2;
                }
                if (player.getGameMode().isUltimateIronman()) {
                    player.getInventory().addOrDrop(1464, amount);
                    player.getBank().add(995, 50000);
                } else {
                    player.getBank().add(1464, amount);
                    player.getBank().add(995, 50000);
                }
                DoingMyPart.complete(player);
                player.sendMessage("Received vote for: "+toplist);
                player.sendMessage("Your rewards have gone to your bank!");
            }
            if (!rs.first()) {
                player.sendMessage("You have no votes to claim.");
                player.rewardsDialogue("You don't have any Votes to claim "+player.name());

            }
            if (count >= totalSites) {
                if (player.isRealism) {
                    player.sendMessage("Thank you for voting on all sites!");
                }
                giveBook();
                player.afkzoneTicks += 72000;

                if (player.storeAmountSpent >= 250 && player.storeAmountSpent < 500) {
                    player.sendMessage("<img=33> <col=800080>You receive 2 extra vote tickets for your donator rank!");
                    player.getInventory().addOrDrop(1464, 2);
                }
                if (player.storeAmountSpent >= 500 && player.storeAmountSpent < 1000) {
                    player.sendMessage("<img=33> <col=800080>You receive 3 extra vote tickets for your donator rank!");
                    player.getInventory().addOrDrop(1464, 3);
                }
                if (player.storeAmountSpent >= 1000) {
                    player.sendMessage("<img=33> <col=800080>You receive 4 extra vote tickets for your donator rank!");
                    player.getInventory().addOrDrop(1464, 4);
                }
                player.getInventory().addOrDrop(new Item(26920, 1));
                player.sendMessage(Color.DARK_RED.wrap("You have received a Vote Mystery Box for voting on all sites!"));
            }
            player.set("lastVoteClaim", System.currentTimeMillis() + 10_000L);
            destroy();
        } catch (Exception e) {
            e.printStackTrace();
            player.unlock();
            player.sendMessage("Something went wrong, please try again. If it continues contact staff");
        }
    }

    private void giveBook() {
        player.getInventory().addOrDrop(new Item(7162, 1));
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