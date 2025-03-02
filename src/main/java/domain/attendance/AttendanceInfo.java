package domain.attendance;

import domain.attendance.constant.AttendanceStatus;
import domain.datetime.CampusDate;
import domain.datetime.CampusTime;
import java.time.DayOfWeek;

public class AttendanceInfo {

    private final CampusDate campusDate;
    private final CampusTime campusTime;
    private final AttendanceStatus attendanceStatus;

    private AttendanceInfo(final CampusDate campusDate, final CampusTime campusTime) {
        this.campusDate = campusDate;
        this.campusTime = campusTime;
        this.attendanceStatus = AttendanceStatus.calculateByDateAndTime(campusDate, campusTime);
    }

    public static AttendanceInfo fromDateAndTime(final CampusDate campusDate, final CampusTime campusTime) {
        return new AttendanceInfo(campusDate, campusTime);
    }

    public AttendanceInfo modifyInfoByTime(final CampusTime campusTime) {
        return new AttendanceInfo(this.campusDate, campusTime);
    }

    public int getMonth() {
        return campusDate.getMonth();
    }

    public int getDay() {
        return campusDate.getDay();
    }

    public int getHour() {
        return campusTime.getHour();
    }

    public int getMinute() {
        return campusTime.getMinute();
    }

    public DayOfWeek getDayOfWeek() {
        return campusDate.getDayOfWeek();
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

}
