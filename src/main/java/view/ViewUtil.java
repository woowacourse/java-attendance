package view;

import domain.AttendanceStatus;
import domain.RiskStatus;

import java.time.DayOfWeek;

public class ViewUtil {
    public static String getDayOfWeekToMessage(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return "월요일";
        }
        if (dayOfWeek == DayOfWeek.TUESDAY) {
            return "화요일";
        }
        if (dayOfWeek == DayOfWeek.WEDNESDAY) {
            return "수요일";
        }
        if (dayOfWeek == DayOfWeek.THURSDAY) {
            return "목요일";
        }
        if (dayOfWeek == DayOfWeek.FRIDAY) {
            return "금요일";
        }
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return "토요일";
        }
        return "일요일";
    }

    public static String getNoneAttendanceMessage() {
        return "--:--";
    }

    public static String getRiskStatusMessage(RiskStatus riskStatus) {
        if (RiskStatus.WARNING == riskStatus) {
            return "경고";
        }
        if (RiskStatus.COUNSELING == riskStatus) {
            return "면담";
        }
        return "제적";
    }

    public static String getAttendanceStatusMessage(AttendanceStatus attendanceStatus) {
        if (AttendanceStatus.ABSENCE == attendanceStatus) {
            return "결석";
        }
        if (AttendanceStatus.ATTENDANCE == attendanceStatus) {
            return "출석";
        }
        return "지각";
    }
}
