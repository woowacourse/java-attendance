import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttendanceFileReader {

    static int REQUIRED_FIELDS_COUNT = 2;

    public List<Crew> loadFile(String filePath) {
        List<Crew> crews = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                List<String> fields = Arrays.stream(line.split(",")).toList();
                createCrewsFromFields(crews, fields);
            }
            return crews;
        } catch (Exception e) {
            System.err.println("파일을 불러오는 데 문제가 발생했습니다. 잠시 후 다시 시도해 주세요.");
        }
        return new ArrayList<>();
    }

    private void createCrewsFromFields(List<Crew> crews, List<String> fields) {
        if (hasValidFieldCount(fields)) {
            crews.add(createCrew(fields));
        }
    }

    private boolean hasValidFieldCount(List<String> fields) {
        return fields.size() == REQUIRED_FIELDS_COUNT;
    }

    private Crew createCrew(List<String> fields) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String name = fields.get(0);

        LocalDateTime localDateTime = LocalDateTime.parse(fields.get(1), formatter);
        LocalDate date = localDateTime.toLocalDate();
        LocalTime time = localDateTime.toLocalTime();

        Attendance attendance = new Attendance(date, time);
        Crew crew = new Crew(name);
        crew.addAttendance(attendance);

        return crew;
    }
}
