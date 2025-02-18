package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Crew {
    private final String nickName;
    private final List<Attandance> attendance = new ArrayList<>();

    public Crew(String nickName) {
        this.nickName = nickName;
    }

    public boolean isSameNickname(String nickName){
        return this.nickName.equals(nickName);
    }
}
