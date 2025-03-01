package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            throw CustomException.from(ErrorMessage.ALREDAY_PRESENCE_ATTENDANCE_RECORD);
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
            if (attendanceTime.isSameDateTime(inputTime)) {
                newRecords.add(attendanceTime.modifyAttendanceTime(inputTime));
                continue;
            }
            newRecords.add(attendanceTime);
        }
        return new AttendanceRecord(newRecords);
    }

    public int checkLateCounts() {
        int totalLateCounts = 0;
        for (AttendanceTime attendanceTime : attendanceRecord) {
            totalLateCounts = calculateLateCounts(attendanceTime.isLate(), totalLateCounts);
        }
        return totalLateCounts;
    }

    public int checkAbsenceCounts() {
        int totalAbsenceCounts = 0;
        for (AttendanceTime attendanceTime : attendanceRecord) {
            totalAbsenceCounts = calculateLateCounts(attendanceTime.isAbsence(), totalAbsenceCounts);
        }
        return totalAbsenceCounts;
    }

    public int checkAttendanceCounts() {
        int totalAttendanceCounts = 0;
        for (AttendanceTime attendanceTime : attendanceRecord) {
            totalAttendanceCounts = calculateLateCounts(attendanceTime.isAttendance(), totalAttendanceCounts);
        }
        return totalAttendanceCounts;
    }

    private static int calculateLateCounts(boolean attendanceTime, int totalLateCounts) {
        if (attendanceTime) {
            totalLateCounts += 1;
        }
        return totalLateCounts;
    }

    public List<AttendanceTime> getAttendanceRecord() {
        return Collections.unmodifiableList(attendanceRecord);
    }

}
