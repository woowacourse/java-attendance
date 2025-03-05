package domain;

import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Crew {
    private final String name;
    private final Attendance attendanceRecord;

    public Crew(String name) {
        this.name = name;
        this.attendanceRecord = new Attendance();
    }

    public void fillAttend(LocalDateTime attendTime) {
        attendanceRecord.addAttendance(attendTime);
    }

    public AttendanceStatus getStatusByLocalDate(LocalDate findDate) {
        return attendanceRecord.getAttendanceStatus(findDate);
    }

    public AttendanceDate getAttendTimeByLocalDate(LocalDate from) {
        return attendanceRecord.findByLocalDate(from);
    }

    public void editAttendanceTime(LocalDateTime editTime) {
        AttendanceDate editDate = attendanceRecord.findByLocalDate(LocalDate.from(editTime));
        editDate.editLocalDate(editTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crew crew = (Crew) o;
        return Objects.equals(name, crew.name) && Objects.equals(attendanceRecord, crew.attendanceRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, attendanceRecord);
    }

    public String getName() {
        return name;
    }

    public Attendance getAttendanceRecord() {
        return attendanceRecord;
    }
}
