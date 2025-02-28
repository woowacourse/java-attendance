package domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final Set<Crew> crews;

    public AttendanceBook(List<String> crews) {
        validate(crews);
        this.crews = convertNameToCrew(crews);
    }

    public boolean has(String findNickname) {
        return crews.stream().anyMatch(crew -> crew.equals(findNickname));
    }

    private void validate(List<String> crewNames) {
        List<String> distinctCrews = crewNames.stream().distinct().toList();
        if (distinctCrews.size() != crewNames.size()) {
            throw new IllegalArgumentException("중복된 닉네임의 크루는 존재할 수 없습니다");
        }
    }

    private Set<Crew> convertNameToCrew(List<String> crewNames) {
        return crewNames.stream()
                .map(crewName -> new Crew(crewName))
                .collect(Collectors.toSet());
    }


}
