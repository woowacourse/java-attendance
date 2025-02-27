package attendance.domain;

import java.util.HashSet;
import java.util.List;

public class Crews {

    private final List<Crew> crews;

    public Crews(final List<String> crewNicknames) {
        validateEmptyNicknames(crewNicknames);
        validateDuplicationNickname(crewNicknames);
        this.crews = toCrews(crewNicknames);
    }

    private void validateEmptyNicknames(final List<String> crewNicknames) {
        if (crewNicknames.isEmpty()) {
            throw new IllegalArgumentException("크루의 닉네임이 존재하지 않습니다.");
        }
    }

    private void validateDuplicationNickname(final List<String> crewNicknames) {
        HashSet<String> uniqueCrewNicknames = new HashSet<>(crewNicknames);
        if (crewNicknames.size() != uniqueCrewNicknames.size()) {
            throw new IllegalArgumentException("크루의 닉네임은 중복될 수 없습니다.");
        }
    }

    private List<Crew> toCrews(final List<String> crewNicknames) {
        return crewNicknames.stream()
                .map(Crew::new)
                .toList();
    }

    public Crew findCrewByNickname(final String nickname) {
        return crews.stream()
                .filter(crew -> crew.isSameNickname(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }

}
