import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Student {
    Map<LocalDate, LocalTime> attendanceTimeRecords = new HashMap<>();
    Map<LocalDate, AttendanceStatus> attendanceStatusRecords = new HashMap<>();

    public void registerAttendanceRecord(LocalDate todayDate, String attendanceTime) {
        attendanceTimeRecords.put(todayDate, LocalTime.parse(attendanceTime));
        AttendanceStatus attendanceStatus = AttendanceStatus.attendanceStatusCalculate(todayDate, attendanceTime);
        attendanceStatusRecords.put(todayDate, attendanceStatus);
    }

    public void modifyAttendanceRecord(String modifyDate, String modifyTime) {
        LocalDate localDate = LocalDate.of(2024,12,Integer.parseInt(modifyDate));
        attendanceTimeRecords.put(localDate, LocalTime.parse(modifyTime));
        AttendanceStatus attendanceStatus = AttendanceStatus.attendanceStatusCalculate(localDate, modifyTime);
        attendanceStatusRecords.put(localDate, attendanceStatus);
    }
}
