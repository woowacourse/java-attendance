package attendance.domain;

import attendance.exception.AttendanceArgumentException;
import java.util.Arrays;
import java.util.Map;

public enum AttendanceStatus {
    
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private static final String INVALID_STATUS = "존재하지 않는 출석 상태입니다.";
    private final String status;

    AttendanceStatus(String status) {
        this.status = status;
    }

    public static AttendanceStatus of(String findStatus) {
        return Arrays.stream(AttendanceStatus.values())
                .filter((attendanceStatus) -> attendanceStatus.status.equals(findStatus))
                .findFirst()
                .orElseThrow(() -> new AttendanceArgumentException(INVALID_STATUS));
    }

    public static int attendanceCount(Map<String, Integer> statusMap) {
        return statusMap.getOrDefault(ATTENDANCE.status, 0);
    }

    public static int lateCount(Map<String, Integer> statusMap) {
        return statusMap.getOrDefault(LATE.status, 0);
    }

    public static int absenceCount(Map<String, Integer> statusMap) {
        return statusMap.getOrDefault(ABSENCE.status, 0);
    }

    public String getStatus() {
        return status;
    }
}
