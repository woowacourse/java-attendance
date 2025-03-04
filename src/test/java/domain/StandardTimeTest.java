package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StandardTimeTest {

    private static final LocalTime MONDAY_LATE_STANDARD_TIME = LocalTime.of(13, 5);
    private static final LocalTime MONDAY_ABSENT_STANDARD_TIME = LocalTime.of(13, 30);
    private static final LocalTime OTHER_DAY_LATE_STANDARD_TIME = LocalTime.of(10, 5);
    private static final LocalTime OTHER_DAY_ABSENT_STANDARD_TIME = LocalTime.of(10, 30);

    static Stream<Arguments> dayOfWeekAndStandardTimes() {
        return Stream.of(
                Arguments.arguments(DayOfWeek.MONDAY, MONDAY_LATE_STANDARD_TIME, MONDAY_ABSENT_STANDARD_TIME),
                Arguments.arguments(DayOfWeek.TUESDAY, OTHER_DAY_LATE_STANDARD_TIME, OTHER_DAY_ABSENT_STANDARD_TIME),
                Arguments.arguments(DayOfWeek.WEDNESDAY, OTHER_DAY_LATE_STANDARD_TIME, OTHER_DAY_ABSENT_STANDARD_TIME),
                Arguments.arguments(DayOfWeek.THURSDAY, OTHER_DAY_LATE_STANDARD_TIME, OTHER_DAY_ABSENT_STANDARD_TIME),
                Arguments.arguments(DayOfWeek.FRIDAY, OTHER_DAY_LATE_STANDARD_TIME, OTHER_DAY_ABSENT_STANDARD_TIME)
        );
    }

    static Stream<Arguments> dateTimeAndDayOfWeekParam() {
        return Stream.of(
                Arguments.arguments(Named.named("월요일 출석 테스트", LocalDateTime.of(2024, 12, 16, 13, 0)), AttendanceStatus.ATTENDANCE),
                Arguments.arguments(Named.named("월요일 출석 경계값 테스트", LocalDateTime.of(2024, 12, 16, 13, 5)), AttendanceStatus.ATTENDANCE),
                Arguments.arguments(Named.named("월요일 지각 테스트", LocalDateTime.of(2024, 12, 16, 13, 6)), AttendanceStatus.LATENESS),
                Arguments.arguments(Named.named("월요일 지각 경계값 테스트", LocalDateTime.of(2024, 12, 16, 13, 30)), AttendanceStatus.LATENESS),
                Arguments.arguments(Named.named("월요일 결석 테스트", LocalDateTime.of(2024, 12, 16, 13, 31)), AttendanceStatus.ABSENCE),
                Arguments.arguments(Named.named("화요일 출석 테스트", LocalDateTime.of(2024, 12, 17, 10, 0)), AttendanceStatus.ATTENDANCE),
                Arguments.arguments(Named.named("화요일 출석 경계값 테스트", LocalDateTime.of(2024, 12, 17, 10, 5)), AttendanceStatus.ATTENDANCE),
                Arguments.arguments(Named.named("화요일 지각 테스트", LocalDateTime.of(2024, 12, 17, 10, 6)), AttendanceStatus.LATENESS),
                Arguments.arguments(Named.named("화요일 지각 경계값 테스트", LocalDateTime.of(2024, 12, 17, 10, 30)), AttendanceStatus.LATENESS),
                Arguments.arguments(Named.named("화요일 결석 테스트", LocalDateTime.of(2024, 12, 17, 10, 31)), AttendanceStatus.ABSENCE)
        );
    }

    @ParameterizedTest
    @MethodSource("dayOfWeekAndStandardTimes")
    void 요일에_대한_출석기준시간을_가져온다(DayOfWeek dayOfWeek, LocalTime lateTime, LocalTime absentTime) {

        StandardTime standardTime = StandardTime.findByDayOfWeek(dayOfWeek);

        assertThat(standardTime.getLateTime()).isEqualTo(lateTime);
        assertThat(standardTime.getAbsentTime()).isEqualTo(absentTime);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("dateTimeAndDayOfWeekParam")
    void 요일_출석_테스트(LocalDateTime dateTime, AttendanceStatus expectedStatus) {
        LocalDateTime localDateTime = dateTime;

        AttendanceStatus attendanceStatus = StandardTime.judge(localDateTime);

        Assertions.assertThat(attendanceStatus).isEqualTo(expectedStatus);
    }
}
