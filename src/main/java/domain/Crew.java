package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import service.DateValidator;

public class Crew {
    public static final int ABSENT_HOUR = 23;
    public static final int ABSENT_MINUTE = 59;

    private final String nickname;
    private final AttendanceStatusCount attendanceStatusCount = new AttendanceStatusCount();
    private final List<Attendance> attendances = new ArrayList<>();

    public Crew(String nickname) {
        this.nickname = nickname;
    }

    public Attendance addAttendance(LocalDateTime date) {
        Attendance attendance = new Attendance(date);
        attendances.add(attendance);
        return attendance;
    }

    public void addAllAbsent(LocalDateTime today) {
        List<Integer> attendanceDates = attendances.stream()
                .map(Attendance::getDay)
                .toList();
        List<Integer> absentDates = calculateAbsentDates(today, attendanceDates);

        for (int day : absentDates) {
            attendances.add(
                    new Attendance(
                            LocalDateTime.of(today.getYear(), today.getMonth(), day, ABSENT_HOUR, ABSENT_MINUTE)));
        }
    }

    private List<Integer> calculateAbsentDates(LocalDateTime today, List<Integer> attendanceDays) {
        DateValidator dateValidator = new DateValidator();
        List<Integer> days = new ArrayList<>();
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            days.add(day);
        }

        List<Integer> weekDays = new ArrayList<>(days.stream()
                .filter(day -> !dateValidator.isHoliday(day, today))
                .toList());

        weekDays.removeAll(attendanceDays);
        return weekDays;
    }

    public Attendance getSpecificAttendance(int date) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDay(date))
                .findFirst()
                .orElseThrow();
    }

    public Attendance changeAttendance(int date, Time time) {
        Attendance targetAttendance = getSpecificAttendance(date);
        attendanceStatusCount.deleteStatus(targetAttendance);
        targetAttendance.updateAttendance(time);
        attendanceStatusCount.addStatus(targetAttendance);

        return targetAttendance;
    }

    public void saveAttendanceCount() {
        for (Attendance attendance : attendances) {
            attendanceStatusCount.addStatus(attendance);
        }
    }

    public AttendanceAlertLevel calculateAttendanceAlertLevel() {
        return attendanceStatusCount.calculateAttendanceAlertLevel();
    }

    public boolean isAlreadyChecked(LocalDateTime today) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameDay(today.getDayOfMonth()));
    }

    public String getNickname() {
        return nickname;
    }

    public AttendanceStatusCount getAttendanceCount() {
        return attendanceStatusCount;
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
