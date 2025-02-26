package domain;

import except.AttendanceException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import strategy.CurrentDateGenerateStrategy;
import strategy.TestAttendanceCurrentDateGenerateStrategy;

public class ModifyAttendanceTest {

    private static CurrentDateGenerateStrategy currentDateGenerateStrategy;

    private static CrewAttendances crewAttendances;

    @BeforeAll
    static void initiate() {
        currentDateGenerateStrategy = new TestAttendanceCurrentDateGenerateStrategy(LocalDate.of(2024, 12, 3));
    }

    @BeforeEach
    void initiateAttendance() {
        crewAttendances = new CrewAttendances(currentDateGenerateStrategy);
        crewAttendances.addAttendance("투다", LocalTime.of(8, 3));
    }

    @Test
    @DisplayName("닉네임과 날짜, 시간을 입력하여 수정할 수 있다")
    void modifyAttendanceTest() {
        String nickname = "투다";
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(8, 3);
        crewAttendances.modifyAttendance(nickname, date, time);

        Assertions.assertThat(crewAttendances.crewAttendance(nickname, date).attendanceStatus())
                .isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    static Stream<Arguments> provideInvalidAttendanceModificationCases() {
        return Stream.of(
                Arguments.of("투다", LocalDate.of(2024, 12, 3), LocalTime.of(7, 3), "학교 시작 시간 전"),
                Arguments.of("투다", LocalDate.of(2024, 12, 3), LocalTime.of(23, 3), "학교 종료 후"),
                Arguments.of("냠냠", LocalDate.of(2024, 12, 3), LocalTime.of(8, 3), "존재하지 않는 유저"),
                Arguments.of("투다", LocalDate.of(2024, 11, 3), LocalTime.of(8, 3), "유효하지 않은 날짜"),
                Arguments.of("투다", LocalDate.of(2024, 12, 2), LocalTime.of(8, 3), "유효하지 않은 날짜"),
                Arguments.of("투다", LocalDate.of(2024, 12, 3), LocalTime.of(23, 1), "유효하지 않은 시간")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidAttendanceModificationCases")
    @DisplayName("잘못된 출석 수정 요청 시 예외 발생")
    void modifyAttendanceTest(String nickname, LocalDate date, LocalTime time, String reason) {
        Assertions.assertThatThrownBy(() -> crewAttendances.modifyAttendance(nickname, date, time))
                .isInstanceOf(AttendanceException.class)
                .describedAs(reason);
    }
}
