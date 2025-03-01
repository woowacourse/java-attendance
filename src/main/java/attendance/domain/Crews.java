package attendance.domain;

import java.util.HashSet;
import java.util.Set;

public class Crews {

    private final Set<String> nicknames;

    public Crews(String... nicknames) {
        this(new HashSet<>(Set.of(nicknames)));
    }

    public Crews(Set<String> nicknames) {
        this.nicknames = nicknames;
    }

    public boolean contains(String nickname) {
        return nicknames.contains(nickname);
    }
}
