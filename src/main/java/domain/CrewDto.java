package domain;


import java.util.List;

public class CrewDto {

    private final String nickName;
    private final Attendances attendances;
    private final int lateCount;
    private final int absentCount;
    private final PenaltyStatus penaltyStatus;

    public CrewDto(String nickName, Attendances attendances) {
        this.nickName = nickName;
        this.attendances = attendances;
        this.lateCount = calculateLateCount();
        this.absentCount = calculateAbsentCount();
        this.penaltyStatus = getPenaltyStatus(attendances.getNonAttendanceCount());
    }

    public String getNickName() {
        return nickName;
    }

    public int getLateCount() {
        return lateCount;
    }

    public Attendances getAttendances() {
        return attendances;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public PenaltyStatus getPenaltyStatus() {
        return penaltyStatus;
    }

    private Integer calculateLateCount() {
        return attendances.getLateCount();
    }

    private Integer calculateAbsentCount() {
        return attendances.getAbsentCount();
    }

    private PenaltyStatus getPenaltyStatus(int nonAttendanceCount) {
        return PenaltyStatus.getInstance(nonAttendanceCount);
    }


    public List<AttendanceDto> getAttendanceDtos() {
        return attendances.createAttendanceDtos();
    }

}
