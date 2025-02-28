import exception.InvalidDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendanceDateTest {
    @DisplayName("크리스마스는 출석 날짜로 생성할 수 없다.")
    @Test
    void test1() {
        // given
        LocalDate christmas = LocalDate.of(2024, 12, 25);

        // when & then
        assertThatThrownBy(() -> {
            new AttendanceDate(christmas);
        }).isInstanceOf(InvalidDateException.class);
    }
}
