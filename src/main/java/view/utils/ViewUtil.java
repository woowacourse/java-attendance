package view.utils;

import domain.AttendanceStatus;
import domain.RiskStatus;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class ViewUtil {
    public static String getDayOfWeekMessage(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static String getEmptyStatusMessage() {
        return "--:--";
    }

    public static String getAttendanceStatusMessage(AttendanceStatus attendanceStatus) {
        if (attendanceStatus.equals(AttendanceStatus.ATTENDANCE)) {
            return "출석";
        }
        if (attendanceStatus.equals(AttendanceStatus.TARDY)) {
            return "지각";
        }
        return "결석";
    }

    public static String getRiskStatusMessage(RiskStatus riskStatus) {
        if (riskStatus.equals(RiskStatus.DISMISSAL)) {
            return "제적";
        }
        if (riskStatus.equals(RiskStatus.COUNSELLING)) {
            return "상담";
        }
        if (riskStatus.equals(RiskStatus.WARNING)) {
            return "경고";
        }
        return "--";
    }
}
