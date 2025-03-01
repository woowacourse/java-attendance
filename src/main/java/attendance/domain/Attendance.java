package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private final String crewName;
    private final Time attendanceTime;

    public Attendance(String crewName, final Time attendanceTime) {
        this.crewName = crewName;
        this.attendanceTime = attendanceTime;
    }

    public Attendance modifyAttendanceTime(LocalTime modifyTime) {
        attendanceTime.modify(modifyTime);
        return this;
    }

    public boolean isSameNameAndLocalDate(String crewName, LocalDate localDate) {
        return this.crewName.equals(crewName) && attendanceTime.isSameLocalDate(localDate);
    }

    public boolean isSameCrewName(String crewName) {
        return this.crewName.equals(crewName);
    }

    public boolean isSameYearAndMonth(final int year, final int month) {
        return attendanceTime.isSameYearAndMonth(year, month);
    }

    public AttendanceStatus checkStatus() {
        return attendanceTime.getStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(crewName, that.crewName) &&
                attendanceTime.isSameLocalDate(that.attendanceTime.getLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(crewName, attendanceTime.getLocalDate());
    }

    public Time getAttendanceTime() {
        return attendanceTime;
    }
}
