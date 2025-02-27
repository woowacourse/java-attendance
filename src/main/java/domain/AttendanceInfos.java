package domain;

import java.util.ArrayList;
import java.util.List;

public class AttendanceInfos {

    private final List<AttendanceInfo> attendanceInfos;

    private AttendanceInfos(final List<AttendanceInfo> attendanceInfos) {
        this.attendanceInfos = attendanceInfos;
    }

    public static AttendanceInfos from(final List<AttendanceInfo> attendanceInfos) {
        return new AttendanceInfos(attendanceInfos);
    }

    public static AttendanceInfos initInfos() {
        return new AttendanceInfos(new ArrayList<>());
    }

    public void addInfo(final AttendanceInfo attendanceInfo) {
        attendanceInfos.add(attendanceInfo);
    }

    public AttendanceInfo findInfoByDay(final int day) {
        return attendanceInfos.stream()
                .filter(attendanceInfo -> attendanceInfo.getDay() == day)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜의 출석 정보를 찾을 수 없습니다."));
    }

    public List<AttendanceInfo> getAttendanceInfos() {
        return new ArrayList<>(attendanceInfos);
    }
}
