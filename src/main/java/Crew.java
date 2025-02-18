import java.util.List;

public class Crew {

    private String nickname;
    private List<Attendance> attendances;

    public Crew(String nickname, List<Attendance> attendances) {
        this.nickname = nickname;
        this.attendances = attendances;
    }

    public boolean isEqualToNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public String getNickname() {
        return nickname;
    }
}
