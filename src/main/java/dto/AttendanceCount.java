package dto;

public class AttendanceCount {
    private final int attendanceCount;
    private final int lateCount;
    private final int absentCount;

    public AttendanceCount(int attendanceCount, int lateCount, int absentCount) {
        this.attendanceCount = attendanceCount;
        this.lateCount = lateCount;
        this.absentCount = absentCount;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }
}
