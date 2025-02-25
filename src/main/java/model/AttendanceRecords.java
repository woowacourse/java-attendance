package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceRecords {
    private static final int SATURDAY = 6;
    private static final int SUNDAY = 7;
    private static final int CHRISTMAS = 25;

    private final Map<LocalDate, AttendanceRecord> record;

    public AttendanceRecords(Map<LocalDate, AttendanceRecord> record) {
        this.record = record;
    }

    public void updateAttendanceStatusByLocalDate(LocalDateTime updateLocalDateTime) {
        validateHoliday(updateLocalDateTime);
        validateOpeningHours(updateLocalDateTime);
        AttendanceStatus newAttendanceStatus = AttendanceRuleByDay
                .calculateAttendance(updateLocalDateTime);

        record.entrySet().stream()
                .filter(e -> compareDayIsSame(updateLocalDateTime, e.getKey()))
                .findFirst()
                .ifPresentOrElse(
                        e -> modifyAttendanceRecord(updateLocalDateTime, e.getKey(), newAttendanceStatus),
                        () -> {
                            throw new IllegalArgumentException("[ERROR] 등록되지 않은 날짜입니다.");
                        }
                );
    }

    public void registerAttendanceRecord(LocalDateTime localDateTime) {
        validateHoliday(localDateTime);
        validateOpeningHours(localDateTime);
        LocalDate localDate = LocalDate.from(localDateTime);
        LocalTime localTime = LocalTime.from(localDateTime);
        AttendanceStatus attendanceStatus = AttendanceRuleByDay.calculateAttendance(localDateTime);
        AttendanceRecord attendanceRecord = new AttendanceRecord(localTime, attendanceStatus);
        record.put(localDate, attendanceRecord);
    }

    public long findTotalAttendanceCount() {
        return (int) record.entrySet().stream()
                .filter(e -> e.getValue().getAttendanceStatus().equals(AttendanceStatus.ATTENDANCE))
                .count();
    }

    public long findTotalLateCount() {
        return (int) record.entrySet().stream()
                .filter(e -> e.getValue().getAttendanceStatus().equals(AttendanceStatus.LATE))
                .count();
    }

    public long findTotalAbsentCount() {
        return (int) record.entrySet().stream()
                .filter(e -> e.getValue().getAttendanceStatus().equals(AttendanceStatus.ABSENT))
                .count();
    }

    public void createAttendanceRecords(LocalDateTime today) {
        AttendanceStatus attendanceStatus = AttendanceRuleByDay.calculateAttendance(today);
        record.put(LocalDate.from(today), new AttendanceRecord(LocalTime.from(today), attendanceStatus));
    }

    public void updateStateNotExistInFile(LocalDate today) {
        LocalDateTime standard = LocalDateTime.of(2024, 12, 1, 0, 0);
        Map<LocalDate, AttendanceRecord> recordClone = makeRecordClone();
        while (!compareDayIsSame(standard, LocalDate.from(today))) {
            if (isExistLocalDate(recordClone, standard)) {
                standard = standard.plusDays(1);
                continue;
            }
            addAbsentRecordForStudent(standard);
            standard = standard.plusDays(1);
        }
    }

    public AttendanceStatus findAttendanceStatusByLocalDateTime(LocalDateTime localDateTime) {
        return record.entrySet().stream()
                .filter(e -> compareDayIsSame(localDateTime, e.getKey()))
                .map(e -> e.getValue().getAttendanceStatus())
                .findFirst().orElse(null);
    }

    private void modifyAttendanceRecord(LocalDateTime updateLocalDateTime, LocalDate recordLocalDate,
                                        AttendanceStatus newAttendanceStatus) {
        LocalTime localTime = LocalTime.from(updateLocalDateTime);
        record.put(recordLocalDate, new AttendanceRecord(localTime, newAttendanceStatus));
    }

    private void addAbsentRecordForStudent(LocalDateTime updateLocalDateTime) {
        record.put(LocalDate.from(updateLocalDateTime), new AttendanceRecord(null, AttendanceStatus.ABSENT));
    }

    private void validateHoliday(LocalDateTime localDateTime) {
        int day = localDateTime.getDayOfWeek().getValue();
        if (day == SATURDAY || day == SUNDAY || localDateTime.getDayOfMonth() == CHRISTMAS) {
            throw new IllegalArgumentException("[주말 및 공휴일에는 등교일이 아닙니다]");
        }
    }

    private void validateOpeningHours(LocalDateTime localDateTime) {
        LocalTime localTime = LocalTime.from(localDateTime);
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(23, 0);
        if (localTime.isBefore(startTime) || localTime.isAfter(endTime)) {
            throw new IllegalArgumentException("[캠퍼스 운영 시간이 아닙니다.]");
        }
    }

    private boolean compareDayIsSame(LocalDateTime localDateTime, LocalDate localDate) {
        return LocalDate.from(localDateTime).equals(localDate);
    }

    private boolean isExistLocalDate(Map<LocalDate, AttendanceRecord> recordClone, LocalDateTime localDateTime) {
        return recordClone.keySet().stream()
                .anyMatch(date -> compareDayIsSame(localDateTime, date));
    }

    private Map<LocalDate, AttendanceRecord> makeRecordClone() {
        Map<LocalDate, AttendanceRecord> clonedMap = new HashMap<>();
        for (Map.Entry<LocalDate, AttendanceRecord> entry : record.entrySet()) {
            clonedMap.put(entry.getKey(), new AttendanceRecord(entry.getValue()));
        }
        return clonedMap;
    }

    public Map<LocalDate, AttendanceRecord> getRecord() {
        return record;
    }
}
