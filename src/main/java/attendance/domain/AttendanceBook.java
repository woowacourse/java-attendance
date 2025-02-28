package attendance.domain;

import java.util.Set;

public class AttendanceBook {

    private final Set<String> crewNames;
    private final Attendances attendances;

    public AttendanceBook(final Set<String> crewNames, Attendances attendances) {
        this.crewNames = crewNames;
        this.attendances = attendances;
    }

    public void hasCrew(String crewName) {
        if (!crewNames.contains(crewName)) {
            throw new IllegalArgumentException("[ERROR] 출석부에 존재하지 않는 닉네임입니다.");
        }
    }

    public long getCountAcademicStatus(AttendanceStatus attendanceStatus, String crewName, int year, int month,
                                       int startHour,
                                       int startMinute) {
        return attendances.getStatusCount(attendanceStatus, crewName, year, month, startHour, startMinute);
    }

    public AcademicStatus getAcademicStatusByCrewName(String crewName, int year, int month, int startHour,
                                                      int startMinute) {

        long late = getCountAcademicStatus(AttendanceStatus.LATE, crewName, year, month, startHour,
                startMinute);
        long absent = getCountAcademicStatus(AttendanceStatus.ABSENT, crewName, year, month, startHour,
                startMinute);

        return AcademicStatus.getStatus(late, absent);
    }
}
