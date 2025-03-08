package domain.record;

import java.util.Objects;

public class AttendanceStatusCounts {
    private final int absence;
    private final int late;
    private final int attendance;
    private final int adjustedAbsence;

    private static final int LATENESS_TO_ABSENCE_RATIO = 3;

    public AttendanceStatusCounts(final int absence, final int late, final int attendance) {
        this.absence = absence;
        this.late = late;
        this.attendance = attendance;
        this.adjustedAbsence = calculateAdjustedAbsenceCount(absence, late);
    }

    private int calculateAdjustedAbsenceCount(final int absenceCount, final int latenessCount) {
        return (latenessCount / LATENESS_TO_ABSENCE_RATIO) + absenceCount;
    }

    public int getAbsence() {
        return absence;
    }

    public int getLate() {
        return late;
    }

    public int getAdjustedAbsence() {
        return adjustedAbsence;
    }

    public int getAttendance() {
        return attendance;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceStatusCounts that = (AttendanceStatusCounts) o;
        return absence == that.absence && late == that.late && attendance == that.attendance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(absence, late, attendance);
    }
}
