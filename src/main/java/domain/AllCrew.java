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

    public Attendance addCrewAttendanceByName(String name, LocalDateTime dateTime) {
        Crew crew = findCrewByName(name);
        return crew.addAttendance(dateTime);
    }

    public List<Attendance> modifyCrewAttendanceByName(String name, LocalDateTime dateTime) {
        Crew crew = findCrewByName(name);
        return crew.update(dateTime);
    }

    public Crew getUpdatedCrew(String name, LocalDate lastDate) {
        Crew crew = findCrewByName(name);
        crew.updateUntil(lastDate);
        return crew;
    }

    private Crew findCrewByName(String name) {
        return allCrew.stream()
                .filter(crew -> crew.isSameName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Crew not found"));
    }

    public void sortAllCrewOrderByWarningInfo(LocalDate date) {
        updateAbsentHistory(date);
        allCrew.sort(new Comparator<Crew>() {
            @Override
            public int compare(Crew o1, Crew o2) {
                int result =
                        (o1.getAbsentCount() + o1.getLateCount() / 3) - (o2.getAbsentCount() + o2.getLateCount() / 3);
                if (result != 0) {
                    return result;
                }
                return o2.getName().compareTo(o1.getName());    // 같으면 이름 역순 정렬
            }
        });
    }

    public void updateAbsentHistory(LocalDate date) {
        allCrew.forEach(crew -> crew.updateAbsentUntil(date));
    }

    public List<Crew> getAllWarningCrew() {
        List<Crew> allWarningCrew = new ArrayList<>();
        for (Crew crew : allCrew) {
            if (crew.calculateWarningStatus().isEmpty()){
                allWarningCrew.add(crew);
            }
        }
        return allWarningCrew;
    }
}
