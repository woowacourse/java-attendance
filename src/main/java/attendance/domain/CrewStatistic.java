package attendance.domain;

import java.time.LocalDate;

public class CrewStatistic {
    private final CrewName crewName;
    private final AttendanceCount attendanceCount;
    private CrewPenalty crewPenalty;

    public CrewStatistic(CrewName crewName) {
        this.crewName = crewName;
        this.attendanceCount = new AttendanceCount();
    }

    public void checkAttendanceStatistic(final Attendances crewAttendances) {
        checkAttendancesCount(crewAttendances);
        checkAttendancePenalty();
    }

    private void checkAttendancesCount(final Attendances crewAttendances) {
        attendanceCount.resetAttendanceCount();
        crewAttendances.getAttendances().stream()
                .filter(attendance -> !attendance.getAttendanceDate().equals(LocalDate.now()))
                .map(Attendance::getAttendanceType)
                .forEach(attendanceCount::checkAttendanceCount);
    }

    private void checkAttendancePenalty() {
        crewPenalty = CrewPenalty.of(attendanceCount.calculatePenaltyCount());
    }

    public String getCrewName() {
        return crewName.getCrewName();
    }

    public int getCrewSafeCount() {
        return attendanceCount.getSafeCount();
    }

    public int getCrewLateCount() {
        return attendanceCount.getLateCount();
    }

    public int getCrewAbsentCount() {
        return attendanceCount.getAbsentCount();
    }

    public String getCrewPenalty() {
        return crewPenalty.toString();
    }
}
