package attendance.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import attendance.exception.AttendanceArgumentException;

public record Attendances(Map<LocalDate, Attendance> attendances) {
    public static final String DUPLICATE_DATE = "이미 출석되었습니다. 수정 기능을 이용해주세요.";

    public Attendances() {
        this(new HashMap<>());
    }

    public void add(Attendance attendance) {
        attendances.put(attendance.dateTime().toLocalDate(), attendance);
    }

    public Optional<Attendance> findAttendance(LocalDate date) {
        return Optional.ofNullable(attendances.get(date));
    }

    public void remove(LocalDate date) {
        attendances.remove(date);
    }

    public void validateDuplicate(LocalDate date) {
        if (attendances.containsKey(date)) {
            throw new AttendanceArgumentException(DUPLICATE_DATE);
        }
    }

    public Map<LocalDate, Attendance> getAttendances() {
        return attendances;
    }
}
