package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @Test
    void 초기_빈값의_출석부를_생성한다() {
        // when
        AttendanceBook attendanceBook = AttendanceBook.initBook();

        // then
        Assertions.assertThat(attendanceBook).isNotNull();
        Assertions.assertThat(attendanceBook.getBook()).hasSize(0);
    }
}
