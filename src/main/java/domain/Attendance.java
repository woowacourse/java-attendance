package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Attendance {
    private final LocalDateTime dateAndTime;
    private final AttendanceStatus attendanceStatus;

    public Attendance(LocalDateTime localDateTime) {
        dateAndTime = localDateTime;
        this.attendanceStatus = AttendanceStatus.checkAttendanceState(localDateTime);
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public int getDayOfMonth() {
        return dateAndTime.getDayOfMonth();
    }

    public AttendanceStatus getStatus() {
        return attendanceStatus;
    }

    public String getStatusValue() {
        return attendanceStatus.getStringValue();
    }

    public String getFormattedAttended() {
        LocalDateTime dateAndTime = getDateAndTime();
        return dateAndTime.format(DateTimeFormatter.ofPattern("MM월 dd일 "))
                + dateAndTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN) + " "
                + getFormattedTimeAndState();
    }

    public String getFormattedTimeAndState() {
        String state = getStatusValue();
        if (state.equals("결석")) {
            return "--:-- " + "(" + state + ")";
        }
        return getDateAndTime().format(DateTimeFormatter.ofPattern("HH:mm ", Locale.KOREAN)) + "("
                + getStatus() + ")";
    }

    public boolean isEqualDate(LocalDateTime localDateTime) {
        return dateAndTime.toLocalDate().isEqual(localDateTime.toLocalDate());
    }

    public boolean isEqualDayOfMonth(int dayOfMonth) {
        return dateAndTime.getDayOfMonth() == dayOfMonth;
    }
}
