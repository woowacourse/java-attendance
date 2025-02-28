package domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceDateTest {

    @Test
    @DisplayName("등교일이 아니므로 예외가 발생한다.")
    void test1() {
        //given
        final LocalDate localDate = LocalDate.of(2024, 12, 25);
        //when
        //then
        assertThatIllegalArgumentException().isThrownBy(() -> new AttendanceDate(localDate));

    }

    private class AttendanceDate {

        private final LocalDate localDate;

        public AttendanceDate(final LocalDate localDate) {
            this.localDate = localDate;
        }
    }
}
