package attendance.model.attendance.log;

import attendance.model.attendance.AttendanceStatus;
import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AttendanceLogs {

    private final List<AttendanceLog> values;

    public AttendanceLogs(final List<AttendanceLog> values) {
        this.values = List.copyOf(values);
    }

    public List<AttendanceLog> getAllAttendanceLogs(
            final LocalDate from,
            final LocalDate to,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        return Stream.concat(
                        values.stream(),
                        getAbsenceAttendanceLogs(from, to, campusOperationPolicy).stream()
                )
                .sorted(Comparator.comparing(AttendanceLog::getDate))
                .toList();
    }

    private List<AttendanceLog> getAbsenceAttendanceLogs(
            final LocalDate from,
            final LocalDate to,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        return from.datesUntil(to)
                .filter(campusOperationPolicy::isOpenDate)
                .filter(date -> !containsDate(date))
                .map(AttendanceLog::fromAbsenceDate)
                .toList();
    }

    private boolean containsDate(final LocalDate date) {
        return values.stream().anyMatch(attendanceLog -> attendanceLog.isSameDate(date));
    }

    public Map<AttendanceStatus, Integer> getAttendanceStatusStatistics(
            final LocalDate from,
            final LocalDate to,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        final List<AttendanceLog> allAttendanceLogs = getAllAttendanceLogs(from, to, campusOperationPolicy);

        final List<AttendanceStatus> attendanceStatuses = allAttendanceLogs.stream()
                .map(AttendanceLog::getAttendanceStatus)
                .toList();

        return AttendanceStatus.getStatistics(attendanceStatuses);
    }
}
