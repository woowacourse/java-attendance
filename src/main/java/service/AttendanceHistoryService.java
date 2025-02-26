package service;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.CrewStatus;
import domain.CrewAttendances;

import java.time.LocalDate;
import java.util.Map;

public class AttendanceHistoryService {
    private final CrewAttendances crewAttendances;

    public AttendanceHistoryService(CrewAttendances crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public Map<LocalDate, Attendance> getHistoriesOf(String name, LocalDate startDate, LocalDate endDate) {
        return crewAttendances.getAttendances(name, startDate, endDate);
    }

    public Map<AttendanceStatus, Integer> getAttendanceResultOf(String name, LocalDate startDate, LocalDate endDate) {
        AttendanceBook attendanceBook = crewAttendances.findAttendanceBookByCrewName(name);
        return attendanceBook.calculateAttendanceResult(startDate, endDate);
    }

    public CrewStatus getCrewStatus(String name, LocalDate startDate, LocalDate endDate) {
        AttendanceBook attendanceBook = crewAttendances.findAttendanceBookByCrewName(name);
        int lateCount = attendanceBook.getLateCount(startDate, endDate);
        int absenceCount = attendanceBook.getAbsenceCount(startDate, endDate);
        return CrewStatus.from(lateCount, absenceCount);
    }
}
