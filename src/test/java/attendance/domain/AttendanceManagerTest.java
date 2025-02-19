package attendance.domain;

import static attendance.domain.AttendanceType.ABSENT;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static attendance.domain.AttendanceType.LATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceManagerTest {

    @Nested
    class CheckAttendanceResult {
        @Test
        void checkAttendanceResult7() {
            AttendanceType result = AttendanceManager.checkAttendanceType(DayOfWeek.TUESDAY, LocalTime.of(8, 12));
            assertThat(result).isEqualTo(ATTENDANCE);
        }

        @Test
        void checkAttendanceResult8() {
            AttendanceType result = AttendanceManager.checkAttendanceType(DayOfWeek.TUESDAY, LocalTime.of(10, 5));
            assertThat(result).isEqualTo(ATTENDANCE);
        }

        @Test
        void checkAttendanceResult9() {
            AttendanceType result = AttendanceManager.checkAttendanceType(DayOfWeek.TUESDAY, LocalTime.of(10, 6));
            assertThat(result).isEqualTo(LATE);
        }

        @Test
        void checkAttendanceResult10() {
            AttendanceType result = AttendanceManager.checkAttendanceType(DayOfWeek.TUESDAY, LocalTime.of(10, 30));
            assertThat(result).isEqualTo(LATE);
        }


        @Test
        void checkAttendanceResult11() {
            AttendanceType result = AttendanceManager.checkAttendanceType(DayOfWeek.TUESDAY, LocalTime.of(10, 31));
            assertThat(result).isEqualTo(ABSENT);
        }

        @Test
        void checkAttendanceResult12() {
            AttendanceType result = AttendanceManager.checkAttendanceType(DayOfWeek.TUESDAY, LocalTime.of(10, 31));
            assertThat(result).isEqualTo(ABSENT);
        }

        @Test
        void checkAttendanceResult13() {
            AttendanceType result = AttendanceManager.checkAttendanceType(DayOfWeek.MONDAY, LocalTime.of(12, 30));
            assertThat(result).isEqualTo(ATTENDANCE);
        }

        @Test
        void checkAttendanceResult133() {
            AttendanceType result = AttendanceManager.checkAttendanceType(DayOfWeek.MONDAY, LocalTime.of(13, 5));
            assertThat(result).isEqualTo(ATTENDANCE);
        }

        @Test
        void checkAttendanceResult14() {
            AttendanceType result = AttendanceManager.checkAttendanceType(DayOfWeek.MONDAY, LocalTime.of(13, 6));
            assertThat(result).isEqualTo(LATE);
        }

        @Test
        void checkAttendanceResult144() {
            AttendanceType result = AttendanceManager.checkAttendanceType(DayOfWeek.MONDAY, LocalTime.of(13, 30));
            assertThat(result).isEqualTo(LATE);
        }

        @Test
        void checkAttendanceResult15() {
            AttendanceType result = AttendanceManager.checkAttendanceType(DayOfWeek.MONDAY, LocalTime.of(13, 31));
            assertThat(result).isEqualTo(ABSENT);
        }
    }

    @Test
    void check_weekday() {
        assertThatCode(() -> AttendanceManager.checkHoliday(LocalDate.of(2024, 12, 26)))
                .doesNotThrowAnyException();
    }

    @Test
    void check_holiday_1() {
        assertThatThrownBy(() -> AttendanceManager.checkHoliday(LocalDate.of(2024, 12, 22)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등교일이 아닙니다");
    }

    @Test
    void check_holiday_2() {
        assertThatThrownBy(() -> AttendanceManager.checkHoliday(LocalDate.of(2024, 12, 25)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등교일이 아닙니다");
    }

}
