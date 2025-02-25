package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.domain.constant.CrewStatus;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class AttendanceHistory {

    private final String crewName;
    private final int attendanceCount;
    private final int lateCount;
    private final int absenceCount;
    private final CrewStatus crewStatus;

    private AttendanceHistory(String crewName, int attendanceCount, int lateCount, int absenceCount) {
        this.crewName = crewName;
        this.attendanceCount = attendanceCount;
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
        this.crewStatus = CrewStatus.from(lateCount, absenceCount);
    }

    public static AttendanceHistory fromDateInfos(String crewName, LocalDate now, DateInfos dateInfos) {

        List<Integer> counts = Arrays.asList(0, 0, 0);
        Map<AttendanceStatus, Consumer<List<Integer>>> statusCountMap = new EnumMap<>(AttendanceStatus.class);
        statusCountMap.put(AttendanceStatus.ATTENDANCE, historyCount -> historyCount.set(0, historyCount.get(0) + 1));
        statusCountMap.put(AttendanceStatus.LATE, historyCount -> historyCount.set(1, historyCount.get(1) + 1));
        statusCountMap.put(AttendanceStatus.ABSENCE, historyCount -> historyCount.set(2, historyCount.get(2) + 1));

        calculateHistoryCounts(now, dateInfos, statusCountMap, counts);
        return new AttendanceHistory(crewName, counts.get(0), counts.get(1), counts.get(2));
    }

    private static void calculateHistoryCounts(LocalDate now, DateInfos dateInfos,
                                               Map<AttendanceStatus, Consumer<List<Integer>>> statusCountMap,
                                               List<Integer> counts) {
        for (int day = 1; day <= now.getDayOfMonth(); day++) {
            LocalDate currentDate = LocalDate.of(now.getYear(), now.getMonthValue(), day);
            if (isWeekend(currentDate)) {
                continue;
            }
            AttendanceStatus status = dateInfos.findAttendanceStatusByDay(day);
            statusCountMap.get(status).accept(counts);
        }
    }

    private static boolean isWeekend(LocalDate currentDate) {
        return currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public boolean isWarningCrew() {
        return crewStatus != CrewStatus.NORMAL;
    }

    public String getCrewName() {
        return crewName;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public CrewStatus getCrewStatus() {
        return crewStatus;
    }

}
