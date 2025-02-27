package domain;

import domain.policy.AttendancePolicy;
import domain.policy.date.AttendanceDatePolicy;
import domain.policy.time.AttendanceTimePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceTimeTest {

    private final AttendancePolicy attendancePolicy = new AttendancePolicy(
            new AttendanceDatePolicy(),
            new AttendanceTimePolicy()
    );

    @Test
    @DisplayName("출석 시간는 출석 정책을 통해서 출석 가능한지 확인할 수 있다.")
    void cannotAttendTimeThrowException() {
        // given
        LocalTime withinCampusTime = LocalTime.of(10, 0);
        LocalTime outsideCampusTime = LocalTime.of(23, 30);

        // when
        // then
        assertAll(
                () -> assertThatCode(() -> AttendanceTime.of(withinCampusTime, attendancePolicy))
                        .doesNotThrowAnyException(),

                () -> assertThatThrownBy(() -> AttendanceTime.of(outsideCampusTime, attendancePolicy))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("등교할 수 없는 시간입니다.")
        );
    }
}
