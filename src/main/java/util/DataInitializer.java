package util;

import domain.Attendance;
import domain.Crew;
import domain.CrewAttendance;
import domain.CrewAttendanceRepository;
import domain.Date;
import domain.DateTime;
import domain.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataInitializer {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDate START_DATE = LocalDate.of(2024, 12, 2);

    public void initialize(LocalDateTime todayLocalDateTime, String filePath) {
        validateDecember2024(todayLocalDateTime);
        Set<String> crewNames = extractCrewNames(filePath);
        Map<Date, Time> initialDateTimes = createInitialAttendanceMap(todayLocalDateTime);
        registerCrewData(crewNames, initialDateTimes, filePath, todayLocalDateTime);
    }

    private void validateDecember2024(LocalDateTime todayLocalDateTime) {
        if (todayLocalDateTime.getYear() != 2024 || todayLocalDateTime.getMonthValue() != 12) {
            throw new IllegalArgumentException("입력된 날짜는 2024년 12월이어야 합니다");
        }
    }

    private Set<String> extractCrewNames(String filePath) {
        List<String> lines = FileDataLoader.loadLines(filePath);
        Set<String> crewNames = new HashSet<>();

        lines.stream()
                .skip(1)
                .map(this::extractCrewName)
                .forEach(crewNames::add);

        return crewNames;
    }

    private String extractCrewName(String line) {
        return splitItems(line)[0];
    }

    private Map<Date, Time> createInitialAttendanceMap(LocalDateTime todayLocalDateTime) {
        Map<Date, Time> dateTimes = new HashMap<>();
        LocalDate currentDate = START_DATE;

        while (!currentDate.isAfter(todayLocalDateTime.toLocalDate())) {
            addNonHolidayDate(dateTimes, currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return dateTimes;
    }

    private void addNonHolidayDate(Map<Date, Time> dateTimes, LocalDate currentDate) {
        Date date = new Date(currentDate);
        if (!date.isHoliday()) {
            dateTimes.put(date, new Time(null, null));
        }
    }

    private void registerCrewData(Set<String> crewNames, Map<Date, Time> initialDateTimes,
                                  String filePath, LocalDateTime todayLocalDateTime) {
        CrewAttendanceRepository crewAttendanceRepository = CrewAttendanceRepository.getInstance();

        crewNames.forEach(name -> {
            Crew crew = new Crew(name);
            CrewAttendance crewAttendance = new CrewAttendance(crew, new Attendance(new HashMap<>(initialDateTimes)));

            loadAndApplyAttendance(crewAttendance, filePath, todayLocalDateTime);
            crewAttendanceRepository.save(crewAttendance);
        });
    }

    private void loadAndApplyAttendance(CrewAttendance crewAttendance, String filePath,
                                        LocalDateTime todayLocalDateTime) {
        List<String> lines = FileDataLoader.loadLines(filePath);

        lines.stream()
                .skip(1)
                .map(this::parseAttendanceData)
                .filter(data -> isValidAttendance(data, crewAttendance, todayLocalDateTime))
                .forEach(data -> crewAttendance.addAttendance(data.dateTime));
    }

    private AttendanceData parseAttendanceData(String line) {
        String[] items = splitItems(line);
        validateSize(items);
        return new AttendanceData(items[0], parseToDate(items[1]));
    }

    private boolean isValidAttendance(AttendanceData data, CrewAttendance crewAttendance,
                                      LocalDateTime todayLocalDateTime) {
        return crewAttendance.getCrew().getName().equals(data.name) &&
                !data.dateTime.getDate().isAfter(new Date(todayLocalDateTime.toLocalDate())) &&
                !data.dateTime.getDate().isHoliday();
    }

    private String[] splitItems(String input) {
        return Arrays.stream(input.split(",", -1))
                .map(String::trim)
                .toArray(String[]::new);
    }

    private void validateSize(String[] inputs) {
        if (inputs.length != 2) {
            throw new IllegalArgumentException("잘못된 데이터 형식: " + Arrays.toString(inputs));
        }
    }

    private LocalDateTime parseToDate(String dateString) {
        try {
            return LocalDateTime.parse(dateString, DEFAULT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 날짜 형식: " + dateString);
        }
    }

    private static class AttendanceData {
        private final String name;
        private final DateTime dateTime;

        AttendanceData(String name, LocalDateTime localDateTime) {
            this.name = name;
            this.dateTime = DateTime.of(localDateTime);
        }
    }
}
