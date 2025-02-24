package attendance;

import java.time.DayOfWeek;
import java.util.Objects;

public class Attendance {

    public String attend(String timeInput, DayOfWeek dayOfWeek) {
        if(Objects.equals("--:--", timeInput)) {
            return "결석";
        }
        int startHour = checkStartHour(dayOfWeek);

        String[] time = timeInput.split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        return checkAttendanceStatus(hour, startHour, minute);
    }

    private static int checkStartHour(DayOfWeek dayOfWeek) {
        int startHour = 10;
        if(dayOfWeek == DayOfWeek.MONDAY) {
            startHour = 13;
        }
        return startHour;
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
