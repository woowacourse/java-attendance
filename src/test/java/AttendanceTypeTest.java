import domain.AttendanceTime;
import domain.AttendanceType;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceTypeTest {

    @ParameterizedTest
    @CsvSource({
            "2, 13, 5, 0, 0, SUCCESS",
            "2, 13, 5, 0, 1, LATE",
            "2, 13, 30, 0, 0, LATE",
            "2, 13, 30, 0, 1, ABSENCE",
            "3, 10, 5, 0, 0, SUCCESS",
            "3, 10, 5, 0, 1, LATE",
            "3, 10, 30, 0, 0, LATE",
            "3, 10, 30, 0, 1, ABSENCE",
    })
    void 출석시간으로_출석_결과를_결정한다(int date, int hour, int minute, int second, int nano, AttendanceType expected) {
        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, date, hour, minute, second, nano);
        AttendanceTime attendanceTime = new AttendanceTime(time);
        //when
        AttendanceType type = AttendanceType.calculateType(attendanceTime);
        //then
        Assertions.assertThat(type).isEqualTo(expected);
    }
}
