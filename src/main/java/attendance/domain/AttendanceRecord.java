package attendance.domain;

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
        int absenceCounts = 0;
        for (AttendanceTime attendanceTime : attendanceRecord) {
            absenceCounts += calculateAbsenceCounts(attendanceTime);
        }
        return PenaltyType.fetchPenaltyType(absenceCounts);
    }

    private int calculateAbsenceCounts(AttendanceTime attendanceTime) {
        if (attendanceTime.isAbsence()) {
            return 1;
        }
        return 0;
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
            throw new IllegalArgumentException("이미 출석 기록이 있습니다.");
        }
    }

    public AttendanceTime findAttendanceRecord(LocalDateTime inputTime) {
        return attendanceRecord.stream()
                .filter(attendanceTime -> attendanceTime.isSameDateTime(inputTime))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 없습니다."));
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

    public List<AttendanceTime> getAttendanceRecord() {
        return Collections.unmodifiableList(attendanceRecord);
    }

}
