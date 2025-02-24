package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.util.ArrayList;
import java.util.List;

public class DateInfos {

    private final List<DateInfo> dateInfos;

    private DateInfos(List<DateInfo> dateInfos) {
        this.dateInfos = dateInfos;
    }

    public static DateInfos initInfos() {
        return new DateInfos(new ArrayList<>());
    }

    public static DateInfos from(final List<DateInfo> dateInfos) {
        return new DateInfos(dateInfos);
    }

    public void addInfo(final DateInfo dateInfo) {
        this.dateInfos.add(dateInfo);
    }

    public DateInfo findDateInfoByDay(int day) {
        return dateInfos.stream().filter(dateInfo -> dateInfo.isAttendanceDay(day))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.NOT_HAVE_ATTENDANCE));
    }

    public AttendanceStatus findAttendanceStatusByDay(int day) {
        for (DateInfo dateInfo : dateInfos) {
            if (dateInfo.isAttendanceDay(day)) {
                return dateInfo.getAttendanceStatus();
            }
        }
        return AttendanceStatus.ABSENCE;
    }

    public int findStatusCounts(AttendanceStatus attendanceStatus) {
        return (int) dateInfos.stream()
                .filter(dateInfo -> dateInfo.getAttendanceStatus().equals(attendanceStatus.getName()))
                .count();
    }

    public List<DateInfo> getDateInfos() {
        return new ArrayList<>(dateInfos);
    }

}
