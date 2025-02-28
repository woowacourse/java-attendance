import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Student {
    final String name;
    final AttendanceTimeRecord attendanceTimeRecord;
    final AttendanceStatusRecord attendanceStatusRecord;
    final AttendanceStatusCount attendanceStatusCount;

    public Student(String name, List<LocalDateTime> localDateTime) {
        this.attendanceStatusRecord = new AttendanceStatusRecord(localDateTime);
        this.attendanceTimeRecord = new AttendanceTimeRecord(localDateTime);
        this.attendanceStatusCount = new AttendanceStatusCount();
        this.name = name;
    }

    public LocalTime findAttendanceLocalTimeByLocalDate(LocalDate localDate){
        return attendanceTimeRecord.getAttendanceTimeRecords().get(localDate);
    }

    public AttendanceStatus findAttendanceStatusByLocalDate(LocalDate localDate){
        return attendanceStatusRecord.getAttendanceStatusRecords().get(localDate);
    }

    public void registerAttendanceRecord(LocalDate todayDate, LocalTime attendanceTime) {
        validateDuplicateAttendance(todayDate);
        attendanceTimeRecord.registerAttendanceTimeRecord(todayDate,attendanceTime);
        AttendanceStatus attendanceStatus = AttendanceStatus.attendanceStatusCalculate(todayDate, attendanceTime);
        attendanceStatusRecord.registerAttendanceStatusRecord(todayDate, attendanceStatus);
    }

    public void modifyAttendanceRecord(String modifyDate, LocalTime modifyTime) {
        LocalDate localDate = LocalDate.of(2024,12,Integer.parseInt(modifyDate));
        attendanceTimeRecord.modifyAttendanceTimeRecord(localDate, modifyTime);
        AttendanceStatus attendanceStatus = AttendanceStatus.attendanceStatusCalculate(localDate, modifyTime);
        attendanceStatusRecord.modifyAttendanceStatusRecord(localDate, attendanceStatus);
    }

    public long convertTardiesToAbsence() {
        return attendanceStatusRecord.findAttendanceStatusCount(AttendanceStatus.LATE)/3;
    }

    public void nonAttendanceRecordStatusIsAbsent(LocalDate today) {
        if (!attendanceTimeRecord.checkAttendanceRecordByLocalDate(today)){
            attendanceTimeRecord.putNullLocalTime(today);
            attendanceStatusRecord.putAttendanceStateToAbsent(today);
        }
    }

    public void updateAttendanceCount() {
        attendanceStatusCount.updateAttendanceCount(attendanceStatusRecord);
    }

    private void validateDuplicateAttendance(LocalDate today){
        if (attendanceTimeRecord.checkAttendanceRecordByLocalDate(today)){
            throw new IllegalArgumentException("[ERROR] 출석기록이 존재합니다.");
        }
    }

    public Map<LocalDate, LocalTime> findAttendanceTimeRecordMap() {
        return attendanceTimeRecord.getAttendanceTimeRecords();
    }

    public String getName() {
        return name;
    }
}