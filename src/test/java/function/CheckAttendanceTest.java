package function;

import static constants.TestTimeMaker.EXCEPT_MONDAY_ATTEND;
import static constants.TestTimeMaker.MONDAY_ATTEND;
import static constants.TestTimeMaker.MONDAY_LATE;
import static constants.TestTimeMaker.NON_OPERATING_TIME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendanceBook;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ErrorCode;

public class CheckAttendanceTest {
    private AttendanceBook attendanceBook;

    @BeforeEach
    void setup() {
        attendanceBook = new AttendanceBook();
        attendanceBook.initialize("우유", Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND));
        attendanceBook.initialize("우유", Map.of(LocalDate.of(2024, 12, 3), EXCEPT_MONDAY_ATTEND));
        attendanceBook.initialize("우유", Map.of(LocalDate.of(2024, 12, 4), EXCEPT_MONDAY_ATTEND));

        attendanceBook.initialize("쿠키", Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND));
        attendanceBook.initialize("쿠키", Map.of(LocalDate.of(2024, 12, 3), EXCEPT_MONDAY_ATTEND));
        attendanceBook.initialize("쿠키", Map.of(LocalDate.of(2024, 12, 4), EXCEPT_MONDAY_ATTEND));
    }

    @Test
    @DisplayName("출석하기_위해서는_닉네임과_등교_시간을_입력_받아야_한다")
    void 출석하기_위해서는_닉네임과_등교_시간을_입력_받아야_한다() {
        attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 5), EXCEPT_MONDAY_ATTEND));
    }

    @Test
    @DisplayName("등록되지_않는_닉네임의_경우_예외를_출력한다")
    void 등록되지_않는_닉네임의_경우_예외를_출력한다() {
        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("없음", Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.NICKNAME_NOT_FOUND.getFormat());

    }

    @Test
    @DisplayName("이미_출석한_경우_다시_출석할_수_없으며_수정_기능을_이용하도록_안내해야_한다")
    void 이미_출석한_경우_다시_출석할_수_없으며_수정_기능을_이용하도록_안내해야_한다() {
        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 2), MONDAY_LATE)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.CHECK_ATTENDANCE_ALREADY_EXISTS.getFormat());
    }

    @Test
    @DisplayName("주말_및_공휴일에는_출석을_받지_않는다")
    void 주말_및_공휴일에는_출석을_받지_않는다() {
        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 25), EXCEPT_MONDAY_ATTEND)))
                .isInstanceOf(IllegalArgumentException.class) // 공휴일
                .hasMessage(ErrorCode.HOLIDAY_NOT_WORKING_DAY_FORMAT.format(25));
        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 1), EXCEPT_MONDAY_ATTEND)))
                .isInstanceOf(IllegalArgumentException.class) // 일요일
                .hasMessage(ErrorCode.SUNDAY_NOT_WORKING_DAY_FORMAT.format(1));
        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 7), EXCEPT_MONDAY_ATTEND)))
                .isInstanceOf(IllegalArgumentException.class) // 토요일
                .hasMessage(ErrorCode.SATURDAY_NOT_WORKING_DAY_FORMAT.format(7));
    }

    @Test
    @DisplayName("출석_확인시_캠퍼스_운영_시간이_아닌_경우_예외를_출력한다")
    void 출석_확인시_캠퍼스_운영_시간이_아닌_경우_예외를_출력한다() {
        assertThatThrownBy(
                () -> attendanceBook.checkAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 5), NON_OPERATING_TIME)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.TIME_NOT_IN_OPERATION_HOUR.getFormat());
    }
}