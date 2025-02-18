import java.util.ArrayList;
import java.util.List;

public class AttendanceBook {
    private final List<Crew> crews;

    public AttendanceBook() {
        this.crews = new ArrayList<>();
    }

    public boolean checkAlreadyExists(String name) {
        boolean result = false;

        for (Crew crew : crews) {
            if (crew.hasName(name)) {
                result = true;
            }
        }

        return result;
    }

    public void initialize(String name, String date, String time) {
        if (!checkAlreadyExists(name)) {
            crews.add(Crew.createByName(name));

        }
        addDailyAttendanceByName(name, date, time);
    }

    public void addDailyAttendanceByName(String name, String date, String time) {
        Crew suitableCrew = getCrewByName(name);
        suitableCrew.addDailyAttendance(date, time);
    }

    public Crew getCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.hasName(name))
                .findFirst()
                .orElseThrow();
    }
}
