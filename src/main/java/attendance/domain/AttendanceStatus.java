package attendance.domain;

public enum AttendanceStatus {
    CHECKIN("출석"),
    ABSENCE("결석"),
    LATE("지각")
    ;

    private String koreanName;

    AttendanceStatus(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}
