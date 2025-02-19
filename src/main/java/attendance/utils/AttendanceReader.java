package attendance.utils;

import attendance.domain.Attendance;
import attendance.dto.AttendanceContentDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendanceReader {

    public static AttendanceContentDTO getAttendanceRecordContent(List<String> attendanceContents) {
        attendanceContents.removeFirst();
        final List<Attendance> attendances = new ArrayList<>();
        final Set<String> names = new HashSet<>();

        for (String content : attendanceContents) {
            String[] split = content.split(",");

            String crewName = split[0];
            LocalDateTime attendanceTime = LocalDateTime.parse(split[1],
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            names.add(crewName);
            attendances.add(new Attendance(crewName, attendanceTime));
        }
        return new AttendanceContentDTO(attendances, names);
    }
}
