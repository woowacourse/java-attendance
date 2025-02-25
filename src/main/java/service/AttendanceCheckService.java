package service;

import domain.Attendance;
import domain.AttendanceBook;
import domain.CrewAttendances;
import service.dto.AttendanceRegisterResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceCheckService {
    private final CrewAttendances crewAttendances;

    public AttendanceCheckService(CrewAttendances crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public AttendanceRegisterResponse register(String crewName, LocalDate date, LocalTime time) {
        AttendanceBook attendanceBook = crewAttendances.findByCrewName(crewName);
        Attendance attendance = attendanceBook.register(date, time);
        return new AttendanceRegisterResponse(
                attendance.getDate(),
                attendance.getTime(),
                attendance.getStatus().getExpression()
        );
    }
}
