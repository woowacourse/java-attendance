package attendance.domain.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CrewHistories {

    private final Map<String, CrewHistory> crews;

    public CrewHistories(final Map<String, CrewHistory> crews) {
        this.crews = new HashMap<>(crews);
    }

    public CrewHistory findCrewByNickname(final String nickname) {
        if (!crews.containsKey(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        return crews.get(nickname);
    }

    public Map<String, AttendanceCounter> findDismissalCrews(final LocalDate todayDate) {
        return crews.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().countAttendanceType(todayDate)))
                .filter(entry -> SubjectType.from(entry.getValue().getAbsentCount(), entry.getValue().getLateCount())
                        .isApplicable())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }
}
