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
        checkCampusOpen(attendanceDateTime);

        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = checkAttendanceStatus(attendanceDateTime);
    }

    public Attendance updateAttendanceTime(LocalTime updateTime) {
        return new Attendance(LocalDateTime.of(LocalDate.from(this.attendanceDateTime), updateTime));
    }

    public boolean isEqualDate(LocalDate date) {
        return LocalDate.from(attendanceDateTime).isEqual(date);
    }

    public boolean isBeforeDate(LocalDate date) {
        return LocalDate.from(attendanceDateTime).isBefore(date);
    }

    private void checkHoliday(LocalDateTime attendanceDateTime) {
        if(WEEKEND.contains(attendanceDateTime.getDayOfWeek()) || attendanceDateTime.getDayOfMonth() == CHRISTMAS) {
            throw new IllegalArgumentException("등교일이 아닙니다.");
        }
    }

    private String checkAttendanceStatus(LocalDateTime attendanceDateTime) {
        int startHour = checkStartHour(attendanceDateTime);
        if(attendanceDateTime.getHour() > startHour || (attendanceDateTime.getHour() >= 10 && attendanceDateTime.getMinute() > 30)) {
            return "결석";
        }
        if(attendanceDateTime.getHour() == startHour && attendanceDateTime.getMinute() > 5) {
            return "지각";
        }
        return "출석";
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return Objects.equals(attendanceDateTime, that.attendanceDateTime) && Objects.equals(attendanceStatus, that.attendanceStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDateTime, attendanceStatus);
    }
}
