import java.util.Optional;

public interface CrewRepository {
    void save(Crew crew);

    Optional<Crew> findByName(String name);
}
