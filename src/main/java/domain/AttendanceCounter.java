package domain;

public class AttendanceCounter {

    private int attendanceCount;
    private int tardinessCount;
    private int absenceCount;

    public static AttendanceCounter of(final Attendances attendances) {
        AttendanceDto dto = attendances.calculateAttendanceCount();
        return new AttendanceCounter(dto.attendanceCount(), dto.tardinessCount(), dto.absenceCount());
    }

    public AttendanceCounter(final int attendanceCount, final int tardinessCount, final int absenceCount) {
        this.attendanceCount = attendanceCount;
        this.tardinessCount = tardinessCount;
        this.absenceCount = absenceCount;
    }

    public void incrementAttendanceCount() {
        attendanceCount++;
    }

    public void incrementTardiness() {
        tardinessCount++;
    }

    public void incrementAbsence() {
        absenceCount++;
    }

    public void decrementAttendanceCount() {
        attendanceCount--;
    }

    public void decrementTardiness() {
        tardinessCount--;
    }

    public void decrementAbsence() {
        absenceCount--;
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
