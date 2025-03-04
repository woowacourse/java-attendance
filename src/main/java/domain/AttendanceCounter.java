package domain;

public class AttendanceCounter {

    private final int attendanceCount;
    private final int tardinessCount;
    private final int absenceCount;

    private AttendanceCounter(final int attendanceCount, final int tardinessCount, final int absenceCount) {
        this.attendanceCount = attendanceCount;
        this.tardinessCount = tardinessCount;
        this.absenceCount = absenceCount;
    }

    public static AttendanceCounter of(final Attendances attendances) {
        final AttendanceDto dto = attendances.calculateAttendanceCount();
        return new AttendanceCounter(dto.attendanceCount(), dto.tardinessCount(), dto.absenceCount());
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public int getTardinessCount() {
        return tardinessCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }
}
