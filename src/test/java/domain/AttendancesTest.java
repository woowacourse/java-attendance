package domain;

import except.AttendanceException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import strategy.TestAttendanceNowDateStrategy;

public class AttendancesTest {

    @Nested
    class AddAttendance {
        @Test
        @DisplayName("닉네임과 등교 시간을 입력해 출석할 수 있다")
        void addAttendance() {
            String nickname = "투다";
            LocalTime time = LocalTime.of(8, 0);
            LocalDate date = LocalDate.of(2024, 12, 3);
            CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceNowDateStrategy(date));
            crewAttendances.addAttendance(nickname, time);

            Assertions.assertThat(
                    crewAttendances.crewAttendance(nickname, date).attendanceStatus()
            ).isEqualTo(AttendanceStatus.ATTENDANCE);
        }

        @ParameterizedTest
        @MethodSource("weekendDate")
        @DisplayName("휴일에 출석시 예외가 발생한다.")
        void addAttendanceAtWeekend(LocalTime time, LocalDate date, String nickname) {
            CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceNowDateStrategy(date));

            Assertions.assertThatThrownBy(() -> crewAttendances.addAttendance(nickname, time))
                    .hasMessageContaining("휴일에는 출석할 수 없습니다.")
                    .isInstanceOf(AttendanceException.class);
        }

        private static Stream<Arguments> weekendDate() {
            return Stream.of(
                    Arguments.arguments(
                            LocalTime.of(8, 0),
                            LocalDate.of(2024, 12, 1),
                            "투다"
                    ),
                    Arguments.arguments(
                            LocalTime.of(8, 0),
                            LocalDate.of(2024, 12, 21),
                            "투다"
                    )
            );
        }

        private static Stream<Arguments> notSchoolRunningDate() {
            return Stream.of(
                    Arguments.arguments(
                            LocalTime.of(8, 0),
                            LocalDate.of(2025, 3, 24),
                            "투다"
                    ),
                    Arguments.arguments(
                            LocalTime.of(8, 0),
                            LocalDate.of(2024, 11, 1),
                            "투다"
                    )
            );
        }

        @ParameterizedTest
        @MethodSource("notSchoolRunningDate")
        @DisplayName("2024년 12월이 아닐시 예외가 발생한다")
        void addAttendanceNotSchoolRunning(LocalTime time, LocalDate date, String nickname) {
            CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceNowDateStrategy(date));

            Assertions.assertThatThrownBy(() -> crewAttendances.addAttendance(nickname, time))
                    .hasMessageContaining("2024년 12월에만 출석할 수 있습니다.")
                    .isInstanceOf(AttendanceException.class);
        }
    }
}
