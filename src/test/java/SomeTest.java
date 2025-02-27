import static org.assertj.core.api.Assertions.*;

import domain.AttendanceStatus;
import domain.ERROR_MESSAGE;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SomeTest {
    @Nested
    @DisplayName("월요일 정상 출석, 지각, 결석 시 출석 상태 결정 테스트")
    class MondayAttendanceTest {
        @DisplayName("정상 출석")
        @Test
        void test1() {
            // given
            LocalDateTime attendedTime =  LocalDateTime.of(2024, 12, 2, 13, 0);

            // when & then
            assertThat(AttendanceStatus.getStatusByAttendedTime(attendedTime))
                    .isEqualTo(AttendanceStatus.ATTEND);
        }

        @DisplayName("지각")
        @Test
        void test2() {
            // given
            LocalDateTime attendedTime =  LocalDateTime.of(2024, 12, 2, 13, 6);

            // when & then
            assertThat(AttendanceStatus.getStatusByAttendedTime(attendedTime))
                    .isEqualTo(AttendanceStatus.LATE);
        }

        @DisplayName("결석")
        @Test
        void test3() {
            // given
            LocalDateTime attendedTime =  LocalDateTime.of(2024, 12, 2, 13, 31);

            // when & then
            assertThat(AttendanceStatus.getStatusByAttendedTime(attendedTime))
                    .isEqualTo(AttendanceStatus.ABSENT);
        }
    }

    @Nested
    @DisplayName("화수목금 정상 출석, 지각, 결석 시 출석 상태 결정 테스트")
    class NonMondayAttendanceTest {
        @DisplayName("정상 출석")
        @Test
        void test1() {
            // given
            LocalDateTime attendedTime =  LocalDateTime.of(2024, 12, 3, 10, 0);

            // when & then
            assertThat(AttendanceStatus.getStatusByAttendedTime(attendedTime))
                    .isEqualTo(AttendanceStatus.ATTEND);
        }

        @DisplayName("지각")
        @Test
        void test2() {
            // given
            LocalDateTime attendedTime =  LocalDateTime.of(2024, 12, 3, 10, 6);

            // when & then
            assertThat(AttendanceStatus.getStatusByAttendedTime(attendedTime))
                    .isEqualTo(AttendanceStatus.LATE);
        }

        @DisplayName("결석")
        @Test
        void test3() {
            // given
            LocalDateTime attendedTime =  LocalDateTime.of(2024, 12, 4, 10, 31);

            // when & then
            assertThat(AttendanceStatus.getStatusByAttendedTime(attendedTime))
                    .isEqualTo(AttendanceStatus.ABSENT);
        }

        @DisplayName("결석 - 월요일이었으면 출석처리")
        @Test
        void test4() {
            // given
            LocalDateTime attendedTime =  LocalDateTime.of(2024, 12, 5, 13, 0);

            // when & then
            assertThat(AttendanceStatus.getStatusByAttendedTime(attendedTime))
                    .isEqualTo(AttendanceStatus.ABSENT);
        }
    }


    @Nested
    @DisplayName("주말 및 공휴일 출석 시도 예외 테스트")
    class ClosedDayAttendanceTest {
        @DisplayName("주말 출석 시도")
        @Test
        void test1() {
            // given
            LocalDateTime attendedTime =  LocalDateTime.of(2024, 12, 7, 10, 0);

            // when & then
            assertThatThrownBy(() -> AttendanceStatus.getStatusByAttendedTime(attendedTime)).isEqualTo(ERROR_MESSAGE.CLOSED_DAY);
        }
    }




    // 미 개장 시간 출석 시도

}
