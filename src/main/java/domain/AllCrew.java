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

    public boolean containsCrewName(String crewName) {
        return allCrew.stream()
                .anyMatch(crew -> crew.getName().equals(crewName));
    }

    public String addCrewAttendanceByName(String name, LocalDateTime dateTime) {
        Crew crew = findCrewByName(name);
        return crew.addAttendance(dateTime).printAttendance();
    }

    public String modifyCrewAttendanceByName(String name, LocalDateTime dateTime) {
        Crew crew = findCrewByName(name);
        return crew.update(dateTime);
    }

    public String printAttendanceHistory(String name, LocalDate lastDate) {
        Crew crew = findCrewByName(name);
        return crew.getAttendanceHistory(lastDate);
    }

    private Crew findCrewByName(String name) {
        Crew findCrew = allCrew.stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Crew not found"));
        return findCrew;
    }

    public String printAllCrewWarningInfo(LocalDate date) {
        updateAbsentHistory(date);
        allCrew.sort(
                Comparator.comparing(Crew::getAbsentCount).reversed()
                        .thenComparing(Crew::getName)
        );
        String result = "";
        for(Crew crew : allCrew) {
            if (crew.calculateWarningStatus(crew.getAbsentCount()).isEmpty()){
                continue;
            }
            result += "- "+crew.printWarningInfo(date) + "\n";
        }
        return result;
    }

    public void updateAbsentHistory(LocalDate date) {
        allCrew.stream().forEach(crew -> {
            crew.updateUntil(date);
        });
    }
}
