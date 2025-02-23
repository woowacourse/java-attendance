 package attendance.domain;

 import java.time.LocalDate;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Set;

 public class CrewManager {
     private final Set<Crew> crews = new HashSet<>();

    public boolean addCrew(Crew crew) {
        return crews.add(crew);
    }

    public Crew findByCrewName(String name){
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 이름을 가진 크루는 없음"));
    }

    public List<Crew> getDangerousCrews(LocalDate today) {
        return crews.stream()
                .filter(crew -> crew.isCrewStatusNotClear(today))
                .toList();
    }
}
