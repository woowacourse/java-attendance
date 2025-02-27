import attendance.model.AttendanceTime;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @ParameterizedTest
    @CsvSource(value = {
            "7,59",
            "23,1"
    })
    void 입력_받은_시간이_캠퍼스_운영_시간이_아니면_예외를_발생한다(final int hour, final int minute) {

        // given
        final LocalDate localdate = LocalDate.of(2025, 2, 27);

        // when & then
        Assertions.assertThatThrownBy(() -> new AttendanceTime(localdate, hour, minute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "8,0",
            "23,0"
    })
    void 입력_받은_시간이_유효한_시간이면_출석_시간이_생성된다(final int hour, final int minute) {

        // given
        final LocalDate localdate = LocalDate.of(2025, 2, 27);

        // when & then
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> new AttendanceTime(localdate, hour, minute));
    }
}
