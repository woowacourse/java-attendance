package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceManager {
    private final Map<Student, Map<LocalDate, AttendanceRecord>> record = new HashMap<>();


    public void findAttendanceStatusByLocalDate(LocalDateTime updateLocalDateTime, Student student) {
        int day = updateLocalDateTime.getDayOfWeek().getValue();
        if (checkHoliday(updateLocalDateTime, day)) {
            throw new IllegalArgumentException("[주말 및 공휴일에는 등교일이 아닙니다]");
        }
        AttendanceStatus newAttendanceStatus = AttendanceRuleByDay.
                calculateAttendance(day, LocalTime.from(updateLocalDateTime));
        Map<LocalDate, AttendanceRecord> studentAttendanceRecord = record.get(student);
        for (LocalDate recordLocalDate : studentAttendanceRecord.keySet()) {
            if (compareDayAndModify(updateLocalDateTime, recordLocalDate, newAttendanceStatus, student)) {
                return;
            }
        }

        updateRecordForNoLocalDateTime(updateLocalDateTime, newAttendanceStatus);
    }

    private static boolean checkHoliday(LocalDateTime localDateTime, int day) {
        return day == 6 || day == 7 || localDateTime.getDayOfMonth() == 25;
    }

    private boolean compareDayAndModify(LocalDateTime updateLocalDateTime, LocalDate recordLocalDate,
                                        AttendanceStatus newAttendanceStatus, Student student) {
        if (compareDayIsSame(updateLocalDateTime, recordLocalDate)) {
            return modifyAttendanceRecord(updateLocalDateTime, recordLocalDate, newAttendanceStatus, student);
        }
        return false;
    }

    public boolean compareDayIsSame(LocalDateTime localDateTime, LocalDate localDate) {
        return LocalDate.from(localDateTime).equals(localDate);
    }

    private boolean modifyAttendanceRecord(LocalDateTime updateLocalDateTime, LocalDate recordLocalDate,
                                           AttendanceStatus newAttendanceStatus, Student student) {
        //Map에 대한 기록 새롭게 하고 Student에는 oldStatus와 newStatus를 보내서 Student에서 oldStatus의 경우 -를 newStatus는 +를 하도록 처리
        Map<LocalDate, AttendanceRecord> studentAttendanceRecord = record.get(student);
        AttendanceStatus oldAttendanceStatus = studentAttendanceRecord.get(recordLocalDate).getAttendanceStatus();

        if (oldAttendanceStatus.equals(AttendanceStatus.ATTENDANCE)) {
            if (modifyRecordAndUpdateStudentForAttendance(updateLocalDateTime, recordLocalDate, attendanceStatus)) {
                return true;
            }
        }

        if (oldAttendanceStatus.equals(AttendanceStatus.LATE)) {
            if (modifyRecordAndUpdateStudentForLate(updateLocalDateTime, localDateTime1, attendanceStatus)) {
                return true;
            }
        }

        if (oldAttendanceStatus.equals(AttendanceStatus.ABSENT)) {
            return modifyRecordAndUpdateStudentForAbsent(updateLocalDateTime, localDateTime1, attendanceStatus);
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




}
