package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsTest {
    private Crews crews;

    @BeforeEach
    void setUp() {
        crews = new Crews();
        crews.initCrews(List.of(
                List.of("쿠키", "2025-02-19 10:34"),
                List.of("쿠키", "2025-02-18 10:00"),
                List.of("빙봉", "2025-02-18 10:01"),
                List.of("쿠키", "2025-02-17 13:03"),
                List.of("빙티", "2025-02-17 13:04"),
                List.of("쿠키", "2025-02-14 10:02"),
                List.of("쿠키", "2025-02-13 10:07"),
                List.of("짱수", "2025-02-13 10:08")
        ));
    }

    @Test
    @DisplayName("기능: 크루 목록 초기화 후 크루 수 확인")
    void checkCrewsInitialization() {
        assertThat(crews.getCrews().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("기능: 이름으로 존재하는 크루 조회 확인")
    void checkFindExistingCrew() {
        assertThat(crews.findCrew("빙티")).isEqualTo(new Crew("빙티"));
    }

    @Test
    @DisplayName("예외: 이름으로 존재하지 않는 크루 조회 시 예외 발생")
    void checkFindNonExistingCrew() {
        assertThatThrownBy(() -> crews.findCrew("초코"))
                .isInstanceOf(IllegalStateException.class);
    }
}
