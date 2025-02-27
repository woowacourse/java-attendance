package attendance.service;

import java.time.LocalDate;

import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.dto.AttendanceRequest;
import attendance.dto.AttendanceResponse;
import attendance.dto.ModifyAttendanceRequest;
import attendance.dto.ModifyAttendanceResponse;

public class AttendanceService {

    private final Crews crews = Crews.fromFile();

    public AttendanceResponse attendance(LocalDate date, AttendanceRequest request) {
        Crew crew = crews.get(request.name());
        crew.attendance(date, request.time());
        return AttendanceResponse.of(crew, date);
    }

    public ModifyAttendanceResponse modifyAttendance(ModifyAttendanceRequest request) {
        Crew crew = crews.get(request.name());
        ModifyAttendanceResponse.InnerAttendance before = getAttendanceResult(request.date(), crew);
        crew.modifyAttendance(request.date(), request.time());
        ModifyAttendanceResponse.InnerAttendance after = getAttendanceResult(request.date(), crew);
        return new ModifyAttendanceResponse(request.date(), before, after);
    }

    private static ModifyAttendanceResponse.InnerAttendance getAttendanceResult(LocalDate date, Crew crew) {
        return new ModifyAttendanceResponse.InnerAttendance(
            crew.getAttendanceTimeOf(date),
            crew.getAttendanceStatusOf(date)
        );
    }
}
