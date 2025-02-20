package domain;

import java.util.List;

public class Crews {

    private final List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByNickname(String nickname) {
        for (Crew crew : crews) {
            if (crew.isEqualTo(nickname)) {
                return crew;
            }
        }
        throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    public void recordAllAbsence() {
        crews.forEach(Crew::recordAbsence);
    }


}
