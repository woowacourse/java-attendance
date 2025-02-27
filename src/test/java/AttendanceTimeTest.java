import attendance.model.AttendanceTime;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceTimeTest {

    @Test
    void 입력_받은_날짜가_등교_날짜가_아닐_경우_예외를_발생한다() {

        // given
        LocalDate localdate = LocalDate.of(2025, 3, 1);
        int hour = 10, minute = 10;

        // when & then
        Assertions.assertThatThrownBy(() -> new AttendanceTime(localdate, hour, minute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등교 날짜가 아닙니다.");
    }
}
