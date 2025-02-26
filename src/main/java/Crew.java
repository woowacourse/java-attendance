import java.util.ArrayList;
import java.util.List;

public class Crew {

    String name;
    List<Attendance> attendances;

    public Crew(String name) {
        this.name = name;
        this.attendances = new ArrayList<>();
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }
}
