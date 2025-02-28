import exception.InvalidDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

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

    @DisplayName("주말은 출석 날짜로 생성할 수 없다.")
    @Test
    void test2() {
        // given
        List<LocalDate> holidays = List.of(
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 2)
        );

        for (LocalDate holiday : holidays) {
            // when & then
            assertThatThrownBy(() -> {
                new AttendanceDate(holiday);
            }).isInstanceOf(InvalidDateException.class);
        }
    }
}
