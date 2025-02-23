package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("출결 유형 테스트")
class AttendanceTypeTest {

    @DisplayName("시작 시간과 출석 시간으로 출결 유형을 판단할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {
            "10:00, 09:30, OK",
            "10:00, 10:05, OK",
            "10:00, 10:06, LATE",
            "10:00, 10:31, ABSENCE",
    })
    void judgeTest(LocalTime startTime, LocalTime attendanceTime, AttendanceType expected) {
        // when
        AttendanceType type = AttendanceType.judge(startTime, attendanceTime);

        // then
        assertThat(type)
                .isEqualTo(expected);
    }
}
