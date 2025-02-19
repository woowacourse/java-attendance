import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendancesFileReader {

    public static Map<String, List<LocalDateTime>> read() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/attendances.csv"))) {
            String line;
            reader.readLine();//첫줄 name, date
            Map<String, List<LocalDateTime>> crewAttendances = new HashMap<>();

            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split(",");

                String nickname = splitLine[0];
                List<LocalDateTime> attendances = crewAttendances.getOrDefault(nickname, new ArrayList<LocalDateTime>());

                attendances.add(formatter(splitLine[1]));
                attendances.sort(LocalDateTime::compareTo);
                crewAttendances.put(nickname, attendances);
            }
            return crewAttendances;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static LocalDateTime formatter(String inputDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(inputDateTime, formatter);
    }
}
