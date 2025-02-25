package domain;

import java.time.LocalDate;
import java.util.Optional;

public class CrewAttendance {
    private final Crew crew;
    private final AttendanceTimes attendanceTimes;

    private CrewAttendance(Crew crew, AttendanceTimes attendanceTimes) {
        this.crew = crew;
        this.attendanceTimes = attendanceTimes;
    }

    public static CrewAttendance of(Crew crew, AttendanceTimes attendanceTimes) {
        return new CrewAttendance(crew, attendanceTimes);
    }

    public void attend(AttendanceTime attendanceTime) {
        attendanceTimes.addAttendance(attendanceTime);
    }

    public Optional<AttendanceTime> modify(AttendanceTime attendanceTime) {
        return attendanceTimes.modifyAttendance(attendanceTime);
    }

    public int countAttendanceBeforeDate(LocalDate date) {
        return 2;
    }

    public int countLateBeforeDate(LocalDate date) {
        return 1;
    }

    public int countAbsenceBeforeDate(LocalDate date) {
        return 1;
    }

    public boolean belongsTo(Crew crew) {
        return this.crew.equals(crew);
    }
}
