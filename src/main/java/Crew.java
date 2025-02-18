import java.util.List;

public class Crew {

    private String nickname;
    private List<String> attendances;

    public Crew(String nickname, List<String> attendances) {
        this.nickname = nickname;
        this.attendances = attendances;
    }

    public boolean isEqualToNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public List<String> getAttendances() {
        return attendances;
    }

    public String getNickname() {
        return nickname;
    }
}
