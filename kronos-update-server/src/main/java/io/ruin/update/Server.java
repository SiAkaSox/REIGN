package io.ruin.update;

import io.ruin.update.cache.FileStore;
import io.ruin.update.network.HandshakeDecoder;
import io.ruin.update.network.NettyServer;
import io.ruin.update.utils.ServerWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.WatchService;
import java.util.Properties;

@Slf4j
public class Server extends ServerWrapper {

    public static FileStore fileStore;
    public static WatchService fileWatcher;

    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            File systemProps = new File("C:/Users/siaka/Desktop/REIGN//server.properties");
            log.info("Looking for system.properties in {}", systemProps.getAbsolutePath());
            try (InputStream in = new FileInputStream(systemProps)) {
                properties.load(in);
            } catch (IOException e) {
                logError("Failed to load server settings!", e);
                throw e;
            }
            System.out.println("Initiating file store...");
            fileStore = new FileStore(properties.getProperty("cache_path"));
            NettyServer nettyServer = NettyServer.start("Update Server", 7304, HandshakeDecoder.class, 5, Boolean.parseBoolean(properties.getProperty("offline_mode")));
            Runtime.getRuntime().addShutdownHook(new Thread(nettyServer::shutdown));
        } catch (Exception e) {
            e.printStackTrace();
            Server.logError("Error", e);
        }
    }

    static {
        init(Server.class);
    }

}

