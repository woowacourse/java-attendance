package domain;

import dto.AttendanceData;
import dto.ModifyResult;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Crew {
    private final String name;
    private final List<Attendance> attendanceInfo;

    public Crew(String name) {
        this.name = name;
        this.attendanceInfo = new ArrayList<>();
    }

    public Attendance addAttendance(LocalDateTime localDateTime) {
        Attendance isAlreadyExisted = getAlreadyExistAttendance(localDateTime);
        if (isAlreadyExisted != null) {
            throw new IllegalArgumentException("이미 출석되었습니다.");
        }
        Attendance attendance = new Attendance(localDateTime);
        attendanceInfo.add(attendance);
        return attendance;
    }

    public String getName() {
        return name;
    }

    private Attendance getAlreadyExistAttendance(LocalDateTime localDateTime) {
        Optional<Attendance> sameDateAttendance = attendanceInfo.stream()
                .filter(attendance ->
                        attendance.isEqualDate(localDateTime))
                .findFirst();
        return sameDateAttendance.orElse(null);
    }

    public ModifyResult update(LocalDateTime newDateAndTime) {
        Attendance existAttendance = getAlreadyExistAttendance(newDateAndTime);
        if (existAttendance == null) {
            throw new IllegalArgumentException("기존의 출석 기록이 없습니다.");
        }
        attendanceInfo.remove(existAttendance);
        Attendance attendance = new Attendance(newDateAndTime);
        attendanceInfo.add(attendance);
        return new ModifyResult(existAttendance, attendance);
    }

    public AttendanceData getAttendanceHistory(LocalDate lastDate) {
        updateUntil(lastDate);
        updateUntil(lastDate);
        attendanceInfo.sort(Comparator.comparing(Attendance::getDayOfMonth));
        return new AttendanceData(Collections.unmodifiableList(attendanceInfo));
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
        WarningStatus warningStatus = WarningStatus.from(getAbsentCount());
        if (warningStatus == WarningStatus.NONE) {
            return nameAndCount;
        }
        nameAndCount += "(" + warningStatus + ")";
        return nameAndCount;
    }

    private int getAttendanceCount() {
        return getStateCount("출석");
    }

    private int getLateCount() {
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
