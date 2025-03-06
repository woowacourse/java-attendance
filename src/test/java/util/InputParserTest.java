package util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputParserTest {

    @Test
    @DisplayName("입력 값에 왼쪽 공백이 있을 경우 공백을 제거한다.")
    void test1() {
        // given
        String input = " ABC";

        // when
        String result = InputParser.trim(input);

        // then
        String expected = "ABC";
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("입력 값에 오른쪽 공백이 있을 경우 공백을 제거한다.")
    void test2() {
        // given
        String input = "ABC ";

        // when
        String result = InputParser.trim(input);

        // then
        String expected = "ABC";
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("입력 값에 양쪽 공백이 있을 경우 공백을 제거한다.")
    void test3() {
        // given
        String input = " ABC ";

        // when
        String result = InputParser.trim(input);

        // then
        String expected = "ABC";
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("입력 값에 중간에 공백이 있는 경우는 공백을 제거하지 않는다.")
    void test4() {
        // given
        String input = " AB C ";

        // when
        String result = InputParser.trim(input);

        // then
        String expected = "AB C";
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("입력 값을 분리한다.")
    void test5() {
        // given
        String input = "AB C";

        // when
        List<String> result = InputParser.split(input, " ");

        // then
        String expected1 = "AB";
        String expected2 = "C";
        assertAll(
                () -> assertThat(result.get(0)).isEqualTo(expected1),
                () -> assertThat(result.get(1)).isEqualTo(expected2)
        );
    }
}
