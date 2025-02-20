package domain;

import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceWarning;
import java.time.LocalDate;

public class Crew implements Comparable<Crew> {
    private final String name;
    private final Attendance attendance;

    public Crew(String name) {
        this.name = name;
        this.attendance = new Attendance(
                LocalDate.of(AttendanceDate.DEFAULT_START_TIME, AttendanceDate.DEFAULT_START_MONTH,
                        AttendanceDate.DEFAULT_START_DAY), LocalDate.now());
    }

    public String getName() {
        return this.name;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public boolean isAttendanceWarning() {
        return AttendanceWarning.determineAttendanceWarning(this.attendance.countAbsenceIncludingTardy())
                != AttendanceWarning.NONE;
    }

    @Override
    public int compareTo(Crew compareCrew) {
        if (this.attendance.countAbsence() < compareCrew.attendance.countAttendance()) {
            return -1;
        }
        if (this.attendance.countAbsence() > compareCrew.attendance.countAttendance()) {
            return 1;
        }
        if (this.attendance.countTardy() < compareCrew.attendance.countTardy()) {
            return -1;
        }
        if (this.attendance.countTardy() > compareCrew.attendance.countTardy()) {
            return 1;
        }
        return 0;
    }
}
