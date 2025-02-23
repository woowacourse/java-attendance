package attendance.domain;

import java.time.LocalDate;
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

    public List<Attendance> getAttendancesOfCrew(Crew crew, LocalDate today) {
        Attendances attendancesOfCrew = attendancesBook.get(crew);
        return attendancesOfCrew.getAddAbsenceAttendances(today);
    }

    public int countAttendanceStatus(List<Attendance> attendances,  AttendanceStatus status) {
        int absenceCount = 0;
        for (Attendance attendance : attendances) {
            if (attendance.getStatus().equals(status)) {
                absenceCount++;
            }
        }
        return absenceCount;
    }
}
