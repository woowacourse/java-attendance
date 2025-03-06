package domain;

import java.time.LocalDate;
import java.util.Objects;

public class Attendance implements Comparable<Attendance> {

    private final LocalDate attendanceDate;
    private AttendanceTime attendanceTime;
    private AttendanceStatus attendanceStatus;

    private Attendance(LocalDate attendanceDate, AttendanceTime attendanceTime, AttendanceStatus attendanceStatus) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = attendanceStatus;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public AttendanceTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void updateAttendance(int updateHour, int updateMinute, AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
        this.attendanceTime = new AttendanceTime(updateHour, updateMinute);
    }

    public boolean isEqualDate(Attendance attendance) {
        return Objects.equals(this.attendanceDate, attendance.attendanceDate);
    }

    public boolean isEqualDate(LocalDate attendanceDate) {
        return Objects.equals(this.attendanceDate, attendanceDate);
    }

    public boolean isBeforeDate(LocalDate date) {
        return attendanceDate.isBefore(date);
    }

    public static Attendance create(LocalDate attendanceDate, AttendanceTime attendanceTime, AttendanceStatus attendanceStatus) {
        return new Attendance(attendanceDate, attendanceTime, attendanceStatus);
    }

    public static Attendance createAbsenceAttendance(LocalDate attendanceDate) {
        return new Attendance(attendanceDate, null, AttendanceStatus.ABSENCE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendance that)) {
            return false;
        }
        return Objects.equals(attendanceDate, that.attendanceDate) && Objects.equals(attendanceTime,
                that.attendanceTime) && attendanceStatus == that.attendanceStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate, attendanceTime, attendanceStatus);
    }

    @Override
    public int compareTo(Attendance attendance) {
        return this.attendanceDate.compareTo(attendance.attendanceDate);
    }
}
