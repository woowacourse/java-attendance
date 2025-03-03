package domain;

import domain.attendance.AttendanceWarning;
import domain.attendance.Attendances;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AttendanceBook {
    private final Map<Crew, Attendances> attendances = new HashMap<>();

    public AttendanceBook(Map<String, List<LocalDateTime>> crewsInfo, LocalDate startDate, LocalDate endDate) {
        validate(crewsInfo.keySet().stream().toList());
        crewsInfo.forEach((name, dateTimes) ->
                this.attendances.put(new Crew(name), new Attendances(dateTimes, startDate, endDate)));
    }

    public boolean has(String findNickname) {
        return attendances.keySet().stream()
                .anyMatch(crew -> crew.equals(findNickname));
    }

    public Attendances findAttendancesByCrew(String nickname) {
        if (!has(nickname)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다");
        }
        return attendances.get(findCrewByNickname(nickname));
    }

    public List<String> findWarningCrews() {
        return attendances.entrySet().stream()
                .filter(entry ->
                        AttendanceWarning.calculateWarning(entry.getValue().countAllAbsence())
                                != AttendanceWarning.NONE)
                .map(entry -> entry.getKey().getNickname())
                .toList();
    }

    private Crew findCrewByNickname(String nickname) {
        return attendances.keySet()
                .stream()
                .filter(crew -> crew.equals(nickname))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다"));
    }

    private void validate(List<String> crewNames) {
        List<String> distinctCrews = crewNames.stream().distinct().toList();
        if (distinctCrews.size() != crewNames.size()) {
            throw new IllegalArgumentException("중복된 닉네임의 크루는 존재할 수 없습니다");
        }
    }
}
