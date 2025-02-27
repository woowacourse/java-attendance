package attendance.domain;

import java.time.LocalTime;

public class Attendance {

    private final String crewName;
    private final Time attendanceTime;

    public Attendance(String crewName, Time attendanceTime) {
        this.crewName = crewName;
        this.attendanceTime = attendanceTime;
    }

    public void isSameLocalDate(Attendance attendance) {
        if (this.crewName.equals(attendance.crewName) && attendanceTime.isSameLocalDate(attendance.attendanceTime)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 존재합니다. 수정 기능을 이용해 주세요.");
        }
    }

    public Attendance modifyAttendanceTime(LocalTime modifyTime) {

        return this;
    }
}
