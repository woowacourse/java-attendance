package domain;

import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceWarning;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Crew implements Comparable<Crew> {
    private final String name;
    private final Attendance attendance;

    public Crew(String name) {
        this.name = name;
        this.attendance = new Attendance(AttendanceDate.DEFAULT_START_DATE, LocalDate.now());
    }

    public void initializeAttendance(List<LocalDateTime> localDateTimes) {
        attendance.fillAttendanceDate();
        localDateTimes.forEach(this::editAttendanceDateTime);
    }

    private void editAttendanceDateTime(LocalDateTime localDateTime) {
        AttendanceDate attendanceDate = attendance.findAttendanceDate(localDateTime.toLocalDate());
        attendanceDate.editDateTime(localDateTime);
    }

    public String getName() {
        return this.name;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public int countAbsence() {
        return attendance.countAbsence();
    }

    public int countTardy() {
        return attendance.countTardy();
    }

    public int countAbsenceIncludingTardy() {
        return attendance.countAbsenceIncludingTardy();
    }

    public AttendanceDate findAttendanceDate(LocalDate localDate) {
        return attendance.findAttendanceDate(localDate);
    }

    public boolean isAttendanceWarning() {
        return AttendanceWarning.determineAttendanceWarning(this.attendance.countAbsenceIncludingTardy())
                != AttendanceWarning.NONE;
    }

    @Override
    public int compareTo(Crew compareCrew) {
        if (this.attendance.countAbsence() < compareCrew.attendance.countAbsence()) {
            return 1;
        }
        if (this.attendance.countAbsence() > compareCrew.attendance.countAbsence()) {
            return -1;
        }
        if (this.attendance.countTardy() < compareCrew.attendance.countTardy()) {
            return 1;
        }
        if (this.attendance.countTardy() > compareCrew.attendance.countTardy()) {
            return -1;
        }
        return this.name.compareTo(compareCrew.name);
    }
}
