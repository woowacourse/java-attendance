package attendance.domain;

import attendance.util.ErrorMessage;

import java.util.List;

public class Crews {

    private final List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public void add(Crew crew) {
        validateDuplicate(crew);
        crews.add(crew);
    }

    private void validateDuplicate(Crew crew) {
        if (crews.contains(crew)) {
            throw new IllegalArgumentException(ErrorMessage.CREW_DUPLICATE_ERROR.getMessage());
        }
    }

    public int size() {
        return crews.size();
    }
}
