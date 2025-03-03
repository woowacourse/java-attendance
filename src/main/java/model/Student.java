package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
    private static final int LATE_COUNT_FOR_ONE_ABSENCE = 3;

    private final String name;
    private final AttendanceTimeRecord attendanceTimeRecord;

    public Student(String name, List<LocalDateTime> localDateTime) {
        this.attendanceTimeRecord = new AttendanceTimeRecord(localDateTime);
        this.name = name;
    }

    public LocalTime findAttendanceLocalTimeByLocalDate(LocalDate localDate) {
        return attendanceTimeRecord.getAttendanceTimeRecords().get(localDate);
    }

    public void registerAttendanceRecord(LocalDate todayDate, LocalTime attendanceTime) {
        attendanceTimeRecord.validateDuplicateAttendance(todayDate);
        CampusOperatingHours.validateOperatingHours(attendanceTime);
        attendanceTimeRecord.putAttendanceTimeRecord(todayDate, attendanceTime);
    }

    public void modifyAttendanceRecord(int modifyDate, LocalTime modifyTime) {
        LocalDate localDate = LocalDate.of(2024, 12, modifyDate);
        CampusOperatingHours.validateOperatingHours(modifyTime);
        attendanceTimeRecord.putAttendanceTimeRecord(localDate, modifyTime);
    }

    public void updateNonAttendanceRecordStatusIsAbsent(LocalDate today) {
        LocalDate startDate = LocalDate.of(today.getYear(), 12, 1);
        LocalDate endDate = today.minusDays(1);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (Holiday.checkHoliday(date)) {
                continue;
            }
            registerAsAbsentIfNoAttendance(date);
        }
    }

    public long calculateTotalAbsentCount() {
        return calculateAbsentCount() + calculateLateCount() / LATE_COUNT_FOR_ONE_ABSENCE;
    }

    public long calculateAbsentCount() {
        return AttendanceStatus.calculateAttendanceStatusCount(getAttendanceTimeRecords(), AttendanceStatus.ABSENT);
    }

    public long calculateLateCount() {
        return AttendanceStatus.calculateAttendanceStatusCount(getAttendanceTimeRecords(), AttendanceStatus.LATE);
    }

    public boolean isAtRiskOfCounselingOrExpulsion() {
        AttendancePenalty attendancePenalty = AttendancePenalty.findPenaltyByAbsentCount(calculateTotalAbsentCount());
        return attendancePenalty.equals(AttendancePenalty.COUNSELING) ||
                attendancePenalty.equals(AttendancePenalty.EXPULSION);
    }

    public Map<AttendanceStatus, Long> getAttendanceStatusCount() {
        Map<AttendanceStatus, Long> attendanceStatusCount = new HashMap<>();
        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            attendanceStatusCount.put(
                    attendanceStatus,
                    AttendanceStatus.calculateAttendanceStatusCount(getAttendanceTimeRecords(), attendanceStatus));
        }
        return attendanceStatusCount;
    }

    private void registerAsAbsentIfNoAttendance(LocalDate date) {
        if (!attendanceTimeRecord.checkAttendanceRecordByLocalDate(date)) {
            attendanceTimeRecord.putAttendanceTimeRecord(date, null);
        }
    }

    public Map<LocalDate, LocalTime> getAttendanceTimeRecords() {
        return attendanceTimeRecord.getAttendanceTimeRecords();
    }

    public String getName() {
        return name;
    }
}