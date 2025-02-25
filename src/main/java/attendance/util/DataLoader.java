package attendance.util;

import attendance.domain.Crew;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataLoader {

    public static List<Crew> loadAll(final List<String> datas) {
        List<Crew> attendances = new ArrayList<>();
        datas.forEach(data -> {
            List<String> seperatedData = Arrays.stream(data.split(",")).toList();
            String name = seperatedData.get(0);
            LocalDateTime localDateTime = LocalDateTime.parse(seperatedData.get(1).replace(" ", "T"));
            findAttendanceByName(attendances, name).attend(localDateTime);
        });
        return attendances;
    }

    private static Crew findAttendanceByName(List<Crew> attendances, String name) {
        return attendances.stream()
                .filter(attendance -> attendance.isNameMatch(name))
                .findFirst()
                .orElseGet(() -> {
                    Crew newAttendance = new Crew(name);
                    attendances.add(newAttendance);
                    return newAttendance;
                });
    }
}
