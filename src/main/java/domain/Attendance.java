package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private final Crew crew;
    private AttendanceTime attendanceTime;

    private Attendance(Crew crew, AttendanceTime attendanceTime) {
        this.crew = crew;
        this.attendanceTime = attendanceTime;
    }

    public static Attendance of(Crew crew, AttendanceTime attendanceTime) {
        return new Attendance(crew, attendanceTime);
    }

    public static Attendance createAbsence(Crew crew, LocalDate day) {
        return new Attendance(crew, AttendanceTime.createAbsenceTime(day));
    }

    public boolean isSameCrewAndTime(Attendance otherAttendance) {
        return this.crew.equals(otherAttendance.crew) && this.attendanceTime.isSameDay(otherAttendance.attendanceTime);
    }

    public boolean compareByCrewAndTime(Crew otherCrew, LocalDate day) {
        return crew.equals(otherCrew) && attendanceTime.isIn(day);
    }

    public void changeAttendanceTime(LocalTime newTime) {
        this.attendanceTime = attendanceTime.changeTime(newTime);
    }

    public AttendanceType judgeType() {
        return AttendanceType.calculateType(attendanceTime);
    }

    public AttendanceTime getAttendanceTime() {
        return attendanceTime;
    }

    public Crew getCrew() {
        return crew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(crew, that.crew) && Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew, attendanceTime);
    }
}
