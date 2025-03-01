package domain;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class AttendanceBook {

    private final Map<Crew, AttendanceRecords> crewRecords = new HashMap<>();

    public Optional<Crew> findCrewByName(String name) {
        return crewRecords.keySet()
            .stream()
            .filter(crew -> crew.hasName(name))
            .findAny();
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
        return listAttendancesOfCrew(crew, fromInclusive, endInclusive)
            .stream()
            .map(AttendanceDateTime::getAttendanceStatus)
            .collect(groupingBy(Function.identity(),
                collectingAndThen(counting(), Long::intValue)));
    }
}
