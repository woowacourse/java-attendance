package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
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
        AttendanceRegistry attendanceRegistry = register.get(findValidatedCrew(crew));
        AttendanceChecker attendanceChecker = attendanceRegistry.findByDay(localDateTime.getDayOfMonth());
        attendanceChecker.modifyAttendanceTime(localDateTime);
        return attendanceChecker;
    }

    public AttendanceChecker findInfo(Crew crew, LocalDateTime modifyDate) {
        AttendanceRegistry attendanceRegistry = register.get(findValidatedCrew(crew));
        return attendanceRegistry.findByDay(modifyDate.getDayOfMonth());
    }

    public AttendanceRegistry checkAttendanceHistory(Crew crew) {
        AttendanceRegistry attendanceRegistry = register.get(findValidatedCrew(crew));
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
            AttendanceRegistry attendanceRegistry = register.get(findValidatedCrew(crew));
            findRiskCrews(crew, riskCrews, attendanceRegistry);
        }
        return riskCrews;
    }

    private Crew findValidatedCrew(Crew crew) {
        return register.keySet().stream()
                .filter(crewName -> crewName.equals(crew))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.NICKNAME_NOT_PRESENCE));
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
