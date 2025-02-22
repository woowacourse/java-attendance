package service;

import domain.attendance.Attendance;
import domain.crew.Crew;
import exception.sub.AttendanceNotExistException;
import repository.AttendanceRepository;
import service.dto.AttendanceModifyResponse;

public class AttendanceModifyService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceModifyService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceModifyResponse modify(Crew crew, int date, int newHour, int newMinutes) {
        Attendance beforeAttendance = attendanceRepository.findByCrewAndDate(crew, date)
                .orElseThrow(AttendanceNotExistException::new);
        Attendance afterAttendance = beforeAttendance.createModifiedAttendance(newHour, newMinutes);
        attendanceRepository.modifyAttendance(crew, beforeAttendance, afterAttendance);
        return new AttendanceModifyResponse(
                beforeAttendance.getTime(),
                beforeAttendance.getStatus(),
                afterAttendance.getTime(),
                afterAttendance.getStatus()
        );
    }
}
