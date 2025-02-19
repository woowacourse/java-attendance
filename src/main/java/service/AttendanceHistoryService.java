package service;

import domain.AttendanceBook;
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

    public Map<String, Integer> getAttendanceResultOf(String name, LocalDate date) {
        return null;
    }
}
