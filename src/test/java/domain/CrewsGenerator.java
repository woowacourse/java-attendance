package domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.DateTimeParser;
import util.FileManager;

public class CrewsGenerator {

    private static final String FILE_NAME = "attendances.csv";
    private static Long initialId = 1L;

    private CrewsGenerator() {
    }

    public static Map<String, Crew> generate() {
        final Map<String, Crew> crews = new HashMap<>();
        List<String> lines = FileManager.readFileLines(FILE_NAME);
        lines.removeFirst();
        for (String line : lines) {
            processAttendanceRecord(line, crews);
        }
        return crews;
    }

    private static void processAttendanceRecord(final String line, final Map<String, Crew> crews) {
        final String[] split = line.split(",");
        final String name = split[0];
        final LocalDateTime localDateTime = DateTimeParser.parseToLocalDateTime(split[1]);
        crews.computeIfAbsent(name, CrewsGenerator::createCrew).putAttendance(localDateTime);
    }


    private static Crew createCrew(final String name) {
        return new Crew(initialId++,name, AttendanceRecordGenerator.generate());
    }
}
