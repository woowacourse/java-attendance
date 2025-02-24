package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import java.time.LocalDate;

public class DateInfo {

    private final LocalDate date;
    private CampusTime campusTime;
    private AttendanceStatus attendanceStatus;

    private DateInfo(LocalDate date, CampusTime campusTime) {
        this.date = date;
        this.campusTime = campusTime;
        this.attendanceStatus = AttendanceStatus.calculateAttendanceStatus(date, campusTime);
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

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public int getDayOfWeek() {
        return date.getDayOfWeek().getValue();
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
