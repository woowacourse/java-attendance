package model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExistingAttendances {
    //TODO : 혹시 만드는게 별로라면 이유는?
    private final Map<String, List<LocalDateTime>> attendances;

    public static ExistingAttendances from(List<String> combinedData) {
        Map<String, List<LocalDateTime>> attendances = new HashMap<>();
        for (String data : combinedData) {
            String crewName = data.split(",")[0];
            LocalDateTime dateTime = parseAttendanceFrom(data);
            if (attendances.containsKey(crewName)) {
                attendances.get(crewName).add(dateTime);
                continue;
            }
            attendances.put(crewName, new ArrayList<>(Arrays.asList(dateTime)));
        }
        return new ExistingAttendances(attendances);
    }

    public ExistingAttendances(Map<String, List<LocalDateTime>> attendances) {
        this.attendances = attendances;
    }

    public static LocalDateTime parseAttendanceFrom(String combinedData) {
        String dateAndTime = combinedData.split(",")[1];
        DateTimeFormatter yearMonthDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime attendanceTime = LocalDateTime.parse(dateAndTime, yearMonthDateTimeFormatter);
        return attendanceTime;
    }

    public Map<String, List<LocalDateTime>> getAttendances() {
        return attendances;
    }
}
