package service;

import domain.Attendance;
import domain.AttendanceBook;
import repository.AttendanceRepository;
import service.dto.AttendanceRegisterResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceCheckService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceCheckService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceRegisterResponse register(String crewName, LocalDate date, LocalTime time) {
        AttendanceBook attendanceBook = attendanceRepository.findByCrewName(crewName);
        Attendance attendance = attendanceBook.create(date, time);
        return new AttendanceRegisterResponse(
                attendance.getDate(),
                attendance.getTime(),
                attendance.getStatus().getExpression()
        );
    }
}
