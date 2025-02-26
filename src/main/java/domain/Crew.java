package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static domain.AttendanceStatus.*;
import static domain.AbsentPenalty.*;

public class Crew {
    private final String name;
    private final List<Attendance> attendanceInfo;

    public Crew(String name) {
        this.name = name;
        this.attendanceInfo = new ArrayList<>();
    }

    public Attendance addAttendance(LocalDateTime localDateTime) {
        if (isAlreadyAttendedDay(localDateTime)) {
            throw new IllegalArgumentException("이미 출석 되었습니다.");
        }
        Attendance attendance = new Attendance(localDateTime);
        attendanceInfo.add(attendance);
        return attendance;
    }

    public boolean isSameName(String name) {
        return name.equals(this.name);
    }

    public List<Attendance> getAttendanceInfo() {
        return attendanceInfo;
    }

    public void sortAttendanceInfo() {
        attendanceInfo.sort(Comparator.comparing(Attendance::getDayOfMonth));
    }

    public String getName() {
        return name;
    }

    private boolean isAlreadyAttendedDay(LocalDateTime localDateTime) {
        return attendanceInfo.stream()
                .anyMatch(attendance ->
                        attendance.isEqualDate(localDateTime));
    }

    public AttendanceUpdateResult update(LocalDateTime newDateAndTime) {
        if (!isAlreadyAttendedDay(newDateAndTime)) {
            throw new IllegalArgumentException("해당 날짜에 출석 기록이 없습니다.");
        }
        Attendance oldAttendance = attendanceInfo.stream()
                .filter(attendance -> attendance.isEqualDate(newDateAndTime))
                .findFirst()
                .orElseThrow();
        attendanceInfo.remove(oldAttendance);
        Attendance newAttendance = new Attendance(newDateAndTime);
        attendanceInfo.add(newAttendance);
        return new AttendanceUpdateResult(oldAttendance, newAttendance);
    }

    public void updateAbsentUntil(LocalDate lastDate) {
        for (int date = 1; date <= lastDate.getDayOfMonth(); date++) {
            DayOfWeek todayDayOfWeek = LocalDate.of(2024, 12, date).getDayOfWeek();
            if (todayDayOfWeek == DayOfWeek.SATURDAY || todayDayOfWeek == DayOfWeek.SUNDAY || date == 25) {
                continue;
            }
            if (!containsDayOfMonth(date)) {
                // absentTime - 15:00
                attendanceInfo.add(new Attendance(LocalDateTime.of(2024, 12, date, 15, 0)));
            }
        }
    }


    public String getFormatedAttendanceInfo() {
        StringBuilder formatedAttendanceInfo = new StringBuilder();
        for (Attendance attendance : attendanceInfo) {
            formatedAttendanceInfo.append(attendance.getFormattedAttended()).append("\n");
        }
        return formatedAttendanceInfo.toString();
    }

    public String getFormatedAttendanceStateInfo() {
        return "출석: " + getAttendanceCount() + "회\n"
                + "지각: " + getLateCount() + "회\n"
                + "결석: " + getAbsentCount() + "회\n";
    }


    public String getFormatedWarningStatus() {
        AbsentPenalty absentPenalty = getAbsentPenalty();
        if (absentPenalty == AbsentPenalty.NONE) {
            return "";
        }
        return absentPenalty.getPenalty() + " 대상자입니다.";
    }



    public AbsentPenalty getAbsentPenalty() {
        int absentCount = getAbsentCount() + getLateCount() / 3;
        if (absentCount > EXPEL.getAbsentCount()) {
            return EXPEL;
        }
        if (absentCount >= COUNSELING.getAbsentCount()) {
            return COUNSELING;
        }
        if (absentCount >= WARNING.getAbsentCount()) {
            return WARNING;
        }
        return NONE;
    }

    public int getAttendanceCount() {
        return getStateCount(ATTENDED);
    }

    public int getLateCount() {
        return getStateCount(LATE);
    }

    public int getAbsentCount() {
        return getStateCount(ABSENT);
    }

    private int getStateCount(AttendanceStatus status) {
        return (int) attendanceInfo.stream()
                .filter(a -> a.getStatus() == status)
                .count();
    }

    private boolean containsDayOfMonth(int dayOfMonth) {
        return attendanceInfo.stream()
                .anyMatch(attendance -> attendance.isEqualDayOfMonth(dayOfMonth));
    }
}
