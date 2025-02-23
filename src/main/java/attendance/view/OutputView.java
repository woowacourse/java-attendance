package attendance.view;

import static attendance.util.DateTimeUtil.TIME_NOT_RECORDED;
import static attendance.util.DateTimeUtil.formatDate;
import static attendance.util.DateTimeUtil.formatDateTime;
import static attendance.util.DateTimeUtil.formatTime;

import attendance.dto.CrewAttendanceSummary;
import attendance.model.Attendance;
import attendance.model.AttendanceTimeline;
import attendance.model.AttendanceTimeline.AttendanceLog;
import attendance.model.AttendanceType;
import attendance.model.AttendanceWarningLevel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";

    public void printDate(LocalDate date) {
        System.out.printf("오늘은 %s입니다. ", formatDate(date));
    }

    public void printCheckAttendance(LocalDateTime dateTime, AttendanceType type) {
        System.out.printf("%s (%s)%n", formatDateTime(dateTime), getAttendanceTypeLabel(type));
    }

    public void printModifiedAttendance(Attendance beforeAttendance, Attendance afterAttendance,
                                        AttendanceType beforeType, AttendanceType afterType) {
        if (beforeAttendance.isNotRecordedTime()) {
            System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n",
                    formatDate(afterAttendance.getDate()),
                    TIME_NOT_RECORDED,
                    getAttendanceTypeLabel(null),
                    formatTime(afterAttendance.getTime()),
                    getAttendanceTypeLabel(afterType)
            );
            return;
        }
        System.out.printf("%s (%s) -> %s (%s) 수정 완료!%n",
                formatDateTime(beforeAttendance.getDateTime()),
                getAttendanceTypeLabel(beforeType),
                formatDateTime(afterAttendance.getDateTime()),
                getAttendanceTypeLabel(afterType)
        );
    }

    public void printAttendanceTimelineInMonth(String nickname, AttendanceTimeline attendanceTimeline) {
        System.out.printf("이번달 %s의 출석 기록입니다.%n%n", nickname);
        for (AttendanceLog attendanceLog : attendanceTimeline.attendanceLogs()) {
            if (attendanceLog.time() == null) {
                System.out.printf("%s %s (%s)%n",
                        formatDate(attendanceLog.date()),
                        TIME_NOT_RECORDED,
                        getAttendanceTypeLabel(attendanceLog.attendanceType()));
                continue;
            }
            System.out.printf("%s %s (%s)%n",
                    formatDate(attendanceLog.date()),
                    formatTime(attendanceLog.time()),
                    getAttendanceTypeLabel(attendanceLog.attendanceType()));
        }
    }

    public void printCountOfAttendanceType(int okCount, int lateCount, int absenceCount) {
        System.out.printf("%n출석: %d%n지각: %d%n결석: %d%n", okCount, lateCount, absenceCount);
    }

    public void printWarningLevel(AttendanceWarningLevel level) {
        if (level != AttendanceWarningLevel.CLEAN) {
            System.out.printf("%n%s 대상자입니다.%n", level.getLabel());
        }
    }

    public void printEmergencyCrews(List<CrewAttendanceSummary> sortedList) {
        System.out.println("제적 위험자 조회 결과");
        sortedList.stream()
                .filter(summary -> summary.level() != AttendanceWarningLevel.CLEAN)
                .forEach(summary -> System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                        summary.crew().getNickname().getValue(),
                        summary.absenceCount(),
                        summary.lateCount(),
                        summary.level().getLabel()));
    }

    public void printErrorMessage(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    private String getAttendanceTypeLabel(AttendanceType type) {
        if (type == null) {
            return AttendanceType.ABSENCE.getLabel();
        }
        return type.getLabel();
    }

    public void printDateTimeErrorMessage() {
        System.out.println(ERROR_PREFIX + "HH:mm (24시간) 형식만 사용할 수 있습니다.");
    }
}
