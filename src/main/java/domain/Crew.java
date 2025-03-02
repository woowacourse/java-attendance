package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {

    private final String name;
    private final List<Attendance> attendances;

    public Crew(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.attendances = new ArrayList<>();
        attendances.add(new Attendance(date, time));
    }

    public Attendance addAttendance(LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(date, time);
        attendances.add(attendance);
        return attendance;
    }

    public Attendance updateAttendance(LocalDate date, LocalTime time) {
        attendances.removeIf(attendance -> attendance.hasSameDate(date));
        return addAttendance(date, time);
    }

    public Attendance findAttendanceByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.hasSameDate(date))
                .findFirst()
                .orElse(null);
    }

    public boolean hasAlreadyAttended(LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.hasSameDate(date));
    }

    public int calculateAttendanceCount(LocalDate nowDate) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.determineStatus() == AttendanceStatus.ATTENDANCE)
                .filter(attendance -> attendance.isBeforeDate(nowDate))
                .count();
    }

    public int calculateLatenessCount(LocalDate nowDate) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.determineStatus() == AttendanceStatus.LATENESS)
                .filter(attendance -> attendance.isBeforeDate(nowDate))
                .count();
    }

    public int calculateAbsenceCount(LocalDate nowDate) {
        LocalDate startDate = LocalDate.of(2024, 12, 1);

        int absenceCount = (int) startDate.datesUntil(nowDate)
                .filter(this::isAbsentDay)
                .count();

        absenceCount += countManualAbsences(nowDate);
        return absenceCount;
    }

    private boolean isAbsentDay(LocalDate date) {
        return !isHoliday(date) && !attendanceExists(date);
    }

    private int countManualAbsences(LocalDate nowDate) {
        return (int) attendances.stream()
                .filter(attendance -> attendance.determineStatus() == AttendanceStatus.ABSENCE)
                .filter(attendance -> attendance.isBeforeDate(nowDate))
                .count();
    }

    private boolean attendanceExists(LocalDate date) {
        return attendances.stream().anyMatch(attendance -> attendance.hasSameDate(date));
    }

    private boolean isHoliday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                date.equals(LocalDate.of(2024, 12, 25));
    }
}
