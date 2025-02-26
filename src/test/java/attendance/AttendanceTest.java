package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
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
            Attendance attendance = new Attendance(date, "ATTENDANCE");

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
            Attendance attendance = new Attendance(date, "ATTENDANCE");

            //when
            boolean result = attendance.isDateEquals(date.plusDays(1));

            //then
            assertThat(result).isFalse();
        }
    }
}
