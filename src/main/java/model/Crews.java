package model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Crews {
    private final List<Crew> crews;

    public static Crews from(List<String> combinedData) {
        List<String> uniqueCrewNames = extractUniqueCrewData(combinedData);
        List<Crew> crews = uniqueCrewNames.stream()
                .map(Crew::new)
                .toList();
        return new Crews(crews);
    }

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    //TODO : private으로
    public static List<String> extractUniqueCrewData(List<String> combinedData) {
        return combinedData.stream()
                .map(data -> data.split(",")[0])
                .distinct()
                .toList();
    }

    public Optional<Crew> findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.equals(new Crew(name)))
                .findAny();
        //TODO : getName대신 이거 써도 되나..? 메모리에반데
    }

    public List<Crew> getCrews() {
        return Collections.unmodifiableList(crews);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Crews targetCrews)) {
            return false;
        }
        return crews.containsAll(targetCrews.crews)
                && targetCrews.crews.containsAll(crews);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(crews);
    }
}
