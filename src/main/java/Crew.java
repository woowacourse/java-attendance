import java.time.LocalDate;
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

    public void existInAttendances(LocalDate date) {
        for (Attendance attendance : attendances) {
            if(attendance.isEqualToDate(date)) {
                throw new IllegalArgumentException("[ERROR] 이미 오늘 출석을 하셨습니다. 출석 수정을 이용해주세요.");
            }
        }
    }

    public String getNickname() {
        return nickname;
    }
}
