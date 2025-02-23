package view;

import domain.Attendance;
import domain.AttendanceDate;
import domain.AttendanceStatistics;
import domain.AttendanceTime;
import domain.Attendances;
import domain.ExpulsionCandidates;
import domain.rule.AbsentRule;
import domain.rule.AttendanceDateRule;
import domain.rule.AttendanceStateRule;
import util.FormatUtil;
import util.TimeMachine;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.stream.IntStream;

public class OutputView {

    public void printAttendance(Attendance attendance) {
        String date = attendance.toLocalDate().format(FormatUtil.DATE_FORMATTER_KOREAN);
        String time = attendance.toLocalTime().format(FormatUtil.TIME_FORMATTER);
        String dayOfWeek = attendance.toLocalDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        String attendanceState = attendance.decisionAttendanceState().description;

        System.out.printf(ViewMessage.ATTENDANCE + "%n",
                date,
                dayOfWeek,
                time,
                attendanceState);
    }

    public void printAttendanceUpdate(Attendance originalAttendance, Attendance updatedAttendance) {
        String originalDate = originalAttendance.toLocalDate().format(FormatUtil.DATE_FORMATTER_KOREAN);
        String originalTime = originalAttendance.toLocalTime().format(FormatUtil.TIME_FORMATTER);
        String originalDayOfWeek = originalAttendance.toLocalDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        String originalAttendanceState = originalAttendance.decisionAttendanceState().description;

        String updatedTime = updatedAttendance.toLocalTime().format(FormatUtil.TIME_FORMATTER);
        String updatedAttendanceState = updatedAttendance.decisionAttendanceState().description;

        System.out.printf(ViewMessage.ATTENDANCE + " -> " + ViewMessage.UPDATE_COMPLETE + "%n",
                originalDate,
                originalTime,
                originalDayOfWeek,
                originalAttendanceState,
                updatedTime,
                updatedAttendanceState);
    }

    public void printAttendanceIntro(String nickname) {
        System.out.printf(ViewMessage.CURRENT_MONTH_ATTENDANCE_SHEET, nickname);
    }

    public void printAttendances(Attendances attendances) {
        IntStream.range(1, TimeMachine.dateOfNow().getDayOfMonth())
                .mapToObj(day -> LocalDate.of(TimeMachine.FIXED_YEAR, TimeMachine.FIXED_MONTH, day))
                .filter(AttendanceDateRule::canAttendDay)
                .map(AttendanceDate::from)
                .forEach(attendanceDate -> printAttendance(attendances, attendanceDate));

        System.out.println();
    }

    private void printAttendance(Attendances attendances, AttendanceDate attendanceDate) {
        String date = attendanceDate.date().format(FormatUtil.DATE_FORMATTER_KOREAN);
        String dayOfWeek = attendanceDate.date().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        String time = ViewMessage.ABSENT_TIME_FORMAT;
        String attendanceState = AttendanceStateRule.ABSENT.description;

        if (attendances.existsByDate(attendanceDate)) {
            AttendanceTime attendanceTime = attendances.findByDate(attendanceDate);
            time = attendanceTime.time().format(FormatUtil.TIME_FORMATTER);
            attendanceState = attendanceTime.checkAttendanceState(attendanceDate.isSpecialDay()).description;
        }

        System.out.printf(ViewMessage.ATTENDANCE + "%n",
                date,
                time,
                dayOfWeek,
                attendanceState
        );
    }

    public void printAttendanceStatistics(AttendanceStatistics attendanceStatistics) {
        System.out.printf(ViewMessage.STATISTICS_FORMAT + "%n",
                attendanceStatistics.attendCount(),
                attendanceStatistics.lateCount(),
                attendanceStatistics.absentCount());
    }

    public void printAbsentPolicy(AbsentRule absentRule) {
        if (absentRule == AbsentRule.NONE) {
            return;
        }
        System.out.printf("%n" +
                ViewMessage.ABSENT_POLICY_FORMAT, absentRule.description);
    }

    public void printRiskOfExpulsionBanner() {
        System.out.println(ViewMessage.RISK_OF_EXPULSION_BANNER);
    }

    public void printExpulsionCandidate(ExpulsionCandidates expulsionCandidates) {
        expulsionCandidates.attendanceStatistics().forEach(expulsionCandidate ->
                System.out.printf(ViewMessage.RISK_OF_EXPULSION_FORMAT,
                        expulsionCandidate.nickname(),
                        expulsionCandidate.absentCount(),
                        expulsionCandidate.lateCount(),
                        AbsentRule.calculateAbsentPolicy(expulsionCandidate).description)
        );
    }
}
