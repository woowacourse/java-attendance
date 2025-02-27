package attendance.model.attendance.log;

import attendance.model.attendance.AttendanceStatus;
import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AttendanceLogs {

    private final List<AttendanceLog> values;

    public AttendanceLogs(final List<AttendanceLog> values) {
        this.values = new ArrayList<>(values);
    }

    public static AttendanceLogs fromLocalDateTimes(
            final List<LocalDateTime> dateTimes,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        final List<AttendanceLog> attendanceLogs = dateTimes.stream()
                .map(dateTime -> AttendanceLog.fromDateTime(dateTime, campusOperationPolicy))
                .toList();

        return new AttendanceLogs(attendanceLogs);
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
                .map(date -> AttendanceLog.fromAbsenceDate(date, campusOperationPolicy))
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

    public void add(final AttendanceLog attendanceLog) {
        values.add(attendanceLog);
    }

    public List<AttendanceLog> getValues() {
        return values;
    }

    public int getPolicyAppliedAbsenceCount() {
        return getAbsenceCount() + getLateCount() / 3;
    }

    public int getAbsenceCount() {
        return Math.toIntExact(
                values.stream()
                        .filter(this::isAbsence)
                        .count()
        );
    }

    public int getPolicyAppliedLateCount() {
        return getLateCount() % 3;
    }

    public int getLateCount() {
        return Math.toIntExact(
                values.stream()
                        .filter(this::isLate)
                        .count()
        );
    }

    private boolean isLate(AttendanceLog attendanceLog) {
        return attendanceLog.getAttendanceStatus() == AttendanceStatus.LATE;
    }

    private boolean isAbsence(AttendanceLog attendanceLog) {
        return attendanceLog.getAttendanceStatus() == AttendanceStatus.ABSENCE;
    }
}
