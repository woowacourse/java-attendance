package attendance.domain;

import attendance.util.DateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceStatusTest {

    private static final DateGenerator dateGenerator = new TestDateGenerator();

    @ParameterizedTest
    @MethodSource
    @DisplayName("출석 목록으로 출결 상황을 종합한다")
    void 출석_목록으로_출결_상황을_종합한다(
            List<Attendance> attendances,
            int attendanceExcepted,
            int tardyExcepted,
            int absenceExcepted
    ) {
        // when
        AttendanceStatus result = AttendanceStatus.fromAttendances(attendances);

        // then
        assertAll(
                () -> assertThat(result.getAttendanceStateCount()).isEqualTo(attendanceExcepted),
                () -> assertThat(result.getTardyStateCount()).isEqualTo(tardyExcepted),
                () -> assertThat(result.getAbsenceStateCount()).isEqualTo(absenceExcepted)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("출석 목록으로 출결 위험도를 판단한다")
    void 출석_목록으로_출결_위험도를_판단한다(List<Attendance> attendances, AttendanceRisk excepted) {
        // when
        AttendanceStatus result = AttendanceStatus.fromAttendances(attendances);

        // then
        assertThat(result.getRisk()).isEqualTo(excepted);
    }

    private static Stream<Arguments> 출석_목록으로_출결_상황을_종합한다() {
        LocalDate nowDate = dateGenerator.generate();
        return Stream.of(
                Arguments.of(List.of(
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate, LocalTime.of(10, 0)))
                ), 1, 0, 0),
                Arguments.of(List.of(
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate, LocalTime.of(10, 0))),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(10, 6)))
                ), 1, 1, 0),
                Arguments.of(List.of(
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate, LocalTime.of(10, 0))),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(10, 6))),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(10, 31)))
                ), 1, 1, 1)
        );
    }

    private static Stream<Arguments> 출석_목록으로_출결_위험도를_판단한다() {
        LocalDate nowDate = dateGenerator.generate();
        return Stream.of(
                Arguments.of(
                        List.of(Attendance.createFromDateTime(LocalDateTime.of(nowDate, LocalTime.of(10, 0)))),
                        AttendanceRisk.NONE
                ),
                Arguments.of(List.of(
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate, LocalTime.of(18, 0))),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(18, 0)))
                ), AttendanceRisk.WARNING),
                Arguments.of(List.of(
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate, LocalTime.of(18, 0))),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(18, 0))),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(18, 0)))
                ), AttendanceRisk.INTERVIEW),
                Arguments.of(List.of(
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate, LocalTime.of(18, 0))),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(18, 0))),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(18, 0))),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(5), LocalTime.of(18, 0))),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(6), LocalTime.of(18, 0))),
                        Attendance.createFromDateTime(LocalDateTime.of(nowDate.minusDays(7), LocalTime.of(18, 0)))
                ), AttendanceRisk.WEEDING)
        );
    }

    private static class TestDateGenerator implements DateGenerator {

        @Override
        public LocalDate generate() {
            return LocalDate.of(2025, 3, 19);
        }
    }
}
