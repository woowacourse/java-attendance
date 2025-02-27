package attendance.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceRecord {
    private final List<AttendanceTime> attendanceRecord;

    public AttendanceRecord() {
        this.attendanceRecord = new ArrayList<>();
    }

    public void addAttendanceTime(LocalDateTime attendanceTime) {
        this.attendanceRecord.add(new AttendanceTime(attendanceTime));
    }

    public List<AttendanceTime> getAttendanceRecord() {
        return Collections.unmodifiableList(attendanceRecord);
    }

    public PenaltyType checkPenaltyStatus() {
        int absenceCounts = 0;
        for (AttendanceTime attendanceTime : attendanceRecord) {
            if (attendanceTime.isAbsence(AttendanceStatus.ABSENCE)) {
                absenceCounts += 1;
            }
        }
        return PenaltyType.fetchPenaltyType(absenceCounts);
    }
}
