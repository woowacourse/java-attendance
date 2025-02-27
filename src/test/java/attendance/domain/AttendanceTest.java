package attendance.domain;

import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceTest {
    @Nested
    class isDateEquals {
        @DisplayName("날짜가_같으면_true_를_반환한다")
        @Test
        void should_ReturnTrue_WhenDateIsSame() {
            //given
            LocalDate date = LocalDate.of(2024, 12, 26);
            LocalTime time = LocalTime.of(10, 0);
            Attendance attendance = new Attendance(date, time, ATTENDANCE);

            //when
            boolean result = attendance.isDateEquals(date);

            //then
            assertThat(result).isTrue();
        }

        @DisplayName("날짜가_다르면_false_를_반환한다")
        @Test
        void should_ReturnTrue_WhenDateIsNotSame() {
            //given
            LocalDate date = LocalDate.of(2024, 12, 26);
            LocalTime time = LocalTime.of(10, 0);
            Attendance attendance = new Attendance(date, time, ATTENDANCE);

            //when
            boolean result = attendance.isDateEquals(date.plusDays(1));

            //then
            assertThat(result).isFalse();
        }
    }
}
