package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Register {

    public static final String COMMA_OR_SPACE_REGEX = "[,\\s]+";
    private final Map<Crew, DateInfos> register;

    private Register(Map<Crew, DateInfos> register) {
        this.register = register;
    }

    public static Register createRegisterByCrews(Crews crews) {
        Map<Crew, DateInfos> register = new HashMap<>();
        for (Crew crew : crews.getCrews()) {
            register.put(crew, DateInfos.initInfos());
        }
        return new Register(register);
    }

    public static Register createRegisterByCrewAttendanceTimeFile(List<String> crewAttendanceTimeFile) {
        Set<String> uniqueCrewName = new HashSet<>();
        Crews crews = Crews.initCrews();
        Map<Crew, DateInfos> register = new HashMap<>();

        for (String crewAttendanceTime : crewAttendanceTimeFile) {
            List<String> parsedString = List.of(crewAttendanceTime.split(COMMA_OR_SPACE_REGEX));
            String crewName = parsedString.getFirst(); // 쿠키
            LocalDate date = LocalDate.parse(parsedString.get(1)); // 2025-02-14
            CampusTime campusTime = CampusTime.fromHourColonMinute(parsedString.get(2)); // 10:08

            if (!uniqueCrewName.contains(crewName)) {
                Crew crew = Crew.from(crewName);
                crews.addCrew(crew);
                register.put(crew, DateInfos.initInfos());
            }

            Crew crew = crews.findCrew(crewName);
            DateInfos dateInfos = register.get(crew);
            dateInfos.addInfo(DateInfo.fromCampusTime(date, campusTime));
        }
        return new Register(register);
    }

    public DateInfo modifyInfo(Crew crew, int date, CampusTime modifyCampusTime) {
        DateInfos dateInfos = register.get(crew);
        DateInfo dateInfo = dateInfos.findByDate(date);
        dateInfo.modifyAttendanceTime(modifyCampusTime);
        return dateInfo;
    }

    public DateInfo findInfo(Crew crew, int modifyDate) {
        DateInfos dateInfos = register.get(crew);
        return dateInfos.findByDate(modifyDate);
    }

    public DateInfos checkAttendanceHistory(Crew crew) {
        DateInfos dateInfos = register.get(crew);
        dateInfos.calculateAttendanceHistory();
        return dateInfos;
    }

    public void fromCrewAttendanceTimeFile(Crews crews, List<String> attendanceTimes) {
        for (String attendanceTime : attendanceTimes) {
            String crewName = make(attendanceTime, ",", 0);
            Crew crew = crews.findCrew(crewName);
            String dateTime = make(attendanceTime, ",", 1);
            String date = make(dateTime, " ", 0);
            String timeNumber = make(dateTime, " ", 1);
            String day = make(date, "-", 2);
            CampusTime campusTime = CampusTime.fromHourColonMinute(timeNumber);
            modifyInfo(crew, Integer.parseInt(day), campusTime);
        }
    }

    private String make(String standard, String delimiter, int findIndex) {
        return List.of(standard.split(delimiter)).get(findIndex);
    }

    public Map<Crew, List<Integer>> findAllExpertRiskCrews() {
        Map<Crew, List<Integer>> riskCrews = new HashMap<>();
        for (Crew crew : register.keySet()) {
            DateInfos dateInfos = register.get(crew);
            findRiskCrews(crew, riskCrews, dateInfos);
        }
        return riskCrews;
    }

    private void findRiskCrews(Crew crew, Map<Crew, List<Integer>> riskCrews, DateInfos dateInfos) {
        int absenceCounts = dateInfos.findStatusCounts(AttendanceStatus.ABSENCE);
        int lateCounts = dateInfos.findStatusCounts(AttendanceStatus.LATE);
        int limitCount = lateCounts / 3 + absenceCounts;
        if (limitCount >= 2) {
            riskCrews.put(crew, List.of(absenceCounts, lateCounts));
        }
    }

}
