package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static attendance.domain.AttendanceType.LATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendancePolicyTest {

    private final LocalDate TUESDAY = LocalDate.of(2024, 12, 26);
    private final LocalDate MONDAY = LocalDate.of(2024, 12, 23);

    @Nested
    class CheckAttendanceHistory {
        @Test
        void checkAttendanceResult7() {
            AttendanceType result = AttendancePolicy.checkAttendanceType(TUESDAY, LocalTime.of(8, 12));
            assertThat(result).isEqualTo(ATTENDANCE);
        }

        @Test
        void checkAttendanceResult8() {
            AttendanceType result = AttendancePolicy.checkAttendanceType(TUESDAY, LocalTime.of(10, 5));
            assertThat(result).isEqualTo(ATTENDANCE);
        }

        @Test
        void checkAttendanceResult9() {
            AttendanceType result = AttendancePolicy.checkAttendanceType(TUESDAY, LocalTime.of(10, 6));
            assertThat(result).isEqualTo(LATE);
        }

        @Test
        void checkAttendanceResult10() {
            AttendanceType result = AttendancePolicy.checkAttendanceType(TUESDAY, LocalTime.of(10, 30));
            assertThat(result).isEqualTo(LATE);
        }


        @Test
        void checkAttendanceResult11() {
            AttendanceType result = AttendancePolicy.checkAttendanceType(TUESDAY, LocalTime.of(10, 31));
            assertThat(result).isEqualTo(ABSENCE);
        }

        @Test
        void checkAttendanceResult12() {
            AttendanceType result = AttendancePolicy.checkAttendanceType(TUESDAY, LocalTime.of(10, 31));
            assertThat(result).isEqualTo(ABSENCE);
        }

        @Test
        void checkAttendanceResult13() {
            AttendanceType result = AttendancePolicy.checkAttendanceType(MONDAY, LocalTime.of(12, 30));
            assertThat(result).isEqualTo(ATTENDANCE);
        }

        @Test
        void checkAttendanceResult133() {
            AttendanceType result = AttendancePolicy.checkAttendanceType(MONDAY, LocalTime.of(13, 5));
            assertThat(result).isEqualTo(ATTENDANCE);
        }

        @Test
        void checkAttendanceResult14() {
            AttendanceType result = AttendancePolicy.checkAttendanceType(MONDAY, LocalTime.of(13, 6));
            assertThat(result).isEqualTo(LATE);
        }

        @Test
        void checkAttendanceResult144() {
            AttendanceType result = AttendancePolicy.checkAttendanceType(MONDAY, LocalTime.of(13, 30));
            assertThat(result).isEqualTo(LATE);
        }

        @Test
        void checkAttendanceResult15() {
            AttendanceType result = AttendancePolicy.checkAttendanceType(MONDAY, LocalTime.of(13, 31));
            assertThat(result).isEqualTo(ABSENCE);
        }
    }

    @DisplayName("날짜가_주말_또는_공휴일이면_예외를_던진다")
    @MethodSource("returnWeekendOrHoliday")
    @ParameterizedTest
    void should_ThrowException_WhenDateIsWeekendOrHoliday(LocalDate date) {
        assertThatThrownBy(() -> AttendancePolicy.checkNotWeekendAndHoliday(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등교일이 아닙니다");
    }

    private static Stream<Arguments> returnWeekendOrHoliday() {
        return Stream.of(Arguments.arguments(LocalDate.of(2024, 12, 21)),
                Arguments.arguments(LocalDate.of(2024, 12, 22)),
                Arguments.arguments(LocalDate.of(2024, 12, 25)));
    }
}
