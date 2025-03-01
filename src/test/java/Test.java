import domain.AttendanceBook;
import domain.AttendanceSystem;
import util.Dates;

import java.time.LocalTime;
import java.util.Map;

import static java.time.LocalTime.of;

public class Test {
    @org.junit.jupiter.api.Test
    void test() {
        AttendanceSystem attendanceSystem = new AttendanceSystem();
        attendanceSystem.editAttendance("123", Dates.TODAY, of(10, 12));
        Map<String, AttendanceBook> riskCrews = attendanceSystem.getRiskCrews();
        riskCrews.get("123").attendance(Dates.TODAY, LocalTime.of(10,40));
        for(var entry : riskCrews.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getAttendanceTimeByDate(Dates.TODAY));
        }
    }
}
