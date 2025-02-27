package domain;

import dto.CheckAttendanceRecordResponse;
import dto.PenaltyResponse;
import java.util.List;

public class PenaltyStatus {
    public static PenaltyResponse judgeCrewAttendanceRecord(List<CheckAttendanceRecordResponse> responses) {
        int attendCount = 0;
        int lateCount = 0;
        int absentCount = 0;

        for (CheckAttendanceRecordResponse response : responses) {
            if (response.attendanceStatus().equals("출석")) {
                attendCount++;
            }
            if (response.attendanceStatus().equals("지각")) {
                lateCount++;
            }
            if (response.attendanceStatus().equals("결석")) {
                absentCount++;
            }
        }

        return new PenaltyResponse(attendCount, lateCount, absentCount, judgePenalty(lateCount, absentCount));
    }

    private static String judgePenalty(int lateCount, int absentCount) {
        int penaltyCount = absentCount + lateCount / 3;
        if (penaltyCount > 5) {
            return "제적";
        }
        if (penaltyCount >= 3) {
            return "면담";
        }

        if (penaltyCount == 2) {
            return "경고";
        }

        return "";
    }
}