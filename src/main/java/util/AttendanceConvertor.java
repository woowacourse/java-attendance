package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceConvertor {

    private static final int NAME_INDEX = 0;
    private static final int DATE_TIME_INDEX = 1;
    private static final String DELIMITER = ",";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Map<String, List<LocalDateTime>> convertToAttendances(List<String> raws) {
        Map<String, List<LocalDateTime>> nameAndAttendances = new HashMap<>();

        for (String raw : raws) {
            convertToAttendance(raw, nameAndAttendances);
        }

        return nameAndAttendances;
    }

    private static void convertToAttendance(String raw, Map<String, List<LocalDateTime>> nameAndAttendances) {
        String name = convertToName(raw);
        LocalDateTime attendanceDateTime = convertToDateTime(raw);

        if (nameAndAttendances.containsKey(name)) {
            nameAndAttendances.get(name).add(attendanceDateTime);
            return;
        }
        List<LocalDateTime> attendanceDateTimes = new ArrayList<>();
        attendanceDateTimes.add(attendanceDateTime);
        nameAndAttendances.put(name, attendanceDateTimes);
    }

    private static String convertToName(String raw) {
        List<String> splittedData = Arrays.stream(raw.split(DELIMITER)).toList();
        return splittedData.get(NAME_INDEX);
    }

    private static LocalDateTime convertToDateTime(String raw) {
        List<String> splittedData = Arrays.stream(raw.split(DELIMITER)).toList();
        return LocalDateTime.parse(splittedData.get(DATE_TIME_INDEX), DATE_TIME_FORMATTER);
    }
}
