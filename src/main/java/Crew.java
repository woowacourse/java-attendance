import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {
    private final String nickname;
    private List<LocalDateTime> localDateTimeList;

    public Crew(String nickname) {
        this.nickname = nickname;
        this.localDateTimeList = new ArrayList<>();
    }

    public Crew(String nickname, LocalDateTime localDateTime) {
        this.nickname = nickname;
        this.localDateTimeList = new ArrayList<>();
        localDateTimeList.add(localDateTime);
    }

    public void attend(LocalDateTime localDateTime) {
        localDateTimeList.add(localDateTime);
    }

    public String getNickname() {
        return nickname;
    }

    public List<LocalDateTime> getLocalDateTimeList() {
        return localDateTimeList;
    }
}
