package attendance.domain;

import attendance.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttendancesBook {

    private final Map<Crew, Attendances> attendancesBook;

    public AttendancesBook(Map<Crew, Attendances> attendancesBook) {
        this.attendancesBook = attendancesBook;
    }

    public void addAttendance(Crew crew, Attendance attendance) {
        if (!attendancesBook.containsKey(crew)) {
            attendancesBook.put(crew, new Attendances(new ArrayList<>(List.of(attendance))));
            return;
        }

        attendancesBook.get(crew).add(attendance);
    }

    public Attendance getExistAttendanceOfCrew(Crew crew, LocalDate modifyingDate) {
        Attendances attendances = attendancesBook.get(crew);
        return attendances.get(modifyingDate);
    }

    public Attendance modify(Crew crew, Attendance previousAttendance, LocalTime modifyingTime) {
        Attendances attendances = attendancesBook.get(crew);
        return attendances.modify(previousAttendance, modifyingTime);
    }

    // TODO : getter로 꺼내오고 있어서 수정 필요
    public List<Attendance> getByCrew(Crew crew, LocalDate date) {
        int day = date.getDayOfMonth();
        List<Attendance> copiedAttendancesOfCrew = new ArrayList<>(attendancesBook.get(crew).getAttendances());
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
