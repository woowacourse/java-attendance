package service;

import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceStatus;
import domain.crew.CrewStatus;
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
        return attendanceBook.getAllAttendance(date);
    }

    public Map<AttendanceStatus, Integer> getAttendanceResultOf(String name, LocalDate date) {
        AttendanceBook attendanceBook = attendanceRepository.findByCrewName(name);
        return attendanceBook.calculateAttendanceResult(date);
    }

    public CrewStatus getCrewStatus(String name, LocalDate date) {
        AttendanceBook attendanceBook = attendanceRepository.findByCrewName(name);
        int lateCount = attendanceBook.getLateCountAt(date);
        int absenceCount = attendanceBook.getAbsenceCountAt(date);
        return CrewStatus.from(lateCount, absenceCount);
    }
}
