package attendance;

import java.time.DayOfWeek;
import java.util.Objects;

public class Attendance {

    public static final int OPEN_HOUR = 8;
    public static final int CLOSE_HOUR = 23;

    public String attend(String timeInput, DayOfWeek dayOfWeek) {
        if(Objects.equals("--:--", timeInput)) {
            return "결석";
        }
        int startHour = checkStartHour(dayOfWeek);

        String[] time = timeInput.split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        checkCampusOpen(hour);

        return checkAttendanceStatus(hour, startHour, minute);
    }

    private static int checkStartHour(DayOfWeek dayOfWeek) {
        int startHour = 10;
        if(dayOfWeek == DayOfWeek.MONDAY) {
            startHour = 13;
        }
        return startHour;
    }

    private static void checkCampusOpen(int hour) {
        if(hour < OPEN_HOUR || hour >= CLOSE_HOUR) {
            throw new IllegalArgumentException("캠퍼스 운영 시간은 8:00 ~ 23:00입니다.");
        }
    }

    private static String checkAttendanceStatus(int hour, int startHour, int minute) {
        if(hour > startHour || minute > 30) {
            return "결석";
        }
        if(minute > 5) {
            return "지각";
        }
        return "출석";
    }
}
