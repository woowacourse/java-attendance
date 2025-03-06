package fixture;

import domain.AttendanceRecord;
import domain.AttendanceRecords;

import java.time.LocalDateTime;

public class AttendanceRecordsFixture {
    public static AttendanceRecords createAttendanceRecords(String... dateTimes) {
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        for (String dateTime : dateTimes) {
            attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse(dateTime)));
        }
        return attendanceRecords;
    }
}
