package domain;

import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceWarning;
import java.time.LocalDate;

public class Crew implements Comparable<Crew> {
    private static final int DEFAULT_START_TIME = 2024;
    private static final int DEFAULT_START_MONTH = 12;
    private static final int DEFAULT_START_DAY = 2;

    public static final LocalDate DEFAULT_START_DATE = java.time.LocalDate.of(DEFAULT_START_TIME, DEFAULT_START_MONTH,
            DEFAULT_START_DAY);

    private final Attendance attendance;
    private final String name;

    public Crew(String name) {
        this.name = name;
        this.attendance = new Attendance(DEFAULT_START_DATE, LocalDate.now());
    }

    public boolean isAttendanceWarning() {
        return AttendanceWarning.determineAttendanceWarning(this.attendance.countAbsenceIncludingTardy())
                != AttendanceWarning.NONE;
    }

    @Override
    public int compareTo(Crew compareCrew) {
        int absenceCount = this.attendance.countAbsence();
        int compareAbsenceCount = compareCrew.getAttendance().countAbsence();
        int tardyCount = this.attendance.countTardy();
        int compareTardyCount = compareCrew.getAttendance().countTardy();

        if(absenceCount < compareAbsenceCount ||
                (absenceCount == compareAbsenceCount) && tardyCount < compareTardyCount){
            return 1;
        }
        if(absenceCount > compareAbsenceCount || tardyCount > compareTardyCount){
            return -1;
        }
        return this.name.compareTo(compareCrew.name);
    }

    public String getName() {
        return this.name;
    }

    public Attendance getAttendance() {
        return attendance;
    }
}
