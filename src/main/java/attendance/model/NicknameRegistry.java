package attendance.model;

import java.util.Collection;
import java.util.Set;

public class NicknameRegistry {

    private final Set<Nickname> nicknames;

    public NicknameRegistry(Collection<Nickname> nicknames) {
        this.nicknames = Set.copyOf(nicknames);
    }

    public Set<Nickname> getNicknames() {
        return nicknames;
    }

    public boolean isRegistered(Nickname nickname) {
        return nicknames.contains(nickname);
    }
}
