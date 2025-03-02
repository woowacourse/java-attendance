package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Attendances {
    private final List<Attendance> attendances = new ArrayList<>();

    public Attendances(List<LocalDateTime> attendances) {
        validate(attendances);
        this.attendances.addAll(attendances.stream()
                .map(Attendance::new)
                .toList());
    }

    public boolean has(LocalDate day) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.has(day));
    }

    public AttendanceStatus attend(LocalDate endDate, LocalDateTime attendDateTime) {
        if (has(attendDateTime.toLocalDate())) {
            throw new IllegalArgumentException("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요");
        }
        if (attendDateTime.toLocalDate().isAfter(endDate)) {
            throw new IllegalArgumentException("미래에 출석할 수 없습니다");
        }
        Attendance attendance = new Attendance(attendDateTime);
        this.attendances.add(attendance);
        return attendance.getStatus();
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
