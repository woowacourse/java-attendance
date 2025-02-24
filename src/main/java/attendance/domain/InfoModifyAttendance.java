package attendance.domain;

import java.util.List;

public class InfoModifyAttendance {
    private final String originalTime;
    private final String originalType;

    private final InfoCheckAttendance newAttendanceInfo;

    public InfoModifyAttendance(String originalTime, AttendanceType originalType, List<String> newAttendanceInfo) {
        this.originalTime = originalTime;
        this.originalType = originalType.toString();
        this.newAttendanceInfo = new InfoCheckAttendance(newAttendanceInfo);
    }

    public String getOriginalTime() {
        return originalTime;
    }

    public String getOriginalType() {
        return originalType;
    }

    public String getMonth() {
        return newAttendanceInfo.getMonth();
    }

    public String getDay() {
        return newAttendanceInfo.getDay();
    }

    public String getDayOfWeek() {
        return newAttendanceInfo.getDayOfWeek();
    }

    public String getNewTime() {
        return newAttendanceInfo.getAttendanceTime();
    }

    public String getNewType() {
        return newAttendanceInfo.getAttendanceType();
    }
}
