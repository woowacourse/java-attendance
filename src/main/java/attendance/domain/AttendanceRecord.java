package attendance.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AttendanceRecord {

    private final Map<AttendanceDate, AttendanceTime> attendanceDateTimes;

    public AttendanceRecord(final Map<AttendanceDate, AttendanceTime> attendanceDateTimes) {
        validateNotNull(attendanceDateTimes);
        this.attendanceDateTimes = new HashMap<>(attendanceDateTimes);
    }

    private void validateNotNull(final Map<AttendanceDate, AttendanceTime> attendanceRecord) {
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
        if (isContainsKey(attendanceDateTime.getAttendanceDate())) {
            throw new IllegalArgumentException("이미 해당 날짜의 출석 시간이 기록되어 있습니다.");
        }

        attendanceDateTimes.put(attendanceDateTime.getAttendanceDate(),
            attendanceDateTime.getAttendanceTime());
    }

    public void modifyAttendanceDateTime(final AttendanceDateTime attendanceDateTime) {
        if (!isContainsKey(attendanceDateTime.getAttendanceDate())) {
            throw new IllegalArgumentException("해당 날짜의 출석 시간이 기록되어 있지 않습니다.");
        }

        attendanceDateTimes.put(attendanceDateTime.getAttendanceDate(),
            attendanceDateTime.getAttendanceTime());
    }

    public AttendanceDateTime findByDate(final AttendanceDate attendanceDate) {
        if (!attendanceDateTimes.containsKey(attendanceDate)) {
            throw new IllegalArgumentException("해당 날짜의 출석 시간이 기록되어 있지 않습니다.");
        }

        final AttendanceTime attendanceTime = attendanceDateTimes.get(
            attendanceDate);

        return new AttendanceDateTime(attendanceDate, attendanceTime);
    }

    private boolean isContainsKey(final AttendanceDate attendanceDate) {
        return attendanceDateTimes.containsKey(
            attendanceDate);
    }

    public List<AttendanceDateTime> findAllUntilDate(final AttendanceDate untilDate) {
        AttendanceDate currentDate = AttendanceDate.FIRST_DATE;
        final List<AttendanceDateTime> attendanceRecord = new ArrayList<>();

        while (currentDate.isBefore(untilDate)) {
            attendanceRecord.add(extractAttendanceDateTime(currentDate));
            currentDate = currentDate.plusDay();
        }

        return attendanceRecord;
    }

    private AttendanceDateTime extractAttendanceDateTime(final AttendanceDate date) {
        final AttendanceTime attendanceTime = attendanceDateTimes.getOrDefault(
            date, AttendanceTime.EMPTY);

        return new AttendanceDateTime(date, attendanceTime);
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
