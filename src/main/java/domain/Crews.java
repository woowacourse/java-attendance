package domain;

import java.util.ArrayList;
import java.util.List;

public class Crews {

    private List<Crew> crews;

    public Crews() {
        this.crews = new ArrayList<>();
    }

    public void saveCrew(final String nickname, final String attendTime) {
        boolean exists = existsByNickname(nickname);
        if (exists) {
            addAttendTime(nickname, attendTime);
            return;
        }
        addCrew(nickname, attendTime);
    }

    public Crew findByNickname(final String nickname) {
        return crews.stream()
                .filter(crew -> crew.isSameName(nickname))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }

    private void addAttendTime(final String nickname, final String attendTime) {
        Crew crew = findByNickname(nickname);
        crew.addAttendTime(attendTime);
    }

    private void addCrew(final String nickname, final String attendTime) {
        Crew crew = new Crew(nickname, attendTime);
        crews.add(crew);
    }

    private boolean existsByNickname(final String nickname) {
        return crews.stream()
                .anyMatch(crew -> crew.isSameName(nickname));
    }

    public List<Crew> getDangerousCrews(String type) {
        List<Crew> dangerousCrews = new ArrayList<>();
        crews.stream()
                .filter(crew -> crew.isSameType(type))
                .forEach(dangerousCrews::add);
        return dangerousCrews;
    }
}
