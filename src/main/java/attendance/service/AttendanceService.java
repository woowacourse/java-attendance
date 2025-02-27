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

    // TODO: 매개변수명에서 date들 전부 today로 변경
    public AttendanceHistoryResponse attendanceHistory(LocalDate date, AttendanceHistoryRequest request) {
        Crew crew = crews.get(request.name());
        return AttendanceHistoryResponse.of(date, crew);
    }

    public RiskCrewsResponse riskCrews(LocalDate date) {
        List<Crew> riskCrews = crews.getRiskCrews(date);
        return RiskCrewsResponse.of(riskCrews, date);
    }
}
