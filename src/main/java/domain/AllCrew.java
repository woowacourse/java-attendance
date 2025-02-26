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

    public boolean isContainedCrewName(String crewName) {
        return allCrew.stream()
                .anyMatch(crew -> crew.isSameName(crewName));
    }

    public Attendance addCrewAttendanceByName(String name, LocalDateTime dateTime) {
        Crew crew = findCrewByName(name);
        return crew.addAttendance(dateTime);
    }

    public AttendanceUpdateResult modifyCrewAttendanceByName(String name, LocalDateTime dateTime) {
        Crew crew = findCrewByName(name);
        return crew.update(dateTime);
    }

    public Crew findCrewByName(String name) {
        return allCrew.stream()
                .filter(crew -> crew.isSameName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루입니다."));
    }

    public void sortAllCrewOrderByWarningInfo() {
        allCrew.sort(new Comparator<Crew>() {
            @Override
            public int compare(Crew o1, Crew o2) {
                // 내림차순 정렬
                int result =
                        (o2.getAbsentCount() + (o2.getLateCount() / 3)) - (o1.getAbsentCount() + (o1.getLateCount() / 3));
                if (result != 0) {
                    return result;
                }
                return o1.getName().compareTo(o2.getName());    // 같으면 이름순 정렬
            }
        });
    }

    public void updateAbsentHistory(LocalDate date) {
        allCrew.forEach(crew -> crew.updateAbsentUntil(date));
    }

    public List<Crew> getAllAbsentPenaltyReceivedCrew() {
        List<Crew> allWarningCrew = new ArrayList<>();
        for (Crew crew : allCrew) {
            if (crew.getAbsentPenalty() != AbsentPenalty.NONE){
                allWarningCrew.add(crew);
            }
        }
        return allWarningCrew;
    }
}
