package attendance.repository;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceTime;
import attendance.domain.Crew;
import attendance.util.FileDataLoader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceBookLoader {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd HH:mm");
    private final String filePath;

    public AttendanceBookLoader(final String filePath) {
        this.filePath = filePath;
    }

    public Map<String, AttendanceBook> loadAttendanceBooks() {
        final List<String> lines = loadLines();

        final Set<String> crewNickNames = extractCrewNickNames(lines);
        final Map<String, Map<AttendanceDate, AttendanceTime>> attendanceDateTimes = extractAttendanceDateTimes(
            lines);

        return crewNickNames.stream()
            .collect(Collectors.toMap(
                crewNickName -> crewNickName,
                crewNickName -> new AttendanceBook(
                    new Crew(crewNickName),
                    new AttendanceRecord(attendanceDateTimes.get(crewNickName))
                )
            ));
    }

    private List<String> loadLines() {
        return FileDataLoader.loadLines(filePath)
            .orElseThrow(() -> new IllegalArgumentException("파일을 읽을 수 없습니다."));
    }

    private Set<String> extractCrewNickNames(final List<String> lines) {
        return lines.stream()
            .skip(1)
            .map(line -> line.split(",", -1)[0].trim())
            .collect(Collectors.toSet());
    }

    private Map<String, Map<AttendanceDate, AttendanceTime>> extractAttendanceDateTimes(final List<String> lines) {
        final Map<String, Map<AttendanceDate, AttendanceTime>> attendanceDateTimes = new HashMap<>();

        lines.stream()
            .skip(1)
            .map(line -> line.split(",", -1))
            .forEach(
                items -> parseAttendanceDateTimes(items, attendanceDateTimes));

        return attendanceDateTimes;
    }

    private void parseAttendanceDateTimes(
        final String[] items,
        final Map<String, Map<AttendanceDate, AttendanceTime>> attendanceRecords
    ) {
        validateSize(items);
        final String name = items[0].trim();
        final LocalDateTime localDateTime = parseLocalDateTime(
            items[1].trim());
        final AttendanceDate attendanceDate = AttendanceDate.from(
            localDateTime.toLocalDate());
        final AttendanceTime attendanceTime = AttendanceTime.from(
            localDateTime.toLocalTime());

        attendanceRecords.computeIfAbsent(name, k -> new HashMap<>())
            .put(attendanceDate, attendanceTime);
    }

    private void validateSize(final String[] items) {
        if (items.length != 2) {
            throw new IllegalArgumentException("잘못된 데이터 형식입니다.");
        }
    }

    private LocalDateTime parseLocalDateTime(final String localDateTime) {
        try {
            return LocalDateTime.parse(localDateTime, DATE_TIME_FORMATTER);
        } catch (final DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 날짜 형식입니다.", e);
        }
    }
}
