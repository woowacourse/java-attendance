package domain;


import domain.date.AttendanceDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceDateTest {
    @Test
    void getDayOfWeekTest() {
        AttendanceDate attendanceDate = new AttendanceDate(1);
        int dayOfWeek = attendanceDate.getDayOfWeek();
        Assertions.assertThat(dayOfWeek).isEqualTo(7);
    }

    @Test
    void getDayOfWeekTest2() {
        AttendanceDate attendanceDate = new AttendanceDate(2);
        int dayOfWeek = attendanceDate.getDayOfWeek();
        Assertions.assertThat(dayOfWeek).isEqualTo(1);
    }

    @Test
    void getDayOfWeekTest3() {
        AttendanceDate attendanceDate = new AttendanceDate(7);
        int dayOfWeek = attendanceDate.getDayOfWeek();
        Assertions.assertThat(dayOfWeek).isEqualTo(6);
    }

    @Test
    void getDayOfWeekTest4() {
        AttendanceDate attendanceDate = new AttendanceDate(26);
        int dayOfWeek = attendanceDate.getDayOfWeek();
        Assertions.assertThat(dayOfWeek).isEqualTo(4);
    }

    @Test
    void getDayOfWeekTest5() {
        AttendanceDate attendanceDate = new AttendanceDate(14);
        int dayOfWeek = attendanceDate.getDayOfWeek();
        Assertions.assertThat(dayOfWeek).isEqualTo(6);
    }

    @Test
    void restDayTest() {
        boolean isRestDay = AttendanceDate.isRestDay(1);
        Assertions.assertThat(isRestDay).isTrue();
    }

    @Test
    void restDayTest1() {
        boolean isRestDay = AttendanceDate.isRestDay(2);
        Assertions.assertThat(isRestDay).isFalse();
    }

    @Test
    void restDayTest2() {
        boolean isRestDay = AttendanceDate.isRestDay(8);
        Assertions.assertThat(isRestDay).isTrue();
    }

    @Test
    void restDayTest3() {
        boolean isRestDay = AttendanceDate.isRestDay(25);
        Assertions.assertThat(isRestDay).isTrue();
    }

    @Test
    void exceptionTest1() {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> new AttendanceDate(0));
    }

    @Test
    void exceptionTest2() {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> new AttendanceDate(32));
    }
}
