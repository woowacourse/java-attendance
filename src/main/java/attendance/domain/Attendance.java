package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private final CrewName crewName;
    private final AttendanceDate attendanceDate;
    private AttendanceTime attendanceTime;
    private AttendanceType attendanceType;

    public Attendance(CrewName crewName, AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.crewName = crewName;
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.attendanceType = AttendanceType.of(attendanceDate, attendanceTime);
    }

    public boolean hasSameCrewName(final String crewName) {
        return this.crewName.getCrewName().equals(crewName);
    }

    public boolean hasSameAttendanceDate(final LocalDate attendanceDate) {
        return this.attendanceDate.getAttendanceDate().equals(attendanceDate);
    }

    public void modifyAttendance(final AttendanceTime attendanceTime) {
        this.attendanceTime = attendanceTime;
        this.attendanceType = AttendanceType.of(attendanceDate, attendanceTime);
    }

    public Attendance copyAttendanceInstance() {
        CrewName newCrewName = new CrewName(crewName.getCrewName());
        AttendanceDate newAttendanceDate = new AttendanceDate(attendanceDate.getAttendanceDate());
        AttendanceTime newAttendanceTime = new AttendanceTime(attendanceTime.getAttendanceTime());
        return new Attendance(newCrewName, newAttendanceDate, newAttendanceTime);
    }

    public String getCrewName() {
        return crewName.getCrewName();
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate.getAttendanceDate();
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime.getAttendanceTime();
    }

    public String getAttendanceType() {
        return attendanceType.toString();
    }
}
