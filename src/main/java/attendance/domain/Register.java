package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register {

    private static final String COMMA = ",";
    private static final String SPACE = " ";
    private static final String HYPHEN = "-";
    private static final int POSITION_ZERO = 0;
    private static final int POSITION_ONE = 1;
    private static final int POSITION_TWO = 2;
    private final Map<Crew, AttendanceRegistry> register;

    public Register(Crews crews, LocalDate now) {
        register = new HashMap<>();
        crews.register(register, AttendanceRegistry.fromDefaultValue(now));
    }

    public DateInfo modifyInfo(Crew crew, LocalDateTime localDateTime) {
        AttendanceRegistry attendanceRegistry = register.get(crew);
        DateInfo dateInfo = attendanceRegistry.findByDate(localDateTime.getDayOfMonth());
        dateInfo.modifyAttendanceTime(localDateTime);
        return dateInfo;
    }

    public DateInfo findInfo(Crew crew, LocalDateTime modifyDate) {
        AttendanceRegistry attendanceRegistry = register.get(crew);
        return attendanceRegistry.findByDate(modifyDate.getDayOfMonth());
    }

    public AttendanceRegistry checkAttendanceHistory(Crew crew) {
        AttendanceRegistry attendanceRegistry = register.get(crew);
        attendanceRegistry.calculateAttendanceHistory();
        return attendanceRegistry;
    }

    public void fromCrewAttendanceTimeFile(Crews crews, List<String> attendanceTimes) {
        for (String attendanceTime : attendanceTimes) {
            String crewName = make(attendanceTime, COMMA, POSITION_ZERO);
            Crew crew = crews.findCrew(crewName);
            String dateTime = make(attendanceTime, COMMA, POSITION_ONE);
            String date = make(dateTime, SPACE, POSITION_ZERO);
            String timeNumber = make(dateTime, SPACE, POSITION_ONE);
            int hour = Integer.parseInt(make(timeNumber, ":", POSITION_ZERO));
            int minute = Integer.parseInt(make(timeNumber, ":", POSITION_ONE));
            int year = Integer.parseInt(make(date, HYPHEN, POSITION_ZERO));
            int month = Integer.parseInt(make(date, HYPHEN, POSITION_ONE));
            int day = Integer.parseInt(make(date, HYPHEN, POSITION_TWO));

            LocalDateTime localDateTime = LocalDateTime.of(year,month,day,hour,minute);
            modifyInfo(crew, localDateTime);
        }
    }
    
    private String make(String standard, String delimiter, int findIndex) {
        return List.of(standard.split(delimiter)).get(findIndex);
    }

    public Map<Crew, List<Integer>> findAllExpertRiskCrews() {
        Map<Crew, List<Integer>> riskCrews = new HashMap<>();
        for (Crew crew : register.keySet()) {
            AttendanceRegistry attendanceRegistry = register.get(crew);
            findRiskCrews(crew, riskCrews, attendanceRegistry);
        }
        return riskCrews;
    }

    private void findRiskCrews(Crew crew, Map<Crew, List<Integer>> riskCrews, AttendanceRegistry attendanceRegistry) {
        int absenceCounts = attendanceRegistry.findStatusCounts(AttendanceStatus.ABSENCE);
        int lateCounts = attendanceRegistry.findStatusCounts(AttendanceStatus.LATE);
        int limitCount = lateCounts / 3 + absenceCounts;
        if (limitCount >= 2) {
            riskCrews.put(crew, List.of(absenceCounts, lateCounts));
        }
    }

}
