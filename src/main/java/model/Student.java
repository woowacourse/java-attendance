package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
    private AttendanceRecords attendanceRecords;
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

    public void updateState(LocalDateTime updateDateTime) {
        int day = updateDateTime.getDayOfWeek().getValue();
        if (checkHoliday(updateDateTime, day)) {
            return;
        }
        AttendanceStatus oldAttendanceStatus = AttendanceRuleByDay.
                calculateAttendance(day, LocalTime.from(updateDateTime));

        for (LocalDateTime recordDateTime : record.keySet()) {
            if (compareDayAndModify(updateDateTime, recordDateTime, oldAttendanceStatus)) {
                return; //비교하는 날짜가 같다면
            }
        }

        updateRecordForNoLocalDateTime(updateDateTime, oldAttendanceStatus); //비교하는 날짜에대한 기록이 없다면
    }

    private boolean compareDayAndModify(LocalDateTime updateDateTime, LocalDateTime recordDateTime,
                              AttendanceStatus oldAttendanceStatus) {
        //비교하는 날짜가 존재한다면 modifyByState 로 수정
        if (compareDayIsSame(recordDateTime, updateDateTime)) {
            return modifyByState(updateDateTime, recordDateTime, oldAttendanceStatus);
        }
        return false;
    }

    private boolean modifyByState(LocalDateTime updateDateTime, LocalDateTime recordDateTime,
                              AttendanceStatus oldAttendanceStatus) {
        if (record.get(recordDateTime).equals(AttendanceStatus.ATTENDANCE)) {
            if (modifyRecordAndUpdateStudentForAttendance(updateDateTime, recordDateTime, oldAttendanceStatus)) {
                return true;
            }
        }

        if (record.get(recordDateTime).equals(AttendanceStatus.LATE)) {
            if (modifyRecordAndUpdateStudentForLate(updateDateTime, recordDateTime, oldAttendanceStatus)) {
                return true;
            }
        }

        if (record.get(recordDateTime).equals(AttendanceStatus.ABSENT)) {
            return modifyRecordAndUpdateStudentForAbsent(updateDateTime, recordDateTime, oldAttendanceStatus);
        }
        return false;
    }

    private boolean modifyRecordAndUpdateStudentForState(LocalDateTime localDateTime,
                                                         LocalDateTime localDateTime1,
                                                         AttendanceStatus oldStatus,
                                                         AttendanceStatus newStatus) {
        if (oldStatus == AttendanceStatus.ABSENT) {
            absent--;
        }
        if (oldStatus == AttendanceStatus.LATE) {
            late--;
        }
        if (oldStatus == AttendanceStatus.ATTENDANCE) {
            attendance--;
        }

        modifyRecord(localDateTime, localDateTime1, newStatus);

        return updateStudentAfterModify(newStatus);
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

    public Map<LocalDateTime, AttendanceStatus> getRecord() {
        return record;
    }

    public boolean compareDayIsSame(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return LocalDate.from(localDateTime1).equals(LocalDate.from(localDateTime2));
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
