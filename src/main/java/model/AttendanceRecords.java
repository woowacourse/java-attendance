package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AttendanceRecords {
    private final Map<LocalDate, AttendanceRecord> record = new HashMap<>();

    public void updateAttendanceStatusByLocalDate(LocalDateTime updateLocalDateTime) {
        //updateLocalDateTime을 통해서 만약 기존 정보에 LocalDate에 관한 정보가 있다면 업데이트하고 없다면 결석처리
        checkHoliday(updateLocalDateTime);
        AttendanceStatus newAttendanceStatus = AttendanceRuleByDay
                .calculateAttendance(updateLocalDateTime);

        record.entrySet().stream()
                .filter(e -> compareDayIsSame(updateLocalDateTime, e.getKey()))
                .findFirst()
                .ifPresentOrElse(
                        e -> modifyAttendanceRecord(updateLocalDateTime, e.getKey(), newAttendanceStatus),
                        () -> addAbsentRecordForStudent(updateLocalDateTime)
                );
    }

    public AttendanceStatus findAttendanceStatusFromRecordsByLocalDate(LocalDate localDate){
        return record.entrySet().stream()
                .filter(e -> e.getKey().equals(localDate))
                .map(Entry::getValue)
                .findFirst()
                .orElseThrow()
                .getAttendanceStatus();
    }

    private void modifyAttendanceRecord(LocalDateTime updateLocalDateTime, LocalDate recordLocalDate,
                                           AttendanceStatus newAttendanceStatus) {
        LocalTime localTime = LocalTime.from(updateLocalDateTime);
        record.put(recordLocalDate, new AttendanceRecord(localTime, newAttendanceStatus));
    }

    private void addAbsentRecordForStudent(LocalDateTime updateLocalDateTime) {
        record.put(LocalDate.from(updateLocalDateTime), new AttendanceRecord(null, AttendanceStatus.ABSENT));
    }

    private void checkHoliday(LocalDateTime localDateTime) {
        int day = localDateTime.getDayOfWeek().getValue();
        if (day == 6 || day == 7 || localDateTime.getDayOfMonth() == 25){
            throw new IllegalArgumentException("[주말 및 공휴일에는 등교일이 아닙니다]");
        };
    }

    public boolean compareDayIsSame(LocalDateTime localDateTime, LocalDate localDate) {
        return LocalDate.from(localDateTime).equals(localDate);
    }
}
