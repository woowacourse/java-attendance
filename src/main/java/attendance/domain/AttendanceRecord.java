package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class AttendanceRecord {
    private final List<AttendanceTime> attendanceRecord;

    public AttendanceRecord(List<AttendanceTime> attendanceRecord) {
        this.attendanceRecord = new ArrayList<>(attendanceRecord);
    }

    public PenaltyType checkPenaltyStatus() {
        return PenaltyType.fetchPenaltyType(checkAbsenceCounts(), checkLateCounts());
    }

    public AttendanceTime registerAttendance(LocalDateTime inputTime) {
        validateSameTime(inputTime);
        AttendanceTime attendanceTime = new AttendanceTime(inputTime);
        attendanceRecord.add(attendanceTime);
        return attendanceRecord.getLast();
    }

    private void validateSameTime(LocalDateTime inputTime) {
        for (AttendanceTime attendanceTime : attendanceRecord) {
            checkSameDateTime(inputTime, attendanceTime);
        }
    }

    private void checkSameDateTime(LocalDateTime inputTime, AttendanceTime attendanceTime) {
        if (attendanceTime.isSameDateTime(inputTime)) {
            throw CustomException.from(ErrorMessage.ALREADY_PRESENCE_ATTENDANCE_RECORD);
        }
    }

    public AttendanceTime findAttendanceRecord(LocalDateTime inputTime) {
        return attendanceRecord.stream()
                .filter(attendanceTime -> attendanceTime.isSameDateTime(inputTime))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.NOT_ATTENDANCE_RECORD));
    }

    public AttendanceRecord modifyAttendanceTime(LocalDateTime inputTime) {
        List<AttendanceTime> newRecords = new ArrayList<>();
        for (AttendanceTime attendanceTime : attendanceRecord) {
            findSameDateTime(inputTime, attendanceTime, newRecords);
        }
        return new AttendanceRecord(newRecords);
    }

    private static void findSameDateTime(LocalDateTime inputTime, AttendanceTime attendanceTime,
                                         List<AttendanceTime> newRecords) {
        if (attendanceTime.isSameDateTime(inputTime)) {
            newRecords.add(attendanceTime.modifyAttendanceTime(inputTime));
            return;
        }
        newRecords.add(attendanceTime);
    }

    public int checkLateCounts() {
        return countMatchingRecords(AttendanceTime::isLate);
    }

    public int checkAbsenceCounts() {
        return countMatchingRecords(AttendanceTime::isAbsence);
    }

    public int checkAttendanceCounts() {
        return countMatchingRecords(AttendanceTime::isAttendance);
    }

    private int countMatchingRecords(Predicate<AttendanceTime> condition) {
        return (int) attendanceRecord.stream()
                .filter(condition)
                .count();
    }

    public List<AttendanceTime> getAttendanceRecord() {
        return Collections.unmodifiableList(attendanceRecord);
    }

}
