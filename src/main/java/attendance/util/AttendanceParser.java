package attendance.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import attendance.domain.Crew;

public class AttendanceParser {

    public static final int HEADER_HEIGHT = 1;

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private AttendanceParser() {
    }

    public static List<Crew> parseFile() {
        try (final Stream<String> lines = Files.lines(Path.of("src/main/resources/attendances.csv"))) {
            return parseLines(lines.skip(HEADER_HEIGHT));
        } catch (final IOException e) {
            throw new IllegalStateException("파일을 읽는 과정에서 문제가 발생했습니다.");
        }
    }

    public static List<Crew> parseLines(Stream<String> lines) {
        List<CrewData> crewData = lines.map(AttendanceParser::parseLine).toList();
        Map<String, Crew> map = new HashMap<>();
        for (CrewData data : crewData) {
            Crew crew = map.computeIfAbsent(data.name, Crew::new);
             crew.attendance(data.date, data.time);
        }
        return new ArrayList<>(map.values());
    }

    public static CrewData parseLine(String line) {
        String[] split = line.split(",");
        return CrewData.of(split[0], LocalDateTime.parse(split[HEADER_HEIGHT], DATETIME_FORMATTER));
    }

    public record CrewData(
        String name,
        LocalDate date,
        LocalTime time
    ) {

        private static CrewData of(String name, LocalDateTime dateTime) {
            return new CrewData(name, dateTime.toLocalDate(), dateTime.toLocalTime());
        }
    }
}
