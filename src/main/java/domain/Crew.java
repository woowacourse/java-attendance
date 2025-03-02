package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Crew {

    private final String name;
    private final Attendances attendances;

    public Crew(String name) {
        this.name = name;
        this.attendances = new Attendances();
    }

    public Attendance addAttendance(LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(date, time);
        attendances.addAttendance(attendance);
        return attendance;
    }

    public Attendance updateAttendance(LocalDate date, LocalTime time) {
        Attendance newAttendance = new Attendance(date, time);
        attendances.updateAttendance(date, newAttendance);
        return newAttendance;
    }

    public Attendance findAttendanceByDate(LocalDate date) {
        return attendances.findAttendanceByDate(date);
    }

    public int calculateAttendanceCount(LocalDate nowDate) {
        return attendances.countAttendanceByStatus(AttendanceStatus.ATTENDANCE, nowDate);
    }

    public int calculateLatenessCount(LocalDate nowDate) {
        return attendances.countAttendanceByStatus(AttendanceStatus.LATENESS, nowDate);
    }

    public int calculateAbsenceCount(LocalDate nowDate) {
        LocalDate startDate = LocalDate.of(2024, 12, 1);

        int absenceCount = (int) startDate.datesUntil(nowDate)
                .filter(this::isAbsentDay)
                .count();

        absenceCount += countManualAbsences(nowDate);
        return absenceCount;
    }

    public Penalty determinePenaltyStatus(LocalDate nowDate) {
        int latenessCount = calculateLatenessCount(nowDate);
        int absenceCount = calculateAbsenceCount(nowDate);
        return Penalty.from(latenessCount, absenceCount);
    }

    public boolean hasAlreadyAttended(LocalDate date) {
        return attendances.hasAlreadyAttended(date);
    }

    public String getName() {
        return name;
    }

    private int countManualAbsences(LocalDate nowDate) {
        return attendances.countAttendanceByStatus(AttendanceStatus.ABSENCE, nowDate);
    }

    private boolean isAbsentDay(LocalDate date) {
        return !isHoliday(date) && !hasAlreadyAttended(date);
    }

    private boolean isHoliday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                date.equals(LocalDate.of(2024, 12, 25));
    }
}
