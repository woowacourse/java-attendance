package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import model.AttendanceDate;
import model.AttendanceHistory;
import model.AttendanceTime;

public class Initializer {

    private static final String DELIMITER_REGEX = ",|\\s";

    public static Map<String, AttendanceHistory> loadAttendanceBook(final List<String> lines) {
        Map<String, Map<AttendanceDate, AttendanceTime>> history = new HashMap<>();
        for (String line : lines.subList(1, lines.size())) {
            String[] tokens = line.split(DELIMITER_REGEX);
            if (history.containsKey(tokens[0])) {
                putRawAttendanceHistory(history.get(tokens[0]), tokens[1], tokens[2]);
                continue;
            }
            history.put(tokens[0], getRawAttendanceHistory(tokens[1], tokens[2]));
        }
        return getRawAttendanceBook(history);
    }

    private static Map<AttendanceDate, AttendanceTime> getRawAttendanceHistory(
            final String rawDate,
            final String rawTime
    ) {
        return new HashMap<>(Map.of(
                new AttendanceDate(InputParser.parseCsvDate(rawDate)),
                new AttendanceTime(InputParser.parseTime(rawTime))
        ));
    }

    private static void putRawAttendanceHistory(
            final Map<AttendanceDate, AttendanceTime> history,
            final String rawDate,
            final String rawTime
    ) {
        history.put(
                new AttendanceDate(InputParser.parseCsvDate(rawDate)),
                new AttendanceTime(InputParser.parseTime(rawTime))
        );
    }

    private static Map<String, AttendanceHistory> getRawAttendanceBook(
            final Map<String, Map<AttendanceDate, AttendanceTime>> rawAttendanceBookWithRawHistory
    ) {
        return rawAttendanceBookWithRawHistory.entrySet().stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        entry -> new AttendanceHistory(entry.getValue())
                ));
    }
}
