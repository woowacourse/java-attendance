package attendance;

import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceReport;
import attendance.domain.EducationDayPolicy;
import attendance.domain.WoowaDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class TestUtil {
    private final static EducationDayPolicy policy = new EducationDayPolicy(Set.of(LocalDate.of(2024, 12, 25)));

    public static AttendanceRecord createRecord(LocalDateTime dateTime) {
        WoowaDate woowaDate = new WoowaDate(dateTime.toLocalDate(), policy);
        return new AttendanceRecord(woowaDate, dateTime.toLocalTime());
    }

    public static AttendanceReport toReport(AttendanceHistory history, int startDay, int endDay) {
        return history.toReport(
                LocalDate.of(2024, 12, startDay),
                LocalDate.of(2024, 12, endDay),
                new EducationDayPolicy(Set.of())
        );
    }

    public static WoowaDate WoowaDatefrom(LocalDate date) {
        return new WoowaDate(date, policy);
    }
}
