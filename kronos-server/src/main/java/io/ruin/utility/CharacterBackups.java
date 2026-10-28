package io.ruin.utility;

import io.ruin.model.World;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author ReverendDread on 6/27/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Slf4j
public class CharacterBackups {

    private static final String BACKUP_PATH = System.getProperty("user.home") + "/Desktop/OSPK/backups/";
    private static String CHARACTER_SAVES_PATH;
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> backup;
    private final long backup_period = TimeUnit.MINUTES.toMillis(15);

    public void start() {
        if (World.isLive()) {
            backup = service.scheduleAtFixedRate(this::backup, backup_period, backup_period, TimeUnit.MILLISECONDS);
        }
    }

    @SneakyThrows
    public void backup() {
        CompletableFuture.runAsync(() -> {
            try {
                log.info("Performing character backup...");

                File folder = new File(BACKUP_PATH);

                if (!folder.exists())
                    folder.mkdirs();

                File backup = new File(folder, "players-" + DateFormatUtils.format(System.currentTimeMillis(), "MM-dd-yyyy HH-mm-ss") + ".zip");

                FileOutputStream fos = new FileOutputStream(backup);
                ZipOutputStream zos = new ZipOutputStream(fos);

                File directory = new File("./data/saves/" + World.getName() + "/");
                zip(directory, directory.getName(), zos);
                zos.close();
                fos.close();

                log.info("Successfully backed up {} character file(s).");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void zip(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zip(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

}
