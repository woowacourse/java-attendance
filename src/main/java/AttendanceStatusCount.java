import java.util.HashMap;
import java.util.Map;

public class AttendanceStatusCount {
    final Map<AttendanceStatus, Long> attendanceStatusCount;

    public AttendanceStatusCount() {
        this.attendanceStatusCount = new HashMap<>();
    }

    public void updateAttendanceCount(AttendanceStatusRecord attendanceStatusRecord) {
        long attendanceCount = attendanceStatusRecord.findAttendanceStatusCount(AttendanceStatus.ATTENDANCE);
        long lateCount = attendanceStatusRecord.findAttendanceStatusCount(AttendanceStatus.LATE);
        long absentCount = attendanceStatusRecord.findAttendanceStatusCount(AttendanceStatus.ABSENT);

        attendanceStatusCount.put(AttendanceStatus.ATTENDANCE, attendanceCount);
        attendanceStatusCount.put(AttendanceStatus.LATE, lateCount);
        attendanceStatusCount.put(AttendanceStatus.ABSENT, absentCount);
    }
}