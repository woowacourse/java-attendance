package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Attendances {
    private final List<LocalDateTime> attendances;

    public Attendances(List<LocalDateTime> attendances) {
        this.attendances = attendances;
    }

    public Attendances add(LocalDateTime attendanceTime) {
        this.attendances.add(attendanceTime);
        return this;
    }

    public boolean haveAttendanceDate(LocalDate attendanceDate) {
        return attendances.stream()
                .map(LocalDateTime::toLocalDate)
                .anyMatch(attendanceDate::isEqual);
    }

    public LocalDateTime edit(LocalDate oldAttendanceTime) {
        return attendances.stream()
                .filter(dateTime -> dateTime.toLocalDate().isEqual(oldAttendanceTime))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 없습니다."));
    }


    public int getAttendanceCount(LocalDate standardDate) {
        return (int) attendances.stream()
                .filter(attendanceDateTime -> attendanceDateTime.toLocalDate().isBefore(standardDate))
                .filter(attendanceDateTime -> AttendanceResult.getAttendanceResult(attendanceDateTime)
                        == AttendanceResult.ATTENDANCE)
                .count();
    }

    public int getLateCount(LocalDate standardDate) {
        return (int) attendances.stream()
                .filter(attendanceDateTime -> attendanceDateTime.toLocalDate().isBefore(standardDate))
                .filter(attendanceDateTime -> AttendanceResult.getAttendanceResult(attendanceDateTime)
                        == AttendanceResult.LATE)
                .count();
    }

    public int getAbsentCount(LocalDate standardDate) {
        return (int) attendances.stream()
                .filter(attendanceDateTime -> attendanceDateTime.toLocalDate().isBefore(standardDate))
                .filter(attendanceDateTime -> AttendanceResult.getAttendanceResult(attendanceDateTime)
                        == AttendanceResult.ABSENT)
                .count();
    }
}
