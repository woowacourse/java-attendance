package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static attendance.domain.AttendanceStatus.LATE;
import static attendance.domain.AttendanceStatus.values;
import static attendance.exception.ErrorMessage.DUPLICATED_ATTENDANCE;
import static attendance.exception.ErrorMessage.NO_ATTENDANCE_TO_MODIFY;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AttendanceHistory {
    private final Set<Attendance> attendances = new HashSet<>();

    public Attendance addAttendance(final Attendance attendance) {
        validateDuplicatedAttendance(attendance.getDate());
        attendances.add(attendance);
        return attendance;
    }

    public void validateDuplicatedAttendance(final LocalDate attendanceDate) {
        findAttendance(attendanceDate).ifPresent(attendance -> {
            throw new IllegalArgumentException(DUPLICATED_ATTENDANCE.getMessage());
        });
    }

    public Optional<Attendance> findAttendance(final LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isDateEquals(date))
                .findAny();
    }

    public Attendance modifyAttendance(final Attendance modifiedAttendance) {
        LocalDate attendanceDateToModify = modifiedAttendance.getDate();
        findAttendance(attendanceDateToModify).ifPresentOrElse(attendances::remove, () -> {
            throw new IllegalArgumentException(NO_ATTENDANCE_TO_MODIFY.getMessage());
        });
        return addAttendance(modifiedAttendance);
    }

    public List<Attendance> getMonthlyAttendances(final LocalDate today) {
        return attendances.stream()
                .filter(attendance -> attendance.isYearMonthEquals(today))
                .toList();
    }

    public AttendanceStatistics getAttendanceStatistics(final LocalDate today) {
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
