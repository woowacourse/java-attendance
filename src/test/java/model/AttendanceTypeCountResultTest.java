package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTypeCountResultTest {

    @DisplayName("출석 상태별 횟수를 계산한다")
    @Test
    void countAttendanceType() {
        List<AttendanceHistoryByDate> histories = List.of(
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 2),
                        LocalTime.of(13, 0),
                        AttendanceType.NONE),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 3),
                        LocalTime.of(10, 7),
                        AttendanceType.LATE),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 4),
                        LocalTime.of(10, 2),
                        AttendanceType.NONE),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 5),
                        LocalTime.of(10, 6),
                        AttendanceType.LATE),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 6),
                        LocalTime.of(10, 1),
                        AttendanceType.NONE),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 9),
                        LocalTime.MAX,
                        AttendanceType.ABSENT),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 10),
                        LocalTime.of(10, 3),
                        AttendanceType.NONE),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 11),
                        LocalTime.MAX,
                        AttendanceType.ABSENT),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 12),
                        LocalTime.MAX,
                        AttendanceType.ABSENT),
                new AttendanceHistoryByDate(
                        LocalDate.of(2024, 12, 13),
                        LocalTime.of(10, 2),
                        AttendanceType.NONE));

        Map<AttendanceType, Integer> expected = Map.of(
                AttendanceType.NONE, 5,
                AttendanceType.LATE, 2,
                AttendanceType.ABSENT, 3
        );

        assertThat(AttendanceTypeCountResult.from(histories).typeResult())
                .containsExactlyInAnyOrderEntriesOf(expected);
    }
}
