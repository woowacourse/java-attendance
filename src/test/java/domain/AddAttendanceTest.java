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
import strategy.TestAttendanceCurrentDateGenerateStrategy;

class AddAttendance {

    private static Stream<Arguments> addAttendanceTest() {
        return Stream.of(
                Arguments.arguments(
                        "투다",
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

    @Test
    @DisplayName("5분 초과시 지각")
    void addAttendanceLate() {
        String nickname = "투다";
        LocalTime time = LocalTime.of(10, 6);
        LocalDate date = LocalDate.of(2024, 12, 3);
        CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceCurrentDateGenerateStrategy(date));
        crewAttendances.addAttendance(nickname, time);
        CrewAttendance crewAttendance = crewAttendances.crewAttendance(nickname, date);
        Assertions.assertThat(crewAttendance.attendanceStatus())
                .isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("10시 5분은 출석이다")
    void addAttendanceLateTest() {
        String nickname = "투다";
        LocalTime time = LocalTime.of(10, 5);
        LocalDate date = LocalDate.of(2024, 12, 3);
        CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceCurrentDateGenerateStrategy(date));
        crewAttendances.addAttendance(nickname, time);

        Assertions.assertThat(crewAttendances.crewAttendance(nickname, date)
                .attendanceStatus()
        ).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("10시 30분은 지각이다")
    void addAttendanceLateTest2() {
        String nickname = "투다";
        LocalTime time = LocalTime.of(10, 30);
        LocalDate date = LocalDate.of(2024, 12, 3);
        CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceCurrentDateGenerateStrategy(date));
        crewAttendances.addAttendance(nickname, time);

        Assertions.assertThat(crewAttendances.crewAttendance(nickname, date)
                .attendanceStatus()
        ).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("10시 31분은 결석이다")
    void addAttendanceLateTest3() {
        String nickname = "투다";
        LocalTime time = LocalTime.of(10, 31);
        LocalDate date = LocalDate.of(2024, 12, 3);
        CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceCurrentDateGenerateStrategy(date));
        crewAttendances.addAttendance(nickname, time);

        Assertions.assertThat(crewAttendances.crewAttendance(nickname, date)
                .attendanceStatus()
        ).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    @DisplayName("23시 00분은 결석이다")
    void addAttendanceLateTest4() {
        String nickname = "투다";
        LocalTime time = LocalTime.of(23, 0);
        LocalDate date = LocalDate.of(2024, 12, 3);
        CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceCurrentDateGenerateStrategy(date));
        crewAttendances.addAttendance(nickname, time);

        Assertions.assertThat(crewAttendances.crewAttendance(nickname, date)
                .attendanceStatus()
        ).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    @DisplayName("닉네임과 등교 시간을 입력해 출석할 수 있다")
    void addAttendance() {
        String nickname = "투다";
        LocalTime time = LocalTime.of(8, 0);
        LocalDate date = LocalDate.of(2024, 12, 3);
        CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceCurrentDateGenerateStrategy(date));
        crewAttendances.addAttendance(nickname, time);

        Assertions.assertThat(crewAttendances.crewAttendance(nickname, date)
                .attendanceStatus()
        ).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Nested
    class InvaliAddAttendance {

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

        private static Stream<Arguments> notSystemRunningDate() {
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
        @MethodSource("weekendDate")
        @DisplayName("휴일에 출석시 예외가 발생한다.")
        void addAttendanceAtWeekend(LocalTime time, LocalDate date, String nickname) {
            CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceCurrentDateGenerateStrategy(date));

            Assertions.assertThatThrownBy(() -> crewAttendances.addAttendance(nickname, time))
                    .hasMessageContaining("휴일에는 출석할 수 없습니다.")
                    .isInstanceOf(AttendanceException.class);
        }

        @ParameterizedTest
        @MethodSource("notSystemRunningDate")
        @DisplayName("출석이 시스템 운영시간 외에 이루어진 경우 예외가 발생한다")
        void addAttendanceOnSystemNotRunningDate(LocalTime time, LocalDate date, String nickname) {
            CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceCurrentDateGenerateStrategy(date));

            Assertions.assertThatThrownBy(() -> crewAttendances.addAttendance(nickname, time))
                    .hasMessageContaining("2024년 12월에만 출석할 수 있습니다.")
                    .isInstanceOf(AttendanceException.class);
        }

        @Test
        @DisplayName("출석이 학교 운영시간 외에 이루어진 경우 예외가 발생한다")
        void addAttendanceNotSchoolRunning() {
            String nickname = "투다";
            LocalTime time = LocalTime.of(6, 0);
            LocalDate date = LocalDate.of(2024, 12, 3);
            CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceCurrentDateGenerateStrategy(date));

            Assertions.assertThatThrownBy(() -> crewAttendances.addAttendance(nickname, time))
                    .isInstanceOf(AttendanceException.class);
        }

        @Test
        @DisplayName("이미 출석한 경우 예외가 발생한다")
        void duplicateAttendance() {
            String nickname = "투다";
            LocalTime time = LocalTime.of(8, 0);
            LocalDate date = LocalDate.of(2024, 12, 3);
            CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceCurrentDateGenerateStrategy(date));
            crewAttendances.addAttendance(nickname, time);
            Assertions.assertThatThrownBy(() -> crewAttendances.addAttendance(nickname, time))
                    .isInstanceOf(AttendanceException.class);
        }
    }
}
