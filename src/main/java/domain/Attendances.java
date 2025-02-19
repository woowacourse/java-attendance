package domain;

import java.util.List;

public class Attendances {
    private final List<Attendance> attendances;

    private Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public static Attendances of(List<Attendance> attendances) {
        return new Attendances(attendances);
    }

    public Attendance findAttendanceByName(String name) {
        for (Attendance attendance : attendances) {
            if (attendance.isSameName(name)) {
                return attendance;
            }
        }
        throw new IllegalArgumentException("[ERROR] 출석부에 해당하는 이름이 없습니다.");
    }
}
