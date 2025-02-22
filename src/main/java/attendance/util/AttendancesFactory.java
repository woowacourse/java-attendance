package attendance.util;

import static attendance.util.DateTimeUtil.parseDateTime;

import attendance.model.Attendance;
import attendance.model.Attendances;
import attendance.model.Crew;
import attendance.model.CrewGroup;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendancesFactory {

    private static final String ATTENDANCES_FILE_PATH = "src/main/resources/attendances.csv";
    private static final int CREW_NICKNAME_INDEX = 0;
    private static final int DATE_TIME_INDEX = 1;

    public Attendances initialize() {
        List<String> lines = readLinesWithoutHeader();
        Set<Crew> crews = new HashSet<>();
        Set<Attendance> attendances = new HashSet<>();
        for (String line : lines) {
            String[] split = line.split(",");
            Crew crew = new Crew(split[CREW_NICKNAME_INDEX]);
            crews.add(crew);
            attendances.add(new Attendance(crew, parseDateTime(split[DATE_TIME_INDEX])));
        }
        return new Attendances(new CrewGroup(crews), attendances);
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
}
