import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceTime;
import domain.AttendanceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
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
        assertThat(type).isEqualTo(expected);
    }

    @Test
    void 결석기준시간을_계산한다() {
        //given
        LocalDate day = LocalDate.of(2024, 12, 3);
        LocalTime expected = LocalTime.of(10, 30);
        //when
        LocalTime actual = AttendanceType.calculateAbsenceStandardTime(day);
        //then
        assertThat(actual).isEqualTo(expected);
    }
}
