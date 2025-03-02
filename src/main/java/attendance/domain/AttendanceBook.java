package attendance.domain;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceBook {

    private final Set<String> crewNames;
    private final Attendances attendances;

    public AttendanceBook(final Set<String> crewNames, final Attendances attendances) {
        this.crewNames = new HashSet<>(crewNames);
        this.attendances = attendances;
    }

    public void hasCrew(final String crewName) {
        if (crewNames.contains(crewName)) {
            return;
        }
        throw new IllegalArgumentException("[ERROR] 출석부에 존재하지 않는 닉네임입니다.");
    }

    public long getCountAttendanceStatus(final Map<LocalDate, Attendance> monthlyAttendances,
                                         AttendanceStatus attendanceStatus) {
        return attendances.getStatusCount(monthlyAttendances, attendanceStatus);
    }

    public AcademicStatus getAcademicStatusByCalendar(final Map<LocalDate, Attendance> monthlyAttendances) {

        long late = getCountAttendanceStatus(monthlyAttendances, AttendanceStatus.LATE);
        long absent = getCountAttendanceStatus(monthlyAttendances, AttendanceStatus.ABSENT);

        return AcademicStatus.getStatus(late, absent);
    }

    public void addAttendance(final Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendanceByCrewNameAndLocalDate(String crewName, LocalDate localDate) {
        return attendances.findByCrewNameAndLocalDate(crewName, localDate);
    }

    public Map<LocalDate, Attendance> findAttendancesByCrewNameAndYearAndMonth(final String crewName, final int year,
                                                                               final int month) {
        return attendances.getMonthlyAttendanceMap(crewName, year, month);
    }

    public List<String> getExpulsionCrews(final AcademicStatus academicStatus,
                                          final LocalDate localDate) {
        List<String> academicStatusCrewsName = filterCrewsByAcademicStatus(academicStatus, localDate);
//        sortAcademicStatusResults(academicStatusCrewsName);
        return academicStatusCrewsName;
    }

    private List<String> filterCrewsByAcademicStatus(final AcademicStatus academicStatus,
                                                     final LocalDate localDate) {
        return crewNames.stream()
                .map(crewName -> {
                    Map<LocalDate, Attendance> monthlyAttendances = findAttendancesByCrewNameAndYearAndMonth(
                            crewName, localDate.getYear(), localDate.getMonthValue());
                    return new AbstractMap.SimpleEntry<>(crewName, monthlyAttendances);
                })
                .filter(entry -> getAcademicStatusByCalendar(entry.getValue()).equals(academicStatus))
                .map(SimpleEntry::getKey)
                .collect(Collectors.toList());
    }

//    private void sortAcademicStatusResults(final List<AcademicStatusResultDTO> academicStatusResultDTOS) {
//        academicStatusResultDTOS.sort(
//                Comparator.comparingLong((AcademicStatusResultDTO dto) -> dto.absent() + dto.late())
//                        .reversed()
//                        .thenComparing(AcademicStatusResultDTO::crewName)
//        );
//    }

    public Map<LocalDate, Attendance> getMonthlyAttendances(final String crewName, final int year, final int month) {
        return attendances.getMonthlyAttendanceMap(crewName, year, month);
    }
}
