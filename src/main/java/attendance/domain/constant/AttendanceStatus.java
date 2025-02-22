package attendance.domain.constant;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석"),
    HOLIDAY("주말");

    public static final String LATE_LIMIT = "05";
    public static final String ABSENT_LIMIT = "30";
    private final String name;

    AttendanceStatus(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}
