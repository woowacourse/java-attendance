package domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.DateTimeParser;
import util.FileManager;

public class AttendancePaperGenerator {

    private static final String FILE_NAME = "attendances.csv";
    private static Long initialId = 1L;

    private AttendancePaperGenerator() {
    }

    public static Map<String, AttendancePaper> generate() {
        final Map<String, AttendancePaper> crews = new HashMap<>();
        List<String> lines = FileManager.readFileLines(FILE_NAME);
        lines.removeFirst();
        for (String line : lines) {
            processAttendanceRecord(line, crews);
        }
        return crews;
    }

    private static void processAttendanceRecord(final String line, final Map<String, AttendancePaper> crews) {
        final String[] split = line.split(",");
        final String name = split[0];
        final LocalDateTime localDateTime = DateTimeParser.parseToLocalDateTime(split[1]);
        crews.computeIfAbsent(name, AttendancePaperGenerator::createCrew).addAttendance(localDateTime);
    }


    private static AttendancePaper createCrew(final String name) {
        return new AttendancePaper(initialId++,name, AttendanceRecordGenerator.generate());
    }
}
