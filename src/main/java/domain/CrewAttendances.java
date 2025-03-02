package domain;

import except.AttendanceException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import strategy.CurrentDateGenerateStrategy;

public class CrewAttendances {

    private static final String NOT_EXISTS_ATTENDANCE_HISTORY = "출석 기록이 존재하지 않습니다.";
    
    private final Map<CrewName, DateCrewAttendanceManager> crewAttendances;
    private final CurrentDateGenerateStrategy currentDateGenerateStrategy;

    public CrewAttendances(CurrentDateGenerateStrategy currentDateGenerateStrategy) {
        this.currentDateGenerateStrategy = currentDateGenerateStrategy;
        this.crewAttendances = new HashMap<>();
    }

    public CrewAttendances(CurrentDateGenerateStrategy currentDateGenerateStrategy,
                           List<AttendanceReadUnit> attendanceReadUnits) {
        this.currentDateGenerateStrategy = currentDateGenerateStrategy;
        this.crewAttendances = new HashMap<>();
        for (AttendanceReadUnit attendanceReadUnit : attendanceReadUnits) {
            CrewName crewName = attendanceReadUnit.crewName();
            crewAttendances.putIfAbsent(attendanceReadUnit.crewName(),
                    new DateCrewAttendanceManager(currentDateGenerateStrategy));
            crewAttendances.get(crewName).addAttendance(attendanceReadUnit);
        }
    }

    public void addAttendance(String nickname, LocalTime attendanceTime) {
        DateCrewAttendanceManager dateCrewAttendanceManager = dateCrewAttendanceManager(nickname);
        dateCrewAttendanceManager.addAttendance(attendanceTime);
    }

    public CrewAttendance crewAttendance(String nickname, LocalDate date) {
        DateCrewAttendanceManager dateCrewAttendanceManager = dateCrewAttendanceManager(nickname);
        return dateCrewAttendanceManager.crewAttendance(date);
    }

    private DateCrewAttendanceManager dateCrewAttendanceManager(String nickname) {
        CrewName crewName = new CrewName(nickname);
        crewAttendances.putIfAbsent(crewName, new DateCrewAttendanceManager(currentDateGenerateStrategy));
        return crewAttendances.get(crewName);
    }

    public void modifyAttendance(String nickname, LocalDate modifyDate, LocalTime modifyTime) {
        DateCrewAttendanceManager dateCrewAttendanceManager = dateCrewAttendanceManager(nickname);
        dateCrewAttendanceManager.modifyAttendance(modifyDate, modifyTime);
    }

    public SystemTimeCrewAttendanceHistories crewAttendancesHistory(String nickname) {
        CrewName crewName = new CrewName(nickname);
        if (!crewAttendances.containsKey(crewName)) {
            throw new AttendanceException(NOT_EXISTS_ATTENDANCE_HISTORY);
        }
        DateCrewAttendanceManager dateCrewAttendanceManager = dateCrewAttendanceManager(nickname);
        CrewAttendanceHistories crewAttendanceHistories = dateCrewAttendanceManager.crewAttendancesHistory();
        return new SystemTimeCrewAttendanceHistories(
                crewAttendanceHistories, currentDateGenerateStrategy);
    }

    public boolean isExistAttendance(String nickname) {
        CrewName crewName = new CrewName(nickname);
        return crewAttendances.containsKey(crewName);
    }

    public List<String> nicknames() {
        return this.crewAttendances.keySet()
                .stream()
                .map(CrewName::nickname)
                .collect(Collectors.toList());
    }

    public List<CrewDismissHistory> orderedDismissHistory() {
        CrewDismissHistories crewDismisses = new CrewDismissHistories();
        for (String crewNickname : nicknames()) {
            SystemTimeCrewAttendanceHistories systemTimeCrewAttendanceHistories = crewAttendancesHistory(crewNickname);
            crewDismisses.addDismissHistory(crewNickname, systemTimeCrewAttendanceHistories);
        }
        return crewDismisses.crewDismissHistories();
    }
}
