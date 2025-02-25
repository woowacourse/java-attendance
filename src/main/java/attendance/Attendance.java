package attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Attendance {

    private static final LocalTime OPEN_HOUR = LocalTime.of(8, 0);
    private static final LocalTime CLOSE_HOUR = LocalTime.of(23, 0);
    private static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private static final int CHRISTMAS = 25;

    private LocalDateTime attendanceDateTime;
    private String attendanceStatus;

    public Attendance(LocalDateTime attendanceDateTime) {
        checkHoliday(attendanceDateTime);
        if(Objects.equals(LocalTime.from(attendanceDateTime), LocalTime.MIN)) {
            this.attendanceDateTime = attendanceDateTime;
            this.attendanceStatus = "결석";
            return;
        }
        int startHour = checkStartHour(attendanceDateTime);
        checkCampusOpen(attendanceDateTime);

        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = checkAttendanceStatus(attendanceDateTime, startHour);
    }

    private void checkHoliday(LocalDateTime attendanceDateTime) {
        if(WEEKEND.contains(attendanceDateTime.getDayOfWeek()) || attendanceDateTime.getDayOfMonth() == CHRISTMAS) {
            throw new IllegalArgumentException("등교일이 아닙니다.");
        }
    }

    private int checkStartHour(LocalDateTime attendanceDateTime) {
        int startHour = 10;
        if(attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            startHour = 13;
        }
        return startHour;
    }

    private void checkCampusOpen(LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = LocalTime.from(attendanceDateTime);
        if(attendanceTime.isBefore(OPEN_HOUR) || attendanceTime.isAfter(CLOSE_HOUR)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간은 8:00 ~ 23:00입니다.");
        }
    }

    private String checkAttendanceStatus(LocalDateTime attendanceTime, int startHour) {
        if(attendanceTime.getHour() > startHour || (attendanceTime.getHour() >= 10 && attendanceTime.getMinute() > 30)) {
            return "결석";
        }
        if(attendanceTime.getHour() == startHour && attendanceTime.getMinute() > 5) {
            return "지각";
        }
        return "출석";
    }

    public boolean isEqualDate(LocalDate date) {
        return LocalDate.from(attendanceDateTime).isEqual(date);
    }
}
