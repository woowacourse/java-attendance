package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTimeTest {

    @DisplayName("캠퍼스 운영 시간에만 출석한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "07,59",
            "23,01"
    }, delimiter = ',')
    void 캠퍼스_운영_시간에만_출석한다(String hour, String minute) {

        // given
        LocalDate localDate = LocalDate.of(2025, 2, 19);

        // when & then
        assertThatThrownBy(() -> new AttendanceTime(localDate, hour, minute, false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 가능한 시간이 아닙니다.");
    }

    @DisplayName("주말에는 출석하지 않는다.")
    @Test
    void 주말에는_출석하지_않는다() {

        // given
        LocalDate localDate = LocalDate.of(2025, 2, 15);

        // when & then
        assertThatThrownBy(() -> new AttendanceTime(localDate, "10", "10", false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 02월 15일 토요일은 등교일이 아닙니다.");
    }

    @DisplayName("현재 출석 일시가 입력 받은 날짜 및 시간보다 이후인지 판단한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "5, false",
            "7, true"
    }, delimiter = ',')
    void 현재_출석_일시가_입력_받은_날짜_및_시간보다_이후인지_판단한다(int attendedMinute, boolean expectedResult) {

        // given
        LocalDateTime deadlineTime = LocalDateTime.of(2025, 2, 13, 10, 5);
        LocalDate date = LocalDate.of(2025, 2, 13);
        AttendanceTime attendanceTime = new AttendanceTime(date, "10", String.valueOf(attendedMinute), false);

        // when
        boolean result = attendanceTime.isAfter(deadlineTime);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }
}
