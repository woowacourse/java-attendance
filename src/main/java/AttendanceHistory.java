import java.util.List;

public class AttendanceHistory {

    private final String name;
    private final List<Attendance> attendances;

    public AttendanceHistory(String name, List<Attendance> attendances) {
        this.name = name;
        this.attendances = attendances;
    }


    public String getName() {
        return name;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}
