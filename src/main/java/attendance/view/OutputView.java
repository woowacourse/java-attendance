package attendance.view;

import attendance.domain.AbsenceRule;
import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static attendance.view.OutputMessage.*;

public class OutputView {

    public void printConfirmResult(final LocalDateTime dateTime, final AttendanceStatus status) {
        System.out.printf(CONFIRM_RESULT_TILE,
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                dateTime.getHour(),
                dateTime.getMinute(),
                getStatusString(status));
    }

    public void printUpdateResult(final Attendance beforeUpdateAttendance, final Attendance afterUpdateAttendance) {
        LocalDateTime beforeDateTime = beforeUpdateAttendance.getDateTime();
        LocalDateTime afterDateTime = afterUpdateAttendance.getDateTime();
        System.out.printf(UPDATE_RESULT_TITLE,
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

    public void printCrewAttendances(LocalDate today, Crew crew) {
        System.out.printf(CREW_ATTENDANCE_TITLE, crew.getNickname());
        List<Attendance> attendances = crew.getCrewAttendancesUtilYesterday(today);
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
        System.out.printf(ATTEND_COUNT, crew.getAttendanceCount(AttendanceStatus.ATTEND));
        System.out.printf(LATE_COUNT, crew.getAttendanceCount(AttendanceStatus.LATE));
        System.out.printf(ABSENCE_COUNT, crew.getAttendanceCount(AttendanceStatus.ABSENCE));

        AbsenceRule penalty = crew.checkAbsenceRule();
        if(!penalty.equals(AbsenceRule.NONE)) {
            System.out.printf(PENALTY_WARNING, getAbsenceRuleString(penalty));
        }
    }

    public void printPenaltyCrews(final Map<AbsenceRule, List<Crew>> penaltiesCrews) {
        System.out.println(PENALTY_CREWS_TITLE);
        for (AbsenceRule absenceRule : AbsenceRule.values()) {
            printCrewsPerPenalty(penaltiesCrews, absenceRule);
        }
    }

    private String getStatusString(final AttendanceStatus status) {
        if(status.equals(AttendanceStatus.ATTEND)) return ATTEND;
        if(status.equals(AttendanceStatus.LATE)) return LATE;
        if(status.equals(AttendanceStatus.ABSENCE)) return ABSENCE;
        return "";
    }

    private void printNotVisitAbsence(final LocalDateTime dateTime) {
        System.out.printf(NOT_VISIT_ABSENCE,
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
    }

    private void printAttendance(final Attendance attendance, final LocalDateTime dateTime) {
        System.out.printf(LATE_ABSENCE,
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                dateTime.getHour(),
                dateTime.getMinute(),
                getStatusString(attendance.getStatus()));
    }

    private String getAbsenceRuleString(final AbsenceRule absenceRule) {
        if(absenceRule.equals(AbsenceRule.EXPULSION)) return EXPULSION;
        if(absenceRule.equals(AbsenceRule.COUNSELING)) return INTERVIEW;
        if(absenceRule.equals(AbsenceRule.WARNING)) return WARNING;
        return "";
    }

    private void printCrewsPerPenalty(final Map<AbsenceRule, List<Crew>> penaltiesCrews, final AbsenceRule absenceRule) {
        List<Crew> crews = penaltiesCrews.get(absenceRule);
        for (Crew crew : crews) {
            System.out.printf(CREWS_PER_PENALTY,
                    crew.getNickname(),
                    crew.getAttendanceCount(AttendanceStatus.ABSENCE),
                    crew.getAttendanceCount(AttendanceStatus.LATE),
                    getAbsenceRuleString(absenceRule));
        }
    }

    public void printExceptionMessage(final Exception e) {
        System.out.println(e.getMessage());
    }
}
