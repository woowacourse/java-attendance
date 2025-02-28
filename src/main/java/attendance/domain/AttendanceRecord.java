package attendance.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttendanceRecord {

    private final Map<AttendanceDate, AttendanceTime> attendanceDateTimes;

    public AttendanceRecord(final Map<AttendanceDate, AttendanceTime> attendanceDateTimes) {
        validateNotNull(attendanceDateTimes);
        this.attendanceDateTimes = new HashMap<>(attendanceDateTimes);
    }

    private static void validateNotNull(final Map<AttendanceDate, AttendanceTime> attendanceRecord) {
        if (attendanceRecord == null) {
            throw new IllegalArgumentException(
                "출석 기록은 기록을 가지고 있어야 합니다.");
        }

        for (final Map.Entry<AttendanceDate, AttendanceTime> entry : attendanceRecord.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new IllegalArgumentException(
                    "출석 기록은 출석 날짜와 출석 시간을 가지고 있어야 합니다.");
            }
        }
    }

    public void addAttendanceDateTime(final AttendanceDateTime attendanceDateTime) {
        if (attendanceDateTimes.containsKey(
            attendanceDateTime.getAttendanceDate())) {
            throw new IllegalArgumentException("이미 해당 날짜의 출석 시간이 기록되어 있습니다.");
        }

        attendanceDateTimes.put(attendanceDateTime.getAttendanceDate(),
            attendanceDateTime.getAttendanceTime());
    }

    public Map<AttendanceDate, AttendanceTime> getAttendanceDateTimes() {
        return Map.copyOf(attendanceDateTimes);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AttendanceRecord that = (AttendanceRecord) o;

        return Objects.equals(attendanceDateTimes, that.attendanceDateTimes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendanceDateTimes);
    }
}
