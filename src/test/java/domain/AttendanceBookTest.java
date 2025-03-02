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
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 24, 10, 0);
        //when
        final AttendanceRecord attendanceRecord = attendanceBook.addAttendance(name, localDateTime);
        //then
        assertAll(
                () -> assertThat(attendanceRecord.attendanceDate().getLocalDate()).isEqualTo(
                        localDateTime.toLocalDate()),
                () -> assertThat(attendanceRecord.attendanceTime().getLocaltime()).isEqualTo(
                        localDateTime.toLocalTime())
        );
    }

    @Test
    @DisplayName("출석 기록이 이미 존재하여 예외가 발생한다.")
    void test4() {
        //given
        final String name = "쿠키";
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 24, 10, 0);
        //when
        attendanceBook.addAttendance(name, localDateTime);
        //then
        assertThatIllegalArgumentException().isThrownBy(
                () -> attendanceBook.validateExistAttendance(localDateTime.toLocalDate(), name));

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
