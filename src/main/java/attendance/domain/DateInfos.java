package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateInfos {

    private final List<DateInfo> dateInfos;
    private int absence;
    private int late;
    private int attendance;

    private DateInfos(List<DateInfo> dateInfos) {
        this.dateInfos = dateInfos;
    }

    public static DateInfos from(final List<DateInfo> dateInfos) {
        return new DateInfos(dateInfos);
    }

    public void calculateAttendanceHistory() {
        int absence = 0;
        int late = 0;
        int attendance = 0;
        for (DateInfo dateInfo : dateInfos) {
            absence += dateInfo.checkAbsenceStatus();
            late += dateInfo.checkLateStatus();
            attendance += dateInfo.checkAttendanceStatus();
        }
        this.absence = absence;
        this.late = late;
        this.attendance = attendance;
    }


    public DateInfo findByDate(int date) {
        return dateInfos.stream().filter(dateInfo -> Integer.parseInt(dateInfo.getDay()) == date)
                .findFirst()
                .orElseThrow();
    }

    public int findStatusCounts(AttendanceStatus attendanceStatus) {
        return (int) dateInfos.stream()
                .filter(dateInfo -> dateInfo.getAttendanceStatus().equals(attendanceStatus.getName()))
                .count();
    }

    public List<DateInfo> getDateInfos() {
        return new ArrayList<>(dateInfos);
    }

    public int getAbsence() {
        return absence;
    }

    public int getLate() {
        return late;
    }

    public int getAttendance() {
        return attendance;
    }

}
