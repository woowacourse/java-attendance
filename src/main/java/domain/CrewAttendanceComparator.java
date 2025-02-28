package domain;

import java.util.Comparator;

public class CrewAttendanceComparator implements Comparator<CrewAttendance> {
    private final AttendanceTimesComparator attendanceTimesComparator;

    public CrewAttendanceComparator(AttendanceTimesComparator attendanceTimesComparator) {
        this.attendanceTimesComparator = attendanceTimesComparator;
    }

    @Override
    public int compare(CrewAttendance c1, CrewAttendance c2) {
        int attendanceComparison = c1.compareAttendanceTimes(c2, attendanceTimesComparator);
        if (attendanceComparison != 0) {
            return attendanceComparison;
        }
        return c1.compareCrew(c2);
    }
}
