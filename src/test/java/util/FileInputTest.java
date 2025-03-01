package util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileInputTest {
    String regex = "[가-힣]+,\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";

    @Test
    @DisplayName("파일에 있는 자료의 형식이 맞지 않으면 예외 던지는 메서드 테스트")
    void test1() {
        String information = "쿠키,,2024-12-02 13:01";
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> information.matches(regex))
                        .isInstanceOf(IllegalArgumentException.class)
                                .hasMessage("[ERROR] 파일 자료 형식에 맞지 않습니다.");
    }

    @Test
    @DisplayName("파일에 있는 자료의 형식이 맞으면 통과하는 테스트")
    void test2() {
        String information = "쿠키,2024-12-02 13:01";
        Assertions.assertTrue(information.matches(regex));
    }
}