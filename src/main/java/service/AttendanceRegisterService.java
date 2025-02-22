package service;

import domain.attendance.Attendance;
import domain.attendance.AttendanceBook;
import domain.crew.Crew;
import repository.AttendanceRepository;

import java.time.LocalDateTime;

public class AttendanceRegisterService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceRegisterService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Attendance register(Crew crew, LocalDateTime time) {
        AttendanceBook attendanceBook = attendanceRepository.findByCrew(crew);
        return attendanceBook.create(time.getDayOfMonth(), time.getHour(), time.getMinute());
    }
}
