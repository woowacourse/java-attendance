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

    public LocalDateTime update(final Crew crew, final String updateTime, final int date) {
        List<LocalDateTime> localDateTimes = attendanceMap.get(crew);
        int i;
        LocalDateTime beforeLocalDateTime = null;
        for (i = 0; i < localDateTimes.size(); i++) {
            LocalDateTime localDateTime = localDateTimes.get(i);
            int dayOfMonth = localDateTime.getDayOfMonth();
            if (dayOfMonth == date) {
                beforeLocalDateTime = localDateTime;
                break;
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String today = String.format("2024-12-%02d %s", date, updateTime);
        LocalDateTime todayLocalDateTime = LocalDateTime.parse(today, formatter);

        localDateTimes.set(i, todayLocalDateTime);

        return beforeLocalDateTime;
    }
}
