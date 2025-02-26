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

}
