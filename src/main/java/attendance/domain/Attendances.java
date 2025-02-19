package attendance.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances = new ArrayList<>();

    public void addAttendance(final Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendanceByLocalDate(final LocalDate findDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDate(findDate))
                .findAny()
                .orElse(Attendance.absence(findDate));
    }

}
