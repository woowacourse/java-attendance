package view;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import domain.AllCrew;
import domain.Crew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputValidatorTest {
    @DisplayName("기능 선택을 위해 [1, 2, 3, 4, Q(q)]의 문자를 입력할 시 정상 동작한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "Q", "q"})
    void test1(String menu) {
        assertDoesNotThrow(() -> InputValidator.validateMenu(menu));
    }

    @DisplayName("기능 선택을 위해 [1, 2, 3, 4, Q(q)]의 문자를 입력할 시 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"5", "a", "2200000000", "", " "})
    void test2(String menu) {
        assertThatThrownBy(() -> InputValidator.validateMenu(menu))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("존재하는 닉네임을 입력할 시 정상 동작한다.")
    @Test
    void test3() {
        String name = "미미";

        AllCrew allCrew = new AllCrew();
        allCrew.addCrew(new Crew(name));

        assertDoesNotThrow(() -> InputValidator.validateName(name, allCrew));
    }

    @DisplayName("없는 닉네임을 입력할 시 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"없는이름", "", " "})
    void test4() {
        AllCrew allCrew = new AllCrew();

        assertThatThrownBy(() -> InputValidator.validateName("없는 이름", allCrew));
    }

    @DisplayName("시간을 hh:mm의 포맷에 맞게 입력한 경우 정상 동작한다.")
    @ParameterizedTest
    @ValueSource(strings = {"09:59", "13:00"})
    void test5(String time) {
        assertDoesNotThrow(() -> InputValidator.validateTimeFormat(time));
    }

    @DisplayName("시간을 hh:mm의 포맷에 맞지 않게 입력한 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"9:59", "", " "})
    void test6(String time) {
        assertThatThrownBy(() -> InputValidator.validateTimeFormat(time));
    }
}
