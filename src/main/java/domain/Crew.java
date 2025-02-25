package domain;

import java.util.List;
import java.util.Objects;

public class Crew {

    private final String name;
    private final Attendances attendances;

    public Crew(String name, Attendances attendances) {
        this.name = name;
        this.attendances = attendances;
    }

    public CrewStatus getCrewStatus() {
        return this.attendances.getCrewStatue();
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

    public List<Attendance> getAttendances() {
        return this.attendances.getAttendances();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
