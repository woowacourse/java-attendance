package attendance.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register {

    public static final String COMMA_OR_SPACE_REGEX = "[,\\s]+";
    private final Map<Crew, DateInfos> register;
    private final Crews crews;

    private Register(Map<Crew, DateInfos> register, Crews crews) {
        this.register = register;
        this.crews = crews;
    }

    public static Register createRegisterByCrewAttendanceTimeFile(List<String> crewAttendanceTimeFile) {
        Crews crews = Crews.initCrews();
        Map<Crew, DateInfos> register = new HashMap<>();

        for (String crewAttendanceTime : crewAttendanceTimeFile) {
            List<String> parsedString = List.of(crewAttendanceTime.split(COMMA_OR_SPACE_REGEX));
            String crewName = parsedString.getFirst();
            LocalDate date = LocalDate.parse(parsedString.get(1));
            CampusTime campusTime = CampusTime.fromHourColonMinute(parsedString.get(2));

            if (!crews.hasCrew(crewName)) {
                Crew crew = Crew.from(crewName);
                crews.addCrew(crew);
                register.put(crew, DateInfos.initInfos());
            }

            Crew crew = crews.findCrew(crewName);
            DateInfos dateInfos = register.get(crew);
            dateInfos.addDateInfo(DateInfo.fromCampusTime(date, campusTime));
        }
        return new Register(register, crews);
    }

    public void addDateInfo(String crewName, DateInfo dateInfo) {
        Crew crew = crews.findCrew(crewName);
        DateInfos dateInfos = register.get(crew);
        dateInfos.addDateInfo(dateInfo);
    }

    public DateInfo modifyDateInfo(String crewName, String day, CampusTime campusTime) {
        DateInfos dateInfos = register.get(crews.findCrew(crewName));
        DateInfo dateInfo = dateInfos.findDateInfoByDay(Integer.parseInt(day));
        dateInfo.modifyAttendanceTime(campusTime);
        return dateInfo;
    }

    public boolean hasDateInfo(String crewName, String day) {
        DateInfos dateInfos = register.get(crews.findCrew(crewName));
        return dateInfos.hasDateInfo(Integer.parseInt(day));
    }

    public DateInfo findOrCreateDateInfo(String crewName, LocalDate date, CampusTime campusTime) {
        DateInfos dateInfos = register.get(crews.findCrew(crewName));
        return dateInfos.findOrCreateDateInfoByDate(date, campusTime);
    }

    public DateInfos findDateInfos(String crewName) {
        return register.get(crews.findCrew(crewName));
    }

    public Crews getCrews() {
        return crews;
    }

}
