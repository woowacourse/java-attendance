package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceRecordTest {
    
    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {
        @Test
        @DisplayName("정상적으로 출석 기록이 생성된다")
        void new_test() {
            // given
            String nickname = "우가";
            Crew crew = new Crew(nickname);
            LocalDate monday = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 5);

            // when && then
            Assertions.assertThatCode(() -> {
                AttendanceRecord.of(crew, monday, time);
            }).doesNotThrowAnyException();
        }

    }

    @Nested
    @DisplayName("예외 테스트")
    class Fail {

        @Test
        @DisplayName("출석 기록 날짜가 주말 혹은 공휴일이면 예외가 발생한다")
        void validateOffDateExceptionTest() {
            // given
            String nickname = "우가";
            Crew crew = new Crew(nickname);
            LocalDate saturday = LocalDate.of(2025, 2, 1);
            LocalTime time = LocalTime.of(13, 5);

            // when && then
            Assertions.assertThatThrownBy(() -> {
                AttendanceRecord.of(crew, saturday, time);
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("출석 기록 시간이 캠퍼스 운영 시간이 아니면 예외가 발생한다")
        void validateCampusTimeExceptionTest() {
            // given
            String nickname = "우가";
            Crew crew = new Crew(nickname);
            LocalDate date = LocalDate.of(2025, 2, 3);
            LocalTime overCampusCloseTime = LocalTime.of(23, 5);

            // when && then
            Assertions.assertThatThrownBy(() -> {
                AttendanceRecord.of(crew, date, overCampusCloseTime);
            }).isInstanceOf(IllegalArgumentException.class);
        }
    }
}