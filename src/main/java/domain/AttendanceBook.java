package domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AttendanceBook {

    private final Map<Crew, AttendanceRecords> crewRecords = new HashMap<>();

    public Optional<Crew> findCrewByName(String name) {
        return crewRecords.keySet()
            .stream()
            .filter(crew -> crew.hasName(name))
            .findAny();
    }

    public Set<Crew> getAllCrews() {
        return Set.copyOf(crewRecords.keySet());
    }

    public void attend(Crew crew, AttendanceDateTime attendDateTime) {
        crewRecords.putIfAbsent(crew, new AttendanceRecords());
        AttendanceRecords records = crewRecords.get(crew);
        records.add(attendDateTime);
    }

    public List<AttendanceDateTime> findAllRecordsByCrew(Crew crew) {
        AttendanceRecords records = crewRecords.getOrDefault(crew, new AttendanceRecords());
        return records.getRecords();
    }

    public List<AttendanceDateTime> listAttendancesOfCrew(Crew crew, LocalDate fromInclusive, LocalDate endInclusive) {
        AttendanceRecords records = crewRecords.getOrDefault(crew, new AttendanceRecords());
        return records.getRecordsWithMissingDatesBetween(fromInclusive, endInclusive);
    }

    public AttendanceDateTime getRecordByCrewAndDate(Crew crew, LocalDate date) {
        return findAllRecordsByCrew(crew)
            .stream()
            .filter(adt -> adt.isSameDate(date))
            .findAny()
            .orElse(AttendanceDateTime.ofAbsence(date));
    }

    public void modify(Crew crew, LocalDate dateToModify, AttendanceDateTime newRecord) {
        AttendanceRecords records = crewRecords.get(crew);
        records.removeIfAttendedOnDate(dateToModify);
        records.add(newRecord);
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatuses(
        Crew crew, LocalDate fromInclusive, LocalDate endInclusive) {
        Map<AttendanceStatus, Integer> counts = initializeZeroCountMap();

        List<AttendanceDateTime> attendances = listAttendancesOfCrew(crew, fromInclusive, endInclusive);
        applyCountsToMap(attendances, counts);
        return counts;
    }

    private Map<AttendanceStatus, Integer> initializeZeroCountMap() {
        Map<AttendanceStatus, Integer> counts = new EnumMap<>(AttendanceStatus.class);
        Arrays.stream(AttendanceStatus.values())
            .forEach(status -> counts.put(status, 0));
        return counts;
    }

    private void applyCountsToMap(List<AttendanceDateTime> attendances,
        Map<AttendanceStatus, Integer> counts) {
        attendances.stream()
            .map(AttendanceDateTime::getAttendanceStatus)
            .forEach(status -> counts.compute(status, (key, val) -> val + 1));
    }
}
