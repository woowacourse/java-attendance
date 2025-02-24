package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.domain.constant.CrewStatus;
import java.time.LocalDate;

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

        int attendanceCount = 0;
        int lateCount = 0;
        int absenceCount = 0;

        for (int day = 1; day < now.getDayOfMonth(); day++) {
            LocalDate currentDate = LocalDate.of(now.getYear(), now.getMonthValue(), day);
            if (isWeekend(currentDate)) {
                continue;
            }

            AttendanceStatus status = dateInfos.findAttendanceStatusByDay(day);
            if (status == AttendanceStatus.LATE) {
                lateCount++;
            }
            if (status == AttendanceStatus.ABSENCE) {
                absenceCount++;
            }
            if (status == AttendanceStatus.ATTENDANCE) {
                attendanceCount++;
            }
        }
        return new AttendanceHistory(crewName, absenceCount, lateCount, attendanceCount);
    }

    private static boolean isWeekend(LocalDate currentDate) {
        return currentDate.getDayOfWeek().getValue() >= 6;
    }

    public boolean findByCrewName(String crewName) {
        return this.crewName.equals(crewName);
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
