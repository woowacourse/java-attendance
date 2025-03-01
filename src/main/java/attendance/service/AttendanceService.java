package attendance.service;

import java.time.LocalDate;
import java.util.List;

import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.dto.AttendanceHistoryRequest;
import attendance.dto.AttendanceHistoryResponse;
import attendance.dto.AttendanceRequest;
import attendance.dto.AttendanceResponse;
import attendance.dto.ModifyAttendanceRequest;
import attendance.dto.ModifyAttendanceResponse;
import attendance.dto.RiskCrewsResponse;

public class AttendanceService {

    private final Crews crews = Crews.fromFile();

    public AttendanceResponse attendance(LocalDate today, AttendanceRequest request) {
        Crew crew = crews.get(request.name());
        crew.attendance(today, request.time());
        return AttendanceResponse.of(crew, today);
    }

    public ModifyAttendanceResponse modifyAttendance(ModifyAttendanceRequest request) {
        Crew crew = crews.get(request.name());
        ModifyAttendanceResponse.InnerAttendance before = getAttendanceResult(request.date(), crew);
        crew.modifyAttendance(request.date(), request.time());
        ModifyAttendanceResponse.InnerAttendance after = getAttendanceResult(request.date(), crew);
        return new ModifyAttendanceResponse(request.date(), before, after);
    }

    private static ModifyAttendanceResponse.InnerAttendance getAttendanceResult(LocalDate today, Crew crew) {
        return new ModifyAttendanceResponse.InnerAttendance(
            crew.getAttendanceTimeOf(today),
            crew.getAttendanceStatusOf(today)
        );
    }

    public AttendanceHistoryResponse attendanceHistory(LocalDate today, AttendanceHistoryRequest request) {
        Crew crew = crews.get(request.name());
        return AttendanceHistoryResponse.of(today, crew);
    }

    public RiskCrewsResponse riskCrews(LocalDate today) {
        List<Crew> riskCrews = crews.getRiskCrews(today);
        return RiskCrewsResponse.of(riskCrews, today);
    }
}
