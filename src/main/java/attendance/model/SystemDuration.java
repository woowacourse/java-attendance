package attendance.model;

import java.time.LocalDate;

public class SystemDuration {
    public static final LocalDate startDate = LocalDate.of(2024, 12, 1);
    public static final LocalDate endDate = LocalDate.of(2024, 12, 31);

    public static boolean isSystemDuration(LocalDate now) {
        return !(now.isBefore(startDate) || now.isAfter(endDate));
    }

    public static LocalDate computeLastAttendanceDate(LocalDate now) {
        if (now.isBefore(SystemDuration.endDate)) {
            return now;
        }
        return SystemDuration.endDate;
    }
}
