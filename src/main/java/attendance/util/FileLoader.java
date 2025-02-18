package attendance.util;

import attendance.domain.Attendance;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class FileLoader {

    public static Attendance load(final String data) {
        List<String> seperatedData = Arrays.stream(data.split(",")).toList();
        LocalDateTime localDateTime = LocalDateTime.parse(seperatedData.get(1).replace(" ", "T"));
        Attendance attendance = new Attendance(seperatedData.get(0));
        attendance.add(localDateTime);
        return attendance;
    }
}
