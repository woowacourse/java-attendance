import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimeTest {
    @Test
    void test() {
        boolean isLate = Application.lateCheck(1, 13, 0);
        Assertions.assertThat(isLate).isFalse();
    }

    @Test
    void test1() {
        boolean isLate = Application.lateCheck(1, 14, 0);
        Assertions.assertThat(isLate).isTrue();
    }

    @Test
    void test2() {
        boolean isLate = Application.lateCheck(1, 13, 6);
        Assertions.assertThat(isLate).isTrue();
    }

    @Test
    void test3() {
        boolean isLate = Application.lateCheck(2, 13, 0);
        Assertions.assertThat(isLate).isTrue();
    }

    @Test
    void test4() {
        boolean isLate = Application.lateCheck(2, 10, 0);
        Assertions.assertThat(isLate).isFalse();
    }

    @Test
    void test5() {
        boolean isLate = Application.lateCheck(2, 10, 6);
        Assertions.assertThat(isLate).isTrue();
    }
}
