package view.parser;

import domain.AttendanceStatus;
import domain.CrewPenaltyPolicy;
import java.util.Map;

public class OutputParser {

    private static final Map<AttendanceStatus, String> ATTENDANCE_STATUS_MESSAGE = Map.of(
            AttendanceStatus.ATTENDANCE, "출석",
            AttendanceStatus.LATE, "지각",
            AttendanceStatus.ABSENCE, "결석"
    );

    private static final Map<CrewPenaltyPolicy, String> PENALTY_STATUS_MESSAGE = Map.of(
            CrewPenaltyPolicy.WARNING, "경고",
            CrewPenaltyPolicy.INTERVIEW, "면담",
            CrewPenaltyPolicy.WEEDING, "제적",
            CrewPenaltyPolicy.NONE, "해당없음"
    );

    public String parseAttendanceStatusToMessage(AttendanceStatus attendanceStatus) {
        return ATTENDANCE_STATUS_MESSAGE.get(attendanceStatus);
    }

    public String parsePenaltyToMessage(CrewPenaltyPolicy crewPenaltyPolicy) {
        return PENALTY_STATUS_MESSAGE.get(crewPenaltyPolicy);
    }
}
