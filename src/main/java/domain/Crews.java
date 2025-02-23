package domain;

import java.util.ArrayList;
import java.util.List;

public class Crews {

    private final List<Crew> crews;

    public Crews(List<String> inputCrews) {
        crews = new ArrayList<>();
        inputCrews.forEach(inputCrew -> {
            String[] s = inputCrew.split(",");
            initializeAttendTime(s[0], s[1]);
        });
    }

    public Crew findCrew(String nickname) {
        return crews.stream()
                .filter(c -> c.getName().equals(nickname))
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
        List<Crew> getDangerousCrews = new ArrayList<>();
        crews.stream()
                .filter(crew -> crew.isSameType(type))
                .forEach(getDangerousCrews::add);
        return getDangerousCrews;
    }

    public List<Crew> getCrews() {
        return crews;
    }
}
