package service;

import domain.attendance.Attendance;
import domain.attendance.AttendanceBook;
import repository.AttendanceRepository;

import java.time.LocalDateTime;

public class AttendanceRegisterService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceRegisterService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Attendance register(String crewName, LocalDateTime time) {
        AttendanceBook attendanceBook = attendanceRepository.findByCrewName(crewName);
        return attendanceBook.create(time.getDayOfMonth(), time.getHour(), time.getMinute());
    }
}
