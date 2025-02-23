package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import error.CustomIllegalArgumentException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceTest {

    @Test
    void 출석이_정상적으로_실행() {
        // given
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 0, 0, 0);

        // when
        // then
        assertThatCode(() -> new Attendance(localDateTime))
                .doesNotThrowAnyException();
    }

    @Test
    void 정적팩터리_메서드에서_객체_정상_생성() {
        // given
        String input = "2024-12-12 11:11";
        LocalDateTime expected = LocalDateTime.of(2024, 12, 12, 11, 11);

        // when
        Attendance attendance = Attendance.of(input);

        // then
        assertThat(attendance.getLocalDateTime()).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2024-12-12 11", "바보", "2024-12-32 11:12", "2024-12-11 99:12"})
    void 잘못된_포멧으로_입력했을_때_예외_처리(String input) {
        // given
        // when
        // then
        assertThatThrownBy(() -> Attendance.of(input))
                .isInstanceOf(CustomIllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("methodSources")
    void 출석_일자와_찾는_일자가_동일한지_확인(LocalDate date, Boolean bool) {
        // given
        String dateTimeInput = "2024-12-16 00:00";
        Attendance attendance = Attendance.of(dateTimeInput);

        // when
        boolean expected = attendance.equals(date);

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
        Attendance attendance = Attendance.of(dateTimeInput);
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
        Attendance attendance = Attendance.of(dateTimeInput);
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
        Attendance attendance = Attendance.of(dateTimeInput);

        AttendanceStatus expected = AttendanceStatus.ATTENDANCE;

        // when
        AttendanceStatus status = attendance.getAttendanceStatus();

        // then
        Assertions.assertThat(status).isEqualTo(expected);
    }
}
