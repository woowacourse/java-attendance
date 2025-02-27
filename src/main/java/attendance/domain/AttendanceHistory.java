package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static attendance.domain.AttendanceStatus.LATE;
import static attendance.domain.AttendanceStatus.values;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<Attendance> getMonthlyAttendances(final LocalDate today) {
        return attendances.stream()
                .filter(attendance -> attendance.isYearMonthEquals(today))
                .collect(Collectors.toUnmodifiableList());
    }

    public AttendanceStatistics returnAttendanceStatistics(final LocalDate today) {
        Map<AttendanceStatus, Integer> statistics = new HashMap<>();
        initializeStatistics(statistics);
        for (int i = today.getDayOfMonth() - 1; i > 0; i--) {
            LocalDate date = today.minusDays(i);
            boolean isOperationDate = CampusManager.isOperationDate(date);
            if (!isOperationDate) {
                continue;
            }
            Optional<Attendance> attendance = findAttendance(date);
            if (attendance.isEmpty()) {
                statistics.put(ABSENCE, statistics.get(ABSENCE) + 1);
                continue;
            }
            AttendanceStatus status = attendance.get().getStatus();
            statistics.put(status, statistics.get(status) + 1);
        }
        return new AttendanceStatistics(statistics.get(ATTENDANCE), statistics.get(LATE), statistics.get(ABSENCE));
    }

    private void initializeStatistics(final Map<AttendanceStatus, Integer> statistics) {
        for (AttendanceStatus status : values()) {
            statistics.put(status, 0);
        }
    }
}
