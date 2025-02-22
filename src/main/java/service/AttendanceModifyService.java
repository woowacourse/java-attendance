package service;

import domain.Attendance;
import repository.AttendanceRepository;
import service.dto.AttendanceModifyResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceModifyService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceModifyService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceModifyResponse modify(String name, LocalDate date, LocalTime time) {
        Attendance beforeAttendance = attendanceRepository.findByCrewAndDate(name, date);
        Attendance afterAttendance = beforeAttendance.modify(time);
        attendanceRepository.modifyAttendance(name, date, time);
        return new AttendanceModifyResponse(
                date,
                beforeAttendance.getTime(),
                beforeAttendance.getStatus().getExpression(),
                afterAttendance.getTime(),
                afterAttendance.getStatus().getExpression()
        );
    }
}
