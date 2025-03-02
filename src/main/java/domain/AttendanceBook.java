package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AttendanceBook {
    private final List<Crew> crews;

    public AttendanceBook() {
        this.crews = new ArrayList<>();
    }

    public void addCrewByName(String name) {
        Crew newCrew = new Crew(name);
        crews.add(newCrew);
    }

    public void validateNameExists(String name) {
        if (!checkNameExists(name)) {
            throw new IllegalArgumentException(ErrorCode.CREW_NAME_NOT_FOUND.getMessage());
        }
    }

    private boolean checkNameExists(String name) {
        return crews.stream()
                .anyMatch(crew -> crew.hasName(name));
    }

    public String getPenaltyMessageByName(String name) {
        return getPenaltyByName(name).getMessage();
    }

    private Crew findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.hasName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.CREW_NAME_NOT_FOUND.getMessage()));
    }

    public int getAttendanceStatusCountByName(String name, AttendanceStatus attendanceStatus) {
        Crew crew = findCrewByName(name);
        return crew.countAttendanceStatusInDecember(attendanceStatus);
    }

    public void putAttendanceRecordByName(String name, LocalDate date, LocalTime time) {
        Crew crew = findCrewByName(name);
        crew.putAttendanceRecord(date, time);
    }

    public void modifyAttendanceRecordByName(String name, LocalDate date, LocalTime time) {
        Crew crew = findCrewByName(name);
        crew.modifyAttendanceRecord(date, time);
    }

    public LocalTime findTimeByNameAndDate(String name, LocalDate date) {
        Crew crew = findCrewByName(name);
        return crew.findTimeByDate(date);
    }

    public Penalty getPenaltyByName(String name) {
        Crew crew = findCrewByName(name);
        return crew.getPenalty();
    }

    public List<Crew> findCrewsWithPenalty() {
        return crews.stream()
                .filter(crew -> !crew.hasPenalty(Penalty.NONE))
                .sorted(Comparator.comparing(Crew::getPenalty, Comparator.comparingInt(Penalty::getPriority))
                        .thenComparing(crew ->
                                        crew.countAttendanceStatusInDecember(AttendanceStatus.ABSENT) +
                                                crew.countAttendanceStatusInDecember(AttendanceStatus.LATE),
                                Comparator.reverseOrder())
                        .thenComparing(Crew::getName))
                .toList();
    }

    public void validateAttendanceRecordExistsByDate(String name, LocalDate date) {
        Crew crew = findCrewByName(name);
        if (!crew.hasAttendanceRecordWithDate(date)) {
            throw new IllegalArgumentException(ErrorCode.ATTENDANCE_DATE_NOT_FOUND.getMessage());
        }
    }
}
