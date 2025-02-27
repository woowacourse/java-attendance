package domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {
    private static final LocalTime openTime = LocalTime.of(8, 0);
    private static final LocalTime closeTime = LocalTime.of(23, 0);
    LocalTime time;

    public Time(String rawTime) {

        LocalTime time = LocalTime.parse(rawTime, DateTimeFormatter.ofPattern("H:m"));
        validateTime(time);
        this.time = time;
    }

    private void validateTime(LocalTime time) {
        if (time.isAfter(closeTime) || time.isBefore(openTime)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
        }
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }
}

