package attendance.domain;

import attendance.repository.AttendanceRepository;
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

    public Set<String> getNames() {
        return Set.copyOf(names);
    }
}
