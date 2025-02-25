package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        Attendance attendance = new Attendance(new AttendanceDate(date), new AttendanceTime(LocalTime.from(dateTime)));
        attendances.add(attendance);
        return attendance;
    }

    public boolean existsByDate(LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isEqualToDate(date));
    }
}
