package domain;

import exception.NotOperatingTimeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceTimeTest {
    @DisplayName("캠퍼스 운영 시간은 오전 8시 ~ 오후 11시이다.")
    @ParameterizedTest
    @CsvSource(value = {"8, 0", "23, 0"})
    void test1(int hour, int minute) {
        // given
        LocalTime time = LocalTime.of(hour, minute);

        // when & then
        assertDoesNotThrow(() -> {
            new AttendanceTime(time);
        });
    }

    @DisplayName("캠퍼스 운영 시간 이외의 시간은 설정할 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"7, 59", "23, 1"})
    void test2(int hour, int minute) {
        // given
        LocalTime time = LocalTime.of(hour, minute);

        // when & then
        assertThatThrownBy(() -> {
            new AttendanceTime(time);
        }).isInstanceOf(NotOperatingTimeException.class);
    }
}
