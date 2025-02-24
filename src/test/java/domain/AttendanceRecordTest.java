package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {
    @Test
    @DisplayName("출결 기록을 예외 없이 생성한다.")
    public void attendanceRecordConstructorTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 2);
        LocalTime time = LocalTime.of(13, 0);
        // when & then
        assertThatCode(() -> AttendanceRecord.of(date, time))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("등교일이 아닐 경우 예외를 발생시킨다.")
    public void validateDateStringTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalTime time = LocalTime.of(10, 0);
        // when & then
        assertThatThrownBy(() -> AttendanceRecord.of(date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 25일 수요일은 등교일이 아닙니다.");
    }
}
