package domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceManager {

    private final DateProvider dateProvider;
    private List<Crew> crews;

    public AttendanceManager(DateProvider dateProvider) {
        this.crews = new ArrayList<>();
        this.dateProvider = dateProvider;
    }

    public LocalDateTime attend(String nickname, LocalTime todayTime) {
        return findCrewByNickname(nickname)
                .map(crew -> crew.attend(todayTime))
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }

    public void addCrew(String nickname, LocalDateTime attendanceTime) {
        findCrewByNickname(nickname)
                .ifPresentOrElse(
                        crew -> crew.addAttendanceTime(attendanceTime),
                        () -> crews.add(new Crew(nickname, attendanceTime, dateProvider))
                );
    }

    public int getCrewSize() {
        return crews.size();
    }

    public Crew findCrewExactlyByNickname(String nickname) {
        return findCrewByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));
    }

    private Optional<Crew> findCrewByNickname(String nickname) {
        return crews.stream()
                .filter(crew -> crew.getNickname().equals(nickname))
                .findAny();
    }

    public List<Crew> getCrews() {
        return crews;
    }
}
