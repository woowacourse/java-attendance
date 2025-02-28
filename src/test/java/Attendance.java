import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {

    private final String nickname;
    private final LocalDate attendDate;
    private LocalTime attendTime;

    public Attendance(String nickname, LocalDate attendDate, LocalTime attendTime) {
        this.nickname = nickname;
        this.attendDate = attendDate;
        this.attendTime = attendTime;
    }
}
