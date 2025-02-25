package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
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

    public void addDateInfo(final DateInfo dateInfo) {
        this.dateInfos.add(dateInfo);
    }

    public DateInfo findDateInfoByDay(final int day) {
        return dateInfos.stream().filter(dateInfo -> dateInfo.isAttendanceDay(day))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.NICKNAME_NOT_PRESENCE));
    }

    public DateInfo findOrCreateDateInfoByDate(final LocalDate date, final CampusTime campusTime) {
        for (DateInfo dateInfo : dateInfos) {
            if (dateInfo.isAttendanceDay(date.getDayOfMonth())) {
                return dateInfo;
            }
        }
        DateInfo newDateInfo = DateInfo.fromCampusTime(date, campusTime);
        dateInfos.add(newDateInfo);
        return newDateInfo;
    }

    public boolean hasDateInfo(final int day) {
        return dateInfos.stream()
                .anyMatch(dateInfo -> dateInfo.isAttendanceDay(day));
    }

    public AttendanceStatus findAttendanceStatusByDay(final int day) {
        for (DateInfo dateInfo : dateInfos) {
            if (dateInfo.isAttendanceDay(day)) {
                return dateInfo.getAttendanceStatus();
            }
        }
        return AttendanceStatus.ABSENCE;
    }

}
