package domain;

import java.util.List;

public class Attendances {

    List<Attendance> attendances;

    public Attendances(final List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public AttendanceDto calculateAttendanceCount() {
        final long attendanceCount = attendances.stream()
                .filter(attendance -> attendance.attendanceStatus.equals(AttendanceStatus.ATTENDANCE))
                .count();
        final long tardinessCount = attendances.stream()
                .filter(attendance -> attendance.attendanceStatus.equals(AttendanceStatus.TARDINESS))
                .count();
        final long absence = attendances.stream()
                .filter(attendance -> attendance.attendanceStatus.equals(AttendanceStatus.ABSENCE))
                .count();

        return new AttendanceDto((int) attendanceCount, (int) tardinessCount, (int) absence);
    }


}
