package domain;

import static constants.TestTimeMaker.EXCEPT_MONDAY_ABSENT;
import static constants.TestTimeMaker.EXCEPT_MONDAY_ATTEND;
import static constants.TestTimeMaker.EXCEPT_MONDAY_LATE;
import static constants.TestTimeMaker.MONDAY_ABSENT;
import static constants.TestTimeMaker.MONDAY_ATTEND;
import static constants.TestTimeMaker.MONDAY_LATE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {
    @Test
    @DisplayName("월요일_출석_시간을_넣으면_출결_상태를_반환_한다")
    void 월요일_출석_시간을_넣으면_출결_상태를_반환_한다() {
        assertThat(AttendanceStatus.getInMonday(MONDAY_ABSENT)).isEqualTo(AttendanceStatus.ABSENT);
        assertThat(AttendanceStatus.getInMonday(MONDAY_LATE)).isEqualTo(AttendanceStatus.LATE);
        assertThat(AttendanceStatus.getInMonday(MONDAY_ATTEND)).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    @DisplayName("월요일이_아닌_요일의_출석_시간을_넣으면_출결_상태를_반환_한다")
    void 월요일이_아닌_요일의_출석_시간을_넣으면_출결_상태를_반환_한다() {
        assertThat(AttendanceStatus.getExceptMonday(EXCEPT_MONDAY_ABSENT)).isEqualTo(AttendanceStatus.ABSENT);
        assertThat(AttendanceStatus.getExceptMonday(EXCEPT_MONDAY_LATE)).isEqualTo(AttendanceStatus.LATE);
        assertThat(AttendanceStatus.getExceptMonday(EXCEPT_MONDAY_ATTEND)).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    @DisplayName("날짜와_시간을_넣으면_출결_상태를_반환_한다")
    void 날짜와_시간을_넣으면_출결_상태를_반환_한다() {
        assertThat(AttendanceStatus.judgeStatus(LocalDate.of(2024, 12, 2), MONDAY_ABSENT))
                .isEqualTo(AttendanceStatus.ABSENT);
        assertThat(AttendanceStatus.judgeStatus(LocalDate.of(2024, 12, 2), MONDAY_LATE))
                .isEqualTo(AttendanceStatus.LATE);
        assertThat(AttendanceStatus.judgeStatus(LocalDate.of(2024, 12, 2), MONDAY_ATTEND))
                .isEqualTo(AttendanceStatus.ATTEND);

        assertThat(AttendanceStatus.judgeStatus(LocalDate.of(2024, 12, 3), EXCEPT_MONDAY_ABSENT))
                .isEqualTo(AttendanceStatus.ABSENT);
        assertThat(AttendanceStatus.judgeStatus(LocalDate.of(2024, 12, 3), EXCEPT_MONDAY_LATE))
                .isEqualTo(AttendanceStatus.LATE);
        assertThat(AttendanceStatus.judgeStatus(LocalDate.of(2024, 12, 3), EXCEPT_MONDAY_ATTEND))
                .isEqualTo(AttendanceStatus.ATTEND);
    }
}