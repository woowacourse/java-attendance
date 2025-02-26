package attendance.domain;

import java.util.List;

public class CrewStatistic {
    private static final int WARNING_COUNT_LIMIT = 2;
    private static final int MEETING_COUNT_LIMIT = 3;
    private static final int EXPEL_COUNT_LIMIT = 5;

    private final Crew crew;
    private final AttendanceCount attendanceCount;
    private final Attendances crewAttendances;
    private CrewStatus status;

    public CrewStatistic(Crew crew, List<Attendance> crewAttendances) {
        this.crew = crew;
        this.attendanceCount = new AttendanceCount();
        this.crewAttendances = new Attendances(crewAttendances);
    }

    public void checkCrewStatistic() {
        resetCrewStatusCount();
        initCrewsStatusCount();
        calculatePenalty();
    }

    private void resetCrewStatusCount() {
        attendanceCount.resetAttendanceCount();
    }

    private void initCrewsStatusCount() {
        for (Attendance crewAttendance : crewAttendances.getAttendances()) {
            crewAttendance.updateCrewAttendanceCount(attendanceCount);
        }
    }

    private void calculatePenalty() {
        int absentCount = attendanceCount.calculatePenalty();
        checkCrewStatus(absentCount);
    }

    private void checkCrewStatus(final int absentCount) {
        if (absentCount > EXPEL_COUNT_LIMIT) {
            status = CrewStatus.EXPEL;
            return;
        }
        if (absentCount >= MEETING_COUNT_LIMIT) {
            status = CrewStatus.MEETING;
            return;
        }
        if (absentCount == WARNING_COUNT_LIMIT) {
            status = CrewStatus.WARNING;
        }
    }

    public int getPenaltyCount() {
        return attendanceCount.checkPenaltyCount();
    }

    public String getCrewName() {
        return crew.getName();
    }

    public CrewStatus getCrewStatus() {
        return status;
    }

    public int getAbsentCount() {
        return attendanceCount.getAbsentCount();
    }

    public int getLateCount() {
        return attendanceCount.getLateCount();
    }

    public int getSafeCount() {
        return attendanceCount.getSafeCount();
    }

    public Attendances getCrewAttendances() {
        return new Attendances(crewAttendances.sortCrewAttendances());
    }
}
