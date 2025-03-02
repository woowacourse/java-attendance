import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.DateProvider;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttendanceManagerTest {

    private LocalDate weekday;
    private LocalDate weekend;

    @BeforeEach
    void setUp() {
        weekday = LocalDate.of(2024, 12, 13);
        weekend = LocalDate.of(2024, 12, 14);
    }

    @Test
    void 등록되지_않은_닉네임의_출석을_등록하면_예외가_발생한다() {
        assertThatThrownBy(() -> new AttendanceManager(() -> weekday).attend("이든", "09:59"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    void 크루_출석_기록을_추가할_때_크루가_있으면_시간만_추가한다() {
        AttendanceManager attendanceManager = new AttendanceManager(() -> weekday);

        attendanceManager.addCrew("이든", LocalDateTime.of(2024, 12, 13, 9, 59));
        attendanceManager.addCrew("이든", LocalDateTime.of(2024, 12, 14, 9, 59));
        assertThat(attendanceManager.getCrewSize()).isEqualTo(1);
    }

    @Test
    void 크루_출석_기록을_추가할_때_크루가_없으면_크루를_추가한다() {
        AttendanceManager attendanceManager = new AttendanceManager(() -> weekday);

        attendanceManager.addCrew("이든", LocalDateTime.of(2024, 12, 13, 9, 59));
        attendanceManager.addCrew("쿠키", LocalDateTime.of(2024, 12, 13, 9, 59));
        assertThat(attendanceManager.getCrewSize()).isEqualTo(2);
    }


    class AttendanceManager {

        private final DateProvider dateProvider;
        private List<Crew> crews;

        public AttendanceManager(DateProvider dateProvider) {
            this.crews = new ArrayList<>();
            this.dateProvider = dateProvider;
        }

        public void attend(String nickname, String time) {
            if (!existsByNickname(nickname)) {
                throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
            }
//            Crew crew = findCrewByNickname(nickname);
//            crew.addAttendanceTime(time);
        }

        private boolean existsByNickname(String nickname) {
            return crews.stream()
                    .anyMatch(crew -> crew.getNickname().equals(nickname));
        }

        public int getCrewSize() {
            return crews.size();
        }

        public void addCrew(String nickname, LocalDateTime time) {
            findCrewByNickname(nickname)
                    .ifPresentOrElse(
                            crew -> crew.addAttendanceTime(time),
                            () -> crews.add(new Crew(nickname, time.toLocalTime(), dateProvider))
                    );
        }

        private Optional<Crew> findCrewByNickname(String nickname) {
            return crews.stream()
                    .filter(crew -> crew.getNickname().equals(nickname))
                    .findAny();
        }
    }

}
