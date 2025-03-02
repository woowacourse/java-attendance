import java.util.ArrayList;
import java.util.List;

public class AttendanceManager {

    private List<Crew> crews;

    public AttendanceManager() {
        this.crews = new ArrayList<>();
    }

    public void attend(String nickname, String time) {
        if (crews.stream()
                .noneMatch(crew -> crew.getNickname().equals(nickname))) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }
}
