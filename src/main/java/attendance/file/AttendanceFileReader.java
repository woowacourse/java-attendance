package attendance.file;

import attendance.domain.Attendance;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.Crews;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFileReader {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static FileContents read(String path) {
        List<String> contentsByLine = readContents(path);
        Attendances attendances = new Attendances();
        Crews crews = new Crews();
        for (String line : contentsByLine) {
            Crew crew = new Crew(line.split(",")[0]);
            crews.addCrew(crew);
            LocalDateTime attendanceDateTime = LocalDateTime.parse(line.split(",")[1], FORMATTER);
            attendances.addAttendance(crew, Attendance.of(attendanceDateTime));
        }
        return new FileContents(attendances, crews);
    }

    private static List<String> readContents(String path) {
        List<String> contentsByLine = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String str;
            br.readLine();
            while ((str = br.readLine()) != null) {
                contentsByLine.add(str);
            }
        } catch (IOException e) {
            throw new IllegalStateException("파일 읽기 오류가 발생했습니다.", e);
        }
        return contentsByLine;
    }

    public record FileContents(Attendances attendances, Crews crews) {}
}
