package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.Duration;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTimeTest {

    @DisplayName("출석 시간을 생성한다")
    @ParameterizedTest
    @CsvSource({
            "08:00",
            "23:00",
            "10:20"
    })
    void createAttendanceTimeTest(final LocalTime attendanceTime) {
        assertThatCode(() -> new AttendanceTime(attendanceTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("캠퍼스 운영시간이 아니라면 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({
            "07:59",
            "23:01"
    })
    void notOperationTimeTest(final LocalTime attendanceTime) {
        assertThatThrownBy(() -> new AttendanceTime(attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }

    @DisplayName("출석 시간이 교육 시간으로부터 얼마나 지났는지 계산한다")
    @Test
    void getOverDurationTest() {
        AttendanceTime time = new AttendanceTime(LocalTime.of(10, 0));
        LocalTime futureTime = LocalTime.of(10, 30);
        LocalTime pastTime = LocalTime.of(9, 30);

        assertAll(
                () -> assertThat(time.getOverDuration(futureTime)).isEqualTo(Duration.ofMinutes(-30)),
                () -> assertThat(time.getOverDuration(pastTime)).isEqualTo(Duration.ofMinutes(+30))
        );
    }
}
