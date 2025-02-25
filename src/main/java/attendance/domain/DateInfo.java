package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateInfo {

    private final LocalDate date;
    private CampusTime campusTime;
    private AttendanceStatus attendanceStatus;

    private DateInfo(LocalDate date, CampusTime campusTime) {
        validateDateIsWeekday(date);
        this.date = date;
        this.campusTime = campusTime;
        this.attendanceStatus = AttendanceStatus.calculateAttendanceStatus(date, campusTime);
    }

    private void validateDateIsWeekday(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw CustomException.from(ErrorMessage.NOT_WEEKEND);
        }
    }

    public static DateInfo fromCampusTime(LocalDate localDate, CampusTime campusTime) {
        return new DateInfo(localDate, campusTime);
    }

    public void modifyAttendanceTime(CampusTime modifyCampusTime) {
        this.campusTime = modifyCampusTime;
        this.attendanceStatus = AttendanceStatus.calculateAttendanceStatus(date, modifyCampusTime);
    }

    public boolean isAttendanceDay(int day) {
        return date.getDayOfMonth() == day;
    }

    private void validateDateIsWeekday(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw CustomException.from(ErrorMessage.NOT_WEEKEND);
        }
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public DayOfWeek getDayOfWeek() {
        return date.getDayOfWeek();
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public int getCampusHour() {
        return campusTime.getHour();
    }

    public int getCampusMinute() {
        return campusTime.getMinute();
    }

}
