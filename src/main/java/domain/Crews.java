package domain;

import java.util.ArrayList;
import java.util.List;

public class Crews {

    private final List<Crew> crews = new ArrayList<>();

    public Crews() {
    }

    public void add(Crew crew) {
        crews.add(crew);
    }

    public List<Crew> getCrews() {
        return crews.stream()
                .map(Crew::new)
                .toList();
    }

    public Crew findByNickname(String nickname) {
        return crews.stream()
                .filter(crew -> crew.isEqualTo(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }

    public void recordAllAbsence() {
        crews.forEach(Crew::recordAbsence);
    }


}
