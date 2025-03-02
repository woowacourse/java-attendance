package domain.policy;

import static domain.policy.AttendanceState.ABSENT;
import static domain.policy.AttendanceState.ATTENDANCE;
import static domain.policy.AttendanceState.LATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AbsentPolicyTest {
    AbsentPolicy absentPolicy;

    @BeforeEach
    void setUp() {
        absentPolicy = new AbsentPolicy();
    }

    @Test
    @DisplayName("주말에 출석하려고 하는 경우 예외가 발생한다")
    public void validateWeekendTest() {
        //given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 8, 10, 31);

        //when-then
        assertThatThrownBy(() -> absentPolicy.checkAttendanceStatus(attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("공휴일에 출석하려고 하는 경우 예외가 발생한다")
    public void validateHolidayTest() {
        //given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 25, 10, 31);

        //when-then
        assertThatThrownBy(() -> absentPolicy.checkAttendanceStatus(attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("결석 처리를 할 수 있다")
    public void checkAbsentStatusTest() {
        LocalDateTime educationDateTime = LocalDateTime.of(2024, 12, 10, 10, 31);

        assertThat(absentPolicy.checkAttendanceStatus(educationDateTime)).isEqualTo(ABSENT);
    }

    @ParameterizedTest
    @DisplayName("시작 시간부터 5분 이하이면 출석이다")
    @MethodSource("provideDateTimeForAttendancePolicy")
    public void checkAttendanceStatusTest(LocalDateTime educationDateTime) {
        assertThat(absentPolicy.checkAttendanceStatus(educationDateTime)).isEqualTo(ATTENDANCE);
    }

    private static Stream<Arguments> provideDateTimeForAttendancePolicy() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 2, 9, 0)),
                Arguments.of(LocalDateTime.of(2024, 12, 9, 13, 5)),
                Arguments.of(LocalDateTime.of(2024, 12, 11, 9, 0)),
                Arguments.of(LocalDateTime.of(2024, 12, 10, 10, 5))
        );
    }

    @ParameterizedTest
    @DisplayName("시작 시간부터 5분 초과 30분 이하이면 지각이다")
    @MethodSource("provideDateTimeForLatePolicy")
    public void checkLateStatusTest(LocalDateTime educationDateTime) {
        assertThat(absentPolicy.checkAttendanceStatus(educationDateTime)).isEqualTo(LATE);
    }

    private static Stream<Arguments> provideDateTimeForLatePolicy() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 10, 10, 6)),
                Arguments.of(LocalDateTime.of(2024, 12, 11, 10, 30)),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 6)),
                Arguments.of(LocalDateTime.of(2024, 12, 9, 13, 30))
        );
    }

}
