import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceCheckTest {
    final LocalDate today = LocalDate.now();

    public static Stream<Arguments> provideEachDayOfWeekAttendance() {
        return Stream.of(
                // 월요일
                Arguments.of(LocalDate.of(2025, 2, 24), "에드", LocalTime.of(13, 0), "출석"),
                Arguments.of(LocalDate.of(2025, 2, 24), "제프", LocalTime.of(13, 6), "지각"),
                //수요일
                Arguments.of(LocalDate.of(2025, 2, 25), "율무", LocalTime.of(13, 0), "결석")
        );
    }

    @Test
    void 닉네임과_등교_시간을_입력하면_출석할_수_있다() {
        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(9, 59);

        AttendanceCheck attendanceCheck = new AttendanceCheck();
        attendanceCheck.attend(today, nickname, attendanceTime);
        final var actual = attendanceCheck.getAttendanceStatus(today, nickname);
        final var expected = "출석";
        assertEquals(expected, actual);
    }

    @Test
    void 출석_후_출석_기록을_확인한다() {
        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(9, 59);

        AttendanceCheck attendanceCheck = new AttendanceCheck();

        attendanceCheck.attend(today, nickname, attendanceTime);

        final var attendanceTimeRecord = attendanceCheck.getAttendanceTime(today, nickname);
        final var attendanceStatus = attendanceCheck.getAttendanceStatus(today, nickname);

        assertEquals(attendanceTime, attendanceTimeRecord);
        assertEquals("출석", attendanceStatus);
    }

    @Test
    void 이미_출석한_상태에서_출석을_시도하면_예외처리() {
        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(9, 59);
        AttendanceCheck attendanceCheck = new AttendanceCheck();

        attendanceCheck.attend(today, nickname, attendanceTime);

        assertThatThrownBy(() -> attendanceCheck.attend(today, nickname, attendanceTime))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 이미 출석이 완료되었습니다. 수정 기능을 이용하세요.");
    }

    @Test
    void 시작_시간으로부터_5분_초과는_지각으로_처리한다() {
        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(10, 6);
        AttendanceCheck attendanceCheck = new AttendanceCheck();

        attendanceCheck.attend(today, nickname, attendanceTime);
        final var expected = "지각";
        assertEquals(expected, attendanceCheck.getAttendanceStatus(today, nickname));
    }

    @Test
    void 시작_시간으로부터_30분_초과는_결석으로_처리한다() {
        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(10, 31);
        AttendanceCheck attendanceCheck = new AttendanceCheck();

        attendanceCheck.attend(today, nickname, attendanceTime);
        final var expected = "결석";
        assertEquals(expected, attendanceCheck.getAttendanceStatus(today, nickname));
    }

    @ParameterizedTest
    @MethodSource("provideEachDayOfWeekAttendance")
    void 요일에_따라_다른_시작_시간을_적용한다(LocalDate today, String nickname, LocalTime attendanceTime, String expected) {
        AttendanceCheck attendanceCheck = new AttendanceCheck();
        attendanceCheck.attend(today, nickname, attendanceTime);
        final var actual = attendanceCheck.getAttendanceStatus(today, nickname);
        assertEquals(expected, actual);
    }

    //주말 및 공휴일에는 출석을 받지 않는다.
    @Test
    void 주말에는_출석을_받지_않는다() {
        // 일요일
        final var sunday = LocalDate.of(2025, 2, 23);

        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(10, 31);
        AttendanceCheck attendanceCheck = new AttendanceCheck();

        assertThatThrownBy(() -> attendanceCheck.attend(sunday, nickname, attendanceTime))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    void 공휴일에는_출석을_받지_않는다() {
        //설날
        final var holiday = LocalDate.of(2025, 1, 1);

        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(10, 31);
        AttendanceCheck attendanceCheck = new AttendanceCheck();

        assertThatThrownBy(() -> attendanceCheck.attend(holiday, nickname, attendanceTime))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @Test
    void 운영_시간을_벗어난_출석은_받지_않는다() {
        final var nickname = "에드";
        final var attendanceTime = LocalTime.of(7, 59);
        AttendanceCheck attendanceCheck = new AttendanceCheck();

        assertThatThrownBy(() -> attendanceCheck.attend(today, nickname, attendanceTime))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}
