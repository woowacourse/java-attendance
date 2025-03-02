package view;

import domain.Attendance;
import domain.AttendanceStateCount;
import domain.PenaltyBook;
import domain.PenaltyType;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import util.DateTimeUtil;

public class OutputView {
    public static void printAttendanceCheck(Attendance attendance) {
        System.out.printf("12월 %02d일 %s %s (%s)\n",
                DateTimeUtil.getDateBy(attendance.getLocalDate()),
                DateTimeUtil.getDayOfWeekBy(attendance.getLocalDate()),
                formatTime(attendance.getLocalTime()),
                attendance.getState().getState());
    }

    public static void printAttendanceUpdate(Attendance beforeAttendance, Attendance afterAttendance) {
        System.out.printf("12월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!\n",
                DateTimeUtil.getDateBy(beforeAttendance.getLocalDate()),
                DateTimeUtil.getDayOfWeekBy(beforeAttendance.getLocalDate()),
                formatTime(beforeAttendance.getLocalTime()),
                beforeAttendance.getState().getState(),
                formatTime(afterAttendance.getLocalTime()),
                afterAttendance.getState().getState()
        );
    }

    private static String formatTime(LocalTime beforeTime) {
        if (beforeTime.equals(LocalTime.of(0, 0))) {
            return "--:--";
        }
        return String.format("%02d:%02d", beforeTime.getHour(), beforeTime.getMinute());
    }

    public static void printAttendanceRecordHistory(List<Attendance> attendanceBookHistory) {
        sortByDate(attendanceBookHistory)
                .forEach(a -> System.out.printf("12월 %02d일 %s %s (%s)\n",
                        DateTimeUtil.getDateBy(a.getLocalDate()),
                        DateTimeUtil.getDayOfWeekBy(a.getLocalDate()),
                        formatTime(a.getLocalTime()),
                        a.getState().getState()
                ));
        System.out.println();
    }

    private static List<Attendance> sortByDate(List<Attendance> attendances) {
        return attendances.stream()
                .sorted(Comparator.comparing(Attendance::getLocalDate))
                .toList();
    }


    public static void printAttendancePenaltyHistory(AttendanceStateCount attendanceStateCount,
                                                     PenaltyType penaltyType) {
        System.out.printf("출석: %d회\n"
                        + "지각: %d회\n"
                        + "결석: %d회\n"
                        + "\n"
                        + "%s 대상자입니다.\n",
                attendanceStateCount.attendance(),
                attendanceStateCount.lateness(),
                attendanceStateCount.absence(),
                penaltyType.getValue());
    }

    public static void printAbsenceHistory(Set<PenaltyBook> penaltyBooks) {
        System.out.println("제적 위험자 조회 결과");
        sortPenaltyBook(penaltyBooks).forEach(penaltyBook -> System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                penaltyBook.crew().getName(),
                penaltyBook.absence(),
                penaltyBook.lateness(),
                penaltyBook.penaltyType().getValue()));
    }

    private static List<PenaltyBook> sortPenaltyBook(Set<PenaltyBook> penaltyBooks) {
        return penaltyBooks.stream()
                .sorted(Comparator
                        .comparing((PenaltyBook penaltyBook) -> getAbsencePriority(penaltyBook.penaltyType()))
                        .thenComparing(penaltyBook -> penaltyBook.lateness() + penaltyBook.absence() * 3,
                                Comparator.reverseOrder())
                        .thenComparing(penaltyBook -> penaltyBook.crew().getName()))
                .toList();

    }

    private static int getAbsencePriority(PenaltyType penaltyType) {
        if (penaltyType == PenaltyType.EXPULSION) {
            return 0;
        }
        if (penaltyType == PenaltyType.INTERVIEW) {
            return 1;
        }
        if (penaltyType == PenaltyType.WARNING) {
            return 2;
        }
        return 3;
    }
}
