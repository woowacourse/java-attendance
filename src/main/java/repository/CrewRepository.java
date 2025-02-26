package repository;

import domain.Crew;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrewRepository {
    private static final List<Crew> CREWS = new ArrayList<>();

    public static void addCrew(Crew crew) {
        if (existsCrew(crew.getNickname())) {
            return;
        }
        CREWS.add(crew);
    }

    public static boolean existsCrew(String nickname) {
        return CREWS.stream()
                .anyMatch(crew -> nickname.equals(crew.getNickname()));
    }

    public static List<Crew> findAll() {
        return Collections.unmodifiableList(CREWS);
    }

    public static void clear() {
        CREWS.clear();
    }
}
