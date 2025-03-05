package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CampusTimeTest {

    @DisplayName("등교시간이 캠퍼스 운영시간이 아니라면 예외가 발생한다.")
    @ParameterizedTest
    @MethodSource("attendanceTimeOutCampusTime")
    void attendanceTimeIsNotInCampusTime(LocalTime attendanceTime) {
        //given

        //when //then
        assertThatThrownBy(() -> CampusTime.validateInTime(attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("캠퍼스 운영시간이 아닙니다.");
    }

    @DisplayName("등교시간이 캠퍼스 운영시간이 아니라면 예외가 발생한다.")
    @ParameterizedTest
    @MethodSource("attendanceTimeInCampusTime")
    void attendanceTimeInCampusTime(LocalTime attendanceTime) {
        //given

        //when //then
        assertThatCode(() -> CampusTime.validateInTime(attendanceTime))
                .doesNotThrowAnyException();
    }


    static Stream<Arguments> attendanceTimeOutCampusTime() {
        return Stream.of(
                Arguments.of(LocalTime.of(7, 59)),
                Arguments.of(LocalTime.of(23, 1))
        );
    }

    static Stream<Arguments> attendanceTimeInCampusTime() {
        return Stream.of(
                Arguments.of(LocalTime.of(8, 0)),
                Arguments.of(LocalTime.of(23, 0))
        );
    }

}
