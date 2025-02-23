package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import error.CustomIllegalArgumentException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceTest {

    @Test
    void 출석이_정상적으로_실행() {
        // given
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 0, 0, 0);

        // when
        // then
        assertThatCode(() -> new Attendance(localDateTime))
                .doesNotThrowAnyException();
    }

    @Test
    void 정적팩터리_메서드에서_객체_정상_생성() {
        // given
        String input = "2024-12-12 11:11";
        LocalDateTime expected = LocalDateTime.of(2024, 12, 12, 11, 11);

        // when
        Attendance attendance = Attendance.of(input);

        // then
        assertThat(attendance.getLocalDateTime()).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2024-12-12 11", "바보", "2024-12-32 11:12", "2024-12-11 99:12"})
    void 잘못된_포멧으로_입력했을_때_예외_처리(String input) {
        // given
        // when
        // then
        assertThatThrownBy(() -> Attendance.of(input))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }
}
