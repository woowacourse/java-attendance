import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTypeCounterTest {

    @Test
    @DisplayName("출석의 타입에 따라 개수를 적절히 센다")
    void test2() {
        // given
        Crew crew = new Crew("히로");
        LocalDate requestedDate = LocalDate.of(2024, 12, 10);
        List<AttendanceHistory> attendanceHistories = List.of(
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 2, 10, 0)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 3, 10, 1)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 5, 10, 31)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 6, 15, 0)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 9, 13, 6))
        );

        Map<LocalDate, AttendanceType> expected = Map.of(
                LocalDate.of(2024, 12, 2), AttendanceType.PRESENT,
                LocalDate.of(2024, 12, 3), AttendanceType.PRESENT,
                LocalDate.of(2024, 12, 4), AttendanceType.ABSENCE,
                LocalDate.of(2024, 12, 5), AttendanceType.ABSENCE,
                LocalDate.of(2024, 12, 6), AttendanceType.ABSENCE,
                LocalDate.of(2024, 12, 9), AttendanceType.LATE
        );

        // when
        Map<LocalDate, AttendanceType> actual = AttendanceTypeCounter.count(requestedDate, attendanceHistories);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("기록이 없는 날에 대해 결석으로 간주한다.")
    void test3() {
        Crew crew = new Crew("히로");
        LocalDate requestedDate = LocalDate.of(2024, 12, 4);
        List<AttendanceHistory> attendanceHistories = List.of(
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 2, 10, 0))
        );

        Map<LocalDate, AttendanceType> result = Map.of(
                LocalDate.of(2024, 12, 2), AttendanceType.PRESENT,
                LocalDate.of(2024, 12, 3), AttendanceType.ABSENCE
        );

        // when
        Map<LocalDate, AttendanceType> actual = AttendanceTypeCounter.count(requestedDate, attendanceHistories);

        // then
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @DisplayName("기록이 없지만 주말인 경우는 기록에 포함하지 않는다.")
    void test4() {
        Crew crew = new Crew("히로");
        LocalDate requestedDate = LocalDate.of(2024, 12, 4);
        List<AttendanceHistory> attendanceHistories = List.of(
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 2, 10, 0))
        );

        // when
        Map<LocalDate, AttendanceType> actual = AttendanceTypeCounter.count(requestedDate, attendanceHistories);

        // then
        assertThat(actual.keySet()).doesNotContain(LocalDate.of(2024, 12, 1));
    }
}
