import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceTimeTest {

    @ParameterizedTest
    @CsvSource({
            "7, 59", "23, 1"
    })
    void 출석시간_객체를_생성할_때_운영시간이_아닌_경우_예외를_발생시킨다(int hour, int minute) {
        //given
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 2, hour, minute);
        //when & then
        Assertions.assertThatThrownBy(() -> new AttendanceTime(checkInTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("운영시간이 아니면 출석할 수 없습니다.");
    }
}
