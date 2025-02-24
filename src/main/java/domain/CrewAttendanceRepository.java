package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import util.AttendanceFileParser;

public class CrewAttendanceRepository {
    public static final WorkDate START_DATE = new WorkDate(2024, 12, 2);
    private final Map<String, CrewAttendance> crewAttendance;

    public CrewAttendanceRepository(Map<String, CrewAttendance> crewAttendance) {
        validateUnique(crewAttendance);
        this.crewAttendance = crewAttendance;
    }

    private void validateUnique(Map<String, CrewAttendance> crewAttendance) {
        Set<String> uniqueKeys = new HashSet<>(crewAttendance.keySet());
        if (uniqueKeys.size() != crewAttendance.size()) {
            throw new IllegalArgumentException("중복된 크루 이름이 존재합니다.");
        }
    }

    public static CrewAttendanceRepository of(LocalDate currentDate, String filePath) {
        Map<String, CrewAttendance> initialData = createInitialAttendance(currentDate, filePath);
        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(initialData);
        loadAttendance(crewAttendanceRepository, filePath);

        return crewAttendanceRepository;
    }

    private static Map<String, CrewAttendance> createInitialAttendance(LocalDate currentDate, String filePath) {
        Set<String> crewNames = AttendanceFileParser.loadCrewNames(filePath);
        Map<WorkDate, WorkTime> initialAttendanceRecords = generateInitialAttendance(currentDate);

        return crewNames.stream()
                .collect(Collectors.toMap(
                        name -> name,
                        name -> new CrewAttendance(new Crew(name), new Attendance(initialAttendanceRecords))
                ));
    }

    private static Map<WorkDate, WorkTime> generateInitialAttendance(LocalDate currentDate) {
        Map<WorkDate, WorkTime> attendanceRecords = new HashMap<>();
        WorkDate attendanceDate = START_DATE;

        while (!attendanceDate.isAfter(currentDate)) {
            attendanceRecords.put(attendanceDate, new WorkTime(null, null));
            attendanceDate = attendanceDate.plusDay();
        }

        return attendanceRecords;
    }

    private static void loadAttendance(CrewAttendanceRepository crewAttendanceRepository, String filePath) {
        Map<String, List<WorkDateTime>> attendanceRecords = AttendanceFileParser.loadAttendanceRecords(filePath);

        attendanceRecords.forEach((name, dateTimes) -> {
            crewAttendanceRepository.findByName(name)
                    .ifPresent(crewAttendance -> dateTimes.forEach(crewAttendance::addAttendance));
        });
    }

    public Optional<CrewAttendance> findByName(String name) {
        return Optional.ofNullable(crewAttendance.get(name));
    }

    public List<CrewAttendance> findAll() {
        return new ArrayList<>(crewAttendance.values());
    }
}
