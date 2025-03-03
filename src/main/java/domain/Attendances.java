package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public void add(final Attendance attendance) {
        this.attendances.add(attendance);
    }

    public void checkAttendance(final String name, final LocalDateTime time) {
        CampusTime.validateInTime(time.toLocalTime());
        Attendance attendance = findAttendanceBy(name);
        attendance.add(time);
    }

    public void updateAttendance(final String name, final LocalDateTime updateDateTime) {
        Attendance attendance = findAttendanceBy(name);
        attendance.update(updateDateTime);
    }

    public LocalDateTime getAttendanceRecordBy(final String name, final LocalDate date) {
        Attendance attendance = findAttendanceBy(name);
        return attendance.getAttendanceBy(date);
    }

    public Map<LocalDateTime, AttendanceState> getHistory(final String name, final LocalDate dateTime) {
        Attendance crewAttendance = findAttendanceBy(name);

        Map<LocalDateTime, AttendanceState> attendanceHistory = new TreeMap<>();
        LocalDate date = LocalDate.of(2024, 12, 1);
        while (date.isBefore(dateTime)) {

            if (Calender.isHolyDay(date)) {
                date = plusOneDay(date);
                continue;
            }

            if (crewAttendance.isContains(date)) {
                LocalDateTime attendanceDateTime = crewAttendance.getAttendanceBy(date);
                AttendanceState status = AttendanceState.findStateBy(attendanceDateTime);
                attendanceHistory.put(attendanceDateTime, status);
                date = plusOneDay(date);
                continue;
            }

            LocalDateTime absenceDateTime = date.atTime(0, 0);
            attendanceHistory.put(absenceDateTime, AttendanceState.ABSENCE);
            date = plusOneDay(date);
        }

        return attendanceHistory;
    }

    private LocalDate plusOneDay(LocalDate date) {
        date = date.plusDays(1);
        return date;
    }

    public Map<AttendanceState, Integer> calculate(final Map<LocalDateTime, AttendanceState> attendancesHistory) {
        Map<AttendanceState, Integer> attendanceStateCounts = new EnumMap<>(AttendanceState.class);

        initializeCounts(attendanceStateCounts);
        insertStateCount(attendancesHistory, attendanceStateCounts);

        return attendanceStateCounts;
    }

    private void insertStateCount(final Map<LocalDateTime, AttendanceState> attendancesHistory,
                                  final Map<AttendanceState, Integer> attendanceStateCounts) {
        for (AttendanceState state : attendancesHistory.values()) {
            attendanceStateCounts.put(state, attendanceStateCounts.getOrDefault(state, 0) + 1);
        }
    }

    private void initializeCounts(final Map<AttendanceState, Integer> attendanceCounts) {
        for (AttendanceState state : AttendanceState.values()) {
            attendanceCounts.put(state, 0);
        }
    }

    public Attendance findAttendanceBy(final String name) {
        return attendances.stream()
                .filter(attendance -> attendance.isSame(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루 입니다."));
    }

    public Map<Crew, Map<AttendanceState, Integer>> calculateAbsence(final LocalDate dateTime) {
        Map<Crew, Map<AttendanceState, Integer>> absenceCounts = new HashMap<>();

        for (Attendance attendance : attendances) {
            Crew crew = attendance.getCrew();

            Map<LocalDateTime, AttendanceState> attendanceHistory = getHistory(crew.getName(), dateTime);
            Map<AttendanceState, Integer> absenceHistory = calculate(attendanceHistory);

            absenceCounts.put(crew, absenceHistory);
        }
        return absenceCounts;
    }

    public List<Attendance> getAttendances() {
        return List.copyOf(attendances);
    }

    public void validateFindCrew(final String name) {
        boolean hasCrew = attendances.stream()
                .anyMatch(attendance -> attendance.isSame(name));

        if (!hasCrew) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }
}
