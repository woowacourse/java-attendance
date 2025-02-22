package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.domain.constant.Weekday;

public class DateInfo {

    private final String month;
    private final String day;
    private final Weekday weekday;
    private Time time;
    private AttendanceStatus attendanceStatus;

    private DateInfo(String month, String day, Weekday weekday, Time time) {
        this.month = month;
        this.day = day;
        this.weekday = weekday;
        this.time = time;
        this.attendanceStatus = calculateStatus();
    }

    public static DateInfo of(int month, int day, Weekday weekday, Time time) {
        String parsedMonth = formatWithLeadingZero(month);
        String parsedDay = formatWithLeadingZero(day);
        return new DateInfo(parsedMonth, parsedDay, weekday, time);
    }

    public static DateInfo makeDefaultValue(int month, int day, Weekday weekday) {
        String parsedMonth = formatWithLeadingZero(month);
        String parsedDay = formatWithLeadingZero(day);
        return new DateInfo(parsedMonth, parsedDay, weekday, Time.makeAbsentValue());
    }

    public void modifyAttendanceTime(Time modifyTime) {
        this.time = modifyTime;
        this.attendanceStatus = calculateStatus();
    }

    public int isAbsence() {
        if (this.attendanceStatus.equals(AttendanceStatus.ABSENCE)) {
            return 1;
        }
        return 0;
    }
    public int isLate() {
        if (this.attendanceStatus.equals(AttendanceStatus.LATE)) {
            return 1;
        }
        return 0;
    }
    public int isAttendance() {
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
    //TODO : 리뷰 : 라인 수 궁금, 메서드 분리 궁금

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
        return weekday.getDayOfWeek().equals("월요일");
    }

    private boolean checkHoliday() {
        return weekday.getDayOfWeek().equals("토요일") || weekday.getDayOfWeek().equals("일요일");
    }

    private boolean checkDefault() {
        return time.getHour().equals("--");
    }

    private AttendanceStatus checkAttendanceStatus(String hourLimit) {
        int hourMinute = Integer.parseInt(time.getHour() + time.getMinute());
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
        return weekday.getDayOfWeek();
    }

    public Time getTime() {
        return time;
    }
}
