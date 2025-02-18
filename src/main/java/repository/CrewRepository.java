package repository;

import domain.Crew;

import java.util.Optional;

public interface CrewRepository {
    void save(Crew crew);

    Optional<Crew> findByName(String name);
}
