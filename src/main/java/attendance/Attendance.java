package attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Attendance {

    public static final int OPEN_HOUR = 8;
    public static final int CLOSE_HOUR = 23;

    public String attend(String timeInput, LocalDate today) {
        List<DayOfWeek> weekend = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        checkHoliday(today, weekend);
        if(Objects.equals("--:--", timeInput)) {
            return "결석";
        }
        int startHour = checkStartHour(today);

        String[] time = timeInput.split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        checkCampusOpen(hour);

        return checkAttendanceStatus(hour, minute, startHour);
    }

    private void checkHoliday(LocalDate today, List<DayOfWeek> weekend) {
        if(weekend.contains(today.getDayOfWeek()) || today.getDayOfMonth() == 25) {
            throw new IllegalArgumentException("등교일이 아닙니다.");
        }
    }

    private int checkStartHour(LocalDate today) {
        int startHour = 10;
        if(today.getDayOfWeek() == DayOfWeek.MONDAY) {
            startHour = 13;
        }
        return startHour;
    }

    private void checkCampusOpen(int hour) {
        if(hour < OPEN_HOUR || hour >= CLOSE_HOUR) {
            throw new IllegalArgumentException("캠퍼스 운영 시간은 8:00 ~ 23:00입니다.");
        }
    }

    private String checkAttendanceStatus(int hour, int minute, int startHour) {
        if(hour > startHour || minute > 30) {
            return "결석";
        }
        if(minute > 5) {
            return "지각";
        }
        return "출석";
    }
}
