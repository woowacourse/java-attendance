package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(Attendance attendance) {
        validateAlreadyAttended(attendance);
        attendances.add(attendance);
    }

    private void validateAlreadyAttended(Attendance attendance) {
        attendances.stream()
                .filter(existAttendance -> existAttendance.getAttendanceDateTime().getDayOfMonth()
                        == attendance.getAttendanceDateTime().getDayOfMonth())
                .findFirst()
                .ifPresent(exception -> {
                    throw new IllegalArgumentException("\n[ERROR] 이미 출석을 완료했습니다. 수정 기능을 이용해주세요.");
                });
    }

    public Attendance get(LocalDate modifyingDate) {
        return attendances.stream()
                .filter(attendance -> attendance.getAttendanceDateTime().getDayOfMonth() == modifyingDate.getDayOfMonth())
                .findFirst()
                .orElse(Attendance.of(LocalDateTime.of(modifyingDate, LocalTime.MIN)));
    }

    public Attendance modify(Attendance existAttendance, LocalTime modifyingTime) {
        LocalDate modifyingDate = existAttendance.getAttendanceDateTime().toLocalDate();
        attendances.remove(existAttendance);
        return attendances.stream()
                .filter(attendance -> attendance.getAttendanceDateTime().toLocalDate() == modifyingDate)
                .peek(attendance -> attendance.modify(LocalDateTime.of(modifyingDate, modifyingTime)))
                .findFirst()
                .orElseGet(() -> Attendance.of(LocalDateTime.of(modifyingDate, modifyingTime)));
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
