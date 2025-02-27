package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.domain.constant.CrewStatus;
import attendance.domain.constant.Weekday;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceRegistry {

    private final List<AttendanceChecker> attendanceCheckers;
    private List<Integer> attendanceTraces;
    private CrewStatus crewStatus;

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
        List<Integer> attendanceCounts = new ArrayList<>(List.of(0, 0, 0));
        for (AttendanceChecker attendanceChecker : attendanceCheckers) {
            attendanceCounts.set(0, attendanceCounts.getFirst() + mappingStatusToNumber(attendanceChecker.isAbsence()));
            attendanceCounts.set(1, attendanceCounts.get(1) + mappingStatusToNumber(attendanceChecker.isLate()));
            attendanceCounts.set(2, attendanceCounts.getLast() + mappingStatusToNumber(attendanceChecker.isAttendance()));
        }
        this.attendanceTraces = attendanceCounts;
        this.crewStatus = CrewStatus.from(attendanceCounts.getLast(), attendanceCounts.getFirst());
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


    public List<Integer> getAttendanceTraces() {
        return attendanceTraces;
    }

    public CrewStatus getCrewStatus() {
        return crewStatus;
    }

}
