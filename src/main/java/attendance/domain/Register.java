package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Register {
    private final Map<Crew, AttendanceRegistry> register;
    private static final int OPEN_TIME = 8;
    private static final int CLOSE_TIME = 23;

    public Register(Crews crews, LocalDate now) {
        register = new HashMap<>();
        crews.register(register, now);
    }

    public AttendanceChecker modifyInfo(Crew crew, LocalDateTime localDateTime) {
        validateCampusOperationTime(localDateTime);
        AttendanceRegistry attendanceRegistry = register.get(findValidatedCrew(crew));
        AttendanceChecker attendanceChecker = attendanceRegistry.findByDay(localDateTime);
        attendanceChecker.modifyAttendanceTime(localDateTime);
        return attendanceChecker;
    }

    private void validateCampusOperationTime(LocalDateTime localDateTime) {
        int hour = localDateTime.getHour();
        if (hour < OPEN_TIME || hour == CLOSE_TIME) {
            throw CustomException.from(ErrorMessage.CAMPUS_NOT_OPERATION);
        }
    }

    public AttendanceChecker findInfo(Crew crew, LocalDateTime modifyDate) {
        AttendanceRegistry attendanceRegistry = register.get(findValidatedCrew(crew));
        return attendanceRegistry.findByDay(modifyDate);
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

    public List<Entry<Crew, List<Integer>>> findAllExpertRiskCrews() {
        Map<Crew, List<Integer>> riskCrewMap = new HashMap<>();
        for (Crew crew : register.keySet()) {
            AttendanceRegistry attendanceRegistry = register.get(findValidatedCrew(crew));
            findRiskCrews(crew, riskCrewMap, attendanceRegistry);
        }
        return orderByAbsenceCounts(riskCrewMap);
    }

    private List<Entry<Crew, List<Integer>>> orderByAbsenceCounts(Map<Crew, List<Integer>> riskCrewMap) {
        List<Map.Entry<Crew, List<Integer>>> riskCrews = new ArrayList<>(riskCrewMap.entrySet());
        riskCrews.sort((e1, e2) -> Integer.compare(
                calculateTotalAbsence(e2.getValue()),
                calculateTotalAbsence(e1.getValue())
        ));
        return riskCrews;
    }

    private int calculateTotalAbsence(List<Integer> absenceCounts) {
        int absence = absenceCounts.getFirst();
        int late = absenceCounts.getLast();
        return divideLate(absence, late);
    }

    private int divideLate(int absence, int late) {
        return absence + (late / 3);
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
        int limitCount = divideLate(absenceCounts, lateCounts);
        if (limitCount >= 2) {
            riskCrews.put(crew, List.of(absenceCounts, lateCounts));
        }
    }

}
