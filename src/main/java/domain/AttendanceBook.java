package domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class AttendanceBook {

    private final Map<String, AttendancePaper> attendancePapers;

    private AttendanceBook(final Map<String, AttendancePaper> attendancePapers) {
        this.attendancePapers = attendancePapers;
    }

    public static AttendanceBook create() {
        return new AttendanceBook(AttendancePaperGenerator.generate());
    }

    public AttendancePaper getAttendancePaperByCrewName(final String crewName) {
        return Optional.ofNullable(attendancePapers.get(crewName))
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }

    public List<AttendancePaper> getSortedPenaltyAttendancePapers() {
        return attendancePapers.values().stream()
                .filter(attendancePaper -> !Objects.equals(attendancePaper.calculatePenalty(), Penalty.NONE))
                .sorted()
                .toList();
    }
}
