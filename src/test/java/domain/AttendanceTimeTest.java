package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceTimeTest {

    @Test
    @DisplayName("출석 시간는 출석 정책을 통해서 출석 가능한지 확인할 수 있다.")
    void cannotAttendTimeThrowException() {
        // given
        LocalTime withinCampusTime = LocalTime.of(10, 0);
        LocalTime outsideCampusTime = LocalTime.of(23, 30);

        // when
        // then
        assertAll(
                () -> assertThatCode(() -> AttendanceTime.from(withinCampusTime))
                        .doesNotThrowAnyException(),

                () -> assertThatThrownBy(() -> AttendanceTime.from(outsideCampusTime))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("등교할 수 없는 시간입니다.")
        );
    }

    @Test
    @DisplayName("내부 값이 같다면, 같은 출석 시간으로 취급한다.")
    void treatedAsTheSameObjectIfValuesAreTheSame() {
        // given
        AttendanceTime attendanceTime1 = AttendanceTime.from(LocalTime.of(10, 10));
        AttendanceTime attendanceTime2 = AttendanceTime.from(LocalTime.of(10, 10));

        // when
        // then
        assertThat(attendanceTime1).isEqualTo(attendanceTime2);
    }
}
