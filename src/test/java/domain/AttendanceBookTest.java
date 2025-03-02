package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Test
    @DisplayName("출석 확인 테스트")
    void test1() {
        //given
        final String name = "쿠키";
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 17, 10, 0);
        //when
        final AttendanceBook attendanceBook = AttendanceBook.create();

        //then
        assertAll(
                () -> assertThatCode(() -> attendanceBook.addAttendance(name, localDateTime)).doesNotThrowAnyException(),
                () -> assertThatIllegalArgumentException().isThrownBy(() -> attendanceBook.addAttendance(name, localDateTime))
        );
    }
}
