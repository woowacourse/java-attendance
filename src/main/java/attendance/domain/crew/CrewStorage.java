package attendance.domain.crew;

import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrewStorage {

    private final Set<Crew> crews = new HashSet<>();


    public void add(String nickname) {
        if (checkIsNotContained(nickname)) {
            crews.add(new Crew(nickname));
        }
    }

    public List<String> findAllNicknames() {
        return crews.stream().map(Crew::getNickname).toList();
    }

    public void validateIsNotContained(String nickname) {
        if (checkIsNotContained(nickname)) {
            throw new AttendanceException(ExceptionMessage.INVALID_CREW.getMessage());
        }
    }

    public boolean checkIsNotContained(String nickname) {
        return crews.stream().noneMatch(crew -> crew.isSameNickname(nickname));
    }
}
