package attendance.domain;

import java.util.Map;

public class CrewDismiss implements Comparable<CrewDismiss> {

    private final String crewDismissResult;
    private final String nickname;
    private Map<String, Integer> attendanceStatus;

    public CrewDismiss(String nickname, String crewDismissResult, Map<String, Integer> attendanceStatus) {
        this.nickname = nickname;
        this.crewDismissResult = crewDismissResult;
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public int compareTo(CrewDismiss o) {
        Map<String, Integer> otherAttendanceStatus = o.attendanceStatus;

        int weight = AttendanceStatus.absenceCount(attendanceStatus) + AttendanceStatus.lateCount(attendanceStatus);
        int otherWeight = AttendanceStatus.absenceCount(otherAttendanceStatus) + AttendanceStatus.lateCount(
                otherAttendanceStatus);
        if (weight == otherWeight) {
            return nickname.compareTo(o.nickname);
        }
        return otherWeight - weight;
    }

    public String getCrewDismissResult() {
        return crewDismissResult;
    }
}
