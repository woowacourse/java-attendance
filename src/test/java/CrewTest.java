import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    void 크루의_출석을_등록한다() {
        String nickname = "이든";
        LocalTime time = LocalTime.of(9, 59);

        Crew crew = new Crew(nickname, time,
                () -> LocalDate.of(2024, 12, 13));

        assertThat(crew.getNickname()).isEqualTo(nickname);
    }


}
