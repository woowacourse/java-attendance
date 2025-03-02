package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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

    @ParameterizedTest
    @MethodSource("dayOfWeekAndStandardTimes")
    void 요일에_대한_출석기준시간을_가져온다(DayOfWeek dayOfWeek, LocalTime lateTime, LocalTime absentTime) {

        StandardTime standardTime = StandardTime.findByDayOfWeek(dayOfWeek);

        assertThat(standardTime.getLateTime()).isEqualTo(lateTime);
        assertThat(standardTime.getAbsentTime()).isEqualTo(absentTime);
    }

    @Test
    void 월요일_출석_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 0);

        AttendanceStatus attendanceStatus = StandardTime.judge(localDateTime);

        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 월요일_출석_경계값_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 5);

        AttendanceStatus attendanceStatus = StandardTime.judge(localDateTime);

        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 월요일_지각_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 6);

        AttendanceStatus attendanceStatus = StandardTime.judge(localDateTime);

        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATENESS);
    }

    @Test
    void 월요일_지각_경계값_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 30);

        AttendanceStatus attendanceStatus = StandardTime.judge(localDateTime);

        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATENESS);
    }

    @Test
    void 월요일_결석_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 31);

        AttendanceStatus attendanceStatus = StandardTime.judge(localDateTime);

        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 화요일_출석_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 17, 10, 0);

        AttendanceStatus attendanceStatus = StandardTime.judge(localDateTime);

        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 화요일_출석_경계값_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 17, 10, 5);

        AttendanceStatus attendanceStatus = StandardTime.judge(localDateTime);

        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 화요일_지각_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 17, 10, 6);

        AttendanceStatus attendanceStatus = StandardTime.judge(localDateTime);

        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATENESS);
    }

    @Test
    void 화요일_지각_경계값_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 17, 10, 30);

        AttendanceStatus attendanceStatus = StandardTime.judge(localDateTime);

        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATENESS);
    }

    @Test
    void 화요일_결석_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 17, 10, 31);

        AttendanceStatus attendanceStatus = StandardTime.judge(localDateTime);

        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }
}
