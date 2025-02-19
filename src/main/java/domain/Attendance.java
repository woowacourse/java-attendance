package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Attendance {

    private static Map<Crew, List<LocalDateTime>> attendanceMap;

    public Attendance(final Map<Crew, List<LocalDateTime>> attendanceMap) {
        this.attendanceMap = attendanceMap;
    }

    public Crew getCrewByName(String name) {
        return attendanceMap.keySet()
                .stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static Map<Crew, List<LocalDateTime>> getAttendanceMap() {
        return attendanceMap;
    }

    public void save(final Crew crew, final String schoolStartTime, final int todayDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<LocalDateTime> localDateTimes = attendanceMap.get(crew);

        String today = String.format("2024-12-%02d %s", todayDay, schoolStartTime);

        LocalDateTime todayLocalDateTime = LocalDateTime.parse(today, formatter);

        localDateTimes.add(todayLocalDateTime);
        attendanceMap.put(crew, localDateTimes);
    }
}
