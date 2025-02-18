import java.util.ArrayList;
import java.util.List;

public class CrewRepository {
    private final List<Crew> crews;

    public CrewRepository() {
        this.crews = new ArrayList<>();
    }

    public void save(Crew crew) {
        crews.add(crew);
    }
}
