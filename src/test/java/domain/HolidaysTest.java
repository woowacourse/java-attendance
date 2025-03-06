package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HolidaysTest {
    @Test
    @DisplayName("일치하는 공휴일이 있으면 false를 정상적으로 반환")
    void isNotHolidayTrueTest() {
        //given
        LocalDate date = LocalDate.of(2024, 12, 25);
        //when
        //then
        assertFalse(Holidays.isNotHoliday(date));
    }

    @Test
    @DisplayName("일치하는 공휴일이 없으면 true를 정상적으로 반환")
    void isNotHolidayFalseTest() {
        //given
        LocalDate date = LocalDate.of(2024, 12, 26);
        //when
        //then
        assertTrue(Holidays.isNotHoliday(date));
    }
}