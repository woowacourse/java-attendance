package file;

import domain.Attendance;
import domain.Crew;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFileReader {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static List<Attendance> read(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String str;
        List<Attendance> attendances = new ArrayList<>();
        br.readLine();
        while ((str = br.readLine()) != null) {
            Crew crew = new Crew(str.split(",")[0]);
            LocalDateTime date = LocalDateTime.parse(str.split(",")[1], formatter);
            attendances.add(Attendance.of(crew, date));
        }
        return attendances;
    }
}