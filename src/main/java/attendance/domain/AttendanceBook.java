package attendance.domain;

import dto.AcademicStatusResultDTO;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public long getCountAttendanceStatus(Map<LocalDate, Attendance> monthlyAttendances,
                                         AttendanceStatus attendanceStatus) {
        return attendances.getStatusCount(monthlyAttendances, attendanceStatus);
    }

    public AcademicStatus getAcademicStatusByCalendar(Map<LocalDate, Attendance> monthlyAttendances) {

        long late = getCountAttendanceStatus(monthlyAttendances, AttendanceStatus.LATE);
        long absent = getCountAttendanceStatus(monthlyAttendances, AttendanceStatus.ABSENT);

        return AcademicStatus.getStatus(late, absent);
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendanceByCrewNameAndLocalDate(String crewName, LocalDate localDate) {
        return attendances.findByCrewNameAndLocalDate(crewName, localDate);
    }

    public Map<LocalDate, Attendance> findAttendancesByCrewNameAndYearAndMonth(String crewName, int year, int month) {
        return attendances.getMonthlyAttendanceMap(crewName, year, month);
    }

    public List<AcademicStatusResultDTO> getExpulsionCrews(AcademicStatus academicStatus, LocalDate localDate) {
        List<AcademicStatusResultDTO> academicStatusResultDTOS = filterCrewsByAcademicStatus(academicStatus, localDate);
        sortAcademicStatusResults(academicStatusResultDTOS);
        return academicStatusResultDTOS;
    }

    private List<AcademicStatusResultDTO> filterCrewsByAcademicStatus(AcademicStatus academicStatus,
                                                                      LocalDate localDate) {
        return crewNames.stream()
                .map(crewName -> {
                    Map<LocalDate, Attendance> monthlyAttendances = findAttendancesByCrewNameAndYearAndMonth(
                            crewName, localDate.getYear(), localDate.getMonthValue());
                    return new AbstractMap.SimpleEntry<>(crewName, monthlyAttendances);
                })
                .filter(entry -> getAcademicStatusByCalendar(entry.getValue()).equals(academicStatus))
                .map(entry -> new AcademicStatusResultDTO(
                        entry.getKey(),
                        attendances.getStatusCount(entry.getValue(), AttendanceStatus.ATTEND),
                        attendances.getStatusCount(entry.getValue(), AttendanceStatus.LATE),
                        attendances.getStatusCount(entry.getValue(), AttendanceStatus.ABSENT),
                        academicStatus))
                .collect(Collectors.toList());
    }

    private void sortAcademicStatusResults(List<AcademicStatusResultDTO> academicStatusResultDTOS) {
        academicStatusResultDTOS.sort(new Comparator<AcademicStatusResultDTO>() {
            @Override
            public int compare(AcademicStatusResultDTO o1, AcademicStatusResultDTO o2) {
                long absences1 = o1.absent() + o1.late();
                long absences2 = o2.absent() + o2.late();

                if (absences1 != absences2) {
                    return Long.compare(absences2, absences1);
                }
                return o1.crewName().compareTo(o2.crewName());
            }
        });
    }
}
