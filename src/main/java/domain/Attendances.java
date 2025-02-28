package domain;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public boolean has(LocalDate day) {
        return false;
    }
}
