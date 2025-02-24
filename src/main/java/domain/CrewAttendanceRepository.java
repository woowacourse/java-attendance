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
    private final Map<String, CrewAttendance> crewAttendance;
    private static final LocalDate START_DATE = LocalDate.of(2024, 12, 2);

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
        validateDecember2024(currentDate);

        Map<String, CrewAttendance> initialData = createInitialAttendance(currentDate, filePath);
        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(initialData);
        loadAttendance(crewAttendanceRepository, filePath);

        return crewAttendanceRepository;
    }

    private static void validateDecember2024(LocalDate currentDate) {
        if (currentDate.getYear() != 2024 || currentDate.getMonthValue() != 12) {
            throw new IllegalArgumentException("입력된 날짜는 2024년 12월이어야 합니다.");
        }
    }

    private static Map<String, CrewAttendance> createInitialAttendance(LocalDate currentDate, String filePath) {
        Set<String> crewNames = AttendanceFileParser.loadCrewNames(filePath);
        Map<Date, Time> initialAttendanceRecords = generateInitialAttendance(currentDate);

        return crewNames.stream()
                .collect(Collectors.toMap(
                        name -> name,
                        name -> new CrewAttendance(new Crew(name), new Attendance(initialAttendanceRecords))
                ));
    }

    private static Map<Date, Time> generateInitialAttendance(LocalDate currentDate) {
        Map<Date, Time> attendanceRecords = new HashMap<>();
        LocalDate attendanceDate = START_DATE;

        while (!attendanceDate.isAfter(currentDate)) {
            addNonHolidayDate(attendanceRecords, attendanceDate);
            attendanceDate = attendanceDate.plusDays(1);
        }

        return attendanceRecords;
    }

    private static void addNonHolidayDate(Map<Date, Time> attendanceRecords, LocalDate localDate) {
        if (Date.isWeekend(localDate) || Date.isPublicHoliday(localDate)) {
            return;
        }

        Date date = Date.from(localDate);
        attendanceRecords.put(date, new Time(null, null));
    }

    private static void loadAttendance(CrewAttendanceRepository crewAttendanceRepository, String filePath) {
        Map<String, List<DateTime>> attendanceRecords = AttendanceFileParser.loadAttendanceRecords(filePath);

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
