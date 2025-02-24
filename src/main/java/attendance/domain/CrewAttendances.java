package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewAttendances {

    private final Map<Crew, Attendances> crewAttendances = new HashMap<>();

    public CrewAttendances(final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes, final LocalDateTime today) {
        crewAttendanceDateTimes.keySet()
                .forEach(crew ->
                        crewAttendances.put(crew, new Attendances(crewAttendanceDateTimes.get(crew), today))
                );
    }

    public Crew findCrewByNickname(final String nickname) {
        return crewAttendances.keySet().stream()
                .filter(crew -> crew.isSameNickName(nickname))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }

    public Attendance findAttendanceByLocalDate(final Crew crew, final LocalDate modificationDate) {
        Attendances attendances = crewAttendances.get(crew);
        return attendances.findAttendanceByLocalDate(modificationDate);
    }

    public Attendance modifyAttendance(final Crew crew, final Attendance originAttendance,
                                       final LocalTime modificationTime) {
        Attendances attendances = crewAttendances.get(crew);
        Attendance modifiedAttendance = originAttendance.changeAttendanceTime(modificationTime);
        attendances.modifyAttendance(originAttendance, modifiedAttendance);
        return modifiedAttendance;
    }

    public void addAttendance(final Crew crew, final Attendance attendance) {
        crewAttendances.get(crew)
                .addAttendance(attendance);
    }

    public Attendances findAllAttendance(final Crew crew) {
        return crewAttendances.get(crew);
    }

    public boolean hasAttendance(final Crew crew, final LocalDate findDate) {
        Attendances attendances = crewAttendances.get(crew);
        return attendances.existsByLocalDate(findDate);
    }

    public Map<Crew, Attendances> getCrewAttendances() {
        return Collections.unmodifiableMap(crewAttendances);
    }

}
