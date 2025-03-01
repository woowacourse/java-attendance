package model;

import java.util.TreeSet;

public class AttendanceBook {

    private final TreeSet<Attendance> attendances;

    public AttendanceBook(final TreeSet<Attendance> attendances) {
        this.attendances = attendances;
    }

    public boolean isSameByDate(final Attendance attendance) {
        return attendances.stream()
                .anyMatch(o -> o.getAttendanceDateTime().getDateTime().equals(attendance.getAttendanceDateTime().getDateTime()));
    }

    public Attendance findByDayOfMonth(final DayOfMonth dayOfMonth) {
        return attendances.stream()
                .filter(o -> o.getAttendanceDateTime().getDateTime().getDayOfMonth() == dayOfMonth.getValue())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("날짜에 대한 출석 기록이 존재하지 않습니다."));
    }

    public void add(final Attendance attendance) {
        attendances.add(attendance);
    }

    public void update(final Attendance oldAttendance, final Attendance newAttendance) {
        attendances.remove(oldAttendance);
        attendances.add(newAttendance);
    }

    public TreeSet<Attendance> getAttendances() {
        return attendances;
    }
}
