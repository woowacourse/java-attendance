package attendance.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AttendanceHistory {
    private final Set<Attendance> attendances = new HashSet<>();

    public Attendance addAttendance(final Attendance attendance) {
        attendances.add(attendance);
        return attendance;
    }

    public Optional<Attendance> findAttendance(final LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isDateEquals(date))
                .findAny();
    }

    public Attendance modifyAttendance(final Attendance modifiedAttendance) {
        LocalDate attendanceDateToModify = modifiedAttendance.getDate();
        Attendance beforeAttendance = findAttendance(attendanceDateToModify).get();
        attendances.remove(beforeAttendance);
        return addAttendance(modifiedAttendance);
    }
}
