package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AllCrew {
    private final List<Crew> allCrew;

    public AllCrew() {
        this.allCrew = new ArrayList<>();
    }

    public void addCrew(Crew crew) {
        allCrew.add(crew);
    }

    public void addCrewAttendanceByName(String name, LocalDateTime dateTime) {
        Crew findCrew = allCrew.stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Crew not found"));

        findCrew.addAttendance(dateTime);
    }

    public String printAllCrewWarningInfo(LocalDate date) {
        allCrew.sort(
                Comparator.comparing(Crew::getAbsentCount)
                        .thenComparing(Crew::getName)
        );
        String result = "";
        for(Crew crew : allCrew) {
            result += crew.printWarningInfo(date) + "\n";
        }
        return result;
    }
}
