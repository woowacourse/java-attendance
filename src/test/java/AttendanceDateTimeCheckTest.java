import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceDateTimeCheckTest {

    @Nested
    class ExcludeMonday {
        @DisplayName("시작 시간 5분 초과 출석은 지각이다.")
        @Test
        void start_time_over_five_minute() {
            // given
            AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();

            // when
            AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6));

            // then
            Assertions.assertThat(policy)
                    .isEqualTo(AttendPolicy.LATE);
        }

        @DisplayName("시작 시간 30분 초과 출석은 결석이다.")
        @Test
        void start_time_over_thirty() {
            // given
            AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();

            // when
            AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 3), LocalTime.of(10, 31));

            // then
            Assertions.assertThat(policy)
                    .isEqualTo(AttendPolicy.ABSENT);
        }

        @DisplayName("시작 시간으로부터 5분까지는 출석이다.")
        @Test
        void start_time_under_five_minute() {
            // given
            AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();

            // when
            AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 3), LocalTime.of(10, 1));

            // then
            Assertions.assertThat(policy)
                    .isEqualTo(AttendPolicy.ATTEND);
        }

        @DisplayName("시작 시간 이전에 출석해도 출석이다.")
        @Test
        void under_start_time() {
            // given
            AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();

            // when
            AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 3), LocalTime.of(9, 57));

            // then
            Assertions.assertThat(policy)
                    .isEqualTo(AttendPolicy.ATTEND);
        }

        @DisplayName("시작 시간이 한참지나 출석하면 결석이다.")
        @Test
        void start_time_too_far() {
            // given
            AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();

            // when
            AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 3), LocalTime.of(12, 0));

            // then
            Assertions.assertThat(policy)
                    .isEqualTo(AttendPolicy.ABSENT);
        }
    }

    @Nested
    class Monday {
        @DisplayName("월요일은 13시 5분 초과 시 지각이다.")
        @Test
        void start_time_over_five_minute() {
            // given
            AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();

            // when
            AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 2), LocalTime.of(13, 6));

            // then
            Assertions.assertThat(policy)
                    .isEqualTo(AttendPolicy.LATE);
        }

        @DisplayName("월요일은 13시 30분 초과 시 결석이다.")
        @Test
        void start_time_over_thirty() {
            // given
            AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();

            // when
            AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 2), LocalTime.of(13, 31));

            // then
            Assertions.assertThat(policy)
                    .isEqualTo(AttendPolicy.ABSENT);
        }

        @DisplayName("월요일은 13시로부터 5분까지는 출석이다.")
        @Test
        void start_time_under_five_minute() {
            // given
            AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();

            // when
            AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 2), LocalTime.of(13, 5));

            // then
            Assertions.assertThat(policy)
                    .isEqualTo(AttendPolicy.ATTEND);
        }

        @DisplayName("월요일은 13시 이전에 출석해도 출석이다.")
        @Test
        void under_start_time() {
            // given
            AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();

            // when
            AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 2), LocalTime.of(11, 31));

            // then
            Assertions.assertThat(policy)
                    .isEqualTo(AttendPolicy.ATTEND);
        }

        @DisplayName("월요일은 13시가 한참지나 출석하면 결석이다.")
        @Test
        void start_time_too_far() {
            // given
            AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();

            // when
            AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 2), LocalTime.of(14, 0));

            // then
            Assertions.assertThat(policy)
                    .isEqualTo(AttendPolicy.ABSENT);
        }
    }

    @DisplayName("운영시간 전에는 출석할 수 없다.")
    @Test
    void check11() {
        // given
        AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();
        LocalDate today = LocalDate.of(2024, 12, 3);
        LocalTime attendanceTime = LocalTime.of(7, 0);

        // when
        // then
        Assertions.assertThatThrownBy(() -> checker.attendanceCheck(today, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 운영 시간이 아닙니다.");
    }

    @DisplayName("운영시간 후에는 출석할 수 없다.")
    @Test
    void check12() {
        // given
        AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();
        LocalDate today = LocalDate.of(2024, 12, 3);
        LocalTime attendanceTime = LocalTime.of(23, 1);

        // when
        // then
        Assertions.assertThatThrownBy(() -> checker.attendanceCheck(today, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 운영 시간이 아닙니다.");
    }

    @DisplayName("주말에는 출석할 수 없다.")
    @Test
    void check14() {
        // given
        AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();
        LocalDate saturday = LocalDate.of(2024, 9, 7);
        LocalDate sunday = LocalDate.of(2024, 12, 8);
        LocalTime attendanceTime = LocalTime.of(10, 12);

        // when
        // then
        Assertions.assertThatThrownBy(() -> checker.attendanceCheck(saturday, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.",
                        saturday.getMonth().getValue(), saturday.getDayOfMonth(), saturday.getDayOfWeek().getDisplayName(
                                TextStyle.FULL, Locale.KOREAN)));
        Assertions.assertThatThrownBy(() -> checker.attendanceCheck(sunday, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.",
                        sunday.getMonth().getValue(), sunday.getDayOfMonth(), sunday.getDayOfWeek().getDisplayName(
                                TextStyle.FULL, Locale.KOREAN)));
    }

    @DisplayName("공휴일에는 출석할 수 없다.")
    @Test
    void check13() {
        // given
        AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();
        LocalDate holiday = LocalDate.of(2024, 12, 25);
        LocalTime attendanceTime = LocalTime.of(10, 12);

        // when
        // then
        Assertions.assertThatThrownBy(() -> checker.attendanceCheck(holiday, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.",
                        holiday.getMonth().getValue(), holiday.getDayOfMonth(), holiday.getDayOfWeek().getDisplayName(
                                TextStyle.FULL, Locale.KOREAN)));
    }
}
