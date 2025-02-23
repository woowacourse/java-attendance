package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import util.Day;

public class Crew {
    public static final int ABSENT_HOUR = 23;
    public static final int ABSENT_MINUTE = 59;
    private final String nickname;
    private final AttendanceCount attendanceCount = new AttendanceCount();
    private final List<Attendance> attendances = new ArrayList<>();

    public Crew(String nickname) {
        this.nickname = nickname;
    }

    public Attendance addAttendance(LocalDateTime date) {
        Attendance attendance = new Attendance(date);
        attendances.add(attendance);
        return attendance;
    }

    public void addAbsent(LocalDateTime today) {
        int dayOfMonth = today.getDayOfMonth();
        List<Integer> attendanceDays = attendances.stream().map(Attendance::getDay).toList();
        List<Integer> weekDays = new ArrayList<>();
        for (int day = 1; day < dayOfMonth; day++) {
            if (Day.isHoliday(day, today)) {
                continue;
            }
            weekDays.add(day);
        }
        weekDays.removeAll(attendanceDays);

        for (int day : weekDays) {
            attendances.add(new Attendance(LocalDateTime.of(today.getYear(), today.getMonth(), day, ABSENT_HOUR,
                    ABSENT_MINUTE)));
        }
    }

    public Attendance getSpecificAttendance(int date) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDay(date))
                .findFirst()
                .orElseThrow();
    }

    public Attendance changeAttendance(int date, Time time) {
        Attendance targetAttendance = getSpecificAttendance(date);
        targetAttendance.updateAttendance(time);
        updateAttendanceCount();

        return targetAttendance;
    }

    public void updateAttendanceCount() {
        for (Attendance attendance : attendances) {
            attendanceCount.calculateRecord(attendance);
        }
    }

    public AttendanceAlertLevel calculateAttendanceAlertLevel() {
        return attendanceCount.calculateAttendanceAlertLevel();
    }

    public boolean isAlreadyChecked(LocalDateTime today) {
        long todayAttendance = attendances.stream()
                .filter(attendance -> attendance.isSameDay(today.getDayOfMonth()))
                .count();

        if (todayAttendance != 0) {
            return true;
        }
        return false;
    }

    public String getNickname() {
        return nickname;
    }

    public AttendanceCount getAttendanceCount() {
        return attendanceCount;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickname);
    }
}
