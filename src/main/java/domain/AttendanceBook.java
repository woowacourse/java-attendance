package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBook {

    private static final String NOT_REGISTERED_CREW_ERROR_MESSAGE = "등록되지 않은 크루입니다.";
    private static final String ALREADY_ATTENDED_ERROR_MESSAGE = "이미 출석 기록이 있으므로 수정만 가능합니다.";
    private static final String NOT_ATTENDED_ERROR_MESSAGE = "수정전 먼저 출석을 확인을 해야합니다.";

    private final Map<String, Crew> crewRecords;

    public AttendanceBook(Map<String, List<LocalDateTime>> attendanceData) {
        this.crewRecords = createCrews(attendanceData);
    }

    public int countCrew() {
        return crewRecords.size();
    }

    public Crew findCrewByName(String name) {
        validateRegisteredCrew(name);

        return crewRecords.get(name);
    }

    public DailyRecord saveAttendanceRecord(String name, LocalDateTime dateTime) {
        validateRegisteredCrew(name);
        validateAlreadyAttended(name, dateTime);

        Crew crew = crewRecords.get(name);
        return crew.addDailyRecord(dateTime);
    }

    public DailyRecord editAttendanceRecord(String name, LocalDateTime editedDateTime) {
        validateRegisteredCrew(name);
        validateAttendedDate(name, editedDateTime);

        Crew crew = crewRecords.get(name);
        return crew.updateDailyRecord(editedDateTime);
    }

    public Map<String, Crew> findWarningCrew(LocalDate startDate, LocalDate endDate) {
        return crewRecords.entrySet().stream()
            .filter(entry -> isWarningCrew(entry.getValue(), startDate, endDate))
            .sorted(createCrewComparator(startDate, endDate))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (existing, replacement) -> existing,
                LinkedHashMap::new
            ));
    }

    private boolean isWarningCrew(Crew crew, LocalDate startDate, LocalDate endDate) {
        Map<AttendanceStatus, Integer> statistics = getAttendanceStatistics
            (crew, startDate, endDate);
        return Penalty.isNotPass
            (statistics.get(AttendanceStatus.LATE), statistics.get(AttendanceStatus.ABSENT));
    }

    private Comparator<Map.Entry<String, Crew>> createCrewComparator
        (LocalDate startDate, LocalDate endDate) {
        return Comparator
            .comparingInt((Map.Entry<String, Crew> entry) -> {
                Map<AttendanceStatus, Integer> stats = getAttendanceStatistics
                    (entry.getValue(), startDate, endDate);
                return (stats.get(AttendanceStatus.LATE) / 3) + stats.get(AttendanceStatus.ABSENT);
            }).reversed()
            .thenComparing((Map.Entry<String, Crew> entry) -> {
                Map<AttendanceStatus, Integer> stats = getAttendanceStatistics
                    (entry.getValue(), startDate, endDate);
                return stats.get(AttendanceStatus.LATE) % 3;
            }, Comparator.reverseOrder())
            .thenComparing((Map.Entry<String, Crew> entry) -> {
                Map<AttendanceStatus, Integer> stats = getAttendanceStatistics
                    (entry.getValue(), startDate, endDate);
                return stats.get(AttendanceStatus.ABSENT);
            }, Comparator.reverseOrder())
            .thenComparing(Map.Entry::getKey);
    }

    private Map<AttendanceStatus, Integer> getAttendanceStatistics
        (Crew crew, LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, DailyRecord> records = crew.findRecordsOfDate(startDate, endDate);
        return AttendanceStatus.countStatus(records);
    }

    private Map<String, Crew> createCrews(Map<String, List<LocalDateTime>> attendanceData) {
        Map<String, Crew> records = new HashMap<>();
        for (String name : attendanceData.keySet()) {
            List<LocalDateTime> dailyRecords = attendanceData.get(name);
            records.put(name, new Crew());
            records.get(name).initializeDailyRecords(dailyRecords);
        }
        return records;
    }

    private void validateRegisteredCrew(String name) {
        if (!crewRecords.containsKey(name)) {
            throw new IllegalArgumentException(NOT_REGISTERED_CREW_ERROR_MESSAGE);
        }
    }

    private void validateAlreadyAttended(String name, LocalDateTime dateTime) {
        Crew crew = crewRecords.get(name);
        if (crew.hasDate(dateTime.toLocalDate())) {
            throw new IllegalArgumentException(ALREADY_ATTENDED_ERROR_MESSAGE);
        }
    }

    private void validateAttendedDate(String name, LocalDateTime editedDateTime) {
        Crew crew = crewRecords.get(name);
        if (!crew.hasDate(editedDateTime.toLocalDate())) {
            throw new IllegalArgumentException(NOT_ATTENDED_ERROR_MESSAGE);
        }
    }
}