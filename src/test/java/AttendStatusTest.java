import domain.Attend;
import domain.AttendStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendStatusTest {

    private static Attend createAttend(final String date, final String time) {
        return new Attend(LocalDate.parse(date), LocalTime.parse(time));
    }

    private static Attend createAttendOnlyDate(final String date) {
        return new Attend(LocalDate.parse(date));
    }

    private static Stream<Arguments> provideAttendAndStatus() {
        return Stream.of(
                Arguments.of(createAttend("2024-12-02", "13:05"), AttendStatus.ATTEND),
                Arguments.of(createAttend("2024-12-03", "10:05"), AttendStatus.ATTEND),
                Arguments.of(createAttend("2024-12-02", "13:06"), AttendStatus.LATE),
                Arguments.of(createAttend("2024-12-03", "10:06"), AttendStatus.LATE),
                Arguments.of(createAttend("2024-12-02", "13:31"), AttendStatus.ABSENCE),
                Arguments.of(createAttend("2024-12-03", "10:31"), AttendStatus.ABSENCE),
                Arguments.of(createAttendOnlyDate("2024-12-02"), AttendStatus.ABSENCE),
                Arguments.of(createAttendOnlyDate("2024-12-03"), AttendStatus.ABSENCE)
        );
    }

    @ParameterizedTest
    @MethodSource("provideAttendAndStatus")
    @DisplayName("요일에 따른 출석 상태 판정 기능")
    void checkAttendStatusUsingDateAndTime(Attend attend, AttendStatus expected) {
        //when
        AttendStatus actual = AttendStatus.checkAttendStatus(attend);

        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
