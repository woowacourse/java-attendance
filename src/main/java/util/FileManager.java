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


    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String SPLIT_DELIMITER = ",";

    public static Attendance readFile(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            br.readLine();

            String line;
            Map<Crew, List<LocalDateTime>> attendances = new LinkedHashMap<>();

            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(SPLIT_DELIMITER);

                String name = lineSplit[0];
                Crew crew = Crew.from(name);
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
}
