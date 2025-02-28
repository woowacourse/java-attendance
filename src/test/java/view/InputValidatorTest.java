package view;

import static org.assertj.core.api.Assertions.*;
import static util.Constants.ERROR_HEADER;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputValidatorTest {
    @DisplayName("선택한 메뉴가 존재하지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "" , " "})
    void test4(String invalidSelectedMenu) {
        assertThatThrownBy(() -> InputValidator.validateSelectedMenu(invalidSelectedMenu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }


    @DisplayName("시간 형식이 올바르지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"9:59", "", " ", "a", "1", "10:3", "10;10"})
    void test1(String invalidTimeFormat) {
        assertThatThrownBy(() -> InputValidator.validateTime(invalidTimeFormat))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }

    @DisplayName("닉네임이 1자 이하, 5자 이상일 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"미", "미미미미미", "", " "})
    void test2(String invalidNameFormat) {
        assertThatThrownBy(() -> InputValidator.validateName(invalidNameFormat))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }

    @DisplayName("날짜 형식이 올바르지 않을 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "32", "0"})
    void test3(String invalidDayFormat) {
        LocalDate today = LocalDate.of(2024, 12, 13);

        assertThatThrownBy(() -> InputValidator.validateDay(today, invalidDayFormat))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }
}
