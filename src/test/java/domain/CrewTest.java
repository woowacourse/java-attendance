package domain;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CrewTest {

    @Test
    void 크루의_이름은_2글자에서_4글자_이내여야_한다() {
        final String name = "시소";

        assertThatNoException()
                .isThrownBy(() -> new Crew(name));
    }
}