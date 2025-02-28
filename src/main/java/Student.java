import java.time.LocalDate;
import java.time.LocalTime;

public class Student {
    String name;
    AttendanceTimeRecord attendanceTimeRecord;
    AttendanceStatusRecord attendanceStatusRecord;
    AttendanceStatusCount attendanceStatusCount;

    public void registerAttendanceRecord(LocalDate todayDate, String attendanceTime) {
        validateDuplicateAttendance(todayDate);
        attendanceTimeRecord.attendanceTimeRecords.put(todayDate, LocalTime.parse(attendanceTime));
        AttendanceStatus attendanceStatus = AttendanceStatus.attendanceStatusCalculate(todayDate, attendanceTime);
        attendanceStatusRecord.attendanceStatusRecords.put(todayDate, attendanceStatus);
    }

    public void modifyAttendanceRecord(String modifyDate, String modifyTime) {
        LocalDate localDate = LocalDate.of(2024,12,Integer.parseInt(modifyDate));
        attendanceTimeRecord.attendanceTimeRecords.put(localDate, LocalTime.parse(modifyTime));
        AttendanceStatus attendanceStatus = AttendanceStatus.attendanceStatusCalculate(localDate, modifyTime);
        attendanceStatusRecord.attendanceStatusRecords.put(localDate, attendanceStatus);
    }

    public void updateAttendanceCount() {
        long attendanceCount = findAttendanceStatusCount(AttendanceStatus.ATTENDANCE);
        long lateCount = findAttendanceStatusCount(AttendanceStatus.LATE);
        long absentCount = findAttendanceStatusCount(AttendanceStatus.ABSENT);

        attendanceStatusCount.attendanceStatusCount.put(AttendanceStatus.ATTENDANCE, attendanceCount);
        attendanceStatusCount.attendanceStatusCount.put(AttendanceStatus.LATE, lateCount);
        attendanceStatusCount.attendanceStatusCount.put(AttendanceStatus.ABSENT, absentCount);
    }

    public long convertTardiesToAbsence() {
        return findAttendanceStatusCount(AttendanceStatus.LATE)/3;
    }

    private long findAttendanceStatusCount(AttendanceStatus attendanceStatus){
        return attendanceStatusRecord.attendanceStatusRecords.entrySet().stream()
                .filter(record -> record.getValue().equals(attendanceStatus))
                .count();
    }

    public void nonAttendanceRecordStatusIsAbsent(LocalDate today) {
        if (attendanceTimeRecord.attendanceTimeRecords.get(today) == null){
            attendanceTimeRecord.attendanceTimeRecords.put(today, null);
            attendanceStatusRecord.attendanceStatusRecords.put(today, AttendanceStatus.ABSENT);
        }
    }

    private void validateDuplicateAttendance(LocalDate today){
        if (attendanceTimeRecord.attendanceTimeRecords.get(today) != null){
            throw new IllegalArgumentException("[ERROR] 출석기록이 존재합니다.");
        }
    }
}
