import static org.assertj.core.api.Assertions.assertThat;

import domain.Attendance;
import domain.Day;
import domain.PenaltyStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceHistoryReadTest {

    private List<Attendance> attendances = List.of(
            new Attendance(new Day(LocalDate.of(2024, 12, 2)), LocalTime.of(13, 0)),
            new Attendance(new Day(LocalDate.of(2024, 12, 3)), LocalTime.of(9, 58)),
            new Attendance(new Day(LocalDate.of(2024, 12, 4)), LocalTime.of(10, 7)),
            new Attendance(new Day(LocalDate.of(2024, 12, 5)), LocalTime.of(10, 6)),
            new Attendance(new Day(LocalDate.of(2024, 12, 6)), LocalTime.of(10, 1)),
            new Attendance(new Day(LocalDate.of(2024, 12, 9)), LocalTime.of(13, 31)),
            new Attendance(new Day(LocalDate.of(2024, 12, 10)), LocalTime.of(10, 8)),
            new Attendance(new Day(LocalDate.of(2024, 12, 11)), LocalTime.of(13, 0)),
            new Attendance(new Day(LocalDate.of(2024, 12, 12)), LocalTime.of(13, 0))
    );

    public static Stream<Arguments> getNonAttendanceCount() {
        return Stream.of(
                Arguments.of(3, 0, ""),
                Arguments.of(3, 1, "경고"),
                Arguments.of(5, 2, "면담"),
                Arguments.of(6, 4, "제적")
        );
    }

    @Test
    void 출석횟수를_계산한다() {

        final var attendanceCount = attendances.stream()
                .filter(attendance -> attendance.getLate().equals(false))
                .filter(attendance -> attendance.getAbsent().equals(false))
                .count();
        assertThat(attendanceCount).isEqualTo(3);
    }

    @Test
    void 지각횟수를_계산한다() {

        final var lateCount = attendances.stream()
                .filter(attendance -> attendance.getLate().equals(true))
                .count();
        assertThat(lateCount).isEqualTo(3);
    }

    @Test
    void 결석횟수를_계산한다() {

        final var absentCount = attendances.stream()
                .filter(attendance -> attendance.getAbsent().equals(true))
                .count();
        assertThat(absentCount).isEqualTo(3);
    }

    @ParameterizedTest
    @MethodSource("getNonAttendanceCount")
    void 제적위험자_여부를_판단한다(int lateCount, int absentCount, String expected) {

        int nonAttendanceCount = absentCount + lateCount / 3;
        String actual = PenaltyStatus.getInstance(nonAttendanceCount).getName();

        assertThat(actual).isEqualTo(expected);
    }
}
