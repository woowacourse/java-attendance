import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTypeCounterTest {

    @Test
    @DisplayName("출석의 타입에 따라 개수를 적절히 센다")
    void test2() {
        Crew crew = new Crew("히로");
        LocalDateTime requestedDate = LocalDateTime.of(2024, 12, 10, 0, 0);
        List<AttendanceHistory> attendanceHistories = List.of(
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 2, 10, 0)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 3, 10, 1)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 5, 10, 31)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 6, 15, 0)),
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 9, 13, 6))
        );

        Map<LocalDateTime, AttendanceType> expected = Map.of(
                LocalDateTime.of(2024, 12, 2, 10, 0), AttendanceType.PRESENT,
                LocalDateTime.of(2024, 12, 3, 10, 1), AttendanceType.PRESENT,
                LocalDateTime.of(2024, 12, 4, 0, 0), AttendanceType.NO_DATA,
                LocalDateTime.of(2024, 12, 5, 10, 31), AttendanceType.ABSENCE,
                LocalDateTime.of(2024, 12, 6, 15, 0), AttendanceType.ABSENCE,
                LocalDateTime.of(2024, 12, 9, 13, 6), AttendanceType.LATE
        );

        Map<LocalDateTime, AttendanceType> actual = AttendanceTypeCounter.count(requestedDate.toLocalDate(),
                attendanceHistories);

        assertThat(actual).isEqualTo(expected);
    }


    @Test
    @DisplayName("기록이 없는 날에 대해 NO_DATA 를 AttendanceType 으로 반환한다.")
    void test3() {
        Crew crew = new Crew("히로");
        LocalDateTime requestedDate = LocalDateTime.of(2024, 12, 4, 0, 0);
        List<AttendanceHistory> attendanceHistories = List.of(
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 2, 10, 0))
        );

        Map<LocalDateTime, AttendanceType> result = Map.of(
                LocalDateTime.of(2024, 12, 2, 10, 0), AttendanceType.PRESENT,
                LocalDateTime.of(2024, 12, 3, 0, 0), AttendanceType.NO_DATA
        );

        Map<LocalDateTime, AttendanceType> actual = AttendanceTypeCounter.count(requestedDate.toLocalDate(),
                attendanceHistories);

        assertThat(actual).isEqualTo(result);
    }

    @Test
    @DisplayName("기록이 없지만 주말인 경우는 기록에 포함하지 않는다.")
    void test4() {
        Crew crew = new Crew("히로");
        LocalDateTime requestedDate = LocalDateTime.of(2024, 12, 4, 0, 0);
        List<AttendanceHistory> attendanceHistories = List.of(
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 2, 10, 0))
        );

        Map<LocalDateTime, AttendanceType> actual = AttendanceTypeCounter.count(requestedDate.toLocalDate(),
                attendanceHistories);

        assertThat(actual.keySet()).doesNotContain(LocalDateTime.of(2024, 12, 1, 0, 0));
    }

    @Test
    @DisplayName("기록이 없는 경우는 NO_DATA 를 AttendanceType 으로 저장한다.")
    void test5() {
        // given
        Crew crew = new Crew("히로");
        LocalDateTime requestedDate = LocalDateTime.of(2024, 12, 5, 0, 0);
        List<AttendanceHistory> attendanceHistories = List.of(
                new AttendanceHistory(crew, LocalDateTime.of(2024, 12, 2, 10, 0))
        );

        // when
        Map<LocalDateTime, AttendanceType> attendanceTypeOfDates = AttendanceTypeCounter.count(
                requestedDate.toLocalDate(),
                attendanceHistories);
        AttendanceType actual = attendanceTypeOfDates.get(LocalDateTime.of(2024, 12, 3, 0, 0));

        assertThat(actual).isEqualTo(AttendanceType.NO_DATA);
    }

}
