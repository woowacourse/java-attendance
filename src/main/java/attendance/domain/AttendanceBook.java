package attendance.domain;

import java.time.LocalDate;
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

    public long getCountAcademicStatus(AttendanceStatus attendanceStatus, String crewName, LocalDate localDate) {
        return attendances.getStatusCount(attendanceStatus, crewName, localDate);
    }

    public AcademicStatus getAcademicStatusByCrewName(String crewName, LocalDate localDate) {

        long late = getCountAcademicStatus(AttendanceStatus.LATE, crewName, localDate);
        long absent = getCountAcademicStatus(AttendanceStatus.ABSENT, crewName, localDate);

        return AcademicStatus.getStatus(late, absent);
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findByCrewNameAndLocalDate(String crewName, LocalDate localDate) {
        return attendances.findByCrewNameAndLocalDate(crewName, localDate);
    }
}
