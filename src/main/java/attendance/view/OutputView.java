package attendance.view;

import attendance.constant.Holiday;
import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.domain.Penalty;
import attendance.domain.StatusStatistics;
import attendance.util.DateUtil;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OutputView {

    private OutputView() {}

    public static void printRecordAttendanceResult(Attendance attendance) {
        System.out.printf("%n%02d월 %02d일 %s %02d:%02d (%s)%n%n",
                attendance.attendDate().getMonthValue(),
                attendance.attendDate().getDayOfMonth(),
                attendance.attendDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                attendance.attendTime().getHour(),
                attendance.attendTime().getMinute(),
                attendance.determineStatus().getName());
    }

    public static void printEditAttendanceResult(Attendance oldAttendance, Attendance newAttendance) {
        System.out.printf("%n%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!%n%n",
                oldAttendance.attendDate().getMonthValue(),
                oldAttendance.attendDate().getDayOfMonth(),
                oldAttendance.attendDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                oldAttendance.attendTime().getHour(),
                oldAttendance.attendTime().getMinute(),
                oldAttendance.determineStatus().getName(),
                newAttendance.attendTime().getHour(),
                newAttendance.attendTime().getMinute(),
                newAttendance.determineStatus().getName());
    }

    public static void printAttendanceRecordsUntilYesterday(Crew crew, LocalDate today, List<Attendance> attendances) {
        System.out.printf("%n이번 달 %s의 출석 기록입니다.%n%n", crew.getNickname().nickname());
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            LocalDate currentDate = LocalDate.of(today.getYear(), today.getMonthValue(), day);
            if (DateUtil.isWeekend(currentDate) || Holiday.isHoliday(currentDate)) {
                continue;
            }
            printAttendanceRecords(attendances, currentDate);
        }
        System.out.println();
    }

    private static void printAttendanceRecords(List<Attendance> attendances, LocalDate currentDate) {
        attendances.stream()
                .filter(attendance -> attendance.isSameDate(currentDate))
                .findFirst()
                .ifPresentOrElse(OutputView::printExistRecords, () -> printNotExistRecords(currentDate));
    }

    private static void printExistRecords(Attendance attendance) {
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s)%n",
                attendance.attendDate().getMonthValue(),
                attendance.attendDate().getDayOfMonth(),
                attendance.attendDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                attendance.attendTime().getHour(),
                attendance.attendTime().getMinute(),
                attendance.determineStatus().getName());
    }

    private static void printNotExistRecords(LocalDate currentDate) {
        System.out.printf("%02d월 %02d일 %s --:-- (결석)%n",
                currentDate.getMonthValue(),
                currentDate.getDayOfMonth(),
                currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
    }

    public static void printStatusStatistics(StatusStatistics statusStatistics) {
        for(AttendanceStatus status : AttendanceStatus.values()) {
            System.out.printf("%s: %d회%n", status.getName(), statusStatistics.getAttendanceStatusCount(status));
        }

        int lateCount = statusStatistics.getAttendanceStatusCount(AttendanceStatus.LATE);
        int absentCount = statusStatistics.getAttendanceStatusCount(AttendanceStatus.ABSENT);
        Penalty penalty = Penalty.determine(lateCount, absentCount);
        if (penalty != Penalty.NONE) {
            System.out.printf("%n%s 대상자입니다.%n", penalty.getName());
        }
    }

    public static void printPenaltyCrews(Map<Crew, StatusStatistics> crewsAndStatistics) {
        System.out.printf("%n제적 위험자 조회 결과%n");
        crewsAndStatistics.forEach(((crew, statusStatistics) -> {
            int lateCount = statusStatistics.getAttendanceStatusCount(AttendanceStatus.LATE);
            int absentCount = statusStatistics.getAttendanceStatusCount(AttendanceStatus.ABSENT);
            Penalty penalty = Penalty.determine(lateCount, absentCount);

            if (penalty != Penalty.NONE) {
                System.out.printf("- %s: %s %d회, %s %d회 (%s)%n",
                        crew.getNickname().nickname(),
                        AttendanceStatus.LATE.getName(),
                        lateCount,
                        AttendanceStatus.ABSENT.getName(),
                        absentCount,
                        penalty.getName());
            }
        }));
        System.out.println();
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }
}
