package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register {

    private final Map<Crew, DateInfos> register;

    public Register(Crews crews, LocalDate now) {
        register = new HashMap<>();
        crews.register(register, DateInfos.fromDefaultValue(now));
    }

    public DateInfo modifyInfo(Crew crew, int date, Time modifyTime) {
        DateInfos dateInfos = register.get(crew);
        DateInfo dateInfo = dateInfos.findByDate(date);
        dateInfo.modifyAttendanceTime(modifyTime);
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
            Time time = Time.from(timeNumber);
            modifyInfo(crew, Integer.parseInt(day), time);
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
