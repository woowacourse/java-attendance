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

    // TODO(fix) : 삭제 고민하기...
    public static DateInfo ofDefaultValue(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        return new DateInfo(date, null);
    }

    public void modifyAttendanceTime(CampusTime modifyCampusTime) {
        this.campusTime = modifyCampusTime;
        this.attendanceStatus = AttendanceStatus.calculateAttendanceStatus(date, modifyCampusTime);
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

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public int getDayOfWeek() {
        return date.getDayOfWeek().getValue();
    }

    public String getAttendanceStatus() {
        return attendanceStatus.getStatus();
    }

    public int getCampusHour() {
        return campusTime.getHour();
    }

    public int getCampusMinute() {
        return campusTime.getMinute();
    }

}
