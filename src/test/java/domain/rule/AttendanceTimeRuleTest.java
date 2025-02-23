package domain.rule;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.FormatUtil;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceTimeRuleTest {

    @Test
    @DisplayName("요일에 따라서 다른 등교 시간을 제공받을 수 있다.")
    void getAttendLimitTime() {
        // given
        LocalDate specialDate = LocalDate.of(2024, 12, 16);
        LocalDate normalDate = LocalDate.of(2024, 12, 17);

        // when
        LocalTime specialAttendLimitTime = AttendanceTimeRule.getAttendLimitTime(AttendanceDateRule.isSpecialDay(specialDate));
        LocalTime normalAttendLimitTime = AttendanceTimeRule.getAttendLimitTime(AttendanceDateRule.isSpecialDay(normalDate));

        // then
        assertAll(
                () -> assertThat(specialAttendLimitTime).isEqualTo(AttendanceTimeRule.SPECIAL_ATTEND_LIMIT_TIME.toLocalTime()),
                () -> assertThat(normalAttendLimitTime).isEqualTo(AttendanceTimeRule.NORMAL_ATTEND_LIMIT_TIME.toLocalTime())
        );
    }

    @Test
    @DisplayName("출입 시간을 검증하고 예외를 던질 수 있다.")
    void validateEnterTime() {
        // given
        LocalTime timeBeforeOpenTime = AttendanceTimeRule.CAMPUS_OPEN_TIME.toLocalTime().minusMinutes(1);
        LocalTime timeAtOpenTime = AttendanceTimeRule.CAMPUS_OPEN_TIME.toLocalTime();
        LocalTime timeAfterOpenTime = AttendanceTimeRule.CAMPUS_OPEN_TIME.toLocalTime().plusMinutes(1);

        LocalTime timeBeforeCloseTime = AttendanceTimeRule.CAMPUS_CLOSE_TIME.toLocalTime().minusMinutes(1);
        LocalTime timeAtCloseTime = AttendanceTimeRule.CAMPUS_CLOSE_TIME.toLocalTime();
        LocalTime timeAfterCloseTime = AttendanceTimeRule.CAMPUS_CLOSE_TIME.toLocalTime().plusMinutes(1);

        // when
        // then
        assertAll(
                () -> assertThatCode(() -> AttendanceTimeRule.validateEnterTime(timeAtOpenTime))
                        .doesNotThrowAnyException(),

                () -> assertThatCode(() -> AttendanceTimeRule.validateEnterTime(timeAtCloseTime))
                        .doesNotThrowAnyException(),

                () -> assertThatCode(() -> AttendanceTimeRule.validateEnterTime(timeAfterOpenTime))
                        .doesNotThrowAnyException(),

                () -> assertThatCode(() -> AttendanceTimeRule.validateEnterTime(timeBeforeCloseTime))
                        .doesNotThrowAnyException(),

                () -> assertThatThrownBy(() -> AttendanceTimeRule.validateEnterTime(timeBeforeOpenTime))
                        .hasMessage(String.format("캠퍼스 출입은 %s시 이후에만 가능합니다.",
                                AttendanceTimeRule.CAMPUS_OPEN_TIME.toLocalTime().format(FormatUtil.TIME_FORMATTER))),

                () -> assertThatThrownBy(() -> AttendanceTimeRule.validateEnterTime(timeAfterCloseTime))
                        .hasMessage(String.format("캠퍼스 출입은 %s시 이전에만 가능합니다.",
                                AttendanceTimeRule.CAMPUS_CLOSE_TIME.toLocalTime().format(FormatUtil.TIME_FORMATTER)))
        );
    }
}
