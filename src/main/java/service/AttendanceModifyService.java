package service;

import domain.Attendance;
import domain.CrewAttendances;
import service.dto.AttendanceModifyResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceModifyService {
    private final CrewAttendances crewAttendances;

    public AttendanceModifyService(CrewAttendances crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public AttendanceModifyResponse modify(String name, LocalDate date, LocalTime time) {
        Attendance beforeAttendance = crewAttendances.findAttendanceByCrewAndDate(name, date);
        Attendance afterAttendance = crewAttendances.modifyAttendance(name, date, time);
        return new AttendanceModifyResponse(
                date,
                beforeAttendance.getTime(),
                beforeAttendance.getStatus().getExpression(),
                afterAttendance.getTime(),
                afterAttendance.getStatus().getExpression()
        );
    }
}
