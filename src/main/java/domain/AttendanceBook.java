package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    public boolean checkNameExists(String name) {
        return crews.stream()
                .anyMatch(crew -> crew.hasName(name));
    }

    public String getPenaltyMessageByName(String name) {
        Crew crew = findCrewByName(name);
        int lateCount = crew.countAttendanceStatusInDecember(AttendanceStatus.LATE);
        int absentCount = crew.countAttendanceStatusInDecember(AttendanceStatus.ABSENT);
        return Penalty.findPenaltyMessageByAttendanceStatusCount(lateCount, absentCount);
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
}
