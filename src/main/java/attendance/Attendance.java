package attendance;

public class Attendance {
    public static int checkAttendance(final int hour, final int minute) {
        if (hour <= 10 && minute <= 5) {
            return 1;
        }
        if (hour == 10 && (minute > 5 && minute <= 30)) {
            return 0;
        }
        return -1;
    }

    public static int checkMondayAttendance(final int hour, final int minute) {
        if (hour <= 13 && minute <= 5) {
            return 1;
        }
        if (hour == 13 && (minute > 5 && minute <= 30)) {
            return 0;
        }
        return -1;
    }
}
