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
}
