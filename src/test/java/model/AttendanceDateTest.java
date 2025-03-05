package model;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceDateTest {

    @DisplayName("출석 날짜를 생성한다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-02",
            "2024-12-03",
            "2024-12-26"
    })
    void createAttendanceDateTime(final LocalDate attendanceDate) {
        assertThatCode(() -> new AttendanceDate(attendanceDate))
                .doesNotThrowAnyException();
    }

    @DisplayName("주말이나 공휴일이라면 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-01",
            "2024-12-07",
            "2024-12-25"
    })
    void notOperationDateTest(final LocalDate attendanceDate) {
        assertThatThrownBy(() -> new AttendanceDate(attendanceDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등교일이 아닙니다.");

        // TODO : [ERROR] 12월 14일 토요일은 등교일이 아닙니다. 라고 출력되어야 한다.
    }
}
