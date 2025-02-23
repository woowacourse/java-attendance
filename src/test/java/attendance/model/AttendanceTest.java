package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.TestUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Test
    void 정시에_도착한_경우_출석이다() {
        //given
        LocalDate testDate = LocalDate.of(2024, 12, 10);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        //when
        Attendance attendance = Attendance.from(TestUtil.createTestWoowaDate(testDate), attendanceTime);

        //then
        assertThat(attendance).isEqualTo(Attendance.PRESENT);
    }

    @Test
    void _5분_초과로_늦게온_경우_지각이다() {
        //given
        LocalDate testDate = LocalDate.of(2024, 12, 10);
        LocalTime attendanceTime = LocalTime.of(10, 6);

        //when
        Attendance attendance = Attendance.from(TestUtil.createTestWoowaDate(testDate), attendanceTime);

        //then
        assertThat(attendance).isEqualTo(Attendance.LATE);
    }

    @Test
    void _31분_초과로_늦게온_경우_결석이다() {
        //given
        LocalDate testDate = LocalDate.of(2024, 12, 10);
        LocalTime attendanceTime = LocalTime.of(10, 31);

        //when
        Attendance attendance = Attendance.from(TestUtil.createTestWoowaDate(testDate), attendanceTime);

        //then
        assertThat(attendance).isEqualTo(Attendance.ABSENT);
    }

}
