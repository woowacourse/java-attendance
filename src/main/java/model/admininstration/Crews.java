package model.admininstration;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import model.attendance.Crew;
import model.exception.SystemException;

public class Crews {
    private final List<Crew> crews;

    public static Crews from(List<String> crewNames) {
        List<Crew> crews = crewNames.stream()
                .map(Crew::new)
                .toList();
        return new Crews(crews);
    }

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public Optional<Crew> findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .findAny();
    }

    public <T> Map<Crew, T> mapCrewWithNameIn(Map<String, T> target) {
        Map<Crew, T> mapped = new HashMap<>();
        target.keySet().forEach(crewName -> {
            mapped.put(findCrewByName(crewName)
                            .orElseThrow(SystemException::new),
                    target.get(crewName)
            );
        });
        return mapped;
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
