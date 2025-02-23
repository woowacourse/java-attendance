package attendance.domain;

import attendance.utility.DateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class AttendanceTest {

    @DisplayName("기본 날짜와 시간으로 출석을 생성할 수 있다.")
    @Test
    void 기본_날짜와_시간으로_출석을_생성할_수_있다() {
        DateGenerator dateGenerator = new MockingDateGenerator();
        LocalDateTime givenDateTime = LocalDateTime.of(
                dateGenerator.now(),
                LocalTime.of(10, 0)
        );

        assertThatCode(() -> new Attendance(givenDateTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("동일 날짜에 여부를 확인한다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-05T10:00:00, 2024-12-05, true",
            "2024-12-05T10:00:00, 2024-12-06, false"
    })
    void 동일_날짜에_여부를_확인한다(LocalDateTime origin, LocalDate compare, boolean expected) {
        // given
        Attendance attendance = new Attendance(origin);

        // then
        assertThat(attendance.isEqualDate(compare))
                .isEqualTo(expected);
    }

    @Test
    void 출석의_시간을_비교해_반환한다() {
        // given
        LocalDateTime firstDateTime = LocalDateTime.of(2024, 12, 7, 10, 0);
        LocalDateTime secondDateTime = LocalDateTime.of(2024, 12, 6, 10, 0);

        Attendance firstAttendance = new Attendance(firstDateTime);
        Attendance secondAttendance = new Attendance(secondDateTime);

        // when
        int result = firstAttendance.compareTo(secondAttendance);

        // then
        assertThat(result)
                .isEqualTo(1);
    }

    private static class MockingDateGenerator implements DateGenerator {

        @Override
        public LocalDate now() {
            return LocalDate.of(2024, 12, 5);
        }
    }
}
