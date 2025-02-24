package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.constants.AttendanceStatus;
import domain.constants.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        @DisplayName("LocalDateTime을 받아 생성할때는 isEmpty가 false로 초기화된다.")
        @Test
        public void ofLocalDateTime() throws Exception {
            // given
            final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 0);

            // when
            final Attendance actual = Attendance.of(dateTime);

            // then
            assertThat(actual.isEmpty()).isFalse();
        }

        @DisplayName("비어있는 Attendance는 올바르게 생성된다.")
        @Test
        public void empty() throws Exception {
            // given
            final LocalDate date = LocalDate.of(2024, 12, 10);

            // when
            final Attendance actual = Attendance.empty(date);

            // then
            assertThat(actual.isEmpty()).isTrue();
        }

        @DisplayName("'출석' 상태를 올바르게 계산한다.")
        @Test
        public void calculateStatus() throws Exception {
            // given
            final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 0);
            final Attendance attendance = Attendance.of(dateTime);

            // when
            final AttendanceStatus actual = attendance.calculateStatus();

            // then
            assertThat(actual).isEqualTo(AttendanceStatus.ATTENDANCE);
        }

        @DisplayName("주어진 날짜와 올바르게 비교한다.")
        @Test
        public void matchDate() throws Exception {
            // given
            final LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 0);
            final Attendance attendance = Attendance.of(dateTime);
            final LocalDate date = LocalDate.of(2024, 12, 10);

            // when
            final boolean actual = attendance.matchDate(date);

            // then
            assertThat(actual).isTrue();
        }

        @DisplayName("비어있는지 여부를 올바르게 비교한다.")
        @Test
        public void isEmpty() throws Exception {
            // given
            final LocalDate date = LocalDate.of(2024, 12, 10);
            final Attendance attendance = Attendance.empty(date);

            // when
            final boolean actual = attendance.isEmpty();

            // then
            assertThat(actual).isTrue();
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
