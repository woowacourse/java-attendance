package attendance.utils;

import attendance.domain.Attendance;
import attendance.domain.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class AttendanceReader {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private AttendanceReader() {
    }

    public static Set<Attendance> getAttendancesOnFile(final List<String> attendanceContents) {
        attendanceContents.removeFirst();

        Set<Attendance> attendances = new HashSet<>();

        for (String content : attendanceContents) {
            String[] split = content.split(",");

            String crewName = split[0];
            LocalDateTime attendanceTime = LocalDateTime.parse(split[1], formatter);

            attendances.add(new Attendance(crewName, new Time(attendanceTime)));
        }
        return attendances;
    }

    public static Set<String> getCrewNamesOnFile(final List<String> attendanceContents) {

        attendanceContents.removeFirst();

        Set<String> crewNames = new HashSet<>();

        for (String content : attendanceContents) {
            String[] split = content.split(",");

            String crewName = split[0];

            crewNames.add(crewName);

        }
        return crewNames;

    }
}
