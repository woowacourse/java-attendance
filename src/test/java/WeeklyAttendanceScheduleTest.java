import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WeeklyAttendanceScheduleTest {

    @Test
    @DisplayName("월요일의 경우 출석 시작 시간 찾기")
    void 월요일의_경우_출석_시작_시간_찾기() {
        LocalDate localDate = LocalDate.of(2024, 12, 9);
        LocalTime expect = LocalTime.of(13, 0);
        LocalTime result = WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(localDate);
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("화요일 경우 출석 시작 시간 찾기")
    void 화요일의_경우_출석_시작_시간_찾기() {
        LocalDate localDate = LocalDate.of(2024, 12, 10);
        LocalTime expect = LocalTime.of(10, 0);
        LocalTime result = WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(localDate);
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("수요일 경우 출석 시작 시간 찾기")
    void 수요일의_경우_출석_시작_시간_찾기() {
        LocalDate localDate = LocalDate.of(2024, 12, 11);
        LocalTime expect = LocalTime.of(10, 0);
        LocalTime result = WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(localDate);
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("목요일 경우 출석 시작 시간 찾기")
    void 목요일의_경우_출석_시작_시간_찾기() {
        LocalDate localDate = LocalDate.of(2024, 12, 12);
        LocalTime expect = LocalTime.of(10, 0);
        LocalTime result = WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(localDate);
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("금요일 경우 출석 시작 시간 찾기")
    void 금요일의_경우_출석_시작_시간_찾기() {
        LocalDate localDate = LocalDate.of(2024, 12, 13);
        LocalTime expect = LocalTime.of(10, 0);
        LocalTime result = WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(localDate);
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("토요일의 경우 예외 처리")
    void 토요일의_경우_예외_처리() {
        LocalDate localDate = LocalDate.of(2024, 12, 14);
        assertThatThrownBy(() -> WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    @DisplayName("일요일의 경우 예외 처리")
    void 일요일의_경우_예외_처리() {
        LocalDate localDate = LocalDate.of(2024, 12, 15);
        assertThatThrownBy(() -> WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 15일 일요일은 등교일이 아닙니다.");
    }

    @Test
    @DisplayName("공휴일의 경우 예외 처리")
    void 크리스마스_경우_예외_처리() {
        LocalDate localDate = LocalDate.of(2024, 12, 25);
        assertThatThrownBy(() -> WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 25일 수요일은 등교일이 아닙니다.");
    }
}