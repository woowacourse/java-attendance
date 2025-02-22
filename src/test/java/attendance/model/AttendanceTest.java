package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Test
    void 정시에_도착한_경우_출석이다() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 0);

        //when
        Attendance attendance = Attendance.from(dateTime);

        //then
        assertThat(attendance).isEqualTo(Attendance.PRESENT);
    }

    @Test
    void _5분_초과로_늦게온_경우_지각이다() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 6);

        //when
        Attendance attendance = Attendance.from(dateTime);

        //then
        assertThat(attendance).isEqualTo(Attendance.LATE);
    }

    @Test
    void _31분_초과로_늦게온_경우_결석이다() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 31);

        //when
        Attendance attendance = Attendance.from(dateTime);

        //then
        assertThat(attendance).isEqualTo(Attendance.ABSENT);
    }

}
