package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CrewAttendances {

    private static final DateTimeFormatter FILE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final String attendanceFileDelimiter = ",";
    private final Map<Crew, Attendances> crewAttendances;

    public CrewAttendances() {
        crewAttendances = new HashMap<>();
    }

    public void initializeCrewAttendances(List<String> previousAttendanceLines) {
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = createAttendanceDateTimes(previousAttendanceLines);
        createCrewAttendances(crewAttendanceDateTimes);
    }

    private Map<Crew, List<LocalDateTime>> createAttendanceDateTimes(final List<String> previousAttendanceLines) {
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = new HashMap<>();
        for (String line : previousAttendanceLines) {
            String[] tokens = line.split(attendanceFileDelimiter);
            Crew crew = new Crew(tokens[0]);
            LocalDateTime attendanceDateTime = LocalDateTime.parse(tokens[1], FILE_DATE_TIME_FORMATTER);
            crewAttendanceDateTimes.computeIfAbsent(crew, value -> new ArrayList<>()).add(attendanceDateTime);
        }
        return crewAttendanceDateTimes;
    }

    private void createCrewAttendances(final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes) {
        for (Crew crew : crewAttendanceDateTimes.keySet()) {
            Attendances attendances = new Attendances(crewAttendanceDateTimes.get(crew), LocalDateTime.now());
            crewAttendances.put(crew, attendances);
        }
    }

    public boolean isRegisteredCrew(Crew crew) {
        if (crewAttendances.containsKey(crew)) {
            return true;
        }
        return false;
    }

    public void addAttendance(Crew crew, Attendance attendance) {
        Attendances attendances = crewAttendances.get(crew);
        validateDuplicateAttendance(attendances, attendance.getAttendanceDateTime().toLocalDate());
        attendances.addAttendance(attendance);
    }

    private void validateDuplicateAttendance(Attendances attendances, LocalDate date) {
        if (attendances.existsByLocalDate(date)) {
            throw new IllegalStateException("이미 해당 날짜에 출석했습니다.");
        }
    }

    public Attendance findAttendanceByCrewAndLocalDate(Crew crew, LocalDate date) {
        Attendances attendances = crewAttendances.get(crew);
        return attendances.findAttendanceByLocalDate(date);
    }

    public void removeAttendance(Crew crew, Attendance attendance) {
        Attendances attendances = crewAttendances.get(crew);
        attendances.remove(attendance);
    }

    public List<LocalDateTime> findCrewAllAttendanceLocalDateTime(Crew crew) {
        return crewAttendances.get(crew).getAttendances().stream()
                .map(Attendance::getAttendanceDateTime)
                .toList();
    }

    public Attendances getAttendancesByCrew(Crew crew) {
        return crewAttendances.get(crew);
    }

    public Set<Map.Entry<Crew, Attendances>> getCrewAttendanceEntrySet() {
        return crewAttendances.entrySet();
    }
}
