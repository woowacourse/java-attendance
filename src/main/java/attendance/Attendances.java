package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class Attendances {
    private final Map<AttendanceDate, AttendanceTime> attendances;

    public Attendances(Map<AttendanceDate, AttendanceTime> attendances) {
        this.attendances = attendances;
    }

    public void add(LocalDateTime dateTime) {
        LocalDate date = LocalDate.from(dateTime);
        if (existsByDate(date)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하셨습니다. 수정 기능을 이용해주세요.");
        }
        attendances.put(new AttendanceDate(date), new AttendanceTime(LocalTime.from(dateTime)));
    }

    public boolean existsByDate(LocalDate date) {
        return attendances.keySet()
                .stream()
                .anyMatch(attendanceDate -> attendanceDate.isEqualToDate(date));
    }
}
