import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendancesFileParser {

    public Crews init() {
        String[] splitAttendances = AttendancesFileReader.read().split("\n");
        List<Crew> crews = new ArrayList<>();

        for (String splitAttendance : splitAttendances) {
            String[] data = splitAttendance.split(",");
            List<Attendance> attendances = new ArrayList<>();
            attendances.add(new Attendance(formatter(data[1])));
            crews.add(new Crew(data[0], attendances));
        }
        return new Crews(crews);
    }

    public LocalDateTime formatter(String inputDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(inputDateTime, formatter);
    }
}
