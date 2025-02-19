package util;

import domain.Attendance;
import domain.Crew;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileManager {

    private static final String ATTENDANCE_FILE_PATH = "src/main/resources/attendances.csv";
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Attendance readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ATTENDANCE_FILE_PATH));
            br.readLine();

            String line;
            Map<Crew, List<LocalDateTime>> attendances = new LinkedHashMap<>();

            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(",");

                String name = lineSplit[0];
                Crew crew = getCrewByName(name, attendances);

                String dateTime = lineSplit[1];
                LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMAT);
                List<LocalDateTime> localDateTimes = attendances.getOrDefault(crew, new ArrayList<>());
                localDateTimes.add(localDateTime);

                attendances.put(crew, localDateTimes);
            }

            return new Attendance(attendances);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Crew getCrewByName(String name, Map<Crew, List<LocalDateTime>> attendances) {
        return attendances.keySet()
                .stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseGet(() -> Crew.from(name)
                );
    }
}
