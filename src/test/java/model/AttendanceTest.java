package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.Attendance;
import global.BaseTest;
import org.junit.jupiter.api.Test;

class AttendanceTest extends BaseTest {

    @Test
    void _5분_초과로_늦게온_경우_지각이다() {
        // given
        int minutesLate = 6;

        // when
        Attendance result = Attendance.from(minutesLate);

        // then
        assertThat(result).isEqualTo(Attendance.LATE);
    }

    @Test
    void _30분_초과로_늦게온_경우_결석이다() {
        // given
        int minutesLate = 31;

        // when
        Attendance result = Attendance.from(minutesLate);

        // then
        assertThat(result).isEqualTo(Attendance.ABSENCE);
    }

    @Test
    void _5분_이내로_들어온_경우는_출석이다() {
        // given
        int minutesLate = 5;

        // when
        Attendance result = Attendance.from(minutesLate);

        // then
        assertThat(result).isEqualTo(Attendance.ATTEND);
    }
}

