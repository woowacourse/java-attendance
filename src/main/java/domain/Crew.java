package domain;

import constant.Constants;
import java.time.LocalDate;
import java.util.Objects;

public class Crew {

    private final String name;
    private final Attendances attendances;

    public Crew(String name, Attendances attendances) {
        this.name = name;
        this.attendances = attendances;
    }

    public CrewStatus getCrewStatus(LocalDate nowDate) {
        return this.attendances.getCrewStatue(nowDate);
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public boolean checkAlreadyAttend(AttendanceDate attendanceDate) {
        return this.attendances.checkAlreadyAttend(attendanceDate);
    }

    public void attend(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendances.addNewAttendance(attendanceDate, attendanceTime);
    }

    public void edit(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendances.editAttendance(attendanceDate, attendanceTime);
    }

    public Attendance findAttendanceByDate(AttendanceDate attendanceDate) {
        return this.attendances.findAttendanceByDate(attendanceDate);
    }

    public boolean isExpelledStatus(LocalDate nowDate) {
        return !this.getCrewStatus(nowDate).equals(CrewStatus.NORMAL);
    }

    public int getLateCount() {
        return this.attendances.countLate();
    }

    public int getAbsentCount(LocalDate nowDate) {
        return this.attendances.countUnattended(nowDate);
    }

    public int getExpelledAbsentCount(LocalDate nowDate) {
        return getAbsentCount(nowDate) + getLateCount() / Constants.LATE_TO_UNATTENDED_UNIT;
    }

    public int getCrewStatusSequence() {
        return getCrewStatus(Constants.NOW_DATE).getSequence();
    }

    public String getName() {
        return this.name;
    }

    public Attendances getAttendances() {
        return this.attendances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(name, crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
