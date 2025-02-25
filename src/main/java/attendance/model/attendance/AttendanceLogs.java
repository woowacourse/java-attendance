package attendance.model.attendance;

import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class AttendanceLogs {

    private final List<AttendanceLog> values;

    public AttendanceLogs(final List<AttendanceLog> values) {
        this.values = List.copyOf(values);
    }

    // 동작
    //LocalDate from 부터 LocalDate to 내에 등교일인데 출석하지 않은 날에 대한 AttendanceLog 들과 함께 AttendanceLog 리스트를 반환한다.
    //LocalDate from 부터 LocalDate to 내에 크루 출석 데이터를 종합하여 Map<AttendanceStatus, Integer> 를 반환한다.
    //Map<AttendanceStatus, Integer> 에는 각 AttendanceStatus 가 몇 개 있는지 저장한다.

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
}
