package attendance.domain;

import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTest {
    @Nested
    class isDateEquals {
        @DisplayName("날짜가_같으면_true_를_반환한다")
        @Test
        void should_ReturnTrue_WhenDateIsSame() {
            //given
            LocalDate attendanceDate = LocalDate.of(2024, 12, 26);
            LocalTime attendanceTime = LocalTime.of(10, 0);
            Attendance attendance = new Attendance(attendanceDate, attendanceTime, ATTENDANCE);

            //when
            boolean result = attendance.isDateEquals(attendanceDate);

            //then
            assertThat(result).isTrue();
        }

        @DisplayName("날짜가_다르면_false_를_반환한다")
        @Test
        void should_ReturnTrue_WhenDateIsNotSame() {
            //given
            LocalDate attendanceDate = LocalDate.of(2024, 12, 26);
            LocalTime attendanceTime = LocalTime.of(10, 0);
            Attendance attendance = new Attendance(attendanceDate, attendanceTime, ATTENDANCE);

            //when
            boolean result = attendance.isDateEquals(attendanceDate.plusDays(1));

            //then
            assertThat(result).isFalse();
        }
    }

    @DisplayName("주어진_날짜가_출석_날짜와_같은_연도_월인지_여부를_반환할_수_있다")
    @CsvSource(value = {"2024-12-02:True", "2024-11-02:False", "2025-12-22:False"}, delimiterString = ":")
    @ParameterizedTest
    void isYearMonthEquals(LocalDate date, boolean expected) {
        //given
        LocalDate attendanceDate = LocalDate.of(2024, 12, 26);
        LocalTime attendanceTime = LocalTime.of(10, 0);
        Attendance attendance = new Attendance(attendanceDate, attendanceTime, ATTENDANCE);

        //when
        boolean result = attendance.isYearMonthEquals(date);

        //then
        assertThat(result).isEqualTo(expected);
    }
}
