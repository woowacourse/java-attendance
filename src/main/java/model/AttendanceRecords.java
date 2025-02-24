package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceRecords {
    private final Map<LocalDate, AttendanceRecord> record;

    public AttendanceRecords(Map<LocalDate, AttendanceRecord> record) {
        this.record = record;
    }

    public void updateAttendanceStatusByLocalDate(LocalDateTime updateLocalDateTime) {
        checkHoliday(updateLocalDateTime);
        checkOpeningHours(updateLocalDateTime);
        AttendanceStatus newAttendanceStatus = AttendanceRuleByDay
                .calculateAttendance(updateLocalDateTime);

        record.entrySet().stream()
                .filter(e -> compareDayIsSame(updateLocalDateTime, e.getKey()))
                .findFirst()
                .ifPresentOrElse(
                        e -> modifyAttendanceRecord(updateLocalDateTime, e.getKey(), newAttendanceStatus),
                        () -> {throw new IllegalArgumentException("[ERROR] 등록되지 않은 날짜입니다.");}
                );
    }

    public void registerAttendanceRecord(LocalDateTime localDateTime){
        checkHoliday(localDateTime);
        checkOpeningHours(localDateTime);
        LocalDate localDate = LocalDate.from(localDateTime);
        LocalTime localTime = LocalTime.from(localDateTime);
        AttendanceStatus attendanceStatus = AttendanceRuleByDay.calculateAttendance(localDateTime);
        AttendanceRecord attendanceRecord = new AttendanceRecord(localTime, attendanceStatus);
        record.put(localDate, attendanceRecord);
    }

    public int findTotalAttendanceCount(){
        return (int) record.entrySet().stream()
                .filter(e-> e.getValue().getAttendanceStatus().equals(AttendanceStatus.ATTENDANCE))
                .count();
    }

    public int findTotalLateCount(){
        return (int) record.entrySet().stream()
                .filter(e-> e.getValue().getAttendanceStatus().equals(AttendanceStatus.LATE))
                .count();
    }

    public int findTotalAbsentCount(){
        return (int) record.entrySet().stream()
                .filter(e-> e.getValue().getAttendanceStatus().equals(AttendanceStatus.ABSENT))
                .count();
    }

    private void modifyAttendanceRecord(LocalDateTime updateLocalDateTime, LocalDate recordLocalDate,
                                           AttendanceStatus newAttendanceStatus) {
        LocalTime localTime = LocalTime.from(updateLocalDateTime);
        record.put(recordLocalDate, new AttendanceRecord(localTime, newAttendanceStatus));
    }

    public void createAttendanceRecords(LocalDateTime today){
        AttendanceStatus attendanceStatus = AttendanceRuleByDay.calculateAttendance(today);
        record.put(LocalDate.from(today), new AttendanceRecord(LocalTime.from(today), attendanceStatus));
    }

    public void updateStateNotExistInFile(LocalDate today) {
        LocalDateTime standard = LocalDateTime.of(2024,12,1,0,0);
        Map<LocalDate, AttendanceRecord> recordClone = makeRecordClone();
        while (!compareDayIsSame(standard,LocalDate.from(today))) {
            if (isExistLocalDate(recordClone,standard)) {
                standard = standard.plusDays(1);
                continue;
            }
            addAbsentRecordForStudent(standard);
            standard = standard.plusDays(1);
        }
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

    public LocalDateTime findLocalDateTime(LocalDateTime localDateTime) {
        return record.entrySet().stream()
                .filter(e -> compareDayIsSame(localDateTime, e.getKey()))
                .map(e-> LocalDateTime.of(e.getKey(), e.getValue().getAttendanceTime()))
                .findFirst()
                .orElse(null);
//        for (LocalDate localDate : record.keySet()) {
//            if (compareDayIsSame(localDateTime, localDate)) {
//                LocalTime localTime = record.get(localDate).getAttendanceTime();
//                return LocalDateTime.of(localDate, localTime);
//            }
//        }
//        return null;
    }
    public AttendanceStatus findAttendanceStatusByLocalDateTime(LocalDateTime localDateTime){
        return record.entrySet().stream()
                .filter(e -> compareDayIsSame(localDateTime, e.getKey()))
                .map(e -> e.getValue().getAttendanceStatus())
                .findFirst().orElse(null);
    }


    private void checkOpeningHours(LocalDateTime localDateTime){
        LocalTime localTime = LocalTime.from(localDateTime);
        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(23,0);
        if (localTime.isBefore(startTime) || localTime.isAfter(endTime)){
            throw new IllegalArgumentException("[캠퍼스 운영 시간이 아닙니다.]");
        }
    }

    public boolean compareDayIsSame(LocalDateTime localDateTime, LocalDate localDate) {
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
