package domain;

import java.time.LocalTime;

public class Attendance {
    private String name;
    private LocalTime localTime;

    public Attendance(String name, LocalTime localTime) {
        this.name = name;
        this.localTime = localTime;
    }

    public String getName() {
        return name;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
