package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Student {
    private final String name;
    private final AttendanceTimeRecord attendanceTimeRecord;
    private final AttendanceStatusRecord attendanceStatusRecord;
    private final AttendanceStatusCount attendanceStatusCount;

    public Student(String name, List<LocalDateTime> localDateTime) {
        this.attendanceStatusRecord = new AttendanceStatusRecord(localDateTime);
        this.attendanceTimeRecord = new AttendanceTimeRecord(localDateTime);
        this.attendanceStatusCount = new AttendanceStatusCount();
        this.name = name;
    }

    public LocalTime findAttendanceLocalTimeByLocalDate(LocalDate localDate) {
        return attendanceTimeRecord.getAttendanceTimeRecords().get(localDate);
    }

    public AttendanceStatus findAttendanceStatusByLocalDate(LocalDate localDate) {
        return attendanceStatusRecord.getAttendanceStatusRecords().get(localDate);
    }

    public void registerAttendanceRecord(LocalDate todayDate, LocalTime attendanceTime) {
        validateDuplicateAttendance(todayDate);
        attendanceTimeRecord.registerAttendanceTimeRecord(todayDate, attendanceTime);
        attendanceStatusRecord.registerAttendanceStatusRecord(
                todayDate, AttendanceStatus.calculateAttendanceStatus(todayDate, attendanceTime)
        );
    }

    public void modifyAttendanceRecord(int modifyDate, LocalTime modifyTime) {
        LocalDate localDate = LocalDate.of(2024, 12, modifyDate);
        attendanceTimeRecord.modifyAttendanceTimeRecord(localDate, modifyTime);
        attendanceStatusRecord.modifyAttendanceStatusRecord(
                localDate, AttendanceStatus.calculateAttendanceStatus(localDate, modifyTime)
        );
    }

    public long convertTardiesToAbsence() {
        return attendanceStatusRecord.findAttendanceStatusCount(AttendanceStatus.LATE) / 3;
    }

    public void updateNonAttendanceRecordStatusIsAbsent(LocalDate today) {
        LocalDate startDate = LocalDate.of(today.getYear(), 12, 1);
        LocalDate endDate = today.minusDays(1);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            registerAsAbsentIfNoAttendance(date);
        }
    }
    public void updateAttendanceCount() {
        attendanceStatusCount.updateAttendanceCount(attendanceStatusRecord);
    }

    public long calculateTotalAbsentCount() {
        updateAttendanceCount();
        return attendanceStatusCount.getAbsentCount() + convertTardiesToAbsence();
    }

    public long calculateAbsentCount(){
        updateAttendanceCount();
        return attendanceStatusCount.getAbsentCount();
    }

    public long calculateLateCount(){
        updateAttendanceCount();
        return attendanceStatusCount.getLateCount();
    }

    private void validateDuplicateAttendance(LocalDate today) {
        if (attendanceTimeRecord.checkAttendanceRecordByLocalDate(today)) {
            throw new IllegalArgumentException("[ERROR] 출석기록이 존재합니다.");
        }
    }

    private void registerAsAbsentIfNoAttendance(LocalDate date) {
        if (!attendanceTimeRecord.checkAttendanceRecordByLocalDate(date)) {
            attendanceTimeRecord.putNullLocalTime(date);
            attendanceStatusRecord.putAttendanceStateToAbsent(date);
        }
    }

    public Map<LocalDate, LocalTime> getAttendanceTimeRecords(){
        return attendanceTimeRecord.getAttendanceTimeRecords();
    }

    public Map<AttendanceStatus, Long> getAttendanceStatusCount(){
        return attendanceStatusCount.getAttendanceStatusCount();
    }
    public String getName() {
        return name;
    }

}