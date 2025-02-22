package attendance.domain;

import static attendance.domain.AttendanceType.ATTENDANCE;
import static attendance.domain.AttendanceType.EXPULSION;
import static attendance.domain.AttendanceType.LATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import attendance.exception.ExceptionMessage;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CampusScheduleTest {

    final static LocalTime CAMPUS_OPEN_TIME = CampusSchedule.CAMPUS_OPEN_TIME.getTime();
    final static LocalTime CAMPUS_CLOSE_TIME = CampusSchedule.CAMPUS_CLOSE_TIME.getTime();
    final static LocalTime MONDAY_EDUCATION_START_TIME = CampusSchedule.MONDAY_EDUCATION_START_TIME.getTime();
    final static LocalTime MONDAY_LATE_TIME = MONDAY_EDUCATION_START_TIME.plusMinutes(LATE.getOverMinutes());
    final static LocalTime MONDAY_EXPULSION_TIME = MONDAY_EDUCATION_START_TIME.plusMinutes(EXPULSION.getOverMinutes());
    final static LocalTime NOT_MONDAY_EDUCATION_START_TIME = CampusSchedule.NOT_MONDAY_EDUCATION_START_TIME.getTime();
    final static LocalTime NOT_MONDAY_LATE_TIME = NOT_MONDAY_EDUCATION_START_TIME.plusMinutes(LATE.getOverMinutes());
    final static LocalTime NOT_MONDAY_EXPULSION_TIME =
            NOT_MONDAY_EDUCATION_START_TIME.plusMinutes(EXPULSION.getOverMinutes());

    @DisplayName("캠퍼스 운영시간이 아닌 경우 예외를 발생시킨다.")
    @ParameterizedTest
    @MethodSource()
    void 캠퍼스_운영시간이_아닌_경우_예외를_발생시킨다(LocalTime time) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CampusSchedule.checkAttendance(true, time))
                .withMessage(ExceptionMessage.NOT_CAMPUS_TIME.getContent());
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CampusSchedule.checkAttendance(false, time))
                .withMessage(ExceptionMessage.NOT_CAMPUS_TIME.getContent());
    }

    static Stream<Arguments> 캠퍼스_운영시간이_아닌_경우_예외를_발생시킨다() {
        return Stream.of(
                Arguments.of(CAMPUS_OPEN_TIME.minusSeconds(1)),
                Arguments.of(CAMPUS_CLOSE_TIME.plusSeconds(1))
        );
    }

    @DisplayName("월요일인 경우의 출석여부를 구한다.")
    @ParameterizedTest
    @MethodSource()
    void 월요일인_경우의_출석여부를_구한다(
            LocalTime arriveTime, AttendanceType expectedType
    ) {
        AttendanceType actualType = CampusSchedule.checkAttendance(true, arriveTime);
        assertThat(actualType).isEqualTo(expectedType);
    }

    static Stream<Arguments> 월요일인_경우의_출석여부를_구한다() {
        return Stream.of(
                Arguments.of(MONDAY_EDUCATION_START_TIME.minusSeconds(1), ATTENDANCE),
                Arguments.of(MONDAY_EDUCATION_START_TIME, ATTENDANCE),
                Arguments.of(MONDAY_LATE_TIME.minusSeconds(1), ATTENDANCE),
                Arguments.of(MONDAY_LATE_TIME, LATE),
                Arguments.of(MONDAY_EXPULSION_TIME.minusSeconds(1), LATE),
                Arguments.of(MONDAY_EXPULSION_TIME, EXPULSION),
                Arguments.of(MONDAY_EXPULSION_TIME.plusSeconds(1), EXPULSION)
        );
    }

    @DisplayName("월요일이 아닌 경우의 출석여부를 구한다.")
    @ParameterizedTest
    @MethodSource()
    void 월요일이_아닌_경우의_출석여부를_구한다(
            LocalTime arriveTime, AttendanceType expectedType
    ) {
        AttendanceType actualType = CampusSchedule.checkAttendance(false, arriveTime);
        assertThat(actualType).isEqualTo(expectedType);
    }

    static Stream<Arguments> 월요일이_아닌_경우의_출석여부를_구한다() {
        return Stream.of(
                Arguments.of(NOT_MONDAY_EDUCATION_START_TIME.minusSeconds(1), ATTENDANCE),
                Arguments.of(NOT_MONDAY_EDUCATION_START_TIME, ATTENDANCE),
                Arguments.of(NOT_MONDAY_LATE_TIME.minusSeconds(1), ATTENDANCE),
                Arguments.of(NOT_MONDAY_LATE_TIME, LATE),
                Arguments.of(NOT_MONDAY_EXPULSION_TIME.minusSeconds(1), LATE),
                Arguments.of(NOT_MONDAY_EXPULSION_TIME, EXPULSION),
                Arguments.of(NOT_MONDAY_EXPULSION_TIME.plusSeconds(1), EXPULSION)
        );
    }
}