import domain.Attendance;
import domain.Day;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceHistoryReadTest {

    private List<Attendance> attendances = List.of(
            new Attendance(new Day(LocalDate.of(2024, 12, 2)), LocalTime.of(13, 0)),
            new Attendance(new Day(LocalDate.of(2024, 12, 3)), LocalTime.of(9, 58)),
            new Attendance(new Day(LocalDate.of(2024, 12, 4)), LocalTime.of(10, 2)),
            new Attendance(new Day(LocalDate.of(2024, 12, 5)), LocalTime.of(10, 6)),
            new Attendance(new Day(LocalDate.of(2024, 12, 6)), LocalTime.of(10, 1)),
            new Attendance(new Day(LocalDate.of(2024, 12, 9)), LocalTime.of(13, 31)),
            new Attendance(new Day(LocalDate.of(2024, 12, 10)), LocalTime.of(10, 8)),
            new Attendance(new Day(LocalDate.of(2024, 12, 11)), LocalTime.of(13, 0)),
            new Attendance(new Day(LocalDate.of(2024, 12, 12)), LocalTime.of(13, 0))
    );

    @Test
    void 출석횟수를_계산한다() {

        final var attendanceCount = attendances.stream()
                .filter(attendance -> attendance.toDto().getLate().equals(false))
                .filter(attendance -> attendance.toDto().getAbsent().equals(false))
                .count();
        Assertions.assertThat(attendanceCount).isEqualTo(4);
    }

    @Test
    void 지각횟수를_계산한다() {

        final var lateCount = attendances.stream()
                .filter(attendance -> attendance.toDto().getLate().equals(true))
                .count();
        Assertions.assertThat(lateCount).isEqualTo(2);
    }

    @Test
    void 결석횟수를_계산한다() {

        final var absentCount = attendances.stream()
                .filter(attendance -> attendance.toDto().getAbsent().equals(true))
                .count();
        Assertions.assertThat(absentCount).isEqualTo(3);
    }
}
