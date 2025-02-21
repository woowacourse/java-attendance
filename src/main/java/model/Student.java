package model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student {

    private final HashMap<LocalDateTime, AttendanceStatus> record = new HashMap<>();
    private final String name;
    private int absent;
    private int attendance;
    private int late;

    public int getLate() {
        return late;
    }

    public int getAttendance() {
        return attendance;
    }

    public String getName() {
        return name;
    }

    public int getAbsent() {
        return absent;
    }

    public Student(String name) {
        this.name = name;
    }

    public void updateState(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        int day = dayOfWeek.getValue();
        if (checkHoliday(localDateTime, day)) {
            return;
        }
        AttendanceStatus attendanceStatus = AttendanceRuleByDay.
                calculateAttendance(day, LocalTime.from(localDateTime));

        for (LocalDateTime localDateTime1 : record.keySet()) {
            if (compareDayAndModify(localDateTime, localDateTime1, attendanceStatus)) {
                return;
            }
        }

        updateRecordForNoLocalDateTime(localDateTime, attendanceStatus);
    }

    private boolean compareDayAndModify(LocalDateTime localDateTime, LocalDateTime localDateTime1,
                              AttendanceStatus attendanceStatus) {
        if (compareDayIsSame(localDateTime1, localDateTime)) {
            return modifyByState(localDateTime, localDateTime1, attendanceStatus);
        }
        return false;
    }

    private boolean modifyByState(LocalDateTime localDateTime, LocalDateTime localDateTime1,
                              AttendanceStatus attendanceStatus) {
        if (record.get(localDateTime1).equals(AttendanceStatus.ATTENDANCE)) {
            if (modifyRecordAndUpdateStudentForAttendance(localDateTime, localDateTime1, attendanceStatus)) {
                return true;
            }
        }

        if (record.get(localDateTime1).equals(AttendanceStatus.LATE)) {
            if (modifyRecordAndUpdateStudentForLate(localDateTime, localDateTime1, attendanceStatus)) {
                return true;
            }
        }

        if (record.get(localDateTime1).equals(AttendanceStatus.ABSENT)) {
            return modifyRecordAndUpdateStudentForAbsent(localDateTime, localDateTime1, attendanceStatus);
        }
        return false;
    }

    private boolean modifyRecordAndUpdateStudentForAbsent(LocalDateTime localDateTime, LocalDateTime localDateTime1,
                              AttendanceStatus attendanceStatus) {
        absent --;
        return modifyRecordAndUpdateStudentForState(localDateTime, localDateTime1, attendanceStatus);
    }

    private boolean modifyRecordAndUpdateStudentForLate(LocalDateTime localDateTime, LocalDateTime localDateTime1,
                              AttendanceStatus attendanceStatus) {
        late --;
        return modifyRecordAndUpdateStudentForState(localDateTime, localDateTime1, attendanceStatus);
    }

    private boolean modifyRecordAndUpdateStudentForState(LocalDateTime localDateTime, LocalDateTime localDateTime1,
                              AttendanceStatus attendanceStatus) {
        modifyRecord(localDateTime, localDateTime1, attendanceStatus);
        assert attendanceStatus != null;
        return updateStudentAfterModify(attendanceStatus);
    }

    private boolean modifyRecordAndUpdateStudentForAttendance(LocalDateTime localDateTime, LocalDateTime localDateTime1,
                              AttendanceStatus attendanceStatus) {
        attendance --;
        return modifyRecordAndUpdateStudentForState(localDateTime, localDateTime1, attendanceStatus);
    }

    private static boolean checkHoliday(LocalDateTime localDateTime, int day) {
        return day == 6 || day == 7 || localDateTime.getDayOfMonth() == 25;
    }

    private void updateRecordForNoLocalDateTime(LocalDateTime localDateTime, AttendanceStatus attendanceStatus) {
        record.putIfAbsent(localDateTime, attendanceStatus);

        assert attendanceStatus != null;
        if (attendanceStatus.equals(AttendanceStatus.ATTENDANCE)){
            attendance++;
            return;
        }
        if (attendanceStatus.equals(AttendanceStatus.LATE)){
            late++;
            return;
        }
        absent++;
    }

    private void modifyRecord(LocalDateTime localDateTime, LocalDateTime localDateTime1,
                           AttendanceStatus attendanceStatus) {
        record.remove(localDateTime1);
        record.putIfAbsent(localDateTime, attendanceStatus);
    }

    private boolean updateStudentAfterModify(AttendanceStatus attendanceStatus) {
        if (attendanceStatus.equals(AttendanceStatus.ATTENDANCE)){
            attendance++;
            return true;
        }
        if (attendanceStatus.equals(AttendanceStatus.LATE)){
            late++;
            return true;
        }
        if (attendanceStatus.equals(AttendanceStatus.ABSENT)){
            absent++;
            return true;
        }
        return false;
    }

    public HashMap<LocalDateTime, AttendanceStatus> getRecord() {
        return record;
    }

    public boolean compareDayIsSame(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        LocalDateTime dayDate1 = localDateTime1.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime dayDate2 = localDateTime2.truncatedTo(ChronoUnit.DAYS);

        return (dayDate1.compareTo(dayDate2) == 0);
    }

    public LocalDateTime findLocalDateTime(LocalDateTime localDateTime) {
        for (LocalDateTime localDateTime1 : record.keySet()) {
            if (compareDayIsSame(localDateTime1,localDateTime)) {
                return localDateTime1;
            }
        }
        return null;
    }

    public String findStateByLocalDateTime(LocalDateTime localDateTime) {
        for (LocalDateTime localDateTime1 : record.keySet()) {
            if (compareDayIsSame(localDateTime1,localDateTime)) {
                return record.get(localDateTime1).getState();
            }
        }
        return null;
    }

    public int calculateAbsent() {
        return absent + late/3;
    }

    private HashMap<LocalDateTime, AttendanceStatus> makeRecordClone() {
        HashMap<LocalDateTime, AttendanceStatus> recordClone = new HashMap<>();
        for (LocalDateTime localDateTime : record.keySet()) {
            recordClone.putIfAbsent(localDateTime, record.get(localDateTime));
        }
        return recordClone;
    }

    private boolean isExistLocalDate(HashMap<LocalDateTime, AttendanceStatus> recordClone, LocalDateTime localDateTime) {
        List<LocalDateTime> localDateTimes = new ArrayList<>(recordClone.keySet());
        for (LocalDateTime localDateTime1 : localDateTimes) {
            if (compareDayIsSame(localDateTime,localDateTime1)) {
                return true;
            }
        }
        return false;
    }

    public void updateStateNotExistInFile(LocalDateTime today) {
        LocalDateTime standard = LocalDateTime.of(2024,12,1,0,0);
        HashMap<LocalDateTime, AttendanceStatus> recordClone = makeRecordClone();
        while (!compareDayIsSame(standard,today)) {
            if (isExistLocalDate(recordClone,standard)) {
                standard = standard.plusDays(1);
                continue;
            }
            updateState(standard);
            standard = standard.plusDays(1);
            }
        }
}
