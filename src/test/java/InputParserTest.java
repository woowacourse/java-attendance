import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class InputParserTest {
    @Test
    @DisplayName("시간 형태의 문자열을 입력하면 LocalDateTime 으로 반환한다")
    void test1() {
        // given
        String input = "13:45";

        // when & then
        assertThat(InputParser.parseTime(input))
                .isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 45)));
    }

    @Test
    @DisplayName("0시 0분에 대해서는 기록이 없음을 의미하는 --:-- 를 반환한다")
    void test4() {
        // given
        LocalDateTime time = LocalDateTime.of(2024, 12, 5, 0, 0);

        // when & then
        assertThat(InputParser.parseDateTimeToString(time)).isEqualTo("12월 05일 목요일 --:--");
    }

    @Test
    @DisplayName("LocalDateTime 을 12월 05일 목요일 09:59 와 같은 형태로 반환한다")
    void test2() {
        // given
        LocalDateTime time = LocalDateTime.of(2024, 12, 5, 9, 59);

        // when & then
        assertThat(InputParser.parseDateTimeToString(time)).isEqualTo("12월 05일 목요일 09:59");
    }

    private static Stream<Arguments> testCasesForParseAttendanceType() {
        return Stream.of(
                Arguments.of(AttendanceType.PRESENT, "출석"),
                Arguments.of(AttendanceType.LATE, "지각"),
                Arguments.of(AttendanceType.ABSENCE, "결석")
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForParseAttendanceType")
    @DisplayName("출석 타입에 따라 적절한 문자열을 반환한다.")
    void test(AttendanceType attendanceType, String expected) {
        // when & then
        assertThat(InputParser.parseAttendanceType(attendanceType)).isEqualTo(expected);
    }

    private static Stream<Arguments> testCasesForParsePenaltyType() {
        return Stream.of(
                Arguments.of(PenaltyType.BAN, "제적"),
                Arguments.of(PenaltyType.ONE_ON_ONE, "면담"),
                Arguments.of(PenaltyType.WARNING, "경고")
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForParsePenaltyType")
    @DisplayName("패널티 타입에 따라 적절한 문자열을 반환한다.")
    void test3(PenaltyType penaltyType, String expected) {
        // when & then
        assertThat(InputParser.parsePenaltyType(penaltyType)).isEqualTo(expected);
    }

    @Test
    @DisplayName("AttendanceTypeCount 를 통해 제적 위험자 출력에 필요한 형태로 파싱한다")
    void test() {
        // given
        Map<LocalDateTime, AttendanceType> attendanceTypeOfDates = Map.of(
                LocalDateTime.of(2024, 12, 2, 10, 1), AttendanceType.PRESENT,
                LocalDateTime.of(2024, 12, 3, 10, 31), AttendanceType.ABSENCE,
                LocalDateTime.of(2024, 12, 4, 10, 6), AttendanceType.LATE,
                LocalDateTime.of(2024, 12, 5, 10, 31), AttendanceType.ABSENCE
        );

        // when & then
        assertThat(InputParser.parseExpulsionCandidate(new Crew("히로"),
                AttendanceTypeCount.createFrom(attendanceTypeOfDates))).isEqualTo("- 히로: 결석 2회, 지각 1회 (경고)");
    }


}
