package attendance.domain;

import java.time.LocalDateTime;

public record Attendance(LocalDateTime dateTime, String state) {
    public Attendance(LocalDateTime dateTime) {
        this(dateTime, judgeStatus());
    }

    private static String judgeStatus() {
        return "ATTENDANCE";
    }
}
