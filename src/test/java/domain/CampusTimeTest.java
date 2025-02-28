package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CampusTimeTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("캠퍼스 운영 시간인지 확인 한다.")
    void test1(final LocalDateTime dateTime, final boolean expected) {
        //should
        assertThat(CampusTime.isOpenTime(dateTime)).isEqualTo(expected);
    }

    private static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 13, 8, 0), true),
                Arguments.of(LocalDateTime.of(2024, 12, 13, 7, 59), false),
                Arguments.of(LocalDateTime.of(2024, 12, 13, 23, 0), true),
                Arguments.of(LocalDateTime.of(2024, 12, 13, 23, 1), false));
    }


}
