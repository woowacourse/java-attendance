package attendance.domain;

import java.time.LocalDate;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(final List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public boolean hasAttendanceByLocalDate(final LocalDate findDate) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameDate(findDate));
    }

    public Attendance findSameDateAttendance(final LocalDate findDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDate(findDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 출석 기록이 존재하지 않습니다."));
    }

}
