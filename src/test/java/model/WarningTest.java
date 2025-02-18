package model;

import attendance.model.AttendenceWarning;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class WarningTest {

    @Test
    void test1() {
        AttendenceWarning warning = AttendenceWarning.경고;
        Assertions.assertThat(warning).isNotNull();
    }

    @Test
    void test2() {
        Assertions.assertThat(AttendenceWarning.from(2)).isEqualTo(AttendenceWarning.경고);
    }

    @Test
    void test3() {
        Assertions.assertThat(AttendenceWarning.from(3)).isEqualTo(AttendenceWarning.면담);
    }

    @Test
    void test4() {
        Assertions.assertThat(AttendenceWarning.from(6)).isEqualTo(AttendenceWarning.제적);
    }

    @Test
    void test5() {
        Assertions.assertThat(AttendenceWarning.from(1)).isEqualTo(AttendenceWarning.해당없음);
    }
}
