package model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.Attendance;
import attendance.model.AttendanceDetail;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Test
    void 정시에_도착한_경우_출석이다() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 0);
        Attendance attendance = Attendance.from(dateTime);
        Assertions.assertThat(attendance).isEqualTo(Attendance.PRESENT);
    }

    @Test
    void _5분_초과로_늦게온_경우_지각이다() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 6);
        Attendance attendance = Attendance.from(dateTime);
        Assertions.assertThat(attendance).isEqualTo(Attendance.LATE);
    }

    @Test
    void _31분_초과로_늦게온_경우_결석이다() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 31);
        Attendance attendance = Attendance.from(dateTime);
        Assertions.assertThat(attendance).isEqualTo(Attendance.ABSENT);
    }

    @Test
    void 출석상세가_등교날짜가_아니라면_예외가_발생한다() {
        assertThatThrownBy(() -> new AttendanceDetail(LocalDateTime.of(2024, 12, 1, 13, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
