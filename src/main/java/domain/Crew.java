package domain;

import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceWarning;
import java.time.LocalDate;

public class Crew implements Comparable<Crew> {
    private final String name;
    private final Attendance attendance;

    private static final int DEFAULT_START_TIME = 2024;
    private static final int DEFAULT_START_MONTH = 12;
    private static final int DEFAULT_START_DAY = 2;

    public static final LocalDate DEFAULT_START_DATE = java.time.LocalDate.of(DEFAULT_START_TIME, DEFAULT_START_MONTH,
            DEFAULT_START_DAY);

    public Crew(String name) {
        this.name = name;
        this.attendance = new Attendance(DEFAULT_START_DATE, LocalDate.now());
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
        if (this.attendance.countAbsence() < compareCrew.attendance.countAbsence()) {
            return 1;
        }
        if (this.attendance.countAbsence() > compareCrew.attendance.countAbsence()) {
            return -1;
        }
        if (this.attendance.countTardy() < compareCrew.attendance.countTardy()) {
            return 1;
        }
        if (this.attendance.countTardy() > compareCrew.attendance.countTardy()) {
            return -1;
        }
        return this.name.compareTo(compareCrew.name);
    }
}
