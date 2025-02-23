package domain;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATENESS("지각"),
    ABSENCE("결석");

    private final String status;

    AttendanceStatus(String status){
        this.status = status;
    }

    public static AttendanceStatus from(String status){
        return switch (status){
            case "출석" -> ATTENDANCE;
            case "지각" -> LATENESS;
            case "결석" -> ABSENCE;
            default -> throw new IllegalArgumentException("유효하지 않은 출석 상태입니다");
        };
    }

    public String getDescription(){
        return status;
    }
}


