import java.util.List;

public class Crews {

    private final List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public Crew findByName(String nickname) {
        for (Crew crew : crews) {
            if (crew.isEqualToNickname(nickname))
                return crew;
        }
        throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    public int size() {
        return crews.size();
    }
}
