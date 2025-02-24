package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.domain.constant.Weekday;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceRegistry {

    private final List<AttendanceChecker> attendanceCheckers;
    private int absence;
    private int late;
    private int attendance;

    private AttendanceRegistry(List<AttendanceChecker> attendanceCheckers) {
        this.attendanceCheckers = attendanceCheckers;
    }

    public static AttendanceRegistry fromDefaultValue(LocalDate now) {
        List<AttendanceChecker> attendanceCheckers = makeDefaultDateInfos(now);
        return new AttendanceRegistry(attendanceCheckers);
    }

    public static AttendanceRegistry from(List<AttendanceChecker> attendanceCheckers) {
        return new AttendanceRegistry(attendanceCheckers);
    }

    public void calculateAttendanceHistory() {
        int absence = 0;
        int late = 0;
        int attendance = 0;
        for (AttendanceChecker attendanceChecker : attendanceCheckers) {
            absence += mappingStatusToNumber(attendanceChecker.isAbsence());
            late += mappingStatusToNumber(attendanceChecker.isLate());
            attendance += mappingStatusToNumber(attendanceChecker.isAttendance());
        }
        this.absence = absence;
        this.late = late;
        this.attendance = attendance;
    }

    private int mappingStatusToNumber(final boolean attendanceStatus) {
        if (attendanceStatus) {
            return 1;
        }
        return 0;
    }

    private static List<AttendanceChecker> makeDefaultDateInfos(LocalDate now) {
        List<AttendanceChecker> attendanceCheckers = new ArrayList<>();
        for (int day = 1; day <= now.getDayOfMonth(); day++) {
            LocalDate currentDay = now.withDayOfMonth(day);
            addWeekdayDateInfo(currentDay, attendanceCheckers);
        }
        return attendanceCheckers;
    }

    private static void addWeekdayDateInfo(LocalDate currentDay, List<AttendanceChecker> attendanceCheckers) {
        if (checkHoliday(currentDay)) return;

        attendanceCheckers.add(AttendanceChecker.makeDefaultValue(currentDay));
    }

    private static boolean checkHoliday(LocalDate currentDay) {
        Weekday weekday = Weekday.from(currentDay.getDayOfWeek());
        return weekday.equals(Weekday.SATURDAY) || weekday.equals(Weekday.SUNDAY);
    }

    public AttendanceChecker findByDay(LocalDateTime day) {
        return attendanceCheckers.stream().filter(dateInfo -> dateInfo.getLocalDateTime().getDayOfMonth() == day.getDayOfMonth())
                .findFirst()
                .orElseThrow();
    }

    public int findStatusCounts(AttendanceStatus attendanceStatus) {
        return (int) attendanceCheckers.stream()
                .filter(dateInfo -> dateInfo.getAttendanceStatus().equals(attendanceStatus.getName()))
                .count();
    }

    public List<AttendanceChecker> getDateInfos() {
        return Collections.unmodifiableList(attendanceCheckers);
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
