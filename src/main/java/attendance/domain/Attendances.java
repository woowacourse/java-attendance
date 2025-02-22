package attendance.domain;

import attendance.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Attendances {

    private final Map<Crew, List<Attendance>> attendances;

    public Attendances(Map<Crew, List<Attendance>> attendances) {
        this.attendances = attendances;
    }

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
        if (existAttendance.getAttendanceDateTime().getDayOfMonth() == attendance.getAttendanceDateTime().getDayOfMonth()) {
            throw new IllegalArgumentException("\n[ERROR] 이미 출석을 완료했습니다. 수정 기능을 이용해주세요.");
        }
    }

    public Attendance getAttendance(Crew crew, LocalDate localDate) {
        List<Attendance> attendancesOfCrew = attendances.get(crew);
        for (Attendance attendance : attendancesOfCrew) {
            if (attendance.getAttendanceDateTime().getDayOfMonth() == localDate.getDayOfMonth()) {
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
        copiedAttendancesOfCrew.removeIf(attendance -> attendance.getAttendanceDateTime().getDayOfMonth() == day);
        int sequenceOfRecord = 0;
        for (int i = 1; i < day; i++) {
            LocalDate currentDay = LocalDate.of(date.getYear(), date.getMonth(), i);
            if (DateUtil.isWeekend(currentDay)) {
                continue;
            }
            addAbsenceRecord(sequenceOfRecord, copiedAttendancesOfCrew, currentDay);
            sequenceOfRecord++;
        }
        return copiedAttendancesOfCrew;
    }

    private void addAbsenceRecord(int sequenceOfRecord, List<Attendance> copiedAttendancesOfCrew, LocalDate currentDay) {
        Attendance attendance;
        if (sequenceOfRecord >= copiedAttendancesOfCrew.size()) {
            attendance = Attendance.of(LocalDateTime.of(currentDay, LocalTime.of(0, 0)));
            copiedAttendancesOfCrew.add(attendance);
            return;
        }
        attendance = copiedAttendancesOfCrew.get(sequenceOfRecord);
        if (attendance.getAttendanceDateTime().getDayOfMonth() > currentDay.getDayOfMonth()) {
            copiedAttendancesOfCrew.add(sequenceOfRecord,
                    Attendance.of(LocalDateTime.of(currentDay, LocalTime.of(0, 0))));
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
