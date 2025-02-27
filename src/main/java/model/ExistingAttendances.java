package model;

import common.Common;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExistingAttendances {
    //TODO : 혹시 만드는게 별로라면 이유는?
    private final Map<String, List<LocalDateTime>> attendances;

    public static ExistingAttendances from(List<String> crewAttendanceData) {
        Map<String, List<LocalDateTime>> uniqueCrewNames = extractUniqueCrewData(crewAttendanceData);
        for (String data : crewAttendanceData) {
            String crewName = data.split(",")[0];
            LocalDateTime dateTime = parseAttendanceData(data);
            uniqueCrewNames.get(crewName).add(dateTime);
        }
        return new ExistingAttendances(uniqueCrewNames);
    }

    public ExistingAttendances(Map<String, List<LocalDateTime>> attendances) {
        this.attendances = attendances;
    }

    private static Map<String, List<LocalDateTime>> extractUniqueCrewData(List<String> crewAttendanceData) {
        Map<String, List<LocalDateTime>> crewData = new HashMap<>();
        crewAttendanceData.stream()
                .map(data -> data.split(",")[0])
                .distinct()
                .forEach(uniqueCrewName -> {
                    crewData.put(uniqueCrewName, new ArrayList<>());
                });
        return crewData;
    }

    //TODO : private
    public static LocalDateTime parseAttendanceData(String crewAttendanceData) {
        String dateAndTime = crewAttendanceData.split(",")[1];
        return LocalDateTime.parse(dateAndTime, Common.yearMonthDateTimeFormatter);
    }

    public Map<String, List<LocalDateTime>> getAttendances() {
        return Collections.unmodifiableMap(attendances);
    }

    public List<String> findAllCrewNames() {
        return attendances.keySet().stream().toList();
    }
}
