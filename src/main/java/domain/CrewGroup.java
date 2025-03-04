package domain;

import DTO.AttendanceEditDto;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceStatus;
import domain.attendance.StudentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class CrewGroup {
    public static final Comparator<Crew> CREW_COMPARATOR = Comparator
            .comparingInt((Crew c) -> c.getAttendanceRecord().getAbsenceCount()).reversed()
            .thenComparing((Crew c) -> c.getAttendanceRecord().getTardyCount(), Comparator.reverseOrder())
            .thenComparing(Crew::getName);

    private final Map<String, Crew> crews;

    public CrewGroup() {
        this.crews = new HashMap<>();
    }

    public void addCrew(String crewName) {
        if (has(crewName)) {
            throw new IllegalArgumentException("중복 되는 닉네임입니다.");
        }
        crews.put(crewName, new Crew(crewName));
    }

    public Crew findByName(String crewName) {
        if (!has(crewName)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
        return crews.get(crewName);
    }

    public boolean has(String crewName) {
        return crews.containsKey(crewName);
    }

    public void attendCrew(String crewName, LocalDateTime attendTime){
        Crew findCrew = findByName(crewName);
        findCrew.fillAttend(attendTime);
    }

    public AttendanceEditDto editCrewAttendance(String crewName, LocalDateTime editTime){
        Crew findCrew = findByName(crewName);
        AttendanceDate beforeRecord = findCrew.getAttendTimeByLocalDate(LocalDate.from(editTime));
        findCrew.editAttendanceTime(editTime);
        AttendanceDate afterRecord = findCrew.getAttendTimeByLocalDate(LocalDate.from(editTime));
        return AttendanceEditDto.from(beforeRecord, afterRecord);
    }

    public AttendanceStatus getCrewDateStatus(String crewName, LocalDate findDate){
        Crew findCrew = findByName(crewName);
        return findCrew.getStatusByLocalDate(findDate);
    }

    public List<Crew> getSortedWarningCrews() {
        Predicate<Map.Entry<String, Crew>> isNotNoneStatus = crewEntry -> !crewEntry.getValue()
                .getAttendanceRecord()
                .getStudentStatus()
                .equals(StudentStatus.NONE);

        return crews.entrySet().stream()
                .filter(isNotNoneStatus)
                .map(Map.Entry::getValue)
                .sorted(CREW_COMPARATOR)
                .toList();
    }
}
