package attendance.domain.constant;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석"),
    HOLIDAY("주말");

    private final String name;

    AttendanceStatus(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
