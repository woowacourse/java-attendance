package attendance.controller.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewAttendanceParser {
    private static final String NEW_LINE = "\n";
    private static final String DELIMITER = ",";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    private CrewAttendanceParser() {
    }

    public static Map<String, List<LocalDateTime>> parseCrewAttendances(String input) {
        String[] lines = input.split(NEW_LINE);
        Map<String, List<LocalDateTime>> crewAttendances = new HashMap<>();
        for (String line : lines) {
            String[] splitLine = line.split(DELIMITER);
            String nickname = splitLine[0];
            List<LocalDateTime> attendances = crewAttendances.getOrDefault(nickname, new ArrayList<LocalDateTime>());
            attendances.add(formatter(splitLine[1]));
            crewAttendances.put(nickname, attendances);
        }
        return crewAttendances;
    }

    private static LocalDateTime formatter(final String inputDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDateTime.parse(inputDateTime, formatter);
    }
}
