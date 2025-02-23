package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register {
    private final Map<Crew, AttendanceRegistry> register;

    public Register(Crews crews, LocalDate now) {
        register = new HashMap<>();
        crews.register(register, now);
    }

    public AttendanceChecker modifyInfo(Crew crew, LocalDateTime localDateTime) {
        AttendanceRegistry attendanceRegistry = register.get(crew);
        AttendanceChecker attendanceChecker = attendanceRegistry.findByDate(localDateTime.getDayOfMonth());
        attendanceChecker.modifyAttendanceTime(localDateTime);
        return attendanceChecker;
    }

    public AttendanceChecker findInfo(Crew crew, LocalDateTime modifyDate) {
        AttendanceRegistry attendanceRegistry = register.get(crew);
        return attendanceRegistry.findByDate(modifyDate.getDayOfMonth());
    }

    public AttendanceRegistry checkAttendanceHistory(Crew crew) {
        AttendanceRegistry attendanceRegistry = register.get(crew);
        attendanceRegistry.calculateAttendanceHistory();
        return attendanceRegistry;
    }

    public void fromCrewAttendanceTimeFile(Map<Crew, List<LocalDateTime>> attendanceTimes) {
        for (Crew crew : attendanceTimes.keySet()) {
            modifyAttendanceTimeEachCrew(attendanceTimes, crew);
        }
    }

    private void modifyAttendanceTimeEachCrew(Map<Crew, List<LocalDateTime>> attendanceTimes, Crew crew) {
        for (LocalDateTime localDateTime : attendanceTimes.get(crew)) {
            modifyInfo(crew, localDateTime);
        }
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
