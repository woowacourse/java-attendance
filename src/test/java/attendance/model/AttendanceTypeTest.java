package attendance.model;

import static attendance.model.TestFixtures.LOCAL_TIME_10_00;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("출석 유형 테스트")
class AttendanceTypeTest {

    @DisplayName("기준 시간과 등교 시간으로 출석 유형을 판단할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "10:00, 09:55, PRESENT",
            "10:00, 10:05, PRESENT",
            "10:00, 10:06, LATE",
            "10:00, 10:30, LATE",
            "10:00, 10:31, ABSENT",
            "10:00, 13:00, ABSENT",
    })
    void attendanceTypeDetermineTest(@JavaTimeConversionPattern("HH:mm") LocalTime baseTime,
                                     @JavaTimeConversionPattern("HH:mm") LocalTime attendanceTime,
                                     AttendanceType expected) {
        // when
        AttendanceType attendanceType = AttendanceType.determine(baseTime, attendanceTime);

        // then
        assertThat(attendanceType)
                .isSameAs(expected);
    }

    @DisplayName("등교 시간이 없는 경우 결석으로 간주한다.")
    @Test
    void shouldReturnAbsent_WhenAttendanceTimeIsNull() {
        // when
        AttendanceType attendanceType = AttendanceType.determine(LOCAL_TIME_10_00, null);

        // then
        assertThat(attendanceType)
                .isSameAs(AttendanceType.ABSENT);
    }
}
