package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance implements Comparable<Attendance> {

    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8,0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);

    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    public Attendance(LocalDate attendanceDate, LocalTime attendanceTime) {
        Holiday.check(attendanceDate);
        validateCampusOperatingHours(attendanceTime);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public boolean hasAttend(LocalDate attendanceDate, LocalTime attendanceTime) {
        return this.attendanceDate.isEqual(attendanceDate)
            && this.attendanceTime.equals(attendanceTime);
    }

    private void validateCampusOperatingHours(LocalTime attendanceTime) {
        if (attendanceTime == null) return;
        if (attendanceTime.isBefore(CAMPUS_OPEN_TIME) || attendanceTime.isAfter(CAMPUS_CLOSE_TIME)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영시간이 아닙니다.");
        }
    }

    public boolean hasAttendDate(LocalDate editDate) {
        return attendanceDate.isEqual(editDate);
    }

    public boolean isBefore(LocalDate today) {
        return attendanceDate.isBefore(today);
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    @Override
    public int compareTo(Attendance o) {
        return this.attendanceDate.compareTo(o.attendanceDate);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return Objects.equals(attendanceDate, that.attendanceDate) && Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate, attendanceTime);
    }
}
