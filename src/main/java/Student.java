import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Student {
    Map<LocalDate, LocalTime> attendanceRecords = new HashMap<>();
    Map<LocalDate, AttendanceStatus> attendanceStatusRecords = new HashMap<>();

    public void registerAttendanceRecord(LocalDate todayDate, String attendanceTime) {
        attendanceRecords.put(todayDate, LocalTime.parse(attendanceTime));
        AttendanceStatus attendanceStatus = AttendanceStatus.attendanceStatusCalculate(todayDate, attendanceTime);
        attendanceStatusRecords.put(todayDate, attendanceStatus);
    }
}
