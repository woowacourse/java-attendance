package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendanceRecordTest {
    @Test
    @DisplayName("출결 기록을 예외 없이 생성한다.")
    public void attendanceRecordConstructorTest() {
        assertThatCode(() -> new AttendanceRecord("2024-12-02 13:00"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("문자열 입력값이 등교일이 아닐 경우 예외를 발생시킨다.")
    public void validateDateStringTest() {
        assertThatThrownBy(() -> new AttendanceRecord("2024-12-25 10:00"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 25일 수요일은 등교일이 아닙니다.");
    }

    @Test
    @DisplayName("시간 입력값이 등교일이 아닐 경우 예외를 발생한다.")
    public void validateLocalTimeTest() {
        assertThatThrownBy(() -> new AttendanceRecord(LocalTime.of(10, 0), () -> LocalDate.of(2024, 12, 25)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 25일 수요일은 등교일이 아닙니다.");
    }
}
