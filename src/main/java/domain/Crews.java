package domain;

import static domain.DangerousTarget.LATE_RATE_OF_ABSENT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return crews.stream().filter(Crew::isDismissalCrew).collect(Collectors.toList());
    }

    public List<Crew> findDismissalCrewsByImportance() {
        List<Crew> crewList = findDismissalCrews();
        Comparator<Crew> comparator = new Comparator<Crew>() {

            @Override
            public int compare(Crew crew1, Crew crew2) {

                int crew1Counts = crew1.getCrewLateCount() / LATE_RATE_OF_ABSENT + crew1.getCrewAbsentCount();
                int crew2Counts = crew2.getCrewLateCount() / LATE_RATE_OF_ABSENT + crew2.getCrewAbsentCount();

                if (crew1Counts == crew2Counts) {
                    int crew1Late = crew1.getCrewLateCount() % LATE_RATE_OF_ABSENT;
                    int crew2Late = crew2.getCrewLateCount() % LATE_RATE_OF_ABSENT;
                    if (crew1Late == crew2Late) {
                        return crew1.getNickname().compareTo(crew2.getNickname());
                    }
                    return crew2Late - crew1Late;
                }
                return crew2Counts - crew1Counts;
            }
        };

        return crewList.stream().sorted(comparator).collect(Collectors.toList());
    }

    public List<Crew> getCrews() {
        return crews;
    }

}
