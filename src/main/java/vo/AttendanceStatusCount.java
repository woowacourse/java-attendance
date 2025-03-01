package vo;

public record AttendanceStatusCount(
    int onTime,
    int late,
    int absence
) {

}
