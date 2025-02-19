package service;

import domain.Attendance;
import domain.AttendanceBook;
import repository.AttendanceRepository;

import java.time.LocalDateTime;

public class AttendanceCheckService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceCheckService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Attendance register(String crewName, LocalDateTime time) {
        AttendanceBook attendanceBook = attendanceRepository.findByCrewName(crewName);
        return attendanceBook.create(time.getDayOfMonth(), time.getHour(), time.getMinute());
    }
}
