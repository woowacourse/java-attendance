package attendance.model;

import java.util.Set;

public class CrewGroup {

    private final Set<Crew> crews;

    public CrewGroup(Set<Crew> crews) {
        this.crews = Set.copyOf(crews);
    }

    public boolean contains(Crew crew) {
        return crews.contains(crew);
    }

    public Set<Crew> getCrews() {
        return Set.copyOf(crews);
    }

    public Crew findCrewByNickname(String nickname) {
        return crews.stream()
                .filter(crew -> crew.isEqualsNickname(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }
}
