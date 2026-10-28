package io.ruin.model.activities.dailytasks;

import java.util.Calendar;

public class DailyTaskReset {

    public static String getDailyTaskReset() {
        // Create a Calendar instance for the current time
        Calendar currentTime = Calendar.getInstance();

        // Create a Calendar instance and set it to 12:00 AM (midnight) of the next day
        Calendar midnight = Calendar.getInstance();
        midnight.set(Calendar.HOUR_OF_DAY, 0);
        midnight.set(Calendar.MINUTE, 0);
        midnight.set(Calendar.SECOND, 0);
        midnight.set(Calendar.MILLISECOND, 0);
        midnight.add(Calendar.DAY_OF_MONTH, 1);

        // Calculate the time difference in milliseconds
        long timeRemainingMillis = midnight.getTimeInMillis() - currentTime.getTimeInMillis();

        // Calculate hours and minutes from the time difference
        long hours = timeRemainingMillis / (60 * 60 * 1000);
        long minutes = (timeRemainingMillis % (60 * 60 * 1000)) / (60 * 1000);

        // Format the timeRemaining as a string and return it
        return hours + "h and " + minutes + "m";
    }
}