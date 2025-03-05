package attendance.domain;

import attendance.util.DateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("출석 종합 테스트")
class AttendancesTest {

    private static final DateGenerator dateGenerator = new TestDateGenerator();

    @ParameterizedTest(name = "출석 시간: {0}, 출결 상황 결과: {1}")
    @MethodSource
    @DisplayName("등교 시간으로 출석을 등록한다")
    void registerAttendanceBasedOnArrivalTime(LocalTime checkTime, AttendanceState exceptedState) {
        // given
        LocalDate nowDate = dateGenerator.generate();
        LocalDateTime checkDateTime = LocalDateTime.of(nowDate, checkTime);

        Attendances defaultAttendances = createAttendances(nowDate, LocalTime.MAX);

        // when
        Attendances newAttendances = defaultAttendances.registerAttendance(checkDateTime);
        Attendance result = newAttendances.findAttendanceByDate(checkDateTime.toLocalDate());

        // then
        assertAll(
                () -> assertThat(result.getDateTime()).isEqualTo(checkDateTime),
                () -> assertThat(result.getState()).isEqualTo(exceptedState)
        );
    }

    @Test
    @DisplayName("출석할때 이미 출석한 경우 예외가 발생한다")
    void throwExceptionWhenAlreadyRegistered() {
        // given
        LocalDate nowDate = dateGenerator.generate();

        Attendances alreadyAttendances = createAttendances(nowDate, LocalTime.of(10, 0));
        LocalDateTime attendanceDateTime = LocalDateTime.of(nowDate, LocalTime.of(10, 0));

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> alreadyAttendances.registerAttendance(attendanceDateTime))
                .withMessage("[ERROR] 이미 출석이 등록되었습니다. 수정 기능을 이용 해주세요.");
    }

    @ParameterizedTest(name = "날짜&시간: {0}, 출결 상황 결과: {1}")
    @MethodSource
    @DisplayName("날짜와 시간으로 출석을 수정한다")
    void updateAttendanceBasedOnDateAndTime(LocalDateTime updateDateTime, AttendanceState exceptedState) {
        // given
        LocalDate nowDate = dateGenerator.generate();

        Attendances attendances = createAttendances(nowDate, LocalTime.of(10, 0));

        // when
        Attendances newAttendances = attendances.updateAttendance(updateDateTime);
        Attendance result = newAttendances.findAttendanceByDate(updateDateTime.toLocalDate());

        // then
        assertAll(
                () -> assertThat(result.getDateTime()).isEqualTo(updateDateTime),
                () -> assertThat(result.getState()).isEqualTo(exceptedState)
        );
    }

    @Test
    @DisplayName("특정 이전 날짜의 출석 날짜, 시간과 출결 상황을 반환한다")
    void returnAttendanceStatusBeforeSpecificDate() {
        // given
        LocalDate nowDate = dateGenerator.generate();

        Attendances initAttendances = createInitAttendances(nowDate);
        Attendances exceptedAttendances = createExceptedAttendances(nowDate);

        // when
        List<Attendance> result = initAttendances.getAttendancesBefore(nowDate);

        // then
        assertThat(result)
                .containsExactlyElementsOf(exceptedAttendances.getAttendances());
    }

    private static Stream<Arguments> updateAttendanceBasedOnDateAndTime() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2025, 3, 19, 10, 2), AttendanceState.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2025, 3, 19, 10, 6), AttendanceState.TARDY),
                Arguments.of(LocalDateTime.of(2025, 3, 19, 10, 31), AttendanceState.ABSENCE)
        );
    }

    private static Stream<Arguments> registerAttendanceBasedOnArrivalTime() {
        return Stream.of(
                Arguments.of(LocalTime.of(10, 0), AttendanceState.ATTENDANCE),
                Arguments.of(LocalTime.of(10, 6), AttendanceState.TARDY),
                Arguments.of(LocalTime.of(10, 31), AttendanceState.ABSENCE)
        );
    }

    private Attendances createAttendances(final LocalDate nowDate, final LocalTime time) {
        Attendance attendance = Attendance.createFromDateTime(LocalDateTime.of(nowDate, time));
        return new Attendances(List.of(attendance));
    }

    private Attendances createInitAttendances(final LocalDate nowDate) {
        return new Attendances(List.of(
                Attendance.createFromDateTime(LocalDateTime.of(nowDate, LocalTime.of(13, 0))),
                Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(13, 0))),
                Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(13, 0)))
        ));
    }

    private Attendances createExceptedAttendances(final LocalDate nowDate) {
        return new Attendances(List.of(
                Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(13, 0))),
                Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(13, 0)))
        ));
    }

    private static class TestDateGenerator implements DateGenerator {

        @Override
        public LocalDate generate() {
            return LocalDate.of(2025, 3, 19);
        }
    }
}
