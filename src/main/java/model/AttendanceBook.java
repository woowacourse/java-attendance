package model;

import java.util.TreeSet;

public class AttendanceBook {

    private final TreeSet<Attendance> attendances;

    public AttendanceBook(final TreeSet<Attendance> attendances) {
        this.attendances = attendances;
    }

    public boolean sameByDate(final Attendance attendance) {
        return attendances.stream()
                .anyMatch(o -> o.getAttendanceDateTime().getDateTime().equals(attendance.getAttendanceDateTime().getDateTime()));
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
