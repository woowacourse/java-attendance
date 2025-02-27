package attendance;

import attendance.domain.AttendanceRecord;
import attendance.domain.EducationDayPolicy;
import attendance.domain.WoowaDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class TestUtil {
    private final static EducationDayPolicy policy = new EducationDayPolicy(Set.of(LocalDate.of(2024, 12, 25)));

    public static AttendanceRecord createRecord(LocalDateTime dateTime) {
        WoowaDate woowaDate = new WoowaDate(dateTime.toLocalDate(), policy);
        return new AttendanceRecord(woowaDate, dateTime.toLocalTime());
    }

    public static AttendanceRecord createRecord(LocalDate date, LocalTime time) {
        WoowaDate woowaDate = new WoowaDate(date, policy);
        return new AttendanceRecord(woowaDate, time);
    }

    public static WoowaDate WoowaDatefrom(LocalDate date) {
        return new WoowaDate(date, policy);
    }
}
