package attendance.model;

import java.util.Set;

public class CrewGroup {

    private final Set<Crew> crews;

    public CrewGroup(Set<Crew> crews) {
        this.crews = crews;
    }

    public boolean contains(String nickname) {
        return crews.stream()
                .anyMatch(crew -> crew.isEqualsNickname(nickname));
    }

    public boolean contains(Crew crew) {
        return crews.contains(crew);
    }
}
