import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendancePolicyTest {

    private LocalDate weekday;
    private LocalDate weekend;

    @BeforeEach
    void setUp() {
        weekday = LocalDate.of(2024, 12, 13);
        weekend = LocalDate.of(2024, 12, 14);
    }

    @Test
    void 출석_날짜가_주말이면_예외가_발생한다() {
        assertThatThrownBy(() -> new AttendanceRecord(() -> weekend).attend("09:59"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"07:59", "23:01"})
    void 캠퍼스_운영_시간이_아니면_예외가_발생한다(String time) {
        assertThatThrownBy(() -> new AttendanceRecord(() -> weekday).attend(time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }
}
