package attendance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Crews {

    private List<Crew> crews;

    public Crews() {
        this.crews = new ArrayList<>();
    }

    public Crew add(String nickname) {
        if(crews.stream().anyMatch(crew -> crew.isEqualCrew(nickname))) {
            throw new IllegalArgumentException("이미 존재하는 크루입니다.");
        }

        Crew crew = new Crew(nickname);
        crews.add(crew);
        return crew;
    }

    public List<Attendance> findCrewAttendanceByNickname(String nickname) {
        return findCrewByNickname(nickname).getAttendances();
    }

    public Crew findCrewByNickname(String nickname) {
        for (Crew crew : crews) {
            if(crew.isEqualCrew(nickname)) {
                return crew;
            }
        }
        throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
    }

    public List<Attendance> getCrewAttendanceUtilYesterday(LocalDate today, String nickname) {
        List<Attendance> crewAttendances = findCrewAttendanceByNickname(nickname);
        return crewAttendances.stream()
                .filter(attendance -> attendance.isBeforeDate(today))
                .toList();
    }
}
