package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        for (int i = 1; i <= lastDate.getDayOfMonth(); i++) {
            DayOfWeek todayDayOfWeek = LocalDate.of(2024, 12, i).getDayOfWeek();
            if (todayDayOfWeek == DayOfWeek.SATURDAY || todayDayOfWeek == DayOfWeek.SUNDAY || i == 25) {
                continue;
            }
            if (!containsDayOfMonth(i)) {
                attendanceInfo.add(new Attendance(LocalDateTime.of(2024, 12, i, 15, 0)));
            }
        }
    }

    public String printWarningInfo(LocalDate lastDate) {
        updateUntil(lastDate.minusDays(1));
        int lateCount = getLateCount();
        int originalAbsentCount = getOriginalAbsentCount();
        String nameAndCount = name + ": 결석 " + originalAbsentCount + "회, 지각 " + lateCount + "회 ";
        String warningStatus = calculateWarningStatus(getAbsentCount());
        if (warningStatus.isEmpty()) {
            return nameAndCount;
        }
        nameAndCount += "(" + warningStatus + ")";
        return nameAndCount;
    }

    public String calculateWarningStatus(int absentCount) {
        if (absentCount > 5) {
            return "제적";
        }
        if (absentCount >= 3) {
            return "면담";
        }
        if (absentCount >= 2) {
            return "경고";
        }
        return "";
    }

    public int getAttendanceCount() {
        return getStateCount("출석");
    }

    public int getLateCount() {
        return getStateCount("지각");
    }

    private int getOriginalAbsentCount() {
        return getStateCount("결석");
    }

    private int getStateCount(String state) {
        return (int) attendanceInfo.stream()
                .filter(a -> a.getState().equals(state))
                .count();
    }

    private boolean containsDayOfMonth(int i) {
        return attendanceInfo.stream()
                .anyMatch(a -> a.getDayOfMonth() == i);
    }
}
