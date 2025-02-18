package attendance;

public class Attendance {
    public static int checkAttendance(final int hour, final int minute) {
        if (hour <= 10 && minute <= 5) {
            return 1;
        }
        if (hour == 10 && (minute <= 30)) {
            return 0;
        }
        return -1;
    }

    public static int checkMondayAttendance(final int hour, final int minute) {
        if (hour <= 13 && minute <= 5) {
            return 1;
        }
        if (hour == 13 && (minute <= 30)) {
            return 0;
        }
        return -1;
    }

    public static void method1(final int hour, final int minute) {
        if (hour < 8) {
            throw new IllegalArgumentException("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
        }
    }
}
