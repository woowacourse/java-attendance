package domain;

import static org.assertj.core.api.Assertions.assertThatIterable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import strategy.TestAttendanceCurrentDateGenerateStrategy;

public class AttendanceHistoryTest {

    private static CrewAttendances crewAttendances;
    private static TestAttendanceCurrentDateGenerateStrategy testAttendanceCurrentDateGenerateStrategy;

    @BeforeAll
    static void initiate() {
        testAttendanceCurrentDateGenerateStrategy = new TestAttendanceCurrentDateGenerateStrategy(
                LocalDate.of(2024, 12, 2));
        crewAttendances = new CrewAttendances(testAttendanceCurrentDateGenerateStrategy);
        crewAttendances.addAttendance("투다", LocalTime.of(8, 3));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 3));
        crewAttendances.addAttendance("투다", LocalTime.of(8, 3));
        LocalDate notIncludeDate = LocalDate.of(2024, 12, 4);
        testAttendanceCurrentDateGenerateStrategy.setTestDate(notIncludeDate);
        crewAttendances.addAttendance("투다", LocalTime.of(8, 3));
    }

    private static Stream<Arguments> attendanceHistoryTest() {
        AttendanceDate attendanceDate1 = new AttendanceDate(LocalDate.of(2024, 12, 2));
        CrewAttendance crewAttendance1 = crewAttendances.crewAttendance("투다", LocalDate.of(2024, 12, 2));
        AttendanceDate attendanceDate2 = new AttendanceDate(LocalDate.of(2024, 12, 3));
        CrewAttendance crewAttendance2 = crewAttendances.crewAttendance("투다", LocalDate.of(2024, 12, 3));
        AttendanceDate attendanceDate3 = new AttendanceDate(LocalDate.of(2024, 12, 4));
        CrewAttendance crewAttendance3 = crewAttendances.crewAttendance("투다", LocalDate.of(2024, 12, 4));
        return Stream.of(
                Arguments.arguments(
                        "투다",
                        List.of(new CrewAttendanceHistory(crewAttendance1, attendanceDate1),
                                new CrewAttendanceHistory(crewAttendance2, attendanceDate2)),
                        LocalDate.of(2024, 12, 4)
                ),
                Arguments.arguments(
                        "투다",
                        List.of(new CrewAttendanceHistory(crewAttendance1, attendanceDate1),
                                new CrewAttendanceHistory(crewAttendance2, attendanceDate2),
                                new CrewAttendanceHistory(crewAttendance3, attendanceDate3)),
                        LocalDate.of(2024, 12, 5)
                ),
                Arguments.arguments(
                        "투다",
                        List.of(),
                        LocalDate.of(2024, 12, 1)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("attendanceHistoryTest")
    @DisplayName("닉네임을 입력하여 전날까지의 출석 기록을 확인할 수 있다.")
    void attendanceHistoryTest(String nickname, List<CrewAttendanceHistory> expectCrewAttendanceHistories,
                               LocalDate notIncludeDate) {
        testAttendanceCurrentDateGenerateStrategy.setTestDate(notIncludeDate);
        assertThatIterable(crewAttendances.crewAttendancesHistory(nickname))
                .containsExactlyInAnyOrderElementsOf(expectCrewAttendanceHistories);
    }
}
