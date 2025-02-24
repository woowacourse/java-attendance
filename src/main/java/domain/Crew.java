package domain;

import constant.AttendanceStatus;
import constant.Warning;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static constant.AttendanceStatus.*;
import static constant.Warning.*;

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

    public List<Attendance> update(LocalDateTime newDateAndTime) {
        if (!isAlreadyAttendedDay(newDateAndTime)) {
            throw new IllegalArgumentException("해당 날짜에 출석 기록이 없습니다.");
        }
        Attendance oldAttendance = attendanceInfo.stream()
                .filter(attendance -> attendance.isEqualDate(newDateAndTime))
                .findFirst()
                .orElseThrow();
        attendanceInfo.remove(oldAttendance);
        Attendance attendance = new Attendance(newDateAndTime);
        attendanceInfo.add(attendance);
        return List.of(oldAttendance, attendance);
    }

    public int getAbsentCount() {
        return getOriginalAbsentCount() + (getLateCount() / 3);
    }

    public void updateUntil(LocalDate lastDate) {
        for (int date = 1; date <= lastDate.getDayOfMonth(); date++) {
            DayOfWeek todayDayOfWeek = LocalDate.of(2024, 12, date).getDayOfWeek();
            if (todayDayOfWeek == DayOfWeek.SATURDAY || todayDayOfWeek == DayOfWeek.SUNDAY || date == 25) {
                continue;
            }
            if (!containsDayOfMonth(date)) {
                attendanceInfo.add(new Attendance(LocalDateTime.of(2024, 12, date, 15, 0)));
            }
        }
    }

    // 인자 : 지각 3회를 결석 1회로 간주한 결석 횟수
    public String calculateWarningStatus() {
        int absentCount = getAbsentCount() + getLateCount() / 3;
        if (absentCount > EXPEL.getAbsentCount()) {
            return EXPEL.getPenalty();
        }
        if (absentCount >= COUNSELING.getAbsentCount()) {
            return COUNSELING.getPenalty();
        }
        if (absentCount >= WARNING.getAbsentCount()) {
            return WARNING.getPenalty();
        }
        return "";
    }

    public int getAttendanceCount() {
        return getStateCount(ATTENDED.getStatus());
    }

    public int getLateCount() {
        return getStateCount(LATE.getStatus());
    }

    private int getOriginalAbsentCount() {
        return getStateCount(ABSENT.getStatus());
    }

    private int getStateCount(String state) {
        return (int) attendanceInfo.stream()
                .filter(a -> a.getState().equals(state))
                .count();
    }

    private boolean containsDayOfMonth(int date) {
        return attendanceInfo.stream()
                .anyMatch(a -> a.getDayOfMonth() == date);
    }
}
