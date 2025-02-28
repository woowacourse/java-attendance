import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private final String nickname;
    private final LocalDate attendDate;
    private LocalTime attendTime;

    public Attendance(String nickname, LocalDate attendDate, LocalTime attendTime) {
        this.nickname = nickname;
        this.attendDate = attendDate;
        this.attendTime = attendTime;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Attendance that = (Attendance) object;
        return Objects.equals(nickname, that.nickname) && Objects.equals(attendDate, that.attendDate)
                && Objects.equals(attendTime, that.attendTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, attendDate, attendTime);
    }
}
