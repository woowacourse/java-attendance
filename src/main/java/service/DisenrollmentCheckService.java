package service;

import domain.attendance.AttendanceBook;
import domain.crew.Crew;
import domain.crew.CrewStatus;
import domain.date.CustomDate;
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
        LocalDate now = CustomDate.now().toLocalDate();
        Map<Crew, AttendanceBook> attendances = attendanceRepository.findAll();
        List<Map.Entry<Crew, AttendanceBook>> disenrollmentAttendances = attendances.entrySet().stream()
                .filter(entry -> {
                    AttendanceBook attendanceBook = entry.getValue();
                    CrewStatus status = CrewStatus.from(
                            attendanceBook.getLateCountUntilBefore(now),
                            attendanceBook.getAbsenceCountUntilBefore(now)
                    );
                    return status != CrewStatus.NORMAL;
                }).toList();

        return disenrollmentAttendances.stream()
                .map(entry -> {
                    String name = entry.getKey().getName();
                    AttendanceBook attendanceBook = entry.getValue();
                    int absenceCount = attendanceBook.getAbsenceCountUntilBefore(now);
                    int lateCount = attendanceBook.getLateCountUntilBefore(now);
                    int convertedAbsenceCount = absenceCount + lateCount / 3;
                    String status = CrewStatus.from(lateCount, absenceCount).getExpression();
                    return new DisenrollmentCheckResponse(name, absenceCount, lateCount, convertedAbsenceCount, status);
                }).toList();
    }
}
