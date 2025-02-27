package domain;

import java.util.ArrayList;
import java.util.List;

public class AttendanceInfos {

    private final List<AttendanceInfo> attendanceInfos;

    private AttendanceInfos(List<AttendanceInfo> attendanceInfos) {
        this.attendanceInfos = attendanceInfos;
    }

    public static AttendanceInfos from(List<AttendanceInfo> attendanceInfos) {
        return new AttendanceInfos(attendanceInfos);
    }

    public static AttendanceInfos initInfos() {
        return new AttendanceInfos(new ArrayList<>());
    }

    public void addInfo(AttendanceInfo attendanceInfo) {
        attendanceInfos.add(attendanceInfo);
    }

    public List<AttendanceInfo> getAttendanceInfos() {
        return new ArrayList<>(attendanceInfos);
    }
}
