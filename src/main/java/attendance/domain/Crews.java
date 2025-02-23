package attendance.domain;

import static attendance.domain.exception.CrewsExceptionMessage.NOT_REGISTERED_NICKNAME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Crews {
    private final List<Crew> crews;

    public Crews(final List<Crew> crews) {
        this.crews = new ArrayList<>(crews);
    }

    public static Crews init(Map<String, List<LocalDateTime>> crewsAttendances, LocalDate now) {
        List<Crew> crews = new ArrayList<>();
        for (Map.Entry<String, List<LocalDateTime>> crewAttendances : crewsAttendances.entrySet()) {
            Attendances attendances = new Attendances(now, crewAttendances.getValue());
            crews.add(new Crew(crewAttendances.getKey(), attendances));
        }
        return new Crews(crews);
    }

    public Crew findByName(final String nickname) {
        return crews.stream()
                .filter(crew -> crew.isEqualToNickname(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_REGISTERED_NICKNAME));
    }

    public int size() {
        return crews.size();
    }

    public List<Crew> collectWarningCrews() {
        return crews.stream()
                .filter(crew -> !crew.checkWarning().equals(Warning.NONE))
                .sorted(Crew::compareTo)
                .toList();
    }
}
