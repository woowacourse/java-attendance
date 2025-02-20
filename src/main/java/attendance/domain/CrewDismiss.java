package attendance.domain;

public class CrewDismiss implements Comparable<CrewDismiss> {
    private final String crewDismissResult;
    private final AttendanceHistory attendanceHistory;
    private final String nickname;

    public CrewDismiss(String nickname, String crewDismissResult, AttendanceHistory attendanceHistory) {
        this.nickname = nickname;
        this.crewDismissResult = crewDismissResult;
        this.attendanceHistory = attendanceHistory;
    }

    @Override
    public int compareTo(CrewDismiss o) {
        AbsenceStatusCount absenceStatusCount = attendanceHistory.countAbsenceStatus();
        AbsenceStatusCount otherAbsenceStatusCount = o.attendanceHistory.countAbsenceStatus();

        int weight = absenceStatusCount.absence() + absenceStatusCount.late();
        int otherWeight = (otherAbsenceStatusCount.absence() + otherAbsenceStatusCount.late());
        if (weight == otherWeight) {
            return nickname.compareTo(o.nickname);
        }
        return otherWeight - weight;
    }

    public String getCrewDismissResult() {
        return crewDismissResult;
    }
}
