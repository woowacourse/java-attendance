package domain;

public class AttendanceInfo {

    private final CampusDate campusDate;
    private final CampusTime campusTime;

    private AttendanceInfo(final CampusDate campusDate, final CampusTime campusTime) {
        this.campusDate = campusDate;
        this.campusTime = campusTime;
    }

    public static AttendanceInfo fromDateAndTime (final CampusDate campusDate, final CampusTime campusTime) {
        return new AttendanceInfo(campusDate, campusTime);
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
}
