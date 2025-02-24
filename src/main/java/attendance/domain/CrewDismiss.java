package attendance.domain;

import attendance.domain.attendanceManager.AttendanceStatistician;

public class CrewDismiss {
    private final String crewDismissResult;
    private final AttendanceStatistician attendanceStatistician;
    private final String nickname;

    public CrewDismiss(String nickname, String crewDismissResult, AttendanceStatistician attendanceStatistician) {
        this.nickname = nickname;
        this.crewDismissResult = crewDismissResult;
        this.attendanceStatistician = attendanceStatistician;
    }

    // @Override
    // public int compareTo(CrewDismiss o) {
    //     // AbsenceStatusCount absenceStatusCount = attendanceStatistician.countAbsenceStatus();
    //     // AbsenceStatusCount otherAbsenceStatusCount = o.attendanceStatistician.countAbsenceStatus();
    //     //
    //     // int weight = absenceStatusCount.absence() + absenceStatusCount.late();
    //     // int otherWeight = (otherAbsenceStatusCount.absence() + otherAbsenceStatusCount.late());
    //     // if (weight == otherWeight) {
    //     //     return nickname.compareTo(o.nickname);
    //     // }
    //     // return otherWeight - weight;
    // }

    public String getCrewDismissResult() {
        return crewDismissResult;
    }
}
