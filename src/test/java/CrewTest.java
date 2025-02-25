import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CrewTest {

    @DisplayName("크루는 출석을 할 수 있다")
    @Test
    void 크루는_출석을_할_수_있다() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 8);
        crew.attend(localDateTime);
        assertThat(crew.getAttendTimes().size()).isEqualTo(1);
    }
    @DisplayName("크루는 주말이나 휴일에 출석을 할 수 없다")
    @Test
    void 크루는_주말이나_휴일에_출석을_할_수_없다() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 15, 10, 8);
        assertThatThrownBy(()->crew.attend(localDateTime)).isInstanceOf(IllegalArgumentException.class);

    }
}
