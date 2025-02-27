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

    public Attendances processCheck(final LocalDateTime dateTime) {
        Attendance before = findAttendanceByDate(dateTime.toLocalDate());

        if (before.isNotDefaultTime()) {
            throw new IllegalArgumentException("[ERROR] 이미 출석이 등록되었습니다. 수정 기능을 이용 해주세요.");
        }

        List<Attendance> newAttendances = new ArrayList<>(attendances);
        newAttendances.remove(before);
        newAttendances.add(new Attendance(dateTime));
        return new Attendances(newAttendances);
    }

    public Attendance findAttendanceByDate(final LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDate(date))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
