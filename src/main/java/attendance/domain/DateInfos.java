package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.domain.constant.Weekday;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DateInfos {

    private final List<DateInfo> dateInfos;
    private int absence;
    private int late;
    private int attendance;

    private DateInfos(List<DateInfo> dateInfos) {
        this.dateInfos = dateInfos;
    }

    public static DateInfos fromDefaultValue(LocalDate now) {
        List<DateInfo> dateInfos = makeDefaultDateInfos(now);
        return new DateInfos(dateInfos);
    }

    public static DateInfos from(List<DateInfo> dateInfos) {
        return new DateInfos(dateInfos);
    }

    public void calculateAttendanceHistory() {
        int absence = 0;
        int late = 0;
        int attendance = 0;
        for (DateInfo dateInfo : dateInfos) {
            absence += dateInfo.isAbsence();
            late += dateInfo.isLate();
            attendance += dateInfo.isAttendance();
        }
        this.absence = absence;
        this.late = late;
        this.attendance = attendance;
    }

    private static List<DateInfo> makeDefaultDateInfos(LocalDate now) {
        List<DateInfo> dateInfos = new ArrayList<>();
        for (int day = 1; day <= now.getDayOfMonth(); day++) {
            LocalDate currentDay = LocalDate.of(now.getYear(), now.getMonthValue(), day);
            addWeekdayDateInfo(currentDay, dateInfos);
        }
        return dateInfos;
    }

    private static void addWeekdayDateInfo(LocalDate currentDay, List<DateInfo> dateInfos) {
        if (checkHoliday(currentDay)) return;

        dateInfos.add(DateInfo.makeDefaultValue(currentDay.getMonthValue(),
                currentDay.getDayOfMonth(),
                Weekday.from(currentDay.getDayOfWeek())));
    }

    private static boolean checkHoliday(LocalDate currentDay) {
        return currentDay.getDayOfWeek().getValue() >= 6;
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
        return Collections.unmodifiableList(dateInfos);
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
