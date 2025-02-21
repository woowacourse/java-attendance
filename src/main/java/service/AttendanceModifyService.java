package service;

import domain.attendance.Attendance;
import exception.AttendanceNotExistException;
import repository.AttendanceRepository;
import service.dto.AttendanceModifyResponse;

public class AttendanceModifyService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceModifyService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceModifyResponse modify(String name, int date, int newHour, int newMinutes) {
        Attendance beforeAttendance = attendanceRepository.findByCrewAndDate(name, date)
                .orElseThrow(AttendanceNotExistException::new);
        Attendance afterAttendance = beforeAttendance.createModifiedAttendance(newHour, newMinutes);
        attendanceRepository.modifyAttendance(name, beforeAttendance, afterAttendance);
        return new AttendanceModifyResponse(
                beforeAttendance.getTime(),
                beforeAttendance.getStatus(),
                afterAttendance.getTime(),
                afterAttendance.getStatus()
        );
    }
}
