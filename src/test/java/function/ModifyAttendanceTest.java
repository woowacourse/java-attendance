package function;

import static constants.TestTimeMaker.EXCEPT_MONDAY_ATTEND;
import static constants.TestTimeMaker.MONDAY_ATTEND;
import static constants.TestTimeMaker.MONDAY_LATE;
import static constants.TestTimeMaker.NON_OPERATING_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendanceBook;
import domain.AttendanceStatus;
import dto.ModifyAttendanceResponse;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ErrorCode;

public class ModifyAttendanceTest {
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
    @DisplayName("출석을_수정하기_위해서는_닉네임_수정하려는_날짜_등교시간을_입력해야_한다")
    void 출석을_수정하기_위해서는_닉네임_수정하려는_날짜_등교시간을_입력해야_한다() {
        ModifyAttendanceResponse modifyResponse = attendanceBook.modifyAttendance("쿠키",
                Map.of(LocalDate.of(2024, 12, 2), MONDAY_LATE));

        assertThat(modifyResponse.date()).isEqualTo(LocalDate.of(2024, 12, 2));

        assertThat(modifyResponse.originalTime()).isEqualTo(MONDAY_ATTEND);
        assertThat(modifyResponse.modifiedTime()).isEqualTo(MONDAY_LATE);

        assertThat(modifyResponse.originalStatus()).isEqualTo(AttendanceStatus.ATTEND);
        assertThat(modifyResponse.modifiedStatus()).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("등록되지_않는_닉네임의_경우_예외를_출력한다")
    void 등록되지_않는_닉네임의_경우_예외를_출력한다() {
        assertThatThrownBy(
                () -> attendanceBook.modifyAttendance("없음", Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.NICKNAME_NOT_FOUND.getFormat());
    }

    @Test
    @DisplayName("수정하려는_날짜의_기록이_존재하지_않을_경우_에러를_출력한다")
    void 수정하려는_날짜의_기록이_존재하지_않을_경우_에러를_출력한다() {
        assertThatThrownBy(
                () -> attendanceBook.modifyAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 5), EXCEPT_MONDAY_ATTEND)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.ATTENDANCE_RECORD_NOT_EXISTS_FORMAT.format(5));
    }

    @Test
    @DisplayName("출석_수정_시_캠퍼스_운영_시간이_아닌_경우_예외를_출력한다")
    void 출석_수정_시_캠퍼스_운영_시간이_아닌_경우_예외를_출력한다() {
        assertThatThrownBy(
                () -> attendanceBook.modifyAttendance("쿠키", Map.of(LocalDate.of(2024, 12, 4), NON_OPERATING_TIME)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.TIME_NOT_IN_OPERATION_HOUR.getFormat());
    }
}