package domain;

import domain.rule.AttendanceStateRule;
import domain.rule.AttendanceTimeRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FormatUtil;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceTimeTest {

    @Test
    @DisplayName("출석 시간을 정상적으로 생성할 수 있다")
    void createAttendanceTime() {
        // given
        LocalTime time = AttendanceTimeRule.NORMAL_ATTEND_LIMIT_TIME.toLocalTime();

        // when
        AttendanceTime attendanceTime = AttendanceTime.from(time);

        // then
        assertThat(attendanceTime).isNotNull();
        assertThat(attendanceTime.time()).isEqualTo(time);
    }

    @Test
    @DisplayName("출석 상태를 결정할 수 있다 - 정상 출석")
    void determineAttendanceState_AsAttend() {
        // given
        AttendanceTime attendanceTime = AttendanceTime.from(AttendanceTimeRule.NORMAL_ATTEND_LIMIT_TIME.toLocalTime());
        AttendanceTime attendanceTimeWhenSpecialDay = AttendanceTime.from(AttendanceTimeRule.SPECIAL_ATTEND_LIMIT_TIME.toLocalTime());

        // when
        AttendanceStateRule state = attendanceTime.checkAttendanceState(false);
        AttendanceStateRule stateWhenSpecialDay = attendanceTimeWhenSpecialDay.checkAttendanceState(true);

        // then
        assertAll(
                () -> assertThat(state).isEqualTo(AttendanceStateRule.ATTEND),
                () -> assertThat(stateWhenSpecialDay).isEqualTo(AttendanceStateRule.ATTEND));
    }

    @Test
    @DisplayName("출석 상태를 결정할 수 있다 - 지각")
    void determineAttendanceState_AsLate() {
        // given
        LocalTime lateTime = AttendanceTimeRule.NORMAL_ATTEND_LIMIT_TIME.toLocalTime()
                .plusMinutes(AttendanceStateRule.LATE.limit + 1);
        AttendanceTime attendanceTime = AttendanceTime.from(lateTime);

        // when
        AttendanceStateRule state = attendanceTime.checkAttendanceState(false);

        // then
        assertThat(state).isEqualTo(AttendanceStateRule.LATE);
    }

    @Test
    @DisplayName("출석 상태를 결정할 수 있다 - 결석")
    void determineAttendanceState_AsAbsent() {
        // given
        LocalTime absentTime = AttendanceTimeRule.NORMAL_ATTEND_LIMIT_TIME.toLocalTime()
                .plusMinutes(AttendanceStateRule.ABSENT.limit + 1);
        AttendanceTime attendanceTime = AttendanceTime.from(absentTime);

        // when
        AttendanceStateRule state = attendanceTime.checkAttendanceState(false);

        // then
        assertThat(state).isEqualTo(AttendanceStateRule.ABSENT);
    }

    @Test
    @DisplayName("입력 시간이 유효하지 않으면 예외를 발생시킨다")
    void shouldThrowException_WhenInvalidTime() {
        // given
        LocalTime invalidTime = AttendanceTimeRule.CAMPUS_OPEN_TIME.toLocalTime()
                .minusMinutes(1);

        // when
        // then
        assertThatThrownBy(() -> AttendanceTime.from(invalidTime))
                .hasMessage(String.format("캠퍼스 출입은 %s시 이후에만 가능합니다.",
                        AttendanceTimeRule.CAMPUS_OPEN_TIME.toLocalTime().format(FormatUtil.TIME_FORMATTER)));
    }
}