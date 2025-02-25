package domain.attendance;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import exception.ErrorException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceLogTest {

    @ParameterizedTest
    @CsvSource({"21", "22", "25"})
    @DisplayName("출석 날짜 예외 테스트")
    void 출석_날짜_예외_테스트(int day) {
        // given
        LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, day, 10, 0);
        // when & then
        assertThatThrownBy(() -> new AttendanceLog(attendDateTime))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }

    @ParameterizedTest
    @CsvSource(value = {"07:59:59", "23:00:00"}, delimiterString = ":")
    @DisplayName("출석 날짜 예외 테스트")
    void 출석_시간_예외_테스트(int hour, int minute, int second) {
        // given
        LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 23, hour, minute, second);
        // when & then
        assertThatThrownBy(() -> new AttendanceLog(attendDateTime))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }
}
