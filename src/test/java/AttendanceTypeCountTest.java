import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTypeCountTest {

    @Test
    @DisplayName("날짜에 따른 출석 타입을 이용해 각 출석 타입의 개수를 올바르게 센다")
    void test1() {
        // given
        Map<LocalDateTime, AttendanceType> attendanceTypeOfDates = Map.of(
                LocalDateTime.of(2024, 12, 23, 13, 0), AttendanceType.PRESENT,
                LocalDateTime.of(2024, 12, 24, 10, 31), AttendanceType.ABSENCE,
                LocalDateTime.of(2024, 12, 26, 10, 6), AttendanceType.LATE,
                LocalDateTime.of(2024, 12, 27, 10, 6), AttendanceType.LATE
        );

        // when
        AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.createFrom(attendanceTypeOfDates);

        // then
        Assertions.assertAll(
                () -> assertThat(attendanceTypeCount.getAbsenceCount()).isEqualTo(1),
                () -> assertThat(attendanceTypeCount.getLateCount()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("지각 횟수를 고려한 결석 횟수를 반환한다")
    void test2() {
        // given
        AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.from(1, 6);

        // when
        int adjustedAbsenceCount = attendanceTypeCount.getAdjustedAbsenceCount();

        // then
        assertThat(adjustedAbsenceCount).isEqualTo(3);
    }

}
