package domain;

import java.util.List;

public class Attendances {
    
    private String name;
    private List<Attendance> attendances;
    
    public Attendances(String name, List<Attendance> attendances) {
        this.name = name;
        this.attendances = attendances;
    }
}
