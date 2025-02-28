package attendance.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public Attendance add(final Attendance attendanceInput) {
        for (Attendance attendance : attendances) {
            if(attendance.isEqualAttendanceDate(attendanceInput)) {
                throw new IllegalArgumentException("해당 날짜에 이미 출석이 존재합니다. 출석 수정 기능을 이용해주세요.");
            }
        }
        attendances.add(attendanceInput);
        return attendanceInput;
    }


    public void remove(final Attendance beforeAttendance) {
        attendances.removeIf(attendance -> attendance.isEqualAttendanceDate(beforeAttendance));
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }
}
