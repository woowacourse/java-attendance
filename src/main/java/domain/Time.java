package domain;

import java.time.LocalTime;
import java.util.regex.Pattern;

public class Time {
    private static final Pattern TIME_PATTERN = Pattern.compile("[0-9]{2}:[0-9]{2}$");
    private final String rawTime;

    public Time(String rawTime) {
        validateTimeFormat(rawTime);
        validateTime(rawTime);
        this.rawTime = rawTime;
    }

    public LocalTime convertTime() {
        String[] splittedTime = rawTime.split(":");
        int hour = Integer.parseInt(splittedTime[0]);
        int minute = Integer.parseInt(splittedTime[1]);
        return LocalTime.of(hour, minute);
    }

    private void validateTimeFormat(String rawTime) {
        if (!TIME_PATTERN.matcher(rawTime).matches()) {
            throw new IllegalArgumentException("잘못된 시간 포맷입니다.");
        }
    }

    private void validateHour(String rawTime) {
        String[] splittedTime = rawTime.split(":");
        int hour = Integer.parseInt(splittedTime[0]);
        if (hour >= 24 || hour < 0) {
            throw new IllegalArgumentException("시간은 00 ~ 23 사이여야합니다.");
        }
    }

    private void validateMinute(String rawTime) {
        String[] splittedTime = rawTime.split(":");
        int minute = Integer.parseInt(splittedTime[1]);
        if (minute >= 60 || minute < 0) {
            throw new IllegalArgumentException("분은 00 ~ 59 사이여야합니다.");
        }
    }

    private void validateTime(String rawTime) {
        validateHour(rawTime);
        validateMinute(rawTime);
    }
}
