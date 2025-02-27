package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.domain.constant.CrewStatus;
import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register {
    private final Map<Crew, AttendanceRegistry> register;
    private static final int OPEN_TIME = 8;
    private static final int CLOSE_TIME = 23;

    public Register(Crews crews, LocalDate now) {
        register = new HashMap<>();
        initializeRegister(crews, now);
    }

    private void initializeRegister(Crews crews, LocalDate now) {
        for (Crew crew : crews.getCrews()) {
            register.put(crew, AttendanceRegistry.fromDefaultValue(now));
        }
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

    public List<CrewRisk> findAllExpertRiskCrews() {
        Map<Crew, AbsenceInfo> riskCrewMap = new HashMap<>();
        for (Crew crew : register.keySet()) {
            AttendanceRegistry attendanceRegistry = register.get(findValidatedCrew(crew));
            findRiskCrews(crew, riskCrewMap, attendanceRegistry);
        }
        return mapToSortedCrewRisks(riskCrewMap);
    }

    private List<CrewRisk> mapToSortedCrewRisks(Map<Crew, AbsenceInfo> riskCrewMap) {
        List<CrewRisk> riskCrews = new ArrayList<>();
        for (Map.Entry<Crew, AbsenceInfo> entry : riskCrewMap.entrySet()) {
            riskCrews.add(new CrewRisk(entry.getKey(), entry.getValue()));
        }
        orderByAbsenceCountsAndNickname(riskCrews);
        return riskCrews;
    }

    private static void orderByAbsenceCountsAndNickname(List<CrewRisk> riskCrews) {
        riskCrews.sort((crew1, crew2) -> {
            int absenceCompare = Integer.compare(
                    crew2.getAbsenceInfo().calculateTotalAbsence(),
                    crew1.getAbsenceInfo().calculateTotalAbsence()
            );
            if (absenceCompare != 0) {
                return absenceCompare;
            }
            return crew1.getCrew().getCrewName().compareTo(crew2.getCrew().getCrewName());
        });
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

    private void findRiskCrews(Crew crew, Map<Crew, AbsenceInfo> riskCrews, AttendanceRegistry attendanceRegistry) {
        int absenceCounts = attendanceRegistry.findStatusCounts(AttendanceStatus.ABSENCE);
        int lateCounts = attendanceRegistry.findStatusCounts(AttendanceStatus.LATE);
        int limitCount = divideLate(absenceCounts, lateCounts);

        if (limitCount >= CrewStatus.WARNING.getLimitCount()) {
            AbsenceInfo absenceInfo = new AbsenceInfo(absenceCounts, lateCounts);
            riskCrews.put(crew, absenceInfo);
        }
    }

}
