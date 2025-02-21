package service;

import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.CrewStatus;
import repository.AttendanceRepository;
import service.dto.AttendanceHistoryResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AttendanceHistoryService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceHistoryService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public List<AttendanceHistoryResponse> getHistoriesOf(String name, LocalDate date) {
        AttendanceBook attendanceBook = attendanceRepository.findByCrewName(name);
        return attendanceBook.getAllAttendanceHistory(date.getDayOfMonth())
                .stream()
                .map(attendanceHistory -> new AttendanceHistoryResponse(
                        attendanceHistory.date(),
                        attendanceHistory.time(),
                        attendanceHistory.status())
                )
                .toList();
    }

    public Map<AttendanceStatus, Integer> getAttendanceResultOf(String name, LocalDate date) {
        AttendanceBook attendanceBook = attendanceRepository.findByCrewName(name);
        return attendanceBook.calculateAttendanceResult(date.getDayOfMonth());
    }

    public CrewStatus getCrewStatus(String name, LocalDate date) {
        AttendanceBook attendanceBook = attendanceRepository.findByCrewName(name);
        int lateCount = attendanceBook.getLateCount(date.getDayOfMonth());
        int absenceCount = attendanceBook.getAbsenceCount(date.getDayOfMonth());
        return CrewStatus.from(lateCount, absenceCount);
    }
}
