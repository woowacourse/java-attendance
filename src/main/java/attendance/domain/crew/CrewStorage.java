package attendance.domain.crew;

import attendance.exception.ExceptionMessage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrewStorage {

    private final Set<Crew> crews = new HashSet<>();

    public void add(Crew crew) {
        crews.add(crew);
    }

    public boolean isContained(String crewName) {
        return crews.stream().anyMatch(crew -> crew.isSameName(crewName));
    }

    public List<Crew> findAll() {
        return crews.stream().toList();
    }

    public void validateCrew(String nickname) {
        boolean isNotContained = crews.stream().noneMatch(crew -> crew.isSameName(nickname));
        if (isNotContained) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_FOUND_CREW.getContent());
        }
    }
}
