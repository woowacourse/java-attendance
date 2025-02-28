package attendance.view;

import attendance.domain.AbsenceRule;
import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printConfirmResult(final LocalDateTime dateTime, final AttendanceStatus status) {
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s)\n",
                dateTime.getMonthValue(),
                dateTime.getMonthValue(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                dateTime.getHour(),
                dateTime.getMinute(),
                getStatusString(status));
    }

    public void printUpdateResult(final Attendance beforeUpdateAttendance, final Attendance afterUpdateAttendance) {
        LocalDateTime beforeDateTime = beforeUpdateAttendance.getDateTime();
        LocalDateTime afterDateTime = afterUpdateAttendance.getDateTime();
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n",
                beforeDateTime.getMonthValue(),
                beforeDateTime.getDayOfMonth(),
                beforeDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                beforeDateTime.getHour(),
                beforeDateTime.getMinute(),
                getStatusString(beforeUpdateAttendance.getStatus()),
                afterDateTime.getHour(),
                afterDateTime.getMinute(),
                getStatusString(afterUpdateAttendance.getStatus()));
    }

    public void printCrewAttendances(Crew crew) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", crew.getNickname());
        List<Attendance> attendances = new ArrayList<>(crew.getAttendances());
        Collections.sort(attendances);
        for (Attendance attendance : attendances) {
            LocalDateTime dateTime = attendance.getDateTime();
            if(LocalTime.from(dateTime).equals(LocalTime.MIN)) {
                printNotVisitAbsence(dateTime);
                continue;
            }
            printAttendance(attendance, dateTime);
        }
    }

    public void printCrewAttendanceStatusCount(final Crew crew) {
        System.out.printf("출석: %d회\n", crew.countAttendanceStatus(AttendanceStatus.ATTEND));
        System.out.printf("지각: %d회\n", crew.countAttendanceStatus(AttendanceStatus.LATE));
        System.out.printf("결석: %d회\n", crew.countAttendanceStatus(AttendanceStatus.ABSENCE));

        AbsenceRule penalty = crew.checkAbsenceRule();
        if(!penalty.equals(AbsenceRule.NONE)) {
            System.out.printf("%s 대상자입니다.\n", getAbsenceRuleString(penalty));
        }
    }

    private static String getStatusString(final AttendanceStatus status) {
        if(status.equals(AttendanceStatus.ATTEND)) return "춣석";
        if(status.equals(AttendanceStatus.LATE)) return "지각";
        if(status.equals(AttendanceStatus.ABSENCE)) return "결석";
        return "";
    }

    private static void printNotVisitAbsence(final LocalDateTime dateTime) {
        System.out.printf("%02d월 %02d일 %s --:-- (결석)\n",
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
    }

    private static void printAttendance(final Attendance attendance, final LocalDateTime dateTime) {
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s)\n",
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                dateTime.getHour(),
                dateTime.getMinute(),
                getStatusString(attendance.getStatus()));
    }

    private static String getAbsenceRuleString(final AbsenceRule absenceRule) {
        if(absenceRule.equals(AbsenceRule.EXPULSION)) return "제적";
        if(absenceRule.equals(AbsenceRule.COUNSELING)) return "면담";
        if(absenceRule.equals(AbsenceRule.WARNING)) return "경고";
        return "";
    }
}
