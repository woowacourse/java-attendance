package attendance.util;

import attendance.domain.Attendance;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileLoader {

    public static Attendance createAttendance(final String name, final LocalDateTime localDateTime) {
        Attendance attendance = new Attendance(name);
        attendance.add(localDateTime);
        return attendance;
    }

    public static List<Attendance> loadAll(final List<String> datas) {
        List<Attendance> attendances = new ArrayList<>();
        datas.forEach(data -> {
            List<String> seperatedData = Arrays.stream(data.split(",")).toList();
            String name = seperatedData.get(0);
            LocalDateTime localDateTime = LocalDateTime.parse(seperatedData.get(1).replace(" ", "T"));

            findAttendanceByName(attendances, name, localDateTime).add(localDateTime);
        });
        return attendances;
    }

    private static Attendance findAttendanceByName(List<Attendance> attendances, String name,
                                                   LocalDateTime localDateTime) {
        return attendances.stream()
                .filter(attendance -> attendance.isNameMatch(name))
                .findFirst()
                .orElseGet(() -> {
                    Attendance newAttendance = createAttendance(name, localDateTime);
                    return newAttendance;
                });
    }
}
