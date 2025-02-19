package file;

import domain.Attendance;
import domain.Attendances;
import domain.Crew;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AttendanceFileReader {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Attendances read(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String str;
        br.readLine();
        Attendances attendances = new Attendances();
        while ((str = br.readLine()) != null) {
            Crew crew = new Crew(str.split(",")[0]);
            LocalDateTime dateTime = LocalDateTime.parse(str.split(",")[1], FORMATTER);
            attendances.addAttendance(crew, Attendance.of(dateTime));
        }
        return attendances;
    }
}
