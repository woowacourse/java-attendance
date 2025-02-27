package domain;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class CampusTime implements Comparable<CampusTime> {

    private static final LocalTime CAMPUS_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_END_TIME = LocalTime.of(23, 0);

    private final LocalTime time;

    private CampusTime(final String inputTime) {
        validateTimeRangeAndType(inputTime);
        LocalTime time = LocalTime.parse(inputTime);
        validateCampusOpenTimeRange(time);
        this.time = time;
    }

    public static CampusTime from(final String inputTime) {
        return new CampusTime(inputTime);
    }

    @Override
    public int compareTo(final CampusTime other) {
        int cmp = Integer.compare(time.getHour(), other.getHour());
        if (cmp == 0) {
            cmp = Integer.compare(time.getMinute(), other.getMinute());
        }
        return cmp;
    }

    public boolean isBefore(final CampusTime other) {
        return compareTo(other) < 0;
    }

    public boolean isAfter(final CampusTime other) {
        return compareTo(other) > 0;
    }

    private void validateTimeRangeAndType(final String inputTime) {
        try {
            LocalTime.parse(inputTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 유효한 범위의 숫자를 입력해 주세요.");
        }
    }

    private void validateCampusOpenTimeRange(final LocalTime inputTime) {
        if (inputTime.isBefore(CAMPUS_START_TIME) || inputTime.isAfter(CAMPUS_END_TIME)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
        }
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }
}
