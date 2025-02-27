package attendance.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Year;

import static org.assertj.core.api.Assertions.*;

public class AttendanceDateTimeTest {

    @Test
    void 출석_일자와_시간을_알려주면_해당_시간대의_출석시간_객체를_생성한다() {
        // Given
        LocalDateTime attendanceDateTime = Year.of(2025).atMonth(2).atDay(26).atTime(10, 0);

        // When & Then
        assertThatCode(() -> new AttendanceDateTime(attendanceDateTime))
                .doesNotThrowAnyException();
    }

    @Test
    void 주말에는_출석할_수_없다() {
        // Given
        LocalDateTime attendanceDateTime = Year.of(2025).atMonth(2).atDay(23).atTime(10, 0);

        // When & Then
        assertThatThrownBy(() -> new AttendanceDateTime(attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("2월 23일 일요일은 등교일이 아닙니다.");
    }

    @Test
    void 공휴일에는_출석할_수_없다() {
        // Given
        LocalDateTime attendanceDateTime = Year.of(2025).atMonth(1).atDay(1).atTime(10, 0);

        // When & Then
        assertThatThrownBy(() -> new AttendanceDateTime(attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("1월 1일 수요일은 등교일이 아닙니다.");
    }

    @Test
    void 캠퍼스_운영시간_외에는_출석할_수_없다() {
        // Given
        LocalDateTime attendanceDateTime = Year.of(2025).atMonth(2).atDay(26).atTime(7, 0);

        // When & Then
        assertThatThrownBy(() -> new AttendanceDateTime(attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("7시 0분은 캠퍼스 운영 시간이 아닙니다.");
    }

    @Test
    void 미래의_시간에는_출석할_수_없다() {
        // Given
        LocalDateTime attendanceDateTime = Year.of(2025).atMonth(12).atDay(26).atTime(10, 0);

        // When & Then
        assertThatThrownBy(() -> new AttendanceDateTime(attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("미래의 시간으로는 출석할 수 없습니다.");
    }

    @Test
    void 두_날짜_사이의_차이를_분으로_환산하여_반환한다() {
        // Given
        LocalDateTime start = Year.of(2025).atMonth(2).atDay(27).atTime(13, 30);
        AttendanceDateTime startDateTime = new AttendanceDateTime(start);
        LocalDateTime end = Year.of(2025).atMonth(2).atDay(27).atTime(10, 00);

        // When & Then
        assertThat(startDateTime.calculateMinuteDifference(end))
                .isEqualTo(210);
    }
}
