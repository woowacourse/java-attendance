package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    public Crews findDangerCrews(Attendances attendances, LocalDate today) {
        Set<Crew> dangerCrews = new HashSet<>();
        for (Crew crew : crews) {
            Attendances attendancesOfCrew = attendances.createMonthlyAttendances(crew, today);
            PenaltyPolicy penalty = PenaltyPolicy.judgePenalty(attendancesOfCrew.countAttendanceType());
            if (penalty.isDanger()) {
                dangerCrews.add(crew);
            }
        }

        return new Crews(dangerCrews);
    }

    public Map<Crew, Attendances> createAttendancesOfDangerCrews(Attendances attendances, LocalDate today) {
        Map<Crew, Attendances> dangerAttendances = new HashMap<>();
        for (Crew crew : crews) {
            dangerAttendances.put(crew, attendances.createMonthlyAttendances(crew, today));
        }
        return dangerAttendances;
    }

    public List<Crew> sortDangerCrews(Attendances attendances, LocalDate today) {
        Map<Crew, Attendances> attendancesOfDangerCrews = createAttendancesOfDangerCrews(attendances, today);
        List<Crew> crews = new ArrayList<>(attendancesOfDangerCrews.keySet());
        crews.sort(new Comparator<Crew>() {
            @Override
            public int compare(Crew o1, Crew o2) {
                Attendances a1 = attendancesOfDangerCrews.get(o1);
                Attendances a2 = attendancesOfDangerCrews.get(o2);
                if (a1.compareWithPenalty(a2) == 0 && a1.compareWithConvertedAbsenceCount(a2) == 0) {
                    return o1.compareTo(o2);
                }
                if (a1.compareWithPenalty(a2) == 0) {
                    return a1.compareWithConvertedAbsenceCount(a2);
                }
                return a1.compareWithPenalty(a2);
            }
        });

        return crews;
    }

    public Set<Crew> getCrews() {
        return crews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crews crews1 = (Crews) o;
        return Objects.equals(crews, crews1.crews);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(crews);
    }
}
