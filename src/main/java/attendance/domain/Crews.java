package attendance.domain;

import java.util.List;

public class Crews {

    private final List<Crew> crews;

    public Crews(final List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByName(final String nickname) {
        return crews.stream()
                .filter(crew -> crew.isEqualToNickname(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }

    public int size() {
        return crews.size();
    }

    public List<Crew> collectWarningCrews() {
        return crews.stream()
                .filter(crew -> !crew.checkWarning().equals(Warning.NONE))
                .sorted(Crew::compareTo)
                .toList();
    }
}
