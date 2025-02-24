package domain;

import java.util.List;

public class AttendanceBook {
    private final List<Crew> crews;

    public AttendanceBook(List<Crew> crews) {
        this.crews = crews;
    }
}