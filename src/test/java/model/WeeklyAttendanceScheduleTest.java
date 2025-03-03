package model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WeeklyAttendanceScheduleTest {

    @ParameterizedTest
    @CsvSource({
            "2024-12-09, 13:00",
            "2024-12-10, 10:00",
            "2024-12-11, 10:00",
            "2024-12-12, 10:00",
            "2024-12-13, 10:00"
    })
    @DisplayName("주중 출석 시작 시간 확인")
    void 출석_시작_시간_찾기(LocalDate localDate, String expectedTime) {
        LocalTime expect = LocalTime.parse(expectedTime);
        LocalTime result = WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(localDate);
        assertEquals(expect, result);
    }

    @ParameterizedTest
    @CsvSource({
            "2024-12-14",
            "2024-12-15",
            "2024-12-25"
    })
    @DisplayName("휴일에 대한 출석 시간 예외 처리")
    void 휴일_출석_예외처리(LocalDate localDate) {
        assertThatThrownBy(() -> WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] "
                        +localDate.getMonthValue() + "월 "
                        +localDate.getDayOfMonth()+"일 "
                        +localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
                        + "은 등교일이 아닙니다.");
    }
}
