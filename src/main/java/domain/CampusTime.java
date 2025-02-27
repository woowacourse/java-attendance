package domain;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class CampusTime implements Comparable<CampusTime> {

    private final LocalTime time;

    private CampusTime(String inputTime) {
        validateTimeRangeAndType(inputTime);
        this.time = LocalTime.parse(inputTime);
    }

    public static CampusTime from(String inputTime) {
        return new CampusTime(inputTime);
    }

    @Override
    public int compareTo(CampusTime other) {
        int cmp = Integer.compare(time.getHour(),other.getHour());
        if (cmp == 0) {
            cmp = Integer.compare(time.getMinute(), other.getMinute());
        }
        return cmp;
    }

    public boolean isBefore(CampusTime other) {
        return compareTo(other) < 0;
    }

    public boolean isAfter(CampusTime other) {
        return compareTo(other) > 0;
    }

    private void validateTimeRangeAndType(String hourColonMinute) {
        try {
            LocalTime.parse(hourColonMinute);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 유효한 범위의 숫자를 입력해 주세요.");
        }
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }
}
