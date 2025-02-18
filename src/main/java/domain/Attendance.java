package domain;

import java.time.LocalDateTime;

public class Attendance {
    private String name;
    private LocalDateTime attendanceTime;

    public Attendance(String name, LocalDateTime attendanceTime) {
        this.name = name;
        this.attendanceTime = attendanceTime;
    }

}
