package domain;

import java.util.HashSet;
import java.util.Set;

public class Crews {
    private final Set<Crew> crews;

    public Crews() {
        crews = new HashSet<>();
        crews.add(Crew.of("히로"));
    }

    public void add(String nickname) {
        crews.add(Crew.of(nickname));
    }

    public Crew findCrewBy(String nickname) {
        return crews.stream()
                .filter(crew -> crew.hasSame(nickname))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루입니다"));
    }

//    private boolean isRegistered(String nickname) {
//        return crews.stream()
//                .anyMatch(crew -> crew.hasSame(nickname));
//    }
}
