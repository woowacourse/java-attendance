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
        return crew.addAttendance(dateTime).getFormattedAttended();
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
        return allCrew.stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 이름의 크루를 찾을 수 없습니다."));
    }

    public String printAllCrewWarningInfo(LocalDate date) {
        updateAbsentHistory(date);
        allCrew.sort(
                Comparator.comparing(Crew::getAbsentCount).reversed()
                        .thenComparing(Crew::getName)
        );
        String result = "";
        for (Crew crew : allCrew) {
            result = scanWarningCrew(date, crew, result);
        }
        return result;
    }

    private static String scanWarningCrew(LocalDate date, Crew crew, String result) {
        if (crew.calculateWarningStatus(crew.getAbsentCount()).isEmpty()) {
            return "";
        }
        result += "- " + crew.printWarningInfo(date) + "\n";
        return result;
    }

    public void updateAbsentHistory(LocalDate date) {
        allCrew.forEach(crew -> crew.updateUntil(date));
    }
}
