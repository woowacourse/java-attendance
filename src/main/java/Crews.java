import java.util.ArrayList;
import java.util.List;

public class Crews {

    private final List<Crew> crews = new ArrayList<>();

    public void add(Crew crew) {
        crews.add(crew);
    }

    public Crew get(String name) {
        return crews.stream()
            .filter(crew -> crew.getName().equals(name))
            .findAny()
            .orElse(null); // TODO 예외처리?
    }
}
