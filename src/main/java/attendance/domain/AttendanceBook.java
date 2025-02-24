package attendance.domain;

import attendance.dto.CrewNameAndAcademicStatusDTO;
import attendance.repository.AttendanceRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceBook {

    private final Set<String> names;

    public AttendanceBook(final Set<String> names) {
        this.names = names;
    }

    public void checkName(final String name) {
        if (!names.contains(name)) {
            throw new IllegalArgumentException("[ERROR] 출석부에 없는 크루원입니다.");
        }
    }

    public void initAbsent(final AttendanceRepository attendanceRepository) {
        for (String name : names) {
            attendanceRepository.initAbsent(name);
        }
    }

    public List<CrewNameAndAcademicStatusDTO> getCrewAtRiskOfExpulsion(
            final AttendanceRepository attendanceRepository,
            final String academicStatus,
            final int month) {

        return names.stream()
                .map(name -> attendanceRepository.getAcademicStatusByName(name, month))
                .filter(dto -> dto.academicStatus().equals(academicStatus))
                .collect(Collectors.toList());
    }
}
