package domain;

public class AttendanceCounter {

    private static final int TARDINESS_TO_ABSENCE_RATIO = 3;

    private final int attendanceCount;
    private final int tardinessCount;
    private final int absenceCount;

    public static AttendanceCounter of(final Attendances attendances) {
        attendanceStatusCounts counts = attendances.calculateAttendanceCount();
        return new AttendanceCounter(counts.attendanceCount(), counts.tardinessCount(), counts.absenceCount());
    }

    public AttendanceCounter(final int attendanceCount, final int tardinessCount, final int absenceCount) {
        this.attendanceCount = attendanceCount;
        this.tardinessCount = tardinessCount;
        this.absenceCount = absenceCount;
    }

    public int calculateAdjustedAbsenceCountWithTardinessCount() {
        return (this.tardinessCount * TARDINESS_TO_ABSENCE_RATIO) + this.absenceCount;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public int getTardiness() {
        return tardinessCount;
    }

    public int getAbsence() {
        return absenceCount;
    }
}
