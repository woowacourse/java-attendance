package domain;

import java.util.ArrayList;
import java.util.List;

public class Crews {

    private List<Crew> crews;

    public void loadCrews(List<String> crewsInFile) {
        this.crews = new ArrayList<>();
        crewsInFile.forEach(crewInFile ->
                loadCrew(crewInFile.split(",")[0], crewInFile.split(",")[1])
        );
    }

    private void loadCrew(final String nickname, final String attendTime) {
        boolean exists = existsByNickname(nickname);
        if (exists) {
            Crew crew = findByNickname(nickname);
            crew.addAttendTime(attendTime);
            return;
        }
        Crew crew = new Crew(nickname, attendTime);
        crews.add(crew);
    }

    private Crew findByNickname(final String nickname) {
        return crews.stream()
                .filter(crew -> crew.isSameName(nickname))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }

    private boolean existsByNickname(final String nickname) {
        return crews.stream()
                .anyMatch(crew -> crew.isSameName(nickname));
    }

    public Crew findCrew(String nickname) {
        return crews.stream()
                .filter(c -> c.isSameName(nickname))
                .findAny()
                .orElse(null);
    }

    public void ifFindNameAddTime(String nickname) {
        Crew crew = findCrew(nickname);
        if (crew == null) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public void initializeAttendTime(String nickname, String time) {
        Crew crew = findCrew(nickname);

        if (crew != null) {
            crew.addAttendTime(time);
            crew.attend(time);
            return;
        }
        Crew crew1 = new Crew(nickname, time);
        crews.add(crew1);
        crew1.attend(time);
    }

    public AttendTime deleteAttendance(String nickname, int date) {
        Crew crew = findCrew(nickname);
        AttendTime attendTime = crew.findAttendanceByDate(date);
        crew.deleteAttendance(date);
        return attendTime;
    }

    public List<Crew> getDangerousCrews(String type) {
        return crews.stream()
                .filter(crew -> crew.isSameType(type))
                .toList();
    }

    public List<Crew> getCrews() {
        return crews;
    }
}
