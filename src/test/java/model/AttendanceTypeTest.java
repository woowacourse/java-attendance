package model;

import static java.util.Map.entry;
import static model.AttendanceType.DEFAULT_TIME;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTypeTest {

    @DisplayName("출석 타입을 결정한다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-02T13:00, 출석",
            "2024-12-03T10:00, 출석",
            "2024-12-03T10:05, 출석",
            "2024-12-03T10:06, 지각",
            "2024-12-03T10:30, 지각",
            "2024-12-03T13:00, 결석",
            "2024-12-03T09:54, 출석"
    })
    void determineAttendanceTypeTest(final LocalDateTime time, final String attendanceTypeName) {
        // Given

        // When
        AttendanceType attendanceType = AttendanceType.from(time);

        // Then
        assertThat(attendanceType.name()).isEqualTo(attendanceTypeName);
    }

    @DisplayName("출석 타입별 횟수를 계산한다")
    @Test
    void countAttendanceTypeTest() {
        // Given
        List<LocalDateTime> attendanceTimes = List.of(
                LocalDateTime.of(2024, 12, 2, 9, 0),
                LocalDateTime.of(2024, 12, 3, 10, 6),
                LocalDateTime.of(2024, 12, 4, 10, 31),
                LocalDateTime.of(LocalDate.of(2024, 12, 5), DEFAULT_TIME),
                LocalDateTime.of(2024, 12, 6, 9, 30)
        );

        // When
        Map<AttendanceType, Integer> result = AttendanceType.countAttendanceType(attendanceTimes);

        // Then
        Assertions.assertThat(result).containsExactly(
                entry(AttendanceType.출석, 2),
                entry(AttendanceType.지각, 1),
                entry(AttendanceType.결석, 2)
        );
    }
}
