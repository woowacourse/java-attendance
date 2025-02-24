package attendance.model.crew;

import java.util.Objects;

public class Crew {

    private final String nickName;

    public Crew(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(nickName, crew.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickName);
    }

    @Override
    public String toString() {
        return "Crew{" +
                "nickName='" + nickName + '\'' +
                '}';
    }
}
