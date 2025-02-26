package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Attendance add(LocalDateTime dateTime) {
        LocalDate date = LocalDate.from(dateTime);
        if (existsByDate(date)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하셨습니다. 수정 기능을 이용해주세요.");
        }
        Attendance attendance = Attendance.from(dateTime);
        attendances.add(attendance);
        return attendance;
    }

    public boolean existsByDate(LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isEqualToDate(date));
    }

    public Attendance updateAttendance(LocalDateTime updateDateTime) {
        LocalDate date = LocalDate.from(updateDateTime);
        Attendance before = findByDate(date);
        int index = attendances.indexOf(before);
        before.updateTime(updateDateTime.toLocalTime());
        attendances.set(index, before);
        return before;
    }

    private Attendance findByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isEqualToDate(date))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Warning calculateWarning() {
        int countTotalAbsence = countAbsence() + countLate() / 3;
        if (countTotalAbsence == 2) {
            return Warning.Warning;
        }
        if (countTotalAbsence == 3) {
            return Warning.INTERVIEW;
        }
        if (countTotalAbsence > 5) {
            return Warning.EXPULSION;
        }
        return Warning.NONE;
    }

    public int countAttend() {
        return Math.toIntExact(attendances.stream()
                .filter(attendance -> attendance.checkAttendanceStatus() == AttendanceStatus.ATTEND)
                .count());
    }

    public int countLate() {
        return Math.toIntExact(attendances.stream()
                .filter(attendance -> attendance.checkAttendanceStatus() == AttendanceStatus.LATE)
                .count());
    }

    public int countAbsence() {
        return Math.toIntExact(attendances.stream()
                .filter(attendance -> attendance.checkAttendanceStatus() == AttendanceStatus.ABSENCE)
                .count());
    }
}
