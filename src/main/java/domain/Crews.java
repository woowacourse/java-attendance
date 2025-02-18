package domain;

import java.util.List;

public class Crews {

    private final List<Crew> crews;

    public Crews(final List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findCrew(final String nickname) {
        return crews.stream()
                .filter(c -> c.getName().equals(nickname))
                .findAny()
                .orElse(null);
    }

    public void ifFindNameAddTime(final String nickname, final String time) {
        Crew crew = findCrew(nickname);
        if (crew != null) {
            crew.attend(time);
            return;
        }
        crews.add(new Crew(nickname, time));
    }

    public List<Crew> getCrews() {
        return crews;
    }
}
