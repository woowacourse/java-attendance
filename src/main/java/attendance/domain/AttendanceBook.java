package attendance.domain;

import java.util.List;

public class AttendanceBook {

    private final List<String> crewNames;

    public AttendanceBook(final List<String> crewNames) {
        this.crewNames = crewNames;
    }

    public void hasCrew(String crewName) {
        if (!crewNames.contains(crewName)) {
            throw new IllegalArgumentException("[ERROR] 출석부에 존재하지 않는 닉네임입니다.");
        }
    }
}
