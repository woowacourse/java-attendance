package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances(List<LocalDateTime> attendances) {
        validate(attendances);
        this.attendances = attendances.stream()
                .map(Attendance::new)
                .toList();
    }

    public boolean has(LocalDate day) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.has(day));
    }

    private void validate(List<LocalDateTime> dateTimes) {
        Set<LocalDate> dates = dateTimes
                .stream()
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toSet());
        if (dateTimes.size() != dates.size()) {
            throw new IllegalArgumentException("동일한 날짜의 출석 기록은 등록할 수 없습니다");
        }
    }

    private Attendance findAttendanceByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.has(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(""));
    }

    public int countAttendance() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.ATTENDANCE)
                .count();
    }

    public int countTardy() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.TARDY)
                .count();
    }

    public int countAbsence(LocalDate endDate) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getStatus() == AttendanceStatus.ABSENCE)
                .count();
    }
}
