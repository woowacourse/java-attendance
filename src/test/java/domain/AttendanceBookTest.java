package domain;

import static constants.TestTimeMaker.EXCEPT_MONDAY_ATTEND;
import static constants.TestTimeMaker.MONDAY_ATTEND;
import static constants.TestTimeMaker.NON_OPERATING_TIME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ErrorCode;

class AttendanceBookTest {
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
    @DisplayName("출석부에_기존이름의_존재여부_확인")
    void 출석부에_기존이름의_존재여부_확인() {
        assertThat(attendanceBook.checkCrewAlreadyExists("쿠키")).isEqualTo(true);
        assertThat(attendanceBook.checkCrewAlreadyExists("없음")).isEqualTo(false);
    }

    @Test
    @DisplayName("이미_해당_날짜에_출석한_경우_예외를_출력한다")
    void 이미_해당_날짜에_출석한_경우_예외를_출력한다() {
        assertThatThrownBy(
                () -> attendanceBook.validateDateAlreadyExistsByCrewName("쿠키", LocalDate.of(2024, 12, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.CHECK_ATTENDANCE_ALREADY_EXISTS.getFormat());
    }

    @Test
    @DisplayName("캠퍼스_운영_시간이_아닌_경우_예외를_출력한다")
    void 캠퍼스_운영_시간이_아닌_경우_예외를_출력한다() {
        assertThatThrownBy(
                () -> attendanceBook.validateIsInOperationHour(NON_OPERATING_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.TIME_NOT_IN_OPERATION_HOUR.getFormat());
    }
}