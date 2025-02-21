package domain;

public class CrewDto {

    private final String nickName;
    private final int attendanceCount;
    private final int lateCount;
    private final int absentCount;
    private final PenaltyStatus penaltyStatus;

    public CrewDto(String nickName, int attendanceSize, int lateCount, int absentCount, PenaltyStatus penaltyStatus) {
        this.nickName = nickName;
        this.lateCount = lateCount;
        this.absentCount = absentCount;
        this.penaltyStatus = penaltyStatus;
        this.attendanceCount = attendanceSize - lateCount - absentCount;
    }

    public String getNickName() {
        return nickName;
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

    public PenaltyStatus getPenaltyStatus() {
        return penaltyStatus;
    }
}
