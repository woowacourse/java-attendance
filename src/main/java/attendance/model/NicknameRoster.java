package attendance.model;

import java.util.Collection;
import java.util.Set;

public class NicknameRoster {

    private final Set<Nickname> nicknames;

    public NicknameRoster(Collection<Nickname> nicknames) {
        this.nicknames = Set.copyOf(nicknames);
    }

    public Set<Nickname> getNicknames() {
        return nicknames;
    }

    public boolean isMissing(Nickname nickname) {
        return !nicknames.contains(nickname);
    }
}
