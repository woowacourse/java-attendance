package attendance.domain;

import attendance.util.FormattedErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

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

    @ParameterizedTest(name = "{index} : {1}")
    @MethodSource("getHoliday")
    void 공휴일에_출석을_하면_예외가_발생한다(LocalDate holiday, String message) {
        assertThatThrownBy(() -> new Attendance(holiday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(FormattedErrorMessage.INVALID_ATTENDANCE_ERROR.getDateFormatMessage(holiday));
    }

    static Stream<Arguments> getHoliday() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 12, 25), "2024년 크리스마스"),
                Arguments.of(LocalDate.of(2025, 1, 29), "2025년 설날"),
                Arguments.of(LocalDate.of(2025, 3, 3), "2025년 삼일절 대체공휴일"),
                Arguments.of(LocalDate.of(2025, 5, 5), "2025년 어린이날"),
                Arguments.of(LocalDate.of(2025, 6, 6), "2025년 현충일"),
                Arguments.of(LocalDate.of(2025, 10, 9), "2025년 한글날"),
                Arguments.of(LocalDate.of(2025, 12, 25), "2025년 크리스마스")
        );
    }
}
