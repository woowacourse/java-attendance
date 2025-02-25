package service;

import domain.*;
import domain.CrewAttendances;
import service.dto.DisenrollmentCheckResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DisenrollmentCheckService {
    private final CrewAttendances crewAttendances;

    public DisenrollmentCheckService(CrewAttendances crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public List<DisenrollmentCheckResponse> getDisenrollmentCheckResult() {
        LocalDate now = AttendanceCustomDate.now().toLocalDate();
        Map<Crew, AttendanceBook> attendances = crewAttendances.findAll();
        return attendances.keySet().stream()
                .filter(crew -> {
                    AttendanceBook attendanceBook = attendances.get(crew);
                    CrewStatus status = CrewStatus.from(
                            attendanceBook.getLateCount(now.withDayOfMonth(1), now),
                            attendanceBook.getAbsenceCount(now.withDayOfMonth(1), now)
                    );
                    return status != CrewStatus.NORMAL;
                })
                .map(crew -> {
                    AttendanceBook attendanceBook = attendances.get(crew);
                    int lateCount = attendanceBook.getLateCount(now.withDayOfMonth(1), now);
                    int absenceCount = attendanceBook.getAbsenceCount(now.withDayOfMonth(1), now);
                    String status = CrewStatus.from(lateCount, absenceCount).getExpression();
                    return new DisenrollmentCheckResponse(crew.getName(), absenceCount, lateCount, status);
                })
                .toList();
    }
}
