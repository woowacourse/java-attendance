package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.DateUtil;

public class Attendances {

    private Map<Crew, List<Attendance>> attendances = new HashMap<>();

    public void addAttendance(Crew crew, Attendance attendance) {
        if (!attendances.containsKey(crew)) {
            attendances.put(crew, new ArrayList<>(List.of(attendance)));
            return;
        }

        attendances.get(crew).add(attendance);
    }

    public List<Attendance> getByCrew(Crew crew, LocalDate date) {
        int day = date.getDayOfMonth();
        List<Attendance> copiedAttendancesOfCrew = new ArrayList<>(attendances.get(crew));
        int sequenceOfRecord = 0;
        for (int i = 1; i < day; i++) {
            if (DateUtil.isWeekend(LocalDate.of(2024, 12, i))) {
                continue;
            }
            addAbsenceIfNotExistRecord(copiedAttendancesOfCrew, sequenceOfRecord, i);
            sequenceOfRecord++;
        }
        return copiedAttendancesOfCrew;
    }

    private void addAbsenceIfNotExistRecord(List<Attendance> copiedAttendancesOfCrew,
        int sequenceOfRecord, int i) {
        Attendance attendance = copiedAttendancesOfCrew.get(sequenceOfRecord);
        if (attendance.getDateTime().getDayOfMonth() > i) {
            copiedAttendancesOfCrew.add(sequenceOfRecord,
                Attendance.of(LocalDateTime.of(2024, 12, i, 0, 0, 0)));
        }
    }

    public int countAttendanceStatus(Crew crew, LocalDate date, AttendanceStatus status) {
        List<Attendance> attendanceList = getByCrew(crew, date);
        int absenceCount = 0;
        for (Attendance attendance : attendanceList) {
            if (attendance.getStatus().equals(status)) {
                absenceCount++;
            }
        }
        return absenceCount;
    }
}
