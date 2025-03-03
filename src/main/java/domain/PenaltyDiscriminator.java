package domain;

import static domain.policy.AttendancePolicy.ABSENT_STATUS;
import static domain.policy.AttendancePolicy.ATTEND_STATUS;
import static domain.policy.AttendancePolicy.LATE_STATUS;

import domain.policy.PenaltyPolicy;
import dto.CheckAttendanceRecordResponse;
import dto.PenaltyResponse;
import java.util.List;

public class PenaltyDiscriminator {
    public static PenaltyResponse judgeCrewAttendanceRecord(List<CheckAttendanceRecordResponse> responses) {
        int attendCount = 0;
        int lateCount = 0;
        int absentCount = 0;

        for (CheckAttendanceRecordResponse response : responses) {
            if (response.attendanceStatus().equals(ATTEND_STATUS.getStatus())) {
                attendCount++;
            }
            if (response.attendanceStatus().equals(LATE_STATUS.getStatus())) {
                lateCount++;
            }
            if (response.attendanceStatus().equals(ABSENT_STATUS.getStatus())) {
                absentCount++;
            }
        }

        return new PenaltyResponse(attendCount, lateCount, absentCount, judgePenalty(lateCount, absentCount));
    }

    private static String judgePenalty(int lateCount, int absentCount) {
        int penaltyCount = PenaltyPolicy.calculatePenaltyCount(absentCount, lateCount);

        if (penaltyCount > PenaltyPolicy.EXPULSION.getCriteria()) {
            return PenaltyPolicy.EXPULSION.getPenalty();
        }

        if (penaltyCount >= PenaltyPolicy.INTERVIEW.getCriteria()) {
            return PenaltyPolicy.INTERVIEW.getPenalty();
        }

        if (penaltyCount == PenaltyPolicy.WARNING.getCriteria()) {
            return PenaltyPolicy.WARNING.getPenalty();
        }

        return PenaltyPolicy.NO_PENALTY.getPenalty();
    }
}