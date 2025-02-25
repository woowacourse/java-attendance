package domain;

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

    public void checkAlreadyAttend(AttendanceDate attendanceDate) {
        if (this.attendances.checkAlreadyAttend(attendanceDate)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용해 주세요.");
        }
    }

    public void attend(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendances.addNewAttendance(attendanceDate, attendanceTime);
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
