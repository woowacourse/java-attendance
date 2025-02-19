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

    public String ifFindNameAddTime(final String nickname, final String time) {
        Crew crew = findCrew(nickname);

        if (crew != null) {
            crew.addAttendTime(time);
            return crew.attend(time);
        }
        Crew crew1 = new Crew(nickname, time);
        crews.add(crew1);
        return crew1.attend(time);

    }

    public List<Crew> getCrews() {
        return crews;
    }
}
