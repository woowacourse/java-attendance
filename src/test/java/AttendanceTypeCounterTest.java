import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTypeCounterTest {
    @Test
    @DisplayName("출석의 타입에 따라 개수를 적절히 센다")
    void test1() {
        // given
        Crew crew = new Crew("히로");
        List<AttendanceHistory> attendanceHistories = List.of(
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 2, 10, 0)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 3, 10, 1)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 4, 10, 6)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 5, 10, 31)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 6, 15, 0)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 9, 13, 31))
        );

        // when
        Map<AttendanceType, Integer> result = AttendanceTypeCounter.count(attendanceHistories);

        // then
        assertAll(
                () -> assertThat(result.get(AttendanceType.LATE)).isEqualTo(1),
                () -> assertThat(result.get(AttendanceType.ABSENCE)).isEqualTo(3)
        );
    }
}
