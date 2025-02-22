package view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import controller.dto.AttendanceTimeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ParserTest {
    @ParameterizedTest
    @ValueSource(strings = {"1", "12", "1:2:3"})
    @DisplayName("시간 형식이 올바르지 않은 경우 예외를 던진다")
    void testParseAttendanceTimeThrowsException(String value) {
        assertThatIllegalArgumentException().isThrownBy(() -> Parser.parseAttendanceTime(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11:30", "12:45", "23:30"})
    @DisplayName("시간 형식이 올바른 경우 예외를 던지지 않는다")
    void testParseAttendanceTimeDoesNotThrowException(String value) {
        assertThatNoException().isThrownBy(() -> Parser.parseAttendanceTime(value));
    }

    @Test
    @DisplayName("시간을 원하는 형태로 변경한다")
    void testParseAttendanceTime() {
        // given
        String input = "11:33";

        // when
        AttendanceTimeDto attendanceTimeDto = Parser.parseAttendanceTime(input);

        // then
        assertAll(
                () -> assertThat(attendanceTimeDto.hour()).isEqualTo(11),
                () -> assertThat(attendanceTimeDto.minute()).isEqualTo(33)
        );
    }

    @Test
    @DisplayName("날짜를 원하는 형태로 변경한다")
    void testParseDateFormat() {
        // given
        int month = 8;
        int day = 1;

        // when
        String result = Parser.parseDateFormat(month, day);

        // then
        assertThat(result).isEqualTo("08월 01일");
    }

    @Test
    @DisplayName("시간을 원하는 형태로 변경한다")
    void testParseTimeFormat() {
        // given
        int hour = 8;
        int minute = 1;

        // when
        String result = Parser.parseTimeFormat(hour, minute);

        // then
        assertThat(result).isEqualTo("08:01");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1*", "sldkfn", ",,"})
    @DisplayName("올바르지 않은 정수 형식인 경우 예외를 던진다")
    void testParseIntegerThrowsException(String input) {
        // when & then
        assertThatThrownBy(() -> Parser.parseInteger(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
