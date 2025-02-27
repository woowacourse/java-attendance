package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class HolidayTest {

    @Test
    @DisplayName("등교 일자가 아닌 경우 예외가 발생한다")
    void shouldThrowExceptionWhenNotASchoolDay() {
        // given
        LocalDate attendanceDate = LocalDate.of(2024, 12, 14);

        // when & then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> Holiday.validateAttendanceDate(attendanceDate))
                .withMessage("[ERROR] 12월 14일 토요일은 등교일이 아닙니다.");
    }
}
