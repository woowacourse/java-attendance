package domain;

import java.time.LocalTime;

public class Time {
    private final String rawTime;

    public Time(String rawTime) {
        this.rawTime = rawTime;
    }

    public LocalTime convertTime() {
        String[] splittedTime = rawTime.split(":");
        int hour = Integer.parseInt(splittedTime[0]);
        int minute = Integer.parseInt(splittedTime[1]);
        return LocalTime.of(hour, minute);
    }

    private boolean isBeforeAndSame(LocalTime startTime) {
        LocalTime currentTime = convertTime();
        return startTime.isBefore(currentTime) || startTime.equals(currentTime);
    }

    private boolean isAfterAndSame(LocalTime endTime) {
        LocalTime currentTime = convertTime();
        return endTime.isAfter(currentTime) || endTime.equals(currentTime);
    }

    public boolean isBetweenTime(LocalTime startTime, LocalTime endTime) {
        return isBeforeAndSame(startTime) && isAfterAndSame(endTime);
    }
}
