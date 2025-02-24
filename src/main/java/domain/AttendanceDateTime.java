package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceDateTime {

    private final AttendanceDate attendanceDate;
    private AttendanceTime attendanceTime;

    private AttendanceDateTime(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public static AttendanceDateTime from(LocalDateTime attendanceDateTime) {
        return new AttendanceDateTime(
                new AttendanceDate(attendanceDateTime.toLocalDate()),
                new AttendanceTime(attendanceDateTime.toLocalTime())
        );
    }

    public void update(LocalTime updateTime) {
        this.attendanceTime = new AttendanceTime(updateTime);
    }

    public AttendanceState check() {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.MONDAY) {
            return decisionByHour(13);
        }
        return decisionByHour(10);
    }

    private AttendanceState decisionByHour(int standardHour) {
        if (attendanceTime.compareHour(standardHour) == 0) {
            return decisionByMinute();
        }

        if (attendanceTime.compareHour(standardHour) > 0) {
            return AttendanceState.ABSENT;
        }

        return AttendanceState.ATTEND;
    }

    private AttendanceState decisionByMinute() {
        if (attendanceTime.compareMinute(30) > 0) {
            return AttendanceState.ABSENT;
        }

        if (attendanceTime.compareMinute(5) > 0) {
            return AttendanceState.LATE;
        }

        return AttendanceState.ATTEND;
    }

    public boolean isSameDay(int day) {
        return attendanceDate.isSameDay(day);
    }

    public AttendanceTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceDate getAttendanceDate() {
        return attendanceDate;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceDateTime dateTime = (AttendanceDateTime) object;
        return Objects.equals(attendanceDate, dateTime.attendanceDate) && Objects.equals(
                getAttendanceTime(), dateTime.getAttendanceTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate, getAttendanceTime());
    }

    public int getHour() {
        return attendanceTime.getHour();
    }

    public int getMinute() {
        return attendanceTime.getMinute();
    }

    public int getDay() {
        return attendanceDate.getDayOfMonth();
    }

    public DayOfWeek getDayOfWeek() {
        return attendanceDate.getDayOfWeek();
    }
}
