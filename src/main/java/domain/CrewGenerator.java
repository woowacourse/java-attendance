package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CrewGenerator {

    public static Crews generate(List<String[]> parsedCrewsData) {
        Map<Nickname, List<Attendance>> crewData = new HashMap<>();
        for (String[] parsedCrewData : parsedCrewsData) {
            String nickname = parsedCrewData[0];
            String localDateTime = parsedCrewData[1];
            final Nickname name = new Nickname(nickname);
            final Attendance attendance = Attendance.of(localDateTime);
            crewData.computeIfAbsent(name, k -> new ArrayList<>()).add(attendance);
        }

        List<Crew> crews = new ArrayList<>();
        for (Entry<Nickname, List<Attendance>> nicknameListEntry : crewData.entrySet()) {
            final Attendances attendances = new Attendances(nicknameListEntry.getValue());
            crews.add(new Crew(nicknameListEntry.getKey(), attendances, AttendanceCounter.of(attendances)));
        }
        return new Crews(crews);
    }
}
