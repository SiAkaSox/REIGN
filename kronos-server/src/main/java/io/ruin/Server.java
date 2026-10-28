package io.ruin;

import com.ea.agentloader.AgentLoader;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.ruin.cache.*;
import io.ruin.cache.filestore.FileStore;
import io.ruin.data.DataFile;
import io.ruin.data.impl.RegionLoader;
import io.ruin.data.yaml.YamlLoader;
import io.ruin.model.World;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.player.Player;
import io.ruin.network.LoginDecoder;
import io.ruin.network.incoming.Incoming;
import io.ruin.network.netty.NettyServer;
import io.ruin.process.CoreWorker;
import io.ruin.process.LoginWorker;
import io.ruin.process.ProcessWorker;
import io.ruin.process.event.EventWorker;
import io.ruin.services.database.Database;
import io.ruin.services.database.DummyDatabase;
import io.ruin.utility.*;
import kilim.WeavingClassLoader;
import kilim.agent.KilimAgent;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Server extends ServerWrapper {

    private static Instant bootedAt = Instant.now();

    public static final ProcessWorker worker = newWorker("server-worker", 600L, Thread.NORM_PRIORITY + 1);

    public static final ExecutorService STARTUP_THREAD = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setDaemon(true).build());

    public static final ExecutorService REWARD_THREAD = Executors.newFixedThreadPool(1);

    public static final ExecutorService HISCORES = Executors.newFixedThreadPool(1);

    public static Database gameDb = new DummyDatabase();

    public static Database siteDb = new DummyDatabase();

    public static Database voteDb = new DummyDatabase();

    public static final Database dumpsDb = new DummyDatabase();

    public static Database con = new DummyDatabase();

    public static Database forumDb = new DummyDatabase();

    public static FileStore fileStore;

    public static File dataFolder;

    public static CharacterBackups backups = new CharacterBackups();

    public static boolean dataOnlyMode = false;

    public static boolean online;

    public static List<Runnable> afterData = new ArrayList<>();


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Throwable {

        if (kilim.tools.Kilim.trampoline(new Object(), true, args))
            return;

        //log.info("{}", Thread.currentThread().getContextClassLoader());
        WeavingClassLoader classLoader = new WeavingClassLoader();
        Thread.currentThread().setContextClassLoader(classLoader);
        AgentLoader.loadAgentClass(KilimAgent.class.getName(), "");
        KilimAgent.classLoader = classLoader;

        long startTime = System.currentTimeMillis();

        init(Server.class);

        Properties properties = new Properties();
        /*
         * Server properties
         */

        println("Loading server settings...");
        File systemProps = new File("C:/Users/siaka/Desktop/REIGN/server.properties");
        log.info("Looking for system.properties in {}", systemProps.getAbsolutePath());
        try (InputStream in = new FileInputStream(systemProps)) {
            properties.load(in);
        } catch (IOException e) {
            logError("Failed to load server settings!", e);
        }
        /*
         * World information
         */
        World.parse(properties);

        // Force World 1 for local development
        World.id = 1;
        // World.type = World.WorldType.LIVE;   // uncomment & adjust if needed

        try {
            fileStore = new FileStore(properties.getProperty("cache_path"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataFolder = FileUtils.get(properties.getProperty("data_path"));

        /*
         * Loading (Data from cache & databases)
         */
        println("Loading server data...");
        try {
            Varpbit.load();
            IdentityKit.load();
            AnimDef.load();
            GfxDef.load();
            ScriptDef.load();
            InterfaceDef.load();
            ItemDef.load();
            NPCDef.load();
            ObjectDef.load();
            RegionLoader.init();
            DataFile.load();
        } catch (Throwable t) {
            logError("", t);
            return;
        }
        /*
         * Loading (Scripts & handlers)
         */
        try {
            PackageLoader.load("io.ruin"); //ensures all static blocks load
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * Loading (After data has been loaded!
         */
        for (Runnable r : afterData) {
            try {
                r.run();
            } catch (Throwable t) {
                logError("", t);
                return;
            }
        }
        afterData.clear();
        afterData = null;

        println("Loading server scripts & handlers...");
        try {
            Special.load();
            Incoming.load();
            YamlLoader.initYamlFiles();
            backups.start();
        } catch (Throwable t) {
            logError("Error loading handlers", t);
            //return;
        }

        /*
         * Processing
         */
        println("Starting server workers...");
        worker.queue(() -> {
            CoreWorker.process();
            EventWorker.process();
            return false;
        });
        LoginWorker.start();
        /*
         * Network
         */
        NettyServer nettyServer = NettyServer.start(World.type.getWorldName() + " World (" + World.id + ") Server", World.port, LoginDecoder.class, 5, (System.currentTimeMillis() - startTime));
        online = true;
        /*
         * Shutdown hook
         */
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println();
            System.out.println("Server shutting down with " + worker.getQueuedTaskCount() + " tasks queued.");
            System.out.println("Gracefully shutting down world server...");
            STARTUP_THREAD.shutdown();
            /*
             * Shutdown network
             */
            nettyServer.shutdown();

            /*
             * Remove players
             */
            int fails = 0;
            while (true) {
                try {
                    for (Player p : World.players) {
                        if (p.getChannel().id() == null)
                            p.logoutStage = -1;
                    }
                    if (Server.worker.getExecutor().submit(World::removePlayers).get())
                        break;

                    ThreadUtils.sleep(10L); //^ that will already be a big enough delay
                } catch (Throwable t) {
                    logError("ERROR: Removing online players", t);
                    if (++fails >= 5 && World.removePlayers())
                        break;
                    ThreadUtils.sleep(1000L);
                }
            }
        }));
    }

    /**
     * Timing
     */
    public static long currentTick() {
        return worker.getExecutions();
    }

    public static long getEnd(long ticks) {
        return currentTick() + ticks;
    }

    public static boolean isPast(long end) {
        return currentTick() >= end;
    }

    public static int tickMs() {
        return (int) Server.worker.getPeriod();
    }

    public static int toTicks(int seconds) {
        return (seconds * 1000) / tickMs();
    }

    public static String getTime() {
        return timeFormatter.format(new Date());
    }

    public static String getDate() {
        return dateFormatter.format(new Date());
    }

    public static String getUptime() {
        return Utils.getDurationAsString(Duration.between(bootedAt, Instant.now()));

    }

    public static final ArrayList<World> worlds = new ArrayList<World>();

    public static Player getPlayer(int userId) {
        for (World world : worlds) {
            for (Player player : world.players) {
                if (player.userId != userId)
                    continue;
                return player;
            }
        }
        return null;
    }

    public static Player getPlayer(String name) {
        for (World world : worlds) {
            for (Player player : world.players) {
                if (!name.equalsIgnoreCase(player.name())) continue;
                return player;
            }
        }
        return null;
    }

    private static final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM");

}