import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceRecordTest {
    @DisplayName("LocalDateTime을 받아 기록 객체를 생성할 수 있다.")
    @Test
    void instanceTest() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 0);

        // then
        assertThat(new AttendanceRecord(dateTime)).isInstanceOf(AttendanceRecord.class);
    }
}
