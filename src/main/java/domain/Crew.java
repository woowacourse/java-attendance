package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {
    // 이름 - 출석 정보
    private final String name;
    private final List<Attendance> attendanceInfo;

    public Crew(String name) {
        this.name = name;
        this.attendanceInfo = new ArrayList<>();
    }

    public Attendance addAttendance(LocalDateTime localDateTime) {
        checkAlreadyExisted(localDateTime);
        Attendance attendance = new Attendance(localDateTime);
        attendanceInfo.add(attendance);
        return attendance;
    }

    private void checkAlreadyExisted(LocalDateTime localDateTime) {
        attendanceInfo.stream().filter(attendance ->
                attendance.isEqualDate(localDateTime)
        ).findFirst().ifPresent(a -> {
            throw new IllegalArgumentException("이미 출첵 완");
        });
    }
}
