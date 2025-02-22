package attendance;

import attendance.domain.Attendance;
import attendance.domain.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFixture {

    public static List<Attendance> makeAttendance() {

        List<Attendance> attendances = new ArrayList<>(List.of(makeAbsentAttendance("체체", 2025, 2, 10),
                makeAbsentAttendance("체체", 2025, 2, 11),
                makeAbsentAttendance("체체", 2025, 2, 12),
                makeAbsentAttendance("체체", 2025, 2, 13),
                makeAbsentAttendance("체체", 2025, 2, 14),
                makeAbsentAttendance("체체", 2025, 2, 17)));

        attendances.addAll(List.of(makeAbsentAttendance("피글렛", 2025, 2, 10),
                makeAbsentAttendance("피글렛", 2025, 2, 11)));

        attendances.addAll(List.of(makeAbsentAttendance("체글렛", 2025, 2, 10),
                makeAbsentAttendance("체글렛", 2025, 2, 11),
                makeAbsentAttendance("체글렛", 2025, 2, 12)));

        attendances.add(makeAbsentAttendance("피글체", 2025, 2, 10));

        return attendances;
    }

    public static Attendance makeAbsentAttendance(String name, int year, int month, int day) {
        return new Attendance(name, new Time(LocalDate.of(year, month, day), "18", "00", true));
    }
}
