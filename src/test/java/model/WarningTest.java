package model;

import attendance.model.AttendanceWarning;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class WarningTest {

    @Test
    void test1() {
        AttendanceWarning warning = AttendanceWarning.경고;
        Assertions.assertThat(warning).isNotNull();
    }

    @Test
    void test2() {
        Assertions.assertThat(AttendanceWarning.from(2)).isEqualTo(AttendanceWarning.경고);
    }

    @Test
    void test3() {
        Assertions.assertThat(AttendanceWarning.from(3)).isEqualTo(AttendanceWarning.면담);
    }

    @Test
    void test4() {
        Assertions.assertThat(AttendanceWarning.from(6)).isEqualTo(AttendanceWarning.제적);
    }

    @Test
    void test5() {
        Assertions.assertThat(AttendanceWarning.from(1)).isEqualTo(AttendanceWarning.해당없음);
    }
}
