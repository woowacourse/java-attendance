package attendance.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Crews {

    private final Set<Crew> crews = new HashSet<>();

    public void add(Crew crew) {
        crews.add(crew);
    }

    public Crew getByNickName(String nickName) {
        return crews.stream()
            .filter(crew -> crew.getNickName().equals(nickName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("\n[ERROR] 등록되지 않은 닉네임입니다."));
    }

    public List<Crew> findAll() {
        return List.copyOf(crews);
    }
}
