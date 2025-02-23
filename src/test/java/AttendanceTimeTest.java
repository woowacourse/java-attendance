import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.date.AttendanceTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceTimeTest {

    @Test
    void lateTest() {
        AttendanceTime time = new AttendanceTime(13, 0);
        boolean isLate = time.isLate(1);
        assertThat(isLate).isFalse();
    }

    @Test
    void lateTest1() {
        AttendanceTime time = new AttendanceTime(14, 0);
        boolean isLate = time.isLate(1);
        assertThat(isLate).isTrue();
    }

    @Test
    void lateTest2() {
        AttendanceTime time = new AttendanceTime(13, 6);
        boolean isLate = time.isLate(1);
        assertThat(isLate).isTrue();
    }

    @Test
    void lateTest3() {
        AttendanceTime time = new AttendanceTime(13, 0);
        boolean isLate = time.isLate(2);
        assertThat(isLate).isTrue();
    }

    @Test
    void lateTest4() {
        AttendanceTime time = new AttendanceTime(10, 0);
        boolean isLate = time.isLate(2);
        assertThat(isLate).isFalse();
    }

    @Test
    void lateTest5() {
        AttendanceTime time = new AttendanceTime(10, 6);
        boolean isLate = time.isLate(2);
        assertThat(isLate).isTrue();
    }

    @Test
    void absence1() {
        AttendanceTime time = new AttendanceTime(13, 31);
        boolean isAbsence = time.isAbsence(1);
        assertThat(isAbsence).isTrue();
    }

    @Test
    void absence2() {
        AttendanceTime time = new AttendanceTime(13, 0);
        boolean isAbsence = time.isAbsence(1);
        assertThat(isAbsence).isFalse();
    }

    @Test
    void absence3() {
        AttendanceTime time = new AttendanceTime(13, 0);
        boolean isAbsence = time.isAbsence(2);
        assertThat(isAbsence).isTrue();
    }

    @Test
    void absence4() {
        AttendanceTime time = new AttendanceTime(14, 0);
        boolean isAbsence = time.isAbsence(1);
        assertThat(isAbsence).isTrue();
    }

    @Test
    void openTimeTest() {
        AttendanceTime time = new AttendanceTime(13, 0);
        boolean isOpenTime = time.isOpenTime();
        assertThat(isOpenTime).isTrue();
    }

    @Test
    void openTimeTest2() {
        AttendanceTime time = new AttendanceTime(7, 0);
        boolean isOpenTime = time.isOpenTime();
        assertThat(isOpenTime).isFalse();
    }

    @Test
    void openTimeTest3() {
        AttendanceTime time = new AttendanceTime(24, 0);
        boolean isOpenTime = time.isOpenTime();
        assertThat(isOpenTime).isFalse();
    }

    @Test
    void openTimeTest4() {
        AttendanceTime time = new AttendanceTime(23, 30);
        boolean isOpenTime = time.isOpenTime();
        assertThat(isOpenTime).isFalse();
    }

    @DisplayName("validateHour가 올바르게 동작하는지 확인한다.")
    @ParameterizedTest
    @ValueSource(ints = {25, -1})
    void validateHourTest1(int hour) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> new AttendanceTime(hour, 0));
    }

    @DisplayName("validateMinute이 올바르게 동작하는지 확인한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 60})
    void validateHourTest2(int minute) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> new AttendanceTime(1, minute));
    }
}
