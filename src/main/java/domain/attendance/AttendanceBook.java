package domain.attendance;

import domain.crew.Crew;
import domain.crew.CrewStatus;
import exception.ErrorException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class AttendanceBook {

    private final Map<Crew, AttendanceLogs> attendanceBook;

    public AttendanceBook(Map<Crew, AttendanceLogs> attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public AttendanceLog findCrewAttendanceLog(String crewName, LocalDate attendDate) {
        Crew crew = findCrew(crewName);
        AttendanceLogs attendanceLogs = attendanceBook.get(crew);
        return attendanceLogs.findAttendanceLog(attendDate);
    }

    public AttendanceLogs findCrewAttendanceLogs(String crewName) {
        Crew crew = findCrew(crewName);
        return attendanceBook.get(crew);
    }

    public AttendanceLog registerCrewAttendanceLog(String crewName, LocalDateTime attendDateTime) {
        AttendanceLogs crewAttendanceLogs = findCrewAttendanceLogs(crewName);
        return crewAttendanceLogs.registerLog(attendDateTime);
    }

    public AttendanceLog editCrewAttendanceLog(String crewName, LocalDate editDate, LocalTime editTime) {
        AttendanceLogs crewAttendanceLogs = findCrewAttendanceLogs(crewName);
        return crewAttendanceLogs.editLog(editDate, editTime);
    }

    public List<Map.Entry<Crew, AttendanceResult>> findExpulsionRiskCrews() {
        Map<Crew, AttendanceResult> attendanceResults = new HashMap<>();
        for (Crew crew : attendanceBook.keySet()) {
            AttendanceResult crewAttendanceResult = calculateCrewAttendanceResult(crew.getName());
            addCrewAttendanceResult(attendanceResults, crew, crewAttendanceResult);
        }
        return sortAttendanceResults(attendanceResults);
    }

    private void addCrewAttendanceResult(Map<Crew, AttendanceResult> attendanceResults, Crew crew, AttendanceResult crewAttendanceResult) {
        if (crewAttendanceResult.getCrewStatus() != CrewStatus.PASS) {
            attendanceResults.put(crew, crewAttendanceResult);
        }
    }

    public Crew findCrew(String crewName) {
        return attendanceBook.keySet().stream()
                .filter(crew -> crew.isCrew(crewName))
                .findFirst()
                .orElseThrow(() -> new ErrorException("등록되지 않은 닉네임입니다."));
    }

    private AttendanceResult calculateCrewAttendanceResult(String crewName) {
        Crew crew = findCrew(crewName);
        AttendanceLogs crewAttendanceLogs = attendanceBook.get(crew);
        Map<AttendanceStatus, Integer> crewAttendanceStatuses = crewAttendanceLogs.calculateLogsStatus();
        return new AttendanceResult(crewAttendanceStatuses);
    }

    private List<Entry<Crew, AttendanceResult>> sortAttendanceResults(Map<Crew, AttendanceResult> attendanceResults) {
        return attendanceResults.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<Crew, AttendanceResult> entry) -> calculatePenaltyCount(entry.getValue()))
                        .reversed()
                        .thenComparing(entry -> entry.getKey().getName()))
                .collect(Collectors.toList());
    }

    private int calculatePenaltyCount(AttendanceResult attendanceResult) {
        Map<AttendanceStatus, Integer> attendanceStatus = attendanceResult.getAttendanceStatus();
        int lateCount = attendanceStatus.getOrDefault(AttendanceStatus.LATE, 0);
        int absentCount = attendanceStatus.getOrDefault(AttendanceStatus.ABSENT, 0);
        return (lateCount / 3) + absentCount;
    }
}
