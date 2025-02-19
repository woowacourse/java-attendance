package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceTest {

    @Test
    void 닉네임과_등교시간으로_출석을_한다() {
        LocalTime time = LocalTime.of(9, 59);
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), time);

        assertThatCode(() -> Attendance.of(localDateTime))
            .doesNotThrowAnyException();
    }

    @Test
    void 등교날짜가_주말이면_예외가_발생한다() {
        LocalTime time = LocalTime.of(9, 59);

        LocalDate saturday = LocalDate.of(2024, 12, 14);
        LocalDateTime localDateTime = LocalDateTime.of(saturday, time);

        assertThatThrownBy(() -> Attendance.of(localDateTime))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 등교날짜가_주말이아니면_출석을_한다() {
        LocalTime time = LocalTime.of(13, 4);

        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalDateTime localDateTime = LocalDateTime.of(monday, time);

        assertThatCode(() -> Attendance.of(localDateTime))
            .doesNotThrowAnyException();
    }

    @CsvSource(value = {"13:5:CHECKIN", "13:6:LATE", "13:31:ABSENCE"}, delimiterString = ":")
    @ParameterizedTest
    void 월요일의_시간에따라_다른출석상태를_반환한다(int hour, int minute, AttendanceStatus expectedStatus) {
        LocalTime time = LocalTime.of(hour, minute);

        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalDateTime localDateTime = LocalDateTime.of(monday, time);
        Attendance attendance = Attendance.of(localDateTime);

        assertThat(attendance.getStatus()).isEqualTo(expectedStatus);
    }

    @CsvSource(value = {"10:5:CHECKIN", "10:6:LATE", "10:31:ABSENCE"}, delimiterString = ":")
    @ParameterizedTest
    void 화요일의_시간에따라_다른출석상태를_반환한다(int hour, int minute, AttendanceStatus expectedStatus) {
        LocalTime time = LocalTime.of(hour, minute);

        LocalDate tuesday = LocalDate.of(2024, 12, 10);
        LocalDateTime localDateTime = LocalDateTime.of(tuesday, time);
        Attendance attendance = Attendance.of(localDateTime);

        assertThat(attendance.getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void 등교날짜가_공휴일이면_예외가_발생한다() {
        LocalTime time = LocalTime.of(9, 59);

        LocalDate holiday = LocalDate.of(2024, 12, 25);
        LocalDateTime localDateTime = LocalDateTime.of(holiday, time);

        assertThatThrownBy(() -> Attendance.of(localDateTime))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출석시간을_수정할수있고_수정된_시간에따라_상태가_변경된다() {
        LocalDate previousDate = LocalDate.of(2024, 12, 3);
        LocalTime previousTime = LocalTime.of(10, 7);
        LocalTime updatedTime = LocalTime.of(9, 58);
        Attendance attendance = Attendance.of(LocalDateTime.of(previousDate, previousTime));

        attendance.update(LocalDateTime.of(previousDate, updatedTime));

        assertThat(attendance.getStatus()).isEqualTo(AttendanceStatus.CHECKIN);
    }
}
