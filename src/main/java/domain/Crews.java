package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Crews {
    private final List<Crew> crews;

    public Crews(Map<String, List<LocalDateTime>> allAttendanceHistories, LocalDate standardDate) {
        this.crews = allAttendanceHistories.entrySet().stream()
                .map(entry -> new Crew(entry.getKey(), entry.getValue(), standardDate))
                .toList();
    }

    public void validateHasCrew(String username) {
        findCrewByUsername(username);
    }

    public AttendanceResult addAttendanceHistory(String username, LocalDateTime attendanceTime) {
        Crew crew = findCrewByUsername(username);
        return crew.addAttendanceHistory(attendanceTime);
    }

    public AttendanceResult editAttendanceHistory(String username, LocalDateTime editTime) {
        Crew crew = findCrewByUsername(username);
        return crew.editAttendanceHistory(editTime);
    }

    public AttendanceHistory findAttendanceHistory(String username, LocalDate standardTime) {
        Crew crew = findCrewByUsername(username);
        return crew.findAttendanceHistory(standardTime);
    }

    public AttendanceAnalyze getAttendanceAnalyze(String username, LocalDate standard) {
        Crew crew = findCrewByUsername(username);
        return crew.getAttendanceAnalyze(standard);
    }

    public Map<Username, AttendanceAnalyze> findExpulsionCrews(LocalDate standard) {
        List<Crew> expulsionCrews = getExpulsionCrewsWithOrders(standard);
        return createExpulsionCrewsInfo(standard, expulsionCrews);
    }

    private Crew findCrewByUsername(String username) {
        return crews.stream().filter(crew -> crew.isSameName(username))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }

    private LinkedHashMap<Username, AttendanceAnalyze> createExpulsionCrewsInfo(LocalDate standard,
                                                                                List<Crew> expulsionCrews) {
        return expulsionCrews.stream().collect(Collectors.toMap(
                Crew::getUsername,
                crew -> crew.getAttendanceAnalyze(standard),
                (existing, replacement) -> existing,
                LinkedHashMap::new
        ));
    }

    private List<Crew> getExpulsionCrewsWithOrders(LocalDate standard) {
        return crews.stream().filter(crew -> crew.isExpulsionTarget(standard))
                .sorted(new Comparator<Crew>() {
                    @Override
                    public int compare(Crew o1, Crew o2) {
                        AttendanceAnalyze attendanceAnalyzeO1 = o1.getAttendanceAnalyze(standard);
                        AttendanceAnalyze attendanceAnalyzeO2 = o2.getAttendanceAnalyze(standard);
                        int compare = Integer.compare(attendanceAnalyzeO2.calculatePenaltyPoints(),
                                attendanceAnalyzeO1.calculatePenaltyPoints());
                        if (compare == 0) {
                            return o1.getUsername().compareTo(o2.getUsername());
                        }
                        return compare;
                    }
                })
                .toList();
    }
}
