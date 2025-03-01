package domain;

import static constants.NumberConstants.EXPULSION_COUNT;
import static constants.NumberConstants.INTERVIEW_COUNT;
import static constants.NumberConstants.LATE_TO_ABSENT;
import static constants.NumberConstants.WARNING_COUNT;
import static domain.AttendanceStatus.ABSENT_STATUS;
import static domain.AttendanceStatus.ATTEND_STATUS;
import static domain.AttendanceStatus.LATE_STATUS;

import dto.CheckAttendanceRecordResponse;
import dto.PenaltyResponse;
import java.util.List;

public class PenaltyStatus {
    public static final String EXPULSION_STATUS = "제적";
    public static final String INTERVIEW_STATUS = "면담";
    public static final String WARNING_STATUS = "경고";
    public static final String NO_PENALTY_STATUS = "";

    public static PenaltyResponse judgeCrewAttendanceRecord(List<CheckAttendanceRecordResponse> responses) {
        int attendCount = 0;
        int lateCount = 0;
        int absentCount = 0;

        for (CheckAttendanceRecordResponse response : responses) {
            if (response.attendanceStatus().equals(ATTEND_STATUS)) {
                attendCount++;
            }
            if (response.attendanceStatus().equals(LATE_STATUS)) {
                lateCount++;
            }
            if (response.attendanceStatus().equals(ABSENT_STATUS)) {
                absentCount++;
            }
        }

        return new PenaltyResponse(attendCount, lateCount, absentCount, judgePenalty(lateCount, absentCount));
    }

    private static String judgePenalty(int lateCount, int absentCount) {
        int penaltyCount = absentCount + lateCount / LATE_TO_ABSENT;
        if (penaltyCount > EXPULSION_COUNT) {
            return EXPULSION_STATUS;
        }

        if (penaltyCount >= INTERVIEW_COUNT) {
            return INTERVIEW_STATUS;
        }

        if (penaltyCount == WARNING_COUNT) {
            return WARNING_STATUS;
        }

        return NO_PENALTY_STATUS;
    }
}