package domain.crew;

import domain.attendance.AttendanceTime;
import domain.attendance.AttendanceTimes;
import java.time.LocalDate;
import java.util.Comparator;
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

    public Optional<AttendanceTime> readLog(LocalDate date) {
        return attendanceTimes.readAttendance(date);
    }

    public DisciplinaryStatus getDisciplinaryStatus(LocalDate date) {
        int absenceCount = getAbsenceBeforeDate(date);
        int lateCount = getLateBeforeDate(date);
        return DisciplinaryStatus.from(absenceCount, lateCount);
    }

    public int getAttendanceBeforeDate(LocalDate date) {
        return attendanceTimes.countAttendanceBeforeDate(date);
    }

    public int getLateBeforeDate(LocalDate date) {
        return attendanceTimes.countLateBeforeDate(date);
    }

    public int getAbsenceBeforeDate(LocalDate date) {
        return attendanceTimes.countAbsenceBeforeDate(date);
    }

    public String getCrewNickname() {
        return crew.getNickName();
    }

    public boolean belongsTo(Crew crew) {
        return this.crew.equals(crew);
    }

    public int compareAttendanceTimes(CrewAttendance other, Comparator<AttendanceTimes> comparator) {
        return comparator.compare(this.attendanceTimes, other.attendanceTimes);
    }

    public int compareCrew(CrewAttendance other) {
        return this.crew.compareTo(other.crew);
    }
}
