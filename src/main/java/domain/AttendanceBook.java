package domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public Optional<AttendanceDateTime> findRecordByCrewAndDate(Crew crew, LocalDate date) {
        return getRecordsOfCrew(crew).getRecords()
            .stream()
            .filter(adt -> adt.isSameDate(date))
            .findAny();
    }
}
