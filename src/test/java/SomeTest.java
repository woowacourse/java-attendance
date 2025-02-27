import domain.AttendanceStatus;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SomeTest {
    // 월요일의 경우, 출석, 지각, 결석 상태 반환

    @Nested
    @DisplayName("월요일 정상 출석, 지각, 결석 시 출석 상태 결정 테스트")
    class MondayAttendanceTest {
        @DisplayName("정상 출석")
        @Test
        void test1() {
            // given
            LocalDateTime attendedTime =  LocalDateTime.of(2024, 12, 2, 13, 0);

            // when & then
            Assertions.assertThat(AttendanceStatus.getStatusByAttendedTime(attendedTime))
                    .isEqualTo(AttendanceStatus.ATTEND);
        }

        @DisplayName("지각")
        @Test
        void test2() {
            // given
            LocalDateTime attendedTime =  LocalDateTime.of(2024, 12, 2, 13, 6);

            // when & then
            Assertions.assertThat(AttendanceStatus.getStatusByAttendedTime(attendedTime))
                    .isEqualTo(AttendanceStatus.LATE);
        }

        @DisplayName("결석")
        @Test
        void test3() {
            // given
            LocalDateTime attendedTime =  LocalDateTime.of(2024, 12, 2, 13, 31);

            // when & then
            Assertions.assertThat(AttendanceStatus.getStatusByAttendedTime(attendedTime))
                    .isEqualTo(AttendanceStatus.ABSENT);
        }
    }


    // 나머지 출석상태 반영
    // 주말 출석 시도
    // 미 개장 시간 출석 시도

}
