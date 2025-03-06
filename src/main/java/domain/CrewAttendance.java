package domain;

import java.util.Objects;

public record CrewAttendance(
        String crewName,
        AttendanceRecords attendanceRecords
) {

    public AttendanceRecords getAttendanceRecords() {
        return attendanceRecords;
    }

    public static CrewAttendance create(String crewName, AttendanceRecords attendanceRecords) {
        return new CrewAttendance(crewName, attendanceRecords);
    }

    public boolean equalName(String crewName) {
        return Objects.equals(this.crewName, crewName);
    }
}
