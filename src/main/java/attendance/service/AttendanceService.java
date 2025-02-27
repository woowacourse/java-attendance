package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendanceReader;
import attendance.domain.Attendances;
import attendance.dto.AttendanceFileDto;

import java.util.List;

public class AttendanceService {

    private final AttendanceReader reader;
    private Attendances attendances;

    public AttendanceService(AttendanceReader reader) {
        this.reader = reader;
    }

    public void readAttendance() {
        List<AttendanceFileDto> read = reader.read();
        Attendances newAttendances = new Attendances();
        for (AttendanceFileDto attendanceFileDto : read) {
            newAttendances.addAttendance(attendanceFileDto.name(),
                new Attendance(attendanceFileDto.attendanceDate(), attendanceFileDto.attendanceTime()));
        }
        this.attendances = newAttendances;
    }
}
