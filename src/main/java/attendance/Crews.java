package attendance;

import java.util.ArrayList;
import java.util.List;

public class Crews {

    private List<Crew> crews;

    public Crews() {
        this.crews = new ArrayList<>();
    }

    public Crew addCrew(String nickname) {
        if(crews.stream().anyMatch(crew -> crew.isEqualCrew(nickname))) {
            throw new IllegalArgumentException("이미 존재하는 크루입니다.");
        }

        Crew crew = new Crew(nickname);
        crews.add(crew);
        return crew;
    }
}
