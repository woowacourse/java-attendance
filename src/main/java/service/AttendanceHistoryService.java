package service;

import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceStatus;
import domain.crew.Crew;
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

    public List<AttendanceHistoryResponse> getHistoriesOf(Crew crew, LocalDate date) {
        AttendanceBook attendanceBook = attendanceRepository.findByCrew(crew);
        return attendanceBook.getAllAttendanceUntilBefore(date);
    }

    public Map<AttendanceStatus, Integer> getAttendanceResultOf(Crew crew, LocalDate date) {
        AttendanceBook attendanceBook = attendanceRepository.findByCrew(crew);
        return attendanceBook.findAttendanceResultUntilBefore(date);
    }

    public CrewStatus getCrewStatus(Crew crew, LocalDate date) {
        AttendanceBook attendanceBook = attendanceRepository.findByCrew(crew);
        int lateCount = attendanceBook.getLateCountUntilBefore(date);
        int absenceCount = attendanceBook.getAbsenceCountUntilBefore(date);
        return CrewStatus.from(lateCount, absenceCount);
    }
}
