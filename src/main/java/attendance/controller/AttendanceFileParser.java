package attendance.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceFileParser {


    public static final String NEW_LINE = "\n";
    public static final String DELIMITER = ",";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static Map<String, List<LocalDateTime>> parse(final String fileContents) {
        String[] splitByNewLine = fileContents.split(NEW_LINE);
        Map<String, List<LocalDateTime>> crewAttendances = new HashMap<>();
        for (String line : splitByNewLine) {
            if(line.isBlank()) {
                throw new IllegalArgumentException("파일이 정상적이지 않습니다.");
            }
            String[] splitByDelimiter = line.split(DELIMITER);
            String nickname = splitByDelimiter[0];
            List<LocalDateTime> attendances = crewAttendances.getOrDefault(nickname, new ArrayList<>());
            attendances.add(formatDateTime(splitByDelimiter));
            crewAttendances.put(nickname, attendances);
        }
        return crewAttendances;
    }

    private static LocalDateTime formatDateTime(String[] splitByDelimiter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        return LocalDateTime.parse(splitByDelimiter[1], formatter);
    }
}
