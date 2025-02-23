package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Crews {

    private final Set<Crew> crews;

    private Crews(Set<Crew> crews) {
        this.crews = crews;
    }

    public static Crews of(Set<Crew> crews) {
        return new Crews(crews);
    }

    public Optional<Crew> findByNickname(String nickname) {
        return crews.stream()
                .filter(crew -> crew.isEqualName(nickname))
                .findFirst();
    }

    public List<AttendanceStatistics> findDangerCrews(Attendances attendances, LocalDate today) {
        List<AttendanceStatistics> dangerCrews = new ArrayList<>();
        for (Crew crew : crews) {
            AttendanceStatistics statistics = attendances.createStatistics(crew, today);
            if (statistics.isDanger()) {
                dangerCrews.add(statistics);
            }
        }
        Collections.sort(dangerCrews);
        return dangerCrews;
    }
}
