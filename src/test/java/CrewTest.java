import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class CrewTest {

    @DisplayName("크루는 출석을 할 수 있다")
    @Test
    void 크루는_출석을_할_수_있다() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 8);
        crew.attend(localDateTime);
        assertThat(crew.getLocalDateTimeList().size()).isEqualTo(1);
    }
}
