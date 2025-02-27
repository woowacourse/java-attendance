package domain;

import java.time.LocalTime;

public class CampusTime {

    private final LocalTime time;

    private CampusTime(LocalTime time) {
        this.time = time;
    }

    public static CampusTime from(String hourColonMinute) {
        LocalTime time = LocalTime.parse(hourColonMinute);
        return new CampusTime(time);
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }
}
