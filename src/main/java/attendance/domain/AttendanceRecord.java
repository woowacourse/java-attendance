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

    public List<AttendanceTime> getAttendanceRecord() {
        return Collections.unmodifiableList(attendanceRecord);
    }

}
