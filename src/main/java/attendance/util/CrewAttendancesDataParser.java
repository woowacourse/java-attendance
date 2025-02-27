package attendance.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewAttendancesDataParser {
    private static final String LINE_BREAK = "\n";
    private static final String DELIMITER = ",";

    public static Map<String, List<LocalDateTime>> parse(String input) {
        String[] lines = input.split(LINE_BREAK);
        Map<String, List<LocalDateTime>> result = new HashMap<>();
        for (String line : lines) {
            String[] data = line.split(DELIMITER);
            String nickname = data[0];
            List<LocalDateTime> attendances = result.getOrDefault(nickname, new ArrayList<>());
            attendances.add(parseLocalDateTime(data[1]));
            result.put(nickname, attendances);
        }
        return result;
    }

    public static LocalDateTime parseLocalDateTime(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(input, formatter);
    }
}
