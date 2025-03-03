package attendance.view;

import static attendance.domain.AttendanceStatus.ABSENT;

import attendance.domain.Attendance;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceStatus;
import attendance.domain.CrewAttendance;
import attendance.util.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;

public class ResultView {
    private static final String CREW_ATTENDANCE_HEADER = "\n이번 달 %s의 출석 기록입니다.\n\n";
    private static final String ABSENT_TIME_FORMAT = " --:-- ";
    private static final String ATTENDANCE_STATUS_FORMAT = "(%s)";
    private static final String ATTENDANCE_STATUS_COUNTS_FORMAT = "\n%s: %d회";


    public void printCrewAttendanceHeader(final String nickname) {
        System.out.printf(CREW_ATTENDANCE_HEADER, nickname);
    }

    public void printCrewAttendances(final CrewAttendance crewAttendance, final LocalDateTime today) {
        Map<LocalDate, AttendanceStatus> attendanceStatuses = crewAttendance.getAttendanceStatusesBefore(today);
        attendanceStatuses.keySet().stream()
                .sorted(Comparator.naturalOrder())
                .forEach(localDate ->
                        System.out.println(formatAttendance(localDate, attendanceStatuses, crewAttendance)));
    }

    private String formatAttendance(final LocalDate localDate,
                                    final Map<LocalDate, AttendanceStatus> attendanceStatuses,
                                    final CrewAttendance crewAttendance) {
        if (attendanceStatuses.get(localDate) == ABSENT) {
            return formatAbsentOn(localDate);
        }
        return format(crewAttendance.getAttendanceOn(localDate));
    }

    private String format(final Attendance attendance) {
        AttendanceRecord attendanceRecord = attendance.record();
        return DateFormatter.formatDate(attendanceRecord.date()) + " "
               + attendanceRecord.time().toString() + " "
               + String.format(ATTENDANCE_STATUS_FORMAT, attendance.status().getDisplayName());
    }

    private String formatAbsentOn(final LocalDate localDate) {
        return DateFormatter.formatDate(localDate)
               + ABSENT_TIME_FORMAT
               + String.format(ATTENDANCE_STATUS_FORMAT, ABSENT.getDisplayName());
    }

    public void printAttendanceStatusCounts(final CrewAttendance crewAttendance, final LocalDateTime today) {
        final Map<AttendanceStatus, Integer> attendanceStatusCounts =
                crewAttendance.countAttendanceStatusesBefore(today);

        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            System.out.printf(ATTENDANCE_STATUS_COUNTS_FORMAT,
                    attendanceStatus.getDisplayName(),
                    attendanceStatusCounts.get(attendanceStatus));
        }
    }
}
