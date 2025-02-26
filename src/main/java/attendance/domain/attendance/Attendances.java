package attendance.domain.attendance;

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

    private static final class Constant {
        private static final String CANT_FIND_ATTENDANCE = "출석 정보를 찾을 수 없습니다.";

        public static final String ATTENDANCE_FORMAT = "MM월 d일 E요일 HH:MM ";
        public static final String ABSENCE = "MM월 d일 E요일 --:-- (결석)\n";
        public static final String STATUS = "(%s)\n";

        private Constant() {
        }
    }
}
