package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceTest {

    @Test
    void 출석이_정상적으로_실행() {
        // given
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 0, 0, 0);
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(localDateTime);

        // when
        // then
        assertThatCode(() -> new Attendance(attendanceDateTime))
                .doesNotThrowAnyException();
    }

    @Test
    void 정적팩터리_메서드에서_객체_정상_생성() {
        // given
        String input = "2024-12-12 11:11";
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(input);

        // when
        Attendance attendance = new Attendance(attendanceDateTime);

        // then
        assertThat(attendance.getAttendanceDateTime()).isEqualTo(attendanceDateTime);
    }


    @ParameterizedTest
    @MethodSource("methodSources")
    void 출석_일자와_찾는_일자가_동일한지_확인(LocalDate date, Boolean bool) {
        // given
        String dateTimeInput = "2024-12-16 00:00";
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(dateTimeInput);
        Attendance attendance = new Attendance(attendanceDateTime);

        // when
        boolean expected = attendance.equalsDate(date);

        // then
        Assertions.assertThat(bool).isEqualTo(expected);
    }

    private static Stream<Arguments> methodSources() {
        return Stream.of(
                Arguments.arguments(LocalDate.of(2024, 12, 16), Boolean.TRUE),
                Arguments.arguments(LocalDate.of(2024, 12, 17), Boolean.FALSE)
        );
    }

    @Test
    void 출석된_일이_잘_불러와지는_지_확인() {
        // given
        String dateTimeInput = "2024-12-16 00:00";
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(dateTimeInput);
        Attendance attendance = new Attendance(attendanceDateTime);
        int expected = 16;

        // when
        int dateOfMonth = attendance.getDateOfMonth();

        // then
        Assertions.assertThat(dateOfMonth).isEqualTo(expected);
    }

    @Test
    void 출석된_LocalDateTime이_잘_불러와지는_지_확인() {
        // given
        String dateTimeInput = "2024-12-16 00:00";
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(dateTimeInput);
        Attendance attendance = new Attendance(attendanceDateTime);
        LocalDateTime expected = LocalDateTime.of(2024, 12, 16, 0, 0);

        // when
        LocalDateTime dateTime = attendance.getLocalDateTime();

        // then
        Assertions.assertThat(dateTime).isEqualTo(expected);
    }

    @Test
    void 출석된_일자의_상태가_잘_불러와지는_지_확인() {
        // given
        String dateTimeInput = "2024-12-16 10:00";
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(dateTimeInput);
        Attendance attendance = new Attendance(attendanceDateTime);

        AttendanceStatus expected = AttendanceStatus.ATTENDANCE;

        // when
        AttendanceStatus status = attendance.getAttendanceStatus();

        // then
        Assertions.assertThat(status).isEqualTo(expected);
    }
}
