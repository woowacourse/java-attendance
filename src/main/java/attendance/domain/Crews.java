package attendance.domain;

import static attendance.constant.ErrorMessage.UNREGISTERED_NICKNAME;

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
                .orElseThrow(() -> new IllegalArgumentException(UNREGISTERED_NICKNAME.getMessage()));
    }

    public int size() {
        return crews.size();
    }

    public List<Crew> collectWarningCrews() {
        return crews.stream()
                .filter(crew -> !crew.checkWarning().equals(Warning.NONE))
                .toList();
    }
}
