import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Crews {
    private final List<Crew> crews;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Crews(List<String> crewAttendanceResource) {
        crews = new ArrayList<>();
        crewAttendanceResource.stream().map(crew -> crew.split(",")
        ).forEach(crew -> addCrew(crew[0], crew[1]));
    }

    private void addCrew(String nickname, String date) {
        Crew crew = findCrewByNickname(nickname).orElse(null);
        if (crew != null) {
            crew.attend(LocalDateTime.parse(date, FORMATTER));
            return;
        }
        crews.add(new Crew(nickname, LocalDateTime.parse(date, FORMATTER)));
    }

    public AttendTime addCrewAttendance(String nickname, String time) {
        Crew crew = findCrewByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("없는 닉네임입니다."));
        return crew.attend(LocalDateTime.parse(time, FORMATTER));

    }

    public Optional<Crew> findCrewByNickname(String nickname) {
        return crews.stream().filter(crew -> crew.getNickname().equals(nickname)).findFirst();
    }

    public List<Crew> findDismissalCrews() {
        return new ArrayList<Crew>(crews.stream().filter(Crew::isDismissalCrew).toList());
    }

    public List<Crew> getCrews() {
        return crews;
    }
}
