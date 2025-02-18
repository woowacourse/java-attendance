package attendance;

import attendance.model.Attendance;
import attendance.model.Attendances;
import attendance.model.Crew;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendancesFactory {

    private static final String ATTENDANCES_FILE_PATH = "src/main/resources/attendances.csv";

    public Attendances initialize() {
        List<String> lines = readLinesWithoutHeader();

        Set<Attendance> attendances = new HashSet<>();
        for (String line : lines) {
            String[] split = line.split(",");
            String nickname = split[0];
            attendances.add(new Attendance(new Crew(nickname), toLocalDateTime(split[1])));
        }
        return new Attendances(attendances);
    }

    private List<String> readLinesWithoutHeader() {
        List<String> lines = readFile(Path.of(ATTENDANCES_FILE_PATH));
        lines.removeFirst();
        return lines;
    }

    private List<String> readFile(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new IllegalStateException("파일을 읽는 도중 예외가 발생했습니다.", e);
        }
    }

    private LocalDateTime toLocalDateTime(String input) {
        return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
