package domain;

import java.time.LocalTime;

public class Time {
    private static final String TIME_DELIMITER = ":";
    public static final int HOUR_INDEX = 0;
    public static final int MINUTE_INDEX = 1;

    private final String rawTime;

    public Time(String rawTime) {
        this.rawTime = rawTime;
    }

    public LocalTime convertTime() {
        String[] splittedTime = rawTime.split(TIME_DELIMITER);
        int hour = Integer.parseInt(splittedTime[HOUR_INDEX]);
        int minute = Integer.parseInt(splittedTime[MINUTE_INDEX]);
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

    public void validateTime(LocalTime startTime, LocalTime endTime) {
        if (!isBetweenTime(startTime, endTime)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
    }
}
