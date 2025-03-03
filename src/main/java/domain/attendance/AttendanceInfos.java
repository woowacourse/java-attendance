package domain.attendance;

import domain.attendance.constant.AttendanceStatus;
import domain.datetime.CampusDate;
import domain.datetime.CampusTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
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

    public AttendanceInfos addInfoByDateAndTime(final CampusDate campusDate, final CampusTime campusTime) {
        AttendanceInfo attendanceInfo = AttendanceInfo.fromDateAndTime(campusDate, campusTime);
        attendanceInfos.add(attendanceInfo);
        return new AttendanceInfos(attendanceInfos);
    }

    public boolean hasInfoByDate(final CampusDate campusDate) {
        return attendanceInfos.stream()
                .anyMatch(attendanceInfo -> ifDayEqual(campusDate, attendanceInfo));
    }

    public AttendanceInfo findInfoByDate(final CampusDate campusDate) {
        return attendanceInfos.stream()
                .filter(attendanceInfo -> ifDayEqual(campusDate, attendanceInfo))
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

    public AttendanceCounts countsByDate(final LocalDate date) {
        AttendanceCounts attendanceCounts = AttendanceCounts.initCounts();
        for (int day = 1; day < date.getDayOfMonth(); day++) {
            if (currentDateIsWeekend(date, day)) {
                continue;
            }
            attendanceCounts = addAttendanceCounts(CampusDate.ofDateWithDay(date, day), attendanceCounts);
        }
        return attendanceCounts;
    }

    private boolean currentDateIsWeekend(LocalDate date, int day) {
        LocalDate currentDate = LocalDate.of(date.getYear(), date.getMonth(), day);
        return currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private AttendanceCounts addAttendanceCounts(CampusDate currentCampusDate, AttendanceCounts attendanceCounts) {
        if (!hasInfoByDate(currentCampusDate)) {
            attendanceCounts = attendanceCounts.plusAttendanceCounts(AttendanceStatus.ABSENCE);
            return attendanceCounts;
        }
        attendanceCounts = attendanceCounts.plusAttendanceCounts(
                findInfoByDate(currentCampusDate).getAttendanceStatus());
        return attendanceCounts;
    }

    private boolean ifDayEqual(final CampusDate campusDate, final AttendanceInfo attendanceInfo) {
        return attendanceInfo.getDay() == campusDate.getDay();
    }

    private AttendanceInfo modifyInfoIfSameDay(final CampusDate campusDate, final CampusTime campusTime,
                                               final AttendanceInfo info) {
        if (ifDayEqual(campusDate, info)) {
            return info.modifyInfoByTime(campusTime);
        }
        return info;
    }

    public List<AttendanceInfo> getAttendanceInfos() {
        return new ArrayList<>(attendanceInfos);
    }
}
