package domain;

public class AttendanceTime {
    private final int hour;
    private final int minute;

    public AttendanceTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
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
