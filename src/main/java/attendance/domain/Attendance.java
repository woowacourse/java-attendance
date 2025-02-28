package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private static final LocalTime OPEN_HOUR = LocalTime.of(8, 0);
    private static final LocalTime CLOSE_HOUR = LocalTime.of(23, 0);
    private static final int NOT_MONDAY_START_TIME = 10;
    private static final int MONDAY_START_TIME = 13;

    private final LocalDateTime dateTime;
    private final AttendanceStatus status;

    public Attendance(final LocalDateTime dateTime) {
        Holiday.isHoliday(dateTime);
        if(Objects.equals(LocalTime.from(dateTime), LocalTime.MIN)) {
            this.dateTime = dateTime;
            this.status = AttendanceStatus.ABSENCE;
            return;
        }
        checkCampusOpen(dateTime);

        this.dateTime = dateTime;
        this.status = AttendanceStatus.of(dateTime);
    }

    public static int checkStartHour(final LocalDateTime attendanceDateTime) {
        int startHour = NOT_MONDAY_START_TIME;
        if(attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            startHour = MONDAY_START_TIME;
        }
        return startHour;
    }

    public Attendance updateAttendanceTime(final LocalTime updateTime) {
        return new Attendance(LocalDateTime.of(LocalDate.from(this.dateTime), updateTime));
    }

    public boolean isEqualDate(final LocalDate date) {
        return LocalDate.from(dateTime).isEqual(date);
    }

    public boolean isEqualStatus(final AttendanceStatus value) {
        return this.status.equals(value);
    }

    public boolean isEqualAttendanceDate(final Attendance attendance) {
        return attendance.dateTime.equals(this.dateTime);
    }

    private void checkCampusOpen(final LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = LocalTime.from(attendanceDateTime);
        if(attendanceTime.isBefore(OPEN_HOUR) || attendanceTime.isAfter(CLOSE_HOUR)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간은 8:00 ~ 23:00입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return Objects.equals(dateTime, that.dateTime) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, status);
    }
}
