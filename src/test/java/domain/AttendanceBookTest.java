package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    private AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        attendanceBook = AttendanceBook.create();
    }

    @Test
    @DisplayName("출석 확인 테스트")
    void test1() {
        //given
        final String name = "쿠키";
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 17, 10, 0);
        //when
        //then
        assertAll(
                () -> assertThatCode(() -> attendanceBook.addAttendance(name, localDateTime)).doesNotThrowAnyException(),
                () -> assertThatIllegalArgumentException().isThrownBy(() -> attendanceBook.addAttendance(name, localDateTime))
        );
    }

    @Test
    @DisplayName("출석 수정 테스트")
    void test2() {
        //given
        final String name = "쿠키";
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 31);

        //when
        final AttendanceRecord attendanceRecord = attendanceBook.modifyAttendance(name, localDateTime);
        //then
        assertThat(attendanceRecord).isNotNull();

    }
}
