import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendanceRecordTest {
    @DisplayName("LocalDateTime을 받아 기록 객체를 생성할 수 있다.")
    @Test
    void instanceTest() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 0);

        // then
        assertThat(new AttendanceRecord(dateTime)).isInstanceOf(AttendanceRecord.class);
    }

    @DisplayName("출석을 기록하려는 날짜가 등교일이 아닐 경우 예외가 발생한다.")
    @Test
    void validateDateTest() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 25, 10, 0);

        // then
        assertThatThrownBy(() -> new AttendanceRecord(dateTime)).isInstanceOf(IllegalArgumentException.class);
    }
}
