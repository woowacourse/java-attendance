package view;

import config.AttendancePolicyConfig;
import domain.Attendance;
import domain.AttendanceCounts;
import domain.AttendanceDate;
import domain.AttendanceStatistics;
import domain.Attendances;
import domain.policy.AttendanceStateRule;
import domain.policy.absent.AbsentRule;
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
        String attendanceState = attendance.decideAttendanceState(AttendancePolicyConfig.getInstance()).getDescription();

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
        String originalAttendanceState = originalAttendance.decideAttendanceState(AttendancePolicyConfig.getInstance()).getDescription();

        String updatedTime = updatedAttendance.toLocalTime().format(FormatUtil.TIME_FORMATTER);
        String updatedAttendanceState = updatedAttendance.decideAttendanceState(AttendancePolicyConfig.getInstance()).getDescription();

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
                .filter(AttendancePolicyConfig.getInstance()::canAttendDate)
                .map(AttendanceDate::from)
                .forEach(attendanceDate -> printAttendance(attendances, attendanceDate));

        System.out.println();
    }

    private void printAttendance(Attendances attendances, AttendanceDate attendanceDate) {
        String date = attendanceDate.date().format(FormatUtil.DATE_FORMATTER_KOREAN);
        String dayOfWeek = attendanceDate.date().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        String time = ViewMessage.ABSENT_TIME_FORMAT;
        String attendanceState = AttendanceStateRule.ABSENT.getDescription();

        if (attendances.existsByDate(attendanceDate)) {
            Attendance attendance = attendances.findByDate(attendanceDate);
            time = attendance.toLocalTime().format(FormatUtil.TIME_FORMATTER);
            attendanceState = attendance.decideAttendanceState(AttendancePolicyConfig.getInstance()).getDescription();
        }

        System.out.printf(ViewMessage.ATTENDANCE + "%n",
                date,
                time,
                dayOfWeek,
                attendanceState
        );
    }

    public void printAttendanceStatistics(AttendanceCounts attendanceCounts) {
        System.out.printf(ViewMessage.STATISTICS_FORMAT + "%n",
                attendanceCounts.getCount(AttendanceStateRule.ATTEND),
                attendanceCounts.getCount(AttendanceStateRule.LATE),
                attendanceCounts.getCount(AttendanceStateRule.ABSENT));
    }

    public void printAbsentPolicy(AbsentRule absentRule) {
        if (absentRule == AbsentRule.NONE) {
            return;
        }
        System.out.printf("%n" +
                ViewMessage.ABSENT_POLICY_FORMAT, absentRule.getDescription());
    }

    public void printRiskOfExpulsionBanner() {
        System.out.println(ViewMessage.RISK_OF_EXPULSION_BANNER);
    }

    public void printExpulsionCandidate(AttendanceStatistics expulsionCandidates) {
        expulsionCandidates.getAttendanceStatistics().forEach(expulsionCandidate ->
                System.out.printf(ViewMessage.RISK_OF_EXPULSION_FORMAT,
                        expulsionCandidate.getNickname(),
                        expulsionCandidate.getCount(AttendanceStateRule.ABSENT),
                        expulsionCandidate.getCount(AttendanceStateRule.LATE),
                        AbsentRule.calculateAbsentPolicy(expulsionCandidate).getDescription())
        );
    }
}
