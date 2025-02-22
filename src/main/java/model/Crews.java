package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crews {

    private final Map<String, Crew> crews;

    public Crews(final Map<String, Crew> crews) {
        this.crews = new HashMap<>(crews);
    }

    public Crew findCrewByNickname(final String nickname) {
        if (!crews.containsKey(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        return crews.get(nickname);
    }

    public List<Crew> findDismissalCrews(final LocalDate todayDate) {
        return crews.values()
                .stream()
                .filter(crew -> SubjectType.isApplicable(crew.countAttendanceType(todayDate)))
                .toList();
    }
}
