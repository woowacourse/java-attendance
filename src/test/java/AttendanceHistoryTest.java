import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceHistory;
import domain.AttendanceRecord;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("새로운 출석 기록을 생성하여 저장한다.")
        @Test
        public void attendance() throws Exception {
            // given
            final var attendanceHistory = new AttendanceHistory();
            final var attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 5);

            // when
            final var actual = attendanceHistory.attendance(attendanceDateTime);

            // then
            assertThat(actual.getDateTime())
                    .hasYear(2024)
                    .hasMonthValue(12)
                    .hasDayOfMonth(13)
                    .hasHour(10)
                    .hasMinute(5);
        }
    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {
    }
}
