package attendance.view;

import attendance.domain.Attendances;
import attendance.util.DataFileReader;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {
    private static final String NAME_DATE_DELIMITER = ",";
    private static final DateTimeFormatter CONVERT_FORMATTER= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Map<String, Attendances> loadAttendancesData(){
        List<String> readFile = DataFileReader.readFile();
        Map<String, Attendances> crewAttendances = new HashMap<>();

        readFile.stream()
                .forEach(line -> {
                    addData(crewAttendances, line);
                });

        return crewAttendances;
    }

    private static void addData(Map<String, Attendances> crewAttendances, String line) {
        String[] split = line.split(NAME_DATE_DELIMITER);
        String name = split[0];
        LocalDateTime dateTime = parseDateTime(split[1]);

        crewAttendances.putIfAbsent(name, new Attendances());
        Attendances attendances = crewAttendances.get(name);
        attendances.addAttendance(dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    private static LocalDateTime parseDateTime(String dateTimeString){
        return LocalDateTime.parse(dateTimeString, CONVERT_FORMATTER);
    }
}
