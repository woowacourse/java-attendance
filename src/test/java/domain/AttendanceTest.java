package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.constants.ErrorMessage;
import java.time.Month;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("yyyy-MM-dd HH:mm 형식의 문자열을 기준으로 Attendance가 올바르게 생성된다.")
        @Test
        public void ofDateTimeText() throws Exception {
            // given
            final String dateTimeText = "2024-12-10 10:00";

            // when
            final Attendance actual = Attendance.of(dateTimeText);

            // then
            assertThat(actual.getDateTime())
                    .hasYear(2024)
                    .hasMonth(Month.DECEMBER)
                    .hasDayOfMonth(10)
                    .hasHour(10)
                    .hasMinute(0);
        }
    }


    @Nested
    @DisplayName("실패 테스트")
    class FailCases {

        @DisplayName("yyyy-MM-dd HH:mm 형식이 올바르지 않다면 예외가 발생한다.")
        @Test
        public void ofDateTimeText() throws Exception {
            // given
            final String invalidFormatDateTime = "asdasdas";

            // when & then
            assertThatThrownBy(() -> {
                Attendance.of(invalidFormatDateTime);
            })
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ErrorMessage.INVALID_DATE_FORMAT.getMessage());

        }

    }

}
