package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void addInfoByDateAndTime(final CampusDate campusDate, final CampusTime campusTime) {
        AttendanceInfo attendanceInfo = AttendanceInfo.fromDateAndTime(campusDate, campusTime);
        attendanceInfos.add(attendanceInfo);
    }

    public boolean hasInfoByDate(final CampusDate campusDate) {
        return attendanceInfos.stream()
                .anyMatch(attendanceInfo -> attendanceInfo.getDay() == campusDate.getDay());
    }

    public AttendanceInfo findInfoByDate(final CampusDate campusDate) {
        return attendanceInfos.stream()
                .filter(attendanceInfo -> attendanceInfo.getDay() == campusDate.getDay())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜의 출석 정보를 찾을 수 없습니다."));
    }

    public AttendanceInfos modifyInfoByDateAndTime(final CampusDate campusDate, final CampusTime campusTime) {
        List<AttendanceInfo> modifiedList = attendanceInfos.stream()
                .map(info -> {
                    return modifyInfoIfSameDay(campusDate, campusTime, info);
                })
                .collect(Collectors.toList());
        return new AttendanceInfos(modifiedList);
    }

    private AttendanceInfo modifyInfoIfSameDay(final CampusDate campusDate, final CampusTime campusTime, final AttendanceInfo info) {
        if (info.getDay() == campusDate.getDay()) {
            return info.modifyInfoByTime(campusTime);
        }
        return info;
    }

    public List<AttendanceInfo> getAttendanceInfos() {
        return new ArrayList<>(attendanceInfos);
    }
}
