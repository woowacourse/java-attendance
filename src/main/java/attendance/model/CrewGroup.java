package attendance.model;

import java.util.Set;

public class CrewGroup {

    private final Set<Crew> crews;

    public CrewGroup(Set<Crew> crews) {
        this.crews = Set.copyOf(crews);
    }

    public boolean contains(Nickname nickname) {
        return crews.stream()
                .anyMatch(crew -> crew.isEqualsNickname(nickname));
    }

    public boolean contains(Crew crew) {
        return crews.contains(crew);
    }

    public Set<Crew> getCrews() {
        return Set.copyOf(crews);
    }
}
