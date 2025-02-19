package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import service.DayComparator;

public class Crew {
    private final String nickname;
    private final AttendanceCount attendanceCount = new AttendanceCount();
    private final List<Attendance> attendances = new ArrayList<>();

    public Crew(String nickname) {
        this.nickname = nickname;
    }

    public void addAttendance(LocalDateTime date) {
        attendances.add(new Attendance(date));
    }

    public void addAbsent(LocalDateTime today) {
        int dayOfMonth = today.getDayOfMonth();
        List<Integer> attendanceDays = attendances.stream().map(Attendance::getDay).toList();
        List<Integer> weekDays = new ArrayList<>();
        for (int day = 1; day < dayOfMonth; day++) {
            if (DayComparator.isHoliday(day, today)) {
                continue;
            }
            weekDays.add(day);
        }
        weekDays.removeAll(attendanceDays);

        for (int day : weekDays) {
            attendances.add(new Attendance(LocalDateTime.of(today.getYear(), today.getMonth(), day, 23, 59)));
        }
    }

    public void updateAttendanceCount() {
        for (Attendance attendance : attendances) {
            attendanceCount.calculateRecord(attendance);
        }
    }

    public AttendanceAlertLevel calculateAttendanceAlertLevel() {
        return attendanceCount.calculateAttendanceAlertLevel();
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
