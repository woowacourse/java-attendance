package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(final List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Attendances registerAttendance(final LocalDateTime dateTime) {
        Attendance before = findAttendanceByDate(dateTime.toLocalDate());
        validateAlreadyAttendance(before);
        return new Attendances(createUpdateAttendances(dateTime, before));
    }

    public Attendances updateAttendance(final LocalDateTime dateTime) {
        Attendance before = findAttendanceByDate(dateTime.toLocalDate());
        return new Attendances(createUpdateAttendances(dateTime, before));
    }

    public Attendance findAttendanceByDate(final LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 입력된 날짜(일)에는 출석 기록이 존재하지 않습니다."));
    }

    public List<Attendance> getAttendancesBefore(LocalDate today) {
        return attendances.stream()
                .filter(attendance -> attendance.isDateBefore(today))
                .toList();
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    private void validateAlreadyAttendance(final Attendance before) {
        if (before.isNotDefaultTime()) {
            throw new IllegalArgumentException("[ERROR] 이미 출석이 등록되었습니다. 수정 기능을 이용 해주세요.");
        }
    }

    private List<Attendance> createUpdateAttendances(final LocalDateTime dateTime, final Attendance before) {
        List<Attendance> newAttendances = new ArrayList<>(attendances);
        newAttendances.remove(before);
        newAttendances.add(Attendance.createFromDateTime(dateTime));
        return newAttendances;
    }
}
