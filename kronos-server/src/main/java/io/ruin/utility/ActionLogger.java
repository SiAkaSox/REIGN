package io.ruin.utility;

import io.ruin.model.entity.player.Player;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

@Slf4j
public class ActionLogger {

    public static final String DIRECTORY_PATH = "data/logs";

    private Player player;

    public ActionLogger(Player player) {
        this.player = player;
    }

    public void log(LogType type, String message) {
        if (player.isBot)
            return;
        logPrivate(type, message);
        logPublic(type, message);
    }

    private void logPrivate(LogType type, String message) {
        checkPath(player);
        ForkJoinPool.commonPool().submit(() -> {
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new FileWriter(DIRECTORY_PATH + "/players/"+ player.name() +"/"+ type.directory, true));
                String buffer = "(" + getDateAndTime() + "): Name=" + player.name() + " IP=" + player.getIp() + " MAC=" + player.getMACAddress() + " UID=" + player.getUUID() + " " + message;
                out.write(buffer);
                out.newLine();
                out.close();
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        });
    }

    private void logPublic(LogType type, String message) {
        ForkJoinPool.commonPool().submit(() -> {
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new FileWriter(DIRECTORY_PATH + "/"+ type.directory, true));
                String buffer = getDateAndTime()+" Name="+player.name()+" IP="+player.getIp()+" MAC="+player.getMACAddress()+" UID="+player.getUUID()+" Info: "+message;
                out.write(buffer);
                out.newLine();
                out.close();
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        });
    }

    public void checkPath(Player player) {
        ForkJoinPool.commonPool().submit(() -> {
            File folder = new File(DIRECTORY_PATH + "/players/" + player.name().toLowerCase() + "/");
            if (!folder.exists())
                folder.mkdirs();
        });
    }


    public static String getDateAndTime() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }

}
