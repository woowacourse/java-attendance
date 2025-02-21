package domain;

public class AttendanceCounter {

    private final int attendanceCount;
    private final int tardinessCount;
    private final int absenceCount;

    public AttendanceCounter(final int attendanceCount, final int tardinessCount, final int absenceCount) {
        this.attendanceCount = attendanceCount;
        this.tardinessCount = tardinessCount;
        this.absenceCount = absenceCount;
    }

    public static AttendanceCounter of(final Attendances attendances) {
        AttendanceDto dto = attendances.calculateAttendanceCount();
        return new AttendanceCounter(dto.attendanceCount(), dto.tardinessCount(), dto.absenceCount());
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
