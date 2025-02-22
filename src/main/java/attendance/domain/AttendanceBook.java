package attendance.domain;

import attendance.dto.CrewNameAndAcademicStatusDTO;
import attendance.repository.AttendanceRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class AttendanceBook {

    private final Set<String> names;

    public AttendanceBook(final Set<String> names) {
        this.names = names;
    }

    public void validateCrewName(final String name) {

        if (!names.contains(name)) {
            throw new IllegalArgumentException("[ERROR] 출석부에 없는 크루원입니다.");
        }
    }

    public void initAbsent(final AttendanceRepository attendanceRepository) {

        for (String name : names) {
            attendanceRepository.initAbsent(name);
        }
    }

    public List<CrewNameAndAcademicStatusDTO> getCrewAtRiskOfExpulsion(final AttendanceRepository attendanceRepository,
                                                                       final String academicStatus) {

        return names.stream()
                .map(attendanceRepository::getAcademicStatusByName)
                .filter(dto -> dto.academicStatus().equals(academicStatus))
                .sorted(Comparator.comparing(CrewNameAndAcademicStatusDTO::crewName))
                .toList();
    }
}
