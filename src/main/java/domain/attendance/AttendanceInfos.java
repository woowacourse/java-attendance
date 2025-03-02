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
        int attendanceCount = 0;
        int tardinessCount = 0;
        int absenceCount = 0;

        for (int day = 1; day < date.getDayOfMonth(); day++) {
            if (isWeekend(LocalDate.of(date.getYear(), date.getMonth(), day))) {
                continue;
            }
            CampusDate currentDate = CampusDate.ofDateWithDay(date, day);
            if (notHaveInfoByDate(currentDate)) {
                absenceCount++;
                continue;
            }
            AttendanceStatus attendanceStatus = findInfoByDate(currentDate).getAttendanceStatus();
            if (attendanceStatus == AttendanceStatus.ATTENDANCE) {
                attendanceCount++;
                continue;
            }
            if (attendanceStatus == AttendanceStatus.ABSENCE) {
                absenceCount++;
                continue;
            }
            if (attendanceStatus == AttendanceStatus.TARDINESS) {
                tardinessCount++;
                continue;
            }
        }
        return AttendanceCounts.ofStatusCounts(attendanceCount, tardinessCount, absenceCount);
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean ifDayEqual(final CampusDate campusDate, final AttendanceInfo attendanceInfo) {
        return attendanceInfo.getDay() == campusDate.getDay();
    }

    private boolean notHaveInfoByDate(CampusDate campusDate) {
        return !hasInfoByDate(campusDate);
    }

    private AttendanceInfo modifyInfoIfSameDay(final CampusDate campusDate, final CampusTime campusTime, final AttendanceInfo info) {
        if (ifDayEqual(campusDate, info)) {
            return info.modifyInfoByTime(campusTime);
        }
        return info;
    }

    public List<AttendanceInfo> getAttendanceInfos() {
        return new ArrayList<>(attendanceInfos);
    }
}
