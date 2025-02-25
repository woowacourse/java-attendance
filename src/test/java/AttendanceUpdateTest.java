import static org.assertj.core.api.Assertions.assertThat;

import domain.Attendance;
import domain.Day;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceUpdateTest {

    public static Stream<Arguments> getTimesAndIsLate() {
        return Stream.of(
                Arguments.of(LocalTime.of(10, 5), LocalTime.of(10, 6), true),
                Arguments.of(LocalTime.of(10, 15), LocalTime.of(9, 58), false)
        );
    }

    public static Stream<Arguments> getTimesAndIsAbsent() {
        return Stream.of(
                Arguments.of(LocalTime.of(10, 5), LocalTime.of(10, 31), true),
                Arguments.of(LocalTime.of(10, 40), LocalTime.of(9, 58), false)
        );
    }

    @Test
    void 수정날짜와_시간_입력시_기존_출석시간이_변경된다() {

        final var date = LocalDate.of(2024, 12, 3);

        Day day = new Day(date);
        Attendance attendance = new Attendance(day, LocalTime.of(10, 7));

        final var modifiedTime = LocalTime.of(9, 58);

        attendance.updateAttendanceTime(modifiedTime);

        assertThat(attendance.getAttendanceTime()).isEqualTo(LocalTime.of(9, 58));
    }

    @ParameterizedTest
    @MethodSource("getTimesAndIsLate")
    void 수정된_시간에_따라_지각_상태가_변경된다(LocalTime originTime, LocalTime modifiedTime, boolean isLate) {

        final var date = LocalDate.of(2024, 12, 3);

        Day day = new Day(date);
        Attendance attendance = new Attendance(day, originTime);

        attendance.updateAttendanceTime(modifiedTime);

        assertThat(attendance.getLate()).isEqualTo(isLate);
    }

    @ParameterizedTest
    @MethodSource("getTimesAndIsAbsent")
    void 수정된_출석시간에_따라_결석_상태가_변경된다(LocalTime originTime, LocalTime modifiedTime, boolean isAbsent) {

        final var date = LocalDate.of(2024, 12, 3);

        Day day = new Day(date);
        Attendance attendance = new Attendance(day, originTime);

        attendance.updateAttendanceTime(modifiedTime);

        assertThat(attendance.getAbsent()).isEqualTo(isAbsent);
    }
}
