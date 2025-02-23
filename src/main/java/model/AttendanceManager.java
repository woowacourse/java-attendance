package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceManager {
    private Map<Student, Map<LocalDate, AttendanceRecord>> record = new HashMap<>();

    public void updateAttendanceStatusByLocalDate(LocalDateTime updateLocalDateTime, Student student) {
        //updateLocalDateTime을 통해서 만약 기존 정보에 LocalDate에 관한 정보가 있다면 업데이트하고 없다면 결석처리
        int day = updateLocalDateTime.getDayOfWeek().getValue();
        checkHoliday(updateLocalDateTime, day);
        AttendanceStatus newAttendanceStatus = AttendanceRuleByDay.
                calculateAttendance(day, LocalTime.from(updateLocalDateTime));
        Map<LocalDate, AttendanceRecord> studentAttendanceRecord = record.get(student);
        for (LocalDate recordLocalDate : studentAttendanceRecord.keySet()) {
            if (compareDayIsSame(updateLocalDateTime, recordLocalDate)) {
                modifyAttendanceRecord(updateLocalDateTime, recordLocalDate, newAttendanceStatus, student);
                return;
            }
        }
        record.get(student).put(LocalDate.from(updateLocalDateTime), new AttendanceRecord(null, AttendanceStatus.ABSENT));
    }


    private void modifyAttendanceRecord(LocalDateTime updateLocalDateTime, LocalDate recordLocalDate,
                                           AttendanceStatus newAttendanceStatus, Student student) {
        LocalTime localTime = LocalTime.from(updateLocalDateTime);
        record.get(student).put(recordLocalDate, new AttendanceRecord(localTime, newAttendanceStatus));
    }

    private void checkHoliday(LocalDateTime localDateTime, int day) {
        if (day == 6 || day == 7 || localDateTime.getDayOfMonth() == 25){
            throw new IllegalArgumentException("[주말 및 공휴일에는 등교일이 아닙니다]");
        };
    }

    public boolean compareDayIsSame(LocalDateTime localDateTime, LocalDate localDate) {
        return LocalDate.from(localDateTime).equals(localDate);
    }
}
