package domain;

import domain.vo.PenaltyTarget;

public class CrewPenaltyManager {

    private final AttendanceStatusCalculator attendanceStatusCalculator;

    public CrewPenaltyManager(
            AttendanceStatusCalculator attendanceStatusCalculator
    ) {
        this.attendanceStatusCalculator = attendanceStatusCalculator;
    }

    public PenaltyTarget retrieveAllPenaltyTarget(String crewName, AttendanceRecords attendanceRecords) {
        AttendanceStatusCounter attendanceStatusCounter = attendanceStatusCalculator.calculateAllCount(attendanceRecords);
        int lateCount = attendanceStatusCounter.retrieveAttendanceStatusCount(AttendanceStatus.LATE);
        int absenceCount = attendanceStatusCounter.retrieveAttendanceStatusCount(AttendanceStatus.ABSENCE);
        CrewPenaltyPolicy crewPenaltyPolicy = calculatePenalty(lateCount, absenceCount);
        return new PenaltyTarget(crewName, lateCount, absenceCount, crewPenaltyPolicy);
    }

    private CrewPenaltyPolicy calculatePenalty(int lateCount, int absenceCount) {
        int totalAbsenceCount = CrewPenaltyPolicy.calculateTotalAbsence(lateCount, absenceCount);
        return CrewPenaltyPolicy.judge(totalAbsenceCount);
    }
}
