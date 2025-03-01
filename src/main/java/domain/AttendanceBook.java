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

    public AttendanceRecords getRecordsOfCrew(Crew crew) {
        return crewRecords.getOrDefault(crew, new AttendanceRecords());
    }

    public List<AttendanceDateTime> listAttendancesOfCrew(Crew crew, LocalDate fromInclusive,
        LocalDate endInclusive) {
        AttendanceRecords records = crewRecords.getOrDefault(crew, new AttendanceRecords());
        return records.getRecordsWithMissingDatesBetween(fromInclusive, endInclusive);
    }

    public Optional<AttendanceDateTime> findRecordByCrewAndDate(Crew crew, LocalDate date) {
        return getRecordsOfCrew(crew).getRecords()
            .stream()
            .filter(adt -> adt.isSameDate(date))
            .findAny();
    }

    public void modify(Crew crew, LocalDate dateToModify, AttendanceDateTime newRecord) {
        AttendanceRecords records = getRecordsOfCrew(crew);
        records.removeIfAttendedOnDate(dateToModify);
        records.add(newRecord);
    }

    public Map<AttendanceStatus, Integer> countAttendanceStatuses(
        Crew crew, LocalDate fromInclusive, LocalDate endInclusive) {
        Map<AttendanceStatus, Integer> counts = new EnumMap<>(AttendanceStatus.class);
        Arrays.stream(AttendanceStatus.values())
            .forEach(status -> counts.put(status, 0));

        List<AttendanceDateTime> attendances = listAttendancesOfCrew(crew, fromInclusive, endInclusive);
        attendances.stream()
            .map(AttendanceDateTime::getAttendanceStatus)
            .forEach(status -> counts.compute(status, (key, val) -> val + 1));
        return counts;
    }
}
