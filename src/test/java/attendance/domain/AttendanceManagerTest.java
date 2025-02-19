package attendance.domain;

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
            String result = AttendanceManager.checkAttendanceResult(DayOfWeek.TUESDAY, LocalTime.of(8, 12));
            assertThat(result).isEqualTo("출석");
        }

        @Test
        void checkAttendanceResult8() {
            String result = AttendanceManager.checkAttendanceResult(DayOfWeek.TUESDAY, LocalTime.of(10, 5));
            assertThat(result).isEqualTo("출석");
        }

        @Test
        void checkAttendanceResult9() {
            String result = AttendanceManager.checkAttendanceResult(DayOfWeek.TUESDAY, LocalTime.of(10, 6));
            assertThat(result).isEqualTo("지각");
        }

        @Test
        void checkAttendanceResult10() {
            String result = AttendanceManager.checkAttendanceResult(DayOfWeek.TUESDAY, LocalTime.of(10, 30));
            assertThat(result).isEqualTo("지각");
        }


        @Test
        void checkAttendanceResult11() {
            String result = AttendanceManager.checkAttendanceResult(DayOfWeek.TUESDAY, LocalTime.of(10, 31));
            assertThat(result).isEqualTo("결석");
        }

        @Test
        void checkAttendanceResult12() {
            String result = AttendanceManager.checkAttendanceResult(DayOfWeek.TUESDAY, LocalTime.of(10, 31));
            assertThat(result).isEqualTo("결석");
        }

        @Test
        void checkAttendanceResult13() {
            String result = AttendanceManager.checkAttendanceResult(DayOfWeek.MONDAY, LocalTime.of(12, 30));
            assertThat(result).isEqualTo("출석");
        }

        @Test
        void checkAttendanceResult133() {
            String result = AttendanceManager.checkAttendanceResult(DayOfWeek.MONDAY, LocalTime.of(13, 5));
            assertThat(result).isEqualTo("출석");
        }

        @Test
        void checkAttendanceResult14() {
            String result = AttendanceManager.checkAttendanceResult(DayOfWeek.MONDAY, LocalTime.of(13, 6));
            assertThat(result).isEqualTo("지각");
        }

        @Test
        void checkAttendanceResult144() {
            String result = AttendanceManager.checkAttendanceResult(DayOfWeek.MONDAY, LocalTime.of(13, 30));
            assertThat(result).isEqualTo("지각");
        }

        @Test
        void checkAttendanceResult15() {
            String result = AttendanceManager.checkAttendanceResult(DayOfWeek.MONDAY, LocalTime.of(13, 31));
            assertThat(result).isEqualTo("결석");
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
