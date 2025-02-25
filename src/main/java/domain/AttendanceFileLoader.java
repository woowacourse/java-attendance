package domain;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AttendanceFileLoader {
    public static final String DELIMITER = ",";

    private final FileReader fileReader;

    public AttendanceFileLoader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public Map<String, Attendances> loadInitialAttendances(Path path) {
        Map<String, Attendances> crewAttendances = new HashMap<>();

        for (String line : fileReader.readLines(path)) {
            String[] splitLines = line.split(DELIMITER);
            String nickname = splitLines[0];
            LocalDateTime dateTime = convertStringToDateTime(splitLines);
            if (crewAttendances.containsKey(nickname)) {
                crewAttendances.get(nickname).addAttendance(dateTime);
                break;
            }
            Attendances attendances = new Attendances();
            attendances.addAttendance(dateTime);
            crewAttendances.put(nickname, attendances);
        }

        return crewAttendances;
    }

    private static LocalDateTime convertStringToDateTime(String[] splitLines) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(splitLines[1], formatter);
    }
}
