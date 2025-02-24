package domain;

import java.util.List;

public class Crews {

    private final List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByNickname(String nickname) {
        return crews.stream()
                .filter(crew -> crew.isEqualTo(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }

    public void recordAllAbsence(Day today) {
        crews.forEach(crew -> crew.recordAbsence(today.getDate()));
    }

    public Crew getOrRegisterCrew(String nickname) {
        return crews.stream()
                .filter(crew -> crew.isEqualTo(nickname))
                .findFirst()
                .orElseGet(() -> {
                    Crew newCrew = new Crew(nickname);
                    crews.add(newCrew);
                    return newCrew;
                });
    }

    public List<Crew> getAllCrews() {
        return List.copyOf(crews);
    }
}
