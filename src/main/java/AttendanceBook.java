import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void initialize(String name, Map<LocalDate, LocalTime> dateAndTime) {
        if (!checkAlreadyExists(name)) {
            addNewCrew(Crew.createByName(name));
        }
        addDailyAttendanceByName(name, dateAndTime);
    }

    public void addNewCrew(Crew newCrew) {
        crews.add(newCrew);
    }

    public void addDailyAttendanceByName(String name, Map<LocalDate, LocalTime> dateAndTime) {
        Crew suitableCrew = getCrewByName(name);
        suitableCrew.addDailyAttendance(dateAndTime);
    }

    public Crew getCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.hasName(name))
                .findFirst()
                .orElseThrow();
    }
}