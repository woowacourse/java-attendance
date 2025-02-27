package attendance.service;

import java.time.LocalDate;

import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.dto.AttendanceRequest;
import attendance.dto.AttendanceResponse;

public class AttendanceService {

    private final Crews crews = Crews.fromFile();

    public AttendanceResponse attendance(LocalDate date, AttendanceRequest request) {
        Crew crew = crews.get(request.name());
        crew.attendance(date, request.time());
        return AttendanceResponse.of(crew, date);
    }
}
