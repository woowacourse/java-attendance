package attendance.util;

import attendance.domain.Attendance;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileLoader {

    public static List<Attendance> loadAll(final List<String> datas) {
        List<Attendance> attendances = new ArrayList<>();
        datas.forEach(data -> {
            List<String> seperatedData = Arrays.stream(data.split(",")).toList();
            String name = seperatedData.get(0);
            LocalDateTime localDateTime = LocalDateTime.parse(seperatedData.get(1).replace(" ", "T"));
            findAttendanceByName(attendances, name).add(localDateTime);
        });
        return attendances;
    }

    private static Attendance findAttendanceByName(List<Attendance> attendances, String name) {
        return attendances.stream()
                .filter(attendance -> attendance.isNameMatch(name))
                .findFirst()
                .orElseGet(() -> {
                    Attendance newAttendance = new Attendance(name);
                    attendances.add(newAttendance);
                    return newAttendance;
                });
    }
}
