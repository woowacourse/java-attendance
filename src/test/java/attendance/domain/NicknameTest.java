package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NicknameTest {

    @Test
    void 닉네임을_생성한다() {
        // Given

        // When & Then
        Assertions.assertThatCode(() -> new Nickname("밍트"))
                .doesNotThrowAnyException();
    }
}
