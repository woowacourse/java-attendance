package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Crews {

    private final Set<Crew> crews;

    public Crews(Set<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByNickname(String nickname) {
        return crews.stream()
                .filter(crew -> crew.isSameNickname(nickname))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("해당 닉네임의 크루가 존재하지 않습니다."));
    }

    public List<Crew> findDangerCrews(Attendances attendances, LocalDate today) {
        List<Crew> dangerCrews = new ArrayList<>();
        for (Crew crew : crews) {
            Attendances attendancesOfCrew = attendances.createMonthlyAttendances(crew, today);
            PenaltyPolicy penalty = PenaltyPolicy.judgePenalty(attendancesOfCrew.countAttendanceType());
            if (penalty.isDanger()) {
                dangerCrews.add(crew);
            }
        }

        return dangerCrews;
    }
}
