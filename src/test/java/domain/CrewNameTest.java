package domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static util.Constants.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CrewNameTest {
    @DisplayName("닉네임 자릿수가 유효할 경우 정상적으로 검증을 마친다.")
    @ParameterizedTest
    @ValueSource(strings = {"미미", "미미미미"})
    void test7(String validName) {
        assertDoesNotThrow(
                () -> new CrewName(validName));
    }

    @DisplayName("닉네임이 1자 이하, 5자 이상일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"미", "미미미미미"})
    void test2(String invalidName) {
        assertThatThrownBy(() -> new CrewName(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }
}
