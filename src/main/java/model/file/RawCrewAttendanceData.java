package model.file;

import common.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RawCrewAttendanceData {
    private static final int CREW_NAME_INDEX = 0;
    private static final int ATTENDANCE_TIME_INDEX = 1;

    private final Map<String, List<LocalDateTime>> data;

    public static RawCrewAttendanceData from(List<String> crewAttendanceData) {
        Map<String, List<LocalDateTime>> uniqueCrewNames = extractUniqueCrewData(crewAttendanceData);
        for (String data : crewAttendanceData) {
            String crewName = data.split(",")[0];
            LocalDateTime dateTime = parseAttendanceData(data);
            uniqueCrewNames.get(crewName).add(dateTime);
        }
        return new RawCrewAttendanceData(uniqueCrewNames);
    }

    public RawCrewAttendanceData(Map<String, List<LocalDateTime>> data) {
        this.data = data;
    }

    private static Map<String, List<LocalDateTime>> extractUniqueCrewData(List<String> crewAttendanceData) {
        Map<String, List<LocalDateTime>> crewData = new HashMap<>();
        crewAttendanceData.stream()
                .map(data -> data.split(",")[CREW_NAME_INDEX])
                .distinct()
                .forEach(uniqueCrewName -> {
                    crewData.put(uniqueCrewName, new ArrayList<>());
                });
        return crewData;
    }

    private static LocalDateTime parseAttendanceData(String crewAttendanceData) {
        String dateAndTime = crewAttendanceData.split(",")[ATTENDANCE_TIME_INDEX];
        return LocalDateTime.parse(dateAndTime, DateTimeFormat.YEAR_MONTH_DATE_TIME_FORMATTER);
    }

    public Map<String, List<LocalDateTime>> getData() {
        return Collections.unmodifiableMap(data);
    }

    public List<String> findAllCrewNames() {
        return data.keySet().stream().toList();
    }
}
