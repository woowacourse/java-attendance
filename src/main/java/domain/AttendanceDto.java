package domain;

public class AttendanceDto {

    private Boolean isLate;
    private Boolean isAbsent;

    public AttendanceDto(Boolean isLate, Boolean isAbsent) {
        this.isLate = isLate;
        this.isAbsent = isAbsent;
    }

    public Boolean getLate() {
        return isLate;
    }

    public Boolean getAbsent() {
        return isAbsent;
    }
}
