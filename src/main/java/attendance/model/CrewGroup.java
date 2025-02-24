package attendance.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CrewGroup {

    private final Set<Crew> crews;

    public CrewGroup(Set<Crew> crews) {
        this.crews = Set.copyOf(crews);
    }

    public boolean contains(Crew crew) {
        return crews.contains(crew);
    }

    public Crew findCrewByNickname(String nickname) {
        return crews.stream()
                .filter(crew -> crew.isEqualsNickname(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(nickname + "은(는) 등록되지 않은 닉네임입니다."));
    }

    public List<AttendanceResult> createAttendanceResultOfAllCrewUntilDate(AttendanceBook attendances,
                                                                           LocalDate endDate) {
        return crews.stream()
                .map(crew -> attendances.findMonthlyAttendance(crew, endDate.getMonth()))
                .map(monthlyAttendance -> monthlyAttendance.calculateAttendanceResultUntilDate(endDate))
                .collect(Collectors.toList());
    }
}
