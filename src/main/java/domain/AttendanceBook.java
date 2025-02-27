package domain;

import dto.AttendanceStatusCountResponse;
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

    public AttendanceStatusCountResponse getAttendanceStatusCountResponseByName(String name) {
        Crew crew = findCrewByName(name);

        return new AttendanceStatusCountResponse(
                crew.countAttendanceStatus(AttendanceStatus.ATTEND),
                crew.countAttendanceStatus(AttendanceStatus.LATE),
                crew.countAttendanceStatus(AttendanceStatus.ABSENT)
        );
    }

    public String getPenaltyMessageByName(String name) {
        Crew crew = findCrewByName(name);
        int lateCount = crew.countAttendanceStatus(AttendanceStatus.LATE);
        int absentCount = crew.countAttendanceStatus(AttendanceStatus.ABSENT);
        return Penalty.findPenaltyMessageByAttendanceStatusCount(lateCount, absentCount);
    }

    public Crew findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.isName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.CREW_NAME_NOT_FOUND.getMessage()));
    }
}
