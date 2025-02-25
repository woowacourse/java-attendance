package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public static Attendances of(List<Attendance> attendances) {
        return new Attendances(attendances);
    }

    public Attendance addAttendance(final LocalDateTime dateTime) {
        Attendance attendance = new Attendance(dateTime);
        attendances.add(attendance);
        return attendance;
    }

    public Attendance deleteAttendance(final LocalDate date) {
        Attendance attendance = find(date);
        attendances.remove(attendance);
        return attendance;
    }

    public Attendance find(final LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 수정하려는 날짜는 출석할 수 없습니다."));
    }

    public void validateAlreadyAttendance(final LocalDate date) {
        if (find(date).isAlreadyChecked()) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
        }
    }

    public List<Attendance> getAttendancesBefore(final LocalDate date) {
        return attendances.stream()
                .sorted(Attendance::compareTo)
                .filter(attendance -> attendance.isBefore(date))
                .toList();
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }
}
