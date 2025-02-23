package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTypeTest {

    @ParameterizedTest
    @CsvSource({
            "2024, 12, 2, 13, 0, 0, SUCCESS",
            "2024, 12, 3, 10, 0, 0, SUCCESS",
            "2024, 12, 3, 10, 5, 1, BE_LATE",
            "2024, 12, 3, 10, 30, 1, ABSENCE",
    })
    void 출석시간으로_출석_결과를_결정한다(int year, int month, int date, int hour, int minute, int second, AttendanceType expected) {
        //given
        LocalDateTime checkInTime = LocalDateTime.of(year, month, date, hour, minute, second);
        //when
        AttendanceType actual = AttendanceType.calculateType(checkInTime);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 1, 1",
            "0, 3, 1, 2",
            "0, 4, 1, 2",
            "0, 6, 1, 3",
    })
    void 지각횟수를_결석횟수로_변환하여_총결석횟수를_계산한다(int successCount, int beLateCount, int absenceCount, int expected) {
        //given
        Map<AttendanceType, Integer> counts = Map.of(
                AttendanceType.SUCCESS, successCount,
                AttendanceType.BE_LATE, beLateCount,
                AttendanceType.ABSENCE, absenceCount
        );
        //when
        int actual = AttendanceType.calculateConvertedAbsenceCount(counts);
        //then
        assertThat(actual).isEqualTo(expected);
    }
}