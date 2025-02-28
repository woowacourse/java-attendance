package model;

import java.util.TreeSet;

public class AttendanceBook {

    private final TreeSet<Attendance> attendances;

    public AttendanceBook(final TreeSet<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(final Attendance attendance) {
        attendances.add(attendance);
    }

    public TreeSet<Attendance> getAttendances() {
        return attendances;
    }


}
