package repository;

import domain.crew.Crew;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrewRepositoryImpl implements CrewRepository {
    private final List<Crew> crews;

    public CrewRepositoryImpl() {
        this.crews = new ArrayList<>();
    }

    @Override
    public void save(Crew crew) {
        crews.add(crew);
    }

    @Override
    public Optional<Crew> findByName(String name) {
        return crews.stream().filter(crew -> crew.getName().equals(name)).findAny();
    }
}
