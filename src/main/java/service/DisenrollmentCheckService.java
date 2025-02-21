package service;

import domain.*;
import repository.AttendanceRepository;
import service.dto.DisenrollmentCheckResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DisenrollmentCheckService {
    private final AttendanceRepository attendanceRepository;

    public DisenrollmentCheckService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public List<DisenrollmentCheckResponse> getDisenrollmentCheckResult() {
        LocalDate now = AttendanceCustomDate.now().toLocalDate();
        Map<Crew, AttendanceBook> attendances = attendanceRepository.findAll();
        return attendances.keySet().stream()
                .filter(crew -> {
                    AttendanceBook attendanceBook = attendances.get(crew);
                    CrewStatus status = CrewStatus.from(
                            attendanceBook.getLateCount(now.getDayOfMonth()),
                            attendanceBook.getAbsenceCount(now.getDayOfMonth())
                    );
                    return status != CrewStatus.NORMAL;
                })
                .map(crew -> {
                    AttendanceBook attendanceBook = attendances.get(crew);
                    int lateCount = attendanceBook.getLateCount(now.getDayOfMonth());
                    int absenceCount = attendanceBook.getAbsenceCount(now.getDayOfMonth());
                    String status = CrewStatus.from(lateCount, absenceCount).getExpression();
                    return new DisenrollmentCheckResponse(crew.getName(), absenceCount, lateCount, status);
                })
                .toList();
    }
}
