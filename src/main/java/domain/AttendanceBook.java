package domain;

import dto.PenaltyCountResponse;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBook {
    private final List<Crew> crews;

    public AttendanceBook() {
        this.crews = new ArrayList<>();
    }

    public void addCrewByName(String name) {
        Crew newCrew = new Crew(name);
        crews.add(newCrew);
    }

    public PenaltyCountResponse getPenaltyCountResponseByName(String name) {
        Crew crew = findCrewByName(name);

        return new PenaltyCountResponse(
                crew.getCountByStatus(AttendanceStatus.ATTEND),
                crew.getCountByStatus(AttendanceStatus.LATE),
                crew.getCountByStatus(AttendanceStatus.ABSENT)
        );

    }

    public Crew findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.isName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.CREW_NAME_NOT_FOUND.getMessage()));
    }
}
