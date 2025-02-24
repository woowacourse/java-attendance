package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.domain.constant.DayOfWeek;

public class DateInfo {

    private final String month;
    private final String day;
    private final DayOfWeek dayOfWeek;
    private CampusTime campusTime;
    private AttendanceStatus attendanceStatus;

    private DateInfo(String month, String day, DayOfWeek dayOfWeek, CampusTime campusTime) {
        this.month = month;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.campusTime = campusTime;
        this.attendanceStatus = calculateStatus();
    }

    public static DateInfo of(int month, int day, DayOfWeek dayOfWeek, CampusTime campusTime) {
        String parsedMonth = formatWithLeadingZero(month);
        String parsedDay = formatWithLeadingZero(day);
        return new DateInfo(parsedMonth, parsedDay, dayOfWeek, campusTime);
    }

    public static DateInfo makeDefaultValue(int month, int day, DayOfWeek dayOfWeek) {
        String parsedMonth = formatWithLeadingZero(month);
        String parsedDay = formatWithLeadingZero(day);
        return new DateInfo(parsedMonth, parsedDay, dayOfWeek, CampusTime.makeAbsentValue());
    }

    public void modifyAttendanceTime(CampusTime modifyCampusTime) {
        this.campusTime = modifyCampusTime;
        this.attendanceStatus = calculateStatus();
    }

    public int checkAbsenceStatus() {
        if (this.attendanceStatus.equals(AttendanceStatus.ABSENCE)) {
            return 1;
        }
        return 0;
    }

    public int checkLateStatus() {
        if (this.attendanceStatus.equals(AttendanceStatus.LATE)) {
            return 1;
        }
        return 0;
    }

    public int checkAttendanceStatus() {
        if (this.attendanceStatus.equals(AttendanceStatus.ATTENDANCE)) {
            return 1;
        }
        return 0;
    }

    private static String formatWithLeadingZero(int number) {
        String parsedNumber = String.valueOf(number);
        if (number < 10) {
            parsedNumber = "0" + number;
        }
        return parsedNumber;
    }

    private AttendanceStatus calculateStatus() {
        if (checkDefault()) {
            return AttendanceStatus.ABSENCE;
        }
        if (checkHoliday()) {
            return AttendanceStatus.HOLIDAY;
        }
        if (checkMonday()) {
            return checkAttendanceStatus("13");
        }
        return checkAttendanceStatus("10");
    }

    private boolean checkMonday() {
        return dayOfWeek.getDayOfWeek().equals("월요일");
    }

    private boolean checkHoliday() {
        return dayOfWeek.getDayOfWeek().equals("토요일") || dayOfWeek.getDayOfWeek().equals("일요일");
    }

    private boolean checkDefault() {
        return campusTime.getHour().equals("--");
    }

    private AttendanceStatus checkAttendanceStatus(String hourLimit) {
        int hourMinute = Integer.parseInt(campusTime.getHour() + campusTime.getMinute());
        int absentTime = Integer.parseInt(hourLimit + "30");
        int lateTime = Integer.parseInt(hourLimit + "05");
        if (hourMinute > absentTime) {
            return AttendanceStatus.ABSENCE;
        }
        if (hourMinute > lateTime) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getAttendanceStatus() {
        return attendanceStatus.getName();
    }

    public String getDayOfWeek() {
        return dayOfWeek.getDayOfWeek();
    }

    public CampusTime getTime() {
        return campusTime;
    }
}
