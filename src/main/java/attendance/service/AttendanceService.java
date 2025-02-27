package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendanceReader;
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.dto.AttendanceFileDto;
import attendance.dto.AttendanceRemarkDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceService {

    private final AttendanceReader reader;
    private Attendances attendances;

    public AttendanceService(AttendanceReader reader) {
        this.reader = reader;
        readAttendance();
    }

    private void readAttendance() {
        List<AttendanceFileDto> read = reader.read();
        Attendances newAttendances = new Attendances();
        for (AttendanceFileDto attendanceFileDto : read) {
            newAttendances.addAttendance(attendanceFileDto.name(),
                new Attendance(attendanceFileDto.attendanceDate(), attendanceFileDto.attendanceTime()));
        }
        this.attendances = newAttendances;
    }

    public void validateNameExists(String name) {
        attendances.validateNameExists(name);
    }

    public boolean hasAttendance(String name, LocalDate attendanceDate) {
        return attendances.hasAttendance(name, attendanceDate);
    }

    public AttendanceRemarkDto remarkAttendance(String name, LocalDate attendanceDate, LocalTime attendanceTime) {
        attendances.addAttendance(name, new Attendance(attendanceDate, attendanceTime));
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(attendanceDate, attendanceTime);
        return AttendanceRemarkDto.of(attendanceDate, attendanceTime, attendanceStatus);
    }
}
