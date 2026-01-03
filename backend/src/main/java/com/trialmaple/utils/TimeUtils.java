package com.trialmaple.utils;

import java.time.Duration;

public class TimeUtils {
    public static String formatDuration(Duration duration) {
        if (duration == null)
            return null;

        long totalMillis = duration.toMillis();
        long hours = totalMillis / (1000 * 60 * 60);
        long minutes = (totalMillis / (1000 * 60)) % 60;
        long seconds = (totalMillis / 1000) % 60;
        long centis = (totalMillis % 1000) / 10;

        StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            sb.append(hours).append(":");
        }

        sb.append(String.format("%02d:%02d.%02d", minutes, seconds, centis));

        return sb.toString();
    }
}
