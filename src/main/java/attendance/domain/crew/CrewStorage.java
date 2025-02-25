package attendance.domain.crew;

import attendance.exception.ExceptionMessage;
import java.util.HashSet;
import java.util.Set;

public class CrewStorage {

    private final Set<Crew> crews = new HashSet<>();


    public void add(String nickname) {
        if (checkIsNotContained(nickname)) {
            crews.add(new Crew(nickname));
        }
    }

    public void validateIsNotContained(String nickname) {
        if (checkIsNotContained(nickname)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_CREW.getMessage());
        }
    }

    private boolean checkIsNotContained(String nickname) {
        return crews.stream().noneMatch(crew -> crew.isSameNickname(nickname));
    }
}
