package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import attendance.util.DateUtil;

public class Attendances {

    private Map<Crew, List<Attendance>> attendances = new HashMap<>();

    public void addAttendance(Crew crew, Attendance attendance) {
        if (!attendances.containsKey(crew)) {
            attendances.put(crew, new ArrayList<>(List.of(attendance)));
            return;
        }

        for (Attendance existAttendance : attendances.get(crew)) {
            validateAlreadyAttended(attendance, existAttendance);
        }

        attendances.get(crew).add(attendance);
    }

    private void validateAlreadyAttended(Attendance attendance, Attendance existAttendance) {
        if (existAttendance.getDateTime().getDayOfMonth() == attendance.getDateTime().getDayOfMonth()) {
            throw new IllegalArgumentException("\n[ERROR] 이미 출석을 완료했습니다. 수정 기능을 이용해주세요.");
        }
    }

    public Attendance getAttendance(Crew crew, LocalDate localDate) {
        List<Attendance> attendancesOfCrew = attendances.get(crew);
        for (Attendance attendance : attendancesOfCrew) {
            if (attendance.getDateTime().getDayOfMonth() == localDate.getDayOfMonth()) {
                return attendance;
            }
        }
        Attendance attendance = Attendance.of(LocalDateTime.of(localDate, LocalTime.of(0, 0)));
        addAttendance(crew, attendance);
        return attendance;
    }

    public List<Attendance> getByCrew(Crew crew, LocalDate date) {
        int day = date.getDayOfMonth();
        List<Attendance> copiedAttendancesOfCrew = new ArrayList<>(attendances.get(crew));
        int sequenceOfRecord = 0;
        for (int i = 1; i < day; i++) {
            if (DateUtil.isWeekend(LocalDate.of(date.getYear(), date.getMonth(), i))) {
                continue;
            }
            addAbsenceIfNotExistRecord(copiedAttendancesOfCrew, sequenceOfRecord, LocalDate.of(date.getYear(), date.getMonth(), i));
            sequenceOfRecord++;
        }
        return copiedAttendancesOfCrew;
    }

    private void addAbsenceIfNotExistRecord(List<Attendance> copiedAttendancesOfCrew,
        int sequenceOfRecord, LocalDate date) {
        Attendance attendance = copiedAttendancesOfCrew.get(sequenceOfRecord);
        if (attendance.getDateTime().getDayOfMonth() > date.getDayOfMonth()) {
            copiedAttendancesOfCrew.add(sequenceOfRecord,
                Attendance.of(LocalDateTime.of(date, LocalTime.of(0, 0, 0))));
        }
    }

    public int countAttendanceStatus(Crew crew, LocalDate date, AttendanceStatus status) {
        List<Attendance> attendancesOfCrew = getByCrew(crew, date);
        int absenceCount = 0;
        for (Attendance attendance : attendancesOfCrew) {
            if (attendance.getStatus().equals(status)) {
                absenceCount++;
            }
        }
        return absenceCount;
    }
}
