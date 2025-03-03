package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceDateTimes {
    private final List<AttendanceDateTime> attendanceDateTimes;

    public AttendanceDateTimes(List<AttendanceDateTime> attendanceDateTimes) {
        this.attendanceDateTimes = attendanceDateTimes;
    }

    public AttendanceDateTimes add(LocalDateTime attendanceDateTime) {
        this.attendanceDateTimes.add(new AttendanceDateTime(attendanceDateTime));
        return this;
    }

    public boolean contains(LocalDate attendanceDate) {
        return attendanceDateTimes.stream()
                .map(AttendanceDateTime::toDate)
                .anyMatch(attendanceDate::isEqual);
    }

    public AttendanceDateTime remove(LocalDate targetDate) {
        return attendanceDateTimes.stream()
                .filter(dateTime -> dateTime.toDate().isEqual(targetDate))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 없는 날짜는 수정할 수 없습니다."));
    }

    public int getPresentCount(LocalDate lastDate) {
        return (int) attendanceDateTimes.stream()
                .filter(attendanceDateTime -> attendanceDateTime.isDateBefore(lastDate))
                .filter(attendanceDateTime -> attendanceDateTime.isStatusOf(AttendanceStatus.PRESENT))
                .count();
    }

    public int getTardyCount(LocalDate lastDate) {
        return (int) attendanceDateTimes.stream()
                .filter(attendanceDateTime -> attendanceDateTime.isDateBefore(lastDate))
                .filter(attendanceDateTime -> attendanceDateTime.isStatusOf(AttendanceStatus.TARDY))
                .count();
    }

    public int getAbsentCount(LocalDate lastDate) {
        int totalCount = AttendanceDateTime.countValidDays(lastDate);
        return totalCount - getPresentCount(lastDate) - getTardyCount(lastDate);
    }

    public String getDisciplinaryStatus() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttendanceDateTimes that = (AttendanceDateTimes) o;
        return attendanceDateTimes.equals(that.attendanceDateTimes);
    }

    @Override
    public int hashCode() {
        return attendanceDateTimes.hashCode();
    }
}
