import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimeTest {
    @Test
    void lateTest() {
        boolean isLate = Application.lateCheck(1, 13, 0);
        Assertions.assertThat(isLate).isFalse();
    }

    @Test
    void lateTest1() {
        boolean isLate = Application.lateCheck(1, 14, 0);
        Assertions.assertThat(isLate).isTrue();
    }

    @Test
    void lateTest2() {
        boolean isLate = Application.lateCheck(1, 13, 6);
        Assertions.assertThat(isLate).isTrue();
    }

    @Test
    void lateTest3() {
        boolean isLate = Application.lateCheck(2, 13, 0);
        Assertions.assertThat(isLate).isTrue();
    }

    @Test
    void lateTest4() {
        boolean isLate = Application.lateCheck(2, 10, 0);
        Assertions.assertThat(isLate).isFalse();
    }

    @Test
    void lateTest5() {
        boolean isLate = Application.lateCheck(2, 10, 6);
        Assertions.assertThat(isLate).isTrue();
    }

    @Test
    void absence1() {
        boolean isAbsence = Application.absenceCheck(1, 13, 31);
        Assertions.assertThat(isAbsence).isTrue();
    }

    @Test
    void absence2() {
        boolean isAbsence = Application.absenceCheck(1, 13, 0);
        Assertions.assertThat(isAbsence).isFalse();
    }

    @Test
    void absence3() {
        boolean isAbsence = Application.absenceCheck(2, 13, 0);
        Assertions.assertThat(isAbsence).isTrue();
    }

    @Test
    void absence4() {
        boolean isAbsence = Application.absenceCheck(1, 14, 0);
        Assertions.assertThat(isAbsence).isTrue();
    }

    @Test
    void openTimeTest() {
        boolean isOpenTime = Application.openTimeCheck(13, 0);
        Assertions.assertThat(isOpenTime).isTrue();
    }

    @Test
    void openTimeTest2() {
        boolean isOpenTime = Application.openTimeCheck(7, 0);
        Assertions.assertThat(isOpenTime).isFalse();
    }

    @Test
    void openTimeTest3() {
        boolean isOpenTime = Application.openTimeCheck(24, 0);
        Assertions.assertThat(isOpenTime).isFalse();
    }

    @Test
    void openTimeTest4() {
        boolean isOpenTime = Application.openTimeCheck(23, 30);
        Assertions.assertThat(isOpenTime).isFalse();
    }

    @Test
    void restDayTest() {
        boolean isRestDay = Application.restDayCheck(1);
        Assertions.assertThat(isRestDay).isTrue();
    }

    @Test
    void restDayTest1() {
        boolean isRestDay = Application.restDayCheck(2);
        Assertions.assertThat(isRestDay).isFalse();
    }

    @Test
    void restDayTest2() {
        boolean isRestDay = Application.restDayCheck(8);
        Assertions.assertThat(isRestDay).isTrue();
    }

    @Test
    void restDayTest3() {
        boolean isRestDay = Application.restDayCheck(25);
        Assertions.assertThat(isRestDay).isTrue();
    }
}
