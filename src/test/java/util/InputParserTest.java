package util;

import static org.assertj.core.api.Assertions.assertThat;

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
}
