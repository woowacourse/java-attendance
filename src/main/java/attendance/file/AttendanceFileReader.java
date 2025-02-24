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

public class AttendanceFileReader {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static FileContents read(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String str;
        br.readLine();
        Attendances attendances = new Attendances();
        Crews crews = new Crews();
        while ((str = br.readLine()) != null) {
            Crew crew = new Crew(str.split(",")[0]);
            crews.add(crew);
            LocalDateTime dateTime = LocalDateTime.parse(str.split(",")[1], FORMATTER);
            attendances.addAttendance(crew, Attendance.of(dateTime));
        }
        return new FileContents(attendances, crews);
    }

    public record FileContents(Attendances attendances, Crews crews) {}
}
