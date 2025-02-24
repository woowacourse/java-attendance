package domain.date;

public class AttendanceTime {

    private static final int VALID_HOUR_MIN_RANGE = 0;
    private static final int VALID_HOUR_MAX_RANGE = 24;
    private static final int VALID_MINUTE_MIN_RANGE = 0;
    private static final int VALID_MINUTE_MAX_RANGE = 59;

    private final int hour;
    private final int minute;

    public AttendanceTime(int hour, int minute) {
        validate(hour, minute);
        this.hour = hour;
        this.minute = minute;
    }

    private void validate(int hour, int minute) {
        validateHour(hour);
        validateMinute(minute);
    }

    private void validateMinute(int minute) {
        if (minute > VALID_MINUTE_MAX_RANGE || minute < VALID_MINUTE_MIN_RANGE) {
            throw new IllegalArgumentException("분 형식이 올바르지 않습니다.");
        }
    }

    private void validateHour(int hour) {
        if (hour > VALID_HOUR_MAX_RANGE || hour < VALID_HOUR_MIN_RANGE) {
            throw new IllegalArgumentException("시각 형식이 올바르지 않습니다.");
        }
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isLate(int dayOfWeek) {
        if (dayOfWeek == 1) {
            return hour > 13 || (hour == 13 && minute > 5);
        }

        return hour > 10 || (hour == 10 && minute > 5);
    }

    public boolean isAbsence(int dayOfWeek) {
        if (dayOfWeek == 1) {
            return hour > 13 || (hour == 13 && minute > 30);
        }
        return hour > 10 || (hour == 10 && minute > 30);
    }

    public boolean isOpenTime() {
        if (hour == 23 && minute > 0) {
            return false;
        }
        return hour >= 8 && hour <= 23;
    }
}