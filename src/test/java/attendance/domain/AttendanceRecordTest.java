package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {

    @Nested
    class InvalidCases {

        @Test
        void 출석_기록은_기록을_가지지않는다면_기록하지않는다() {
            // when & then
            assertThatThrownBy(() -> new AttendanceRecord(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록은 기록을 가지고 있어야 합니다.");
        }

        @Test
        void 출석_기록은_출석날짜와_출석시간을_가지지않는다면_기록하지않는다() {
            // given
            Map<AttendanceDate, AttendanceTime> attendanceRecord = new HashMap<>();
            attendanceRecord.put(null, null);

            // when & then
            assertThatThrownBy(
                () -> new AttendanceRecord(attendanceRecord))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록은 출석 날짜와 출석 시간을 가지고 있어야 합니다.");
        }
    }
}
