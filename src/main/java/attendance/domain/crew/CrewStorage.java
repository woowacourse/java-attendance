package attendance.domain.crew;

import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CrewStorage {

    private final Map<String, Crew> crews = new HashMap<>();

    public void add(String nickname) {
        if (checkIsNotContained(nickname)) {
            crews.put(nickname, new Crew(nickname));
        }
    }

    public Set<String> findAllNicknames() {
        return crews.keySet();
    }

    public void validateIsNotContained(String nickname) {
        if (checkIsNotContained(nickname)) {
            throw new AttendanceException(ExceptionMessage.INVALID_CREW.getMessage());
        }
    }

    public boolean checkIsNotContained(String nickname) {
        return !crews.containsKey(nickname);
    }
}
