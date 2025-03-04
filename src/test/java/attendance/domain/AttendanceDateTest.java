package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceDateTest {
    @DisplayName("예외: 잘못된 형식의 날짜 형식 입력 시 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"삼", "3일", "77"})
    void causeExceptionFormatDate(String dateInput) {
        assertThatThrownBy(() -> new AttendanceDate(dateInput)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상: 올바른 형식의 날짜 형식 입력 시 정상 처리")
    @ParameterizedTest
    @ValueSource(strings = {"1", "12", "23"})
    void successExecutionFormatDate(String dateInput) {
        AttendanceDate attendanceDate = new AttendanceDate(dateInput);
        assertThatCode(attendanceDate::getAttendanceDate).doesNotThrowAnyException();
    }

    @DisplayName("예외: 날짜가 주말인 경우 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"2025-02-22", "2025-02-23"})
    void causeExceptionWeekend(String date) {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.parse(date));
        assertThatThrownBy(attendanceDate::checkAttendanceDateIsWeekend)
                .isInstanceOf(IllegalArgumentException.class);
    }
}
