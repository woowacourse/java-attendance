import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttendancesFileReader {

    public Crews init() {
        String[] splitAttendances = read().split("\n");
        List<Crew> crews = new ArrayList<>();

        for (String splitAttendance : splitAttendances) {
            String[] data = splitAttendance.split(",");
            List<String> attendances = new ArrayList<>();
            attendances.add(data[1]);
            crews.add(new Crew(data[0], attendances));
        }
        return new Crews(crews);
    }

    public String read() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/attendances.csv"))) {
            String line;
            reader.readLine(); //첫줄
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
