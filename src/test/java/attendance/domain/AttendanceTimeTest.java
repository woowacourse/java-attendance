package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceTimeTest {
    @DisplayName("예외: 잘못된 형식의 시간 형식 입력 시 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"77:77", "7시 7분", "7:17", "24:00", "07:00", "23:30"})
    void causeExceptionFormatTime(String timeInput) {
        assertThatThrownBy(() -> new AttendanceTime(timeInput)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상: 올바른 형식의 시간 형식 입력 시 정상 처리")
    @ParameterizedTest
    @ValueSource(strings = {"09:07", "23:00", "08:00"})
    void successExecutionFormatTime(String timeInput) {
        AttendanceTime attendanceTime = new AttendanceTime(timeInput);
        assertThatCode(attendanceTime::getAttendanceTime).doesNotThrowAnyException();
    }
}
