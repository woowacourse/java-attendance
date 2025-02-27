import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Student {
    Map<LocalDate, LocalTime> attendanceTimeRecords = new HashMap<>();
    Map<LocalDate, AttendanceStatus> attendanceStatusRecords = new HashMap<>();
    Map<AttendanceStatus, Long> attendanceStatusCount = new HashMap<>();

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

    public void updateAttendanceCount() {
        long attendanceCount = findAttendanceStatusCount(AttendanceStatus.ATTENDANCE);
        long lateCount = findAttendanceStatusCount(AttendanceStatus.LATE);
        long absentCount = findAttendanceStatusCount(AttendanceStatus.ABSENT);

        attendanceStatusCount.put(AttendanceStatus.ATTENDANCE, attendanceCount);
        attendanceStatusCount.put(AttendanceStatus.LATE, lateCount);
        attendanceStatusCount.put(AttendanceStatus.ABSENT, absentCount);
    }
    private long findAttendanceStatusCount(AttendanceStatus attendanceStatus){
        return attendanceStatusRecords.entrySet().stream()
                .filter(record -> record.getValue().equals(attendanceStatus))
                .count();
    }

    public long convertTardiesToAbsence() {
        return findAttendanceStatusCount(AttendanceStatus.LATE)/3;
    }
}
