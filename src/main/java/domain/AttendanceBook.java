package domain;

import domain.attendance.Attendances;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final Map<Crew, Attendances> attendances = new HashMap<>();

    public AttendanceBook(Map<String, List<LocalDateTime>> crewsInfo) {
        validate(crewsInfo.keySet().stream().toList());
        crewsInfo.forEach((name, dateTimes) ->
                this.attendances.put(new Crew(name), new Attendances(dateTimes))
        );
    }

    public boolean has(String findNickname) {
        return attendances.keySet().stream()
                .anyMatch(crew -> crew.equals(findNickname));
    }

    private void validate(List<String> crewNames) {
        List<String> distinctCrews = crewNames.stream().distinct().toList();
        if (distinctCrews.size() != crewNames.size()) {
            throw new IllegalArgumentException("중복된 닉네임의 크루는 존재할 수 없습니다");
        }
    }

    private Set<Crew> convertNameToCrew(List<String> crewNames) {
        return crewNames.stream()
                .map(Crew::new)
                .collect(Collectors.toSet());
    }
}
