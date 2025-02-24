package attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Attendance {

    private static final int OPEN_HOUR = 8;
    private static final int CLOSE_HOUR = 23;
    private static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private static final int CHRISTMAS = 25;

    private LocalDateTime attendanceDateTime;
    private String attendanceStatus;

    public Attendance() {
    }

    public Attendance(LocalDateTime attendanceDateTime, String attendanceStatus) {
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = attendanceStatus;
    }

    public Attendance attend(String timeInput, LocalDate today) {

        checkHoliday(today);
        if(Objects.equals("--:--", timeInput)) {
            String attendanceStatus = "결석";
            return new Attendance(today.atStartOfDay(), attendanceStatus);
        }
        int startHour = checkStartHour(today);

        LocalTime attendanceTime = formatTimeInput(timeInput);

        checkCampusOpen(attendanceTime.getHour());

        return new Attendance(LocalDateTime.of(today, attendanceTime), checkAttendanceStatus(attendanceTime, startHour));
    }

    private void checkHoliday(LocalDate today) {
        if(WEEKEND.contains(today.getDayOfWeek()) || today.getDayOfMonth() == CHRISTMAS) {
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

    private LocalTime formatTimeInput(String timeInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[HH:mm][HH:m][H:mm][H:m]");
        return LocalTime.parse(timeInput, formatter);
    }

    private void checkCampusOpen(int hour) {
        if(hour < OPEN_HOUR || hour >= CLOSE_HOUR) {
            throw new IllegalArgumentException("캠퍼스 운영 시간은 8:00 ~ 23:00입니다.");
        }
    }

    private String checkAttendanceStatus(LocalTime attendanceTime, int startHour) {
        if(attendanceTime.getHour() > startHour || attendanceTime.getMinute() > 30) {
            return "결석";
        }
        if(attendanceTime.getMinute() > 5) {
            return "지각";
        }
        return "출석";
    }
}
