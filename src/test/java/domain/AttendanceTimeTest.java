package domain;

import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceTimeTest {

    @DisplayName("등교시간이 캠퍼스 운영 시간이 아니라면 예외를 발생시킨다.")
    @ParameterizedTest
    @MethodSource("boundaryProvider")
    void campusTime(LocalTime localTime) {

        //when & then
        Assertions.assertThatThrownBy(() -> AttendanceTime.validateCampusTime(localTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("캠퍼스 운영 시간은 매일 08:00~23:00입니다.");
    }

    static Stream<Arguments> boundaryProvider() {
        return Stream.of(
                Arguments.of(LocalTime.of(7, 59)),
                Arguments.of(LocalTime.of(23, 1))
        );
    }

}
