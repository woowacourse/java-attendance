package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SystemTimeCrewAttendanceHistories {

    private final LocalTime ABSENCE_DUMMY_TIME = LocalTime.of(23, 0);
    private final Map<LocalDate, CrewAttendance> renewDateCrewAttendance = new TreeMap<>(LocalDate::compareTo);
    private final CrewDismiss renewCrewDismiss;

    private Map<LocalDate, CrewAttendance> calculateDateCrewAttendance(
            CrewAttendanceHistories crewAttendanceHistories) {
        Map<LocalDate, CrewAttendance> dateCrewAttendance = new HashMap<>();
        for (CrewAttendanceHistory crewAttendanceHistory : crewAttendanceHistories.crewAttendanceHistories()) {
            CrewAttendance crewAttendance = crewAttendanceHistory.crewAttendance();
            AttendanceDate attendanceDate = crewAttendanceHistory.attendanceDate();
            dateCrewAttendance.put(attendanceDate.date(), crewAttendance);
        }
        return dateCrewAttendance;
    }

    public SystemTimeCrewAttendanceHistories(CrewAttendanceHistories dateCrewAttendance) {
        Map<LocalDate, CrewAttendance> prevDateCrewAttendance = calculateDateCrewAttendance(dateCrewAttendance);
        LocalDate systemStartDate = AttendanceDate.schoolStartDate();
        LocalDate systemLastDate = AttendanceDate.schoolLastDate().plusDays(1);
        LocalDate now = LocalDate.now();
        if (now.isBefore(systemLastDate)) {
            systemLastDate = now;
        }
        this.renewDateCrewAttendance.putAll(
                calculateNewAbsenceCount(prevDateCrewAttendance, systemStartDate, systemLastDate));
        this.renewCrewDismiss = renewCrewDismiss(renewDateCrewAttendance);
    }

    private CrewDismiss renewCrewDismiss(Map<LocalDate, CrewAttendance> renewDateCrewAttendanceMap) {
        Map<AttendanceStatus, Integer> statusCount = new HashMap<>();
        for (LocalDate date : renewDateCrewAttendanceMap.keySet()) {
            CrewAttendance crewAttendance = renewDateCrewAttendanceMap.get(date);
            statusCount.put(crewAttendance.attendanceStatus(),
                    statusCount.getOrDefault(crewAttendance.attendanceStatus(), 0) + 1);
        }
        return new CrewDismiss(statusCount);
    }

    private Map<LocalDate, CrewAttendance> calculateNewAbsenceCount(
            Map<LocalDate, CrewAttendance> dateCrewAttendanceOrigin,
            LocalDate systemDate,
            LocalDate systemLastDate) {

        Map<LocalDate, CrewAttendance> dateCrewAttendance = new HashMap<>(dateCrewAttendanceOrigin);
        while (systemDate.isBefore(systemLastDate)) {
            addNotAttendanceAbsence(systemDate, dateCrewAttendance);
            systemDate = systemDate.plusDays(1);
        }
        return dateCrewAttendance;
    }

    private void addNotAttendanceAbsence(LocalDate date, Map<LocalDate, CrewAttendance> dateCrewAttendance) {
        if (!AttendanceDate.isValidAttendanceDate(date)) {
            return;
        }
        AttendanceTime absenceAttendanceTime = new AttendanceTime(ABSENCE_DUMMY_TIME, new AttendanceDate(date));
        dateCrewAttendance.putIfAbsent(date, new CrewAttendance(absenceAttendanceTime));
    }

    public CrewDismiss crewDismiss() {
        return renewCrewDismiss;
    }

    public Map<LocalDate, CrewAttendance> renewDateCrewAttendance() {
        return Collections.unmodifiableMap(renewDateCrewAttendance);
    }
}
