package attendance.domain;

import attendance.util.FormattedErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("출석 테스트")
public class AttendanceTest {

    @Test
    void 주말에_출석을_하면_예외가_발생한다() {
        LocalDate saturday = LocalDate.of(2024, 12, 14);
        LocalDate sunday = LocalDate.of(2024, 12, 15);

        assertAll(
                () -> assertThatThrownBy(() -> new Attendance(saturday))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage(FormattedErrorMessage.INVALID_ATTENDANCE_ERROR.getDateFormatMessage(saturday)),

                () -> assertThatThrownBy(() -> new Attendance(sunday))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage(FormattedErrorMessage.INVALID_ATTENDANCE_ERROR.getDateFormatMessage(sunday))
        );
    }

    @Test
    void 공휴일에_출석을_하면_예외가_발생한다() {
        LocalDate holiday = LocalDate.of(2024, 12, 25);

        assertThatThrownBy(() -> new Attendance(holiday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(FormattedErrorMessage.INVALID_ATTENDANCE_ERROR.getDateFormatMessage(holiday));
    }
}
