package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import util.FileParser;

public class CrewRepository {

    private final static List<Crew> CREWS = new ArrayList<>();

    private CrewRepository() {
    }

    public static boolean exists(String nickname) {
        return CREWS.stream()
                .anyMatch(crew -> nickname.equals(crew.getNickname()));
    }

    public static void addCrew(Crew crew) {
        if(exists(crew.getNickname())) {
            throw new IllegalArgumentException(crew.getNickname() + ": 이미 존재하는 크루명입니다");
        }
        CREWS.add(crew);
    }

    public static Crew findByNickname(String nickname) {
        return CREWS.stream()
                .filter(crew -> nickname.equals(crew.getNickname()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(nickname + ": 존재하지 않는 크루명입니다."));
    }

    public static List<Crew> findAll() {
        return CREWS;
    }

    public static void clear() {
        CREWS.clear();
    }
}
