package domain;

public record AttendanceResult(Attend attend, AttendStatus attendStatus) {
    public boolean isEqualStatus(AttendStatus attendStatus) {
        return this.attendStatus == attendStatus;
    }
}
