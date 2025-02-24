package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
        this.crewAttendance = new HashMap<>(crewAttendance);
    }

    public static CrewAttendanceRepository of(LocalDate currentDate, String filePath) {
        Map<String, CrewAttendance> initialData = createInitialAttendance(currentDate, filePath);
        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(initialData);
        loadAttendance(crewAttendanceRepository, currentDate, filePath);

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

    private static void loadAttendance(CrewAttendanceRepository crewAttendanceRepository, LocalDate currentDate,
                                       String filePath) {
        Map<String, List<WorkDateTime>> attendanceRecords = AttendanceFileParser.loadAttendanceRecords(filePath);

        attendanceRecords.forEach((name, workDateTimes) -> {
            crewAttendanceRepository.findByName(name)
                    .ifPresent(crewAttendance -> workDateTimes.forEach(workDateTime -> {
                        WorkDate workDate = workDateTime.getDate();

                        if (!workDate.isAfter(currentDate)) {
                            crewAttendance.addAttendance(workDateTime);
                        }
                    }));
        });
    }

    public Optional<CrewAttendance> findByName(String name) {
        return Optional.ofNullable(crewAttendance.get(name));
    }

    public List<CrewAttendance> findAll() {
        return new ArrayList<>(crewAttendance.values());
    }
}
