package presentation;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.DateTimeUtil;

public class InputParser {
    public static Map<String, List<LocalDateTime>> getFileAttendanceInfo(Map<String, List<String>> attendanceInfo) {
        Map<String, List<LocalDateTime>> crewInitAttendanceDates = new HashMap<>();

        for (String key : attendanceInfo.keySet()) {

            crewInitAttendanceDates.put(key,
                    attendanceInfo.get(key).stream()
                            .map(DateTimeUtil::convertStringToLocalDateTime)
                            .toList());
        }
        return crewInitAttendanceDates;
    }
}

