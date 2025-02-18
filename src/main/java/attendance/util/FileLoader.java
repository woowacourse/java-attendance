package attendance.util;

import attendance.domain.Attendance;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
            LocalDateTime localDateTime = LocalDateTime.parse(seperatedData.get(1).replace(" ", "T"));

            Optional<Attendance> crewAttendance = attendances.stream()
                    .filter(a -> a.isNameMatch(seperatedData.get(0)))
                    .findFirst();

            if (crewAttendance.isEmpty()) {
                attendances.add(createAttendance(seperatedData.get(0), localDateTime));
                return;
            }
            crewAttendance.get().add(localDateTime);
        });
        return attendances;
    }
}
