package model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HolidayTest {

    @ParameterizedTest
    @CsvSource({
            "2024-12-14, true",
            "2024-12-15, true",
            "2024-12-25, true"
    })
    @DisplayName("휴일에 대한 출석 여부 확인")
    void 출석_여부_확인(LocalDate localDate, boolean expected) {
        boolean result = Holiday.checkHoliday(localDate);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "2024-12-14",
            "2024-12-15",
            "2024-12-25"
    })
    @DisplayName("휴일에 대한 출석 시간 예외 처리")
    void 휴일_출석_예외처리(LocalDate localDate) {
        assertThatThrownBy(() -> Holiday.validateHoliday(localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] "
                        +localDate.getMonthValue() + "월 "
                        +localDate.getDayOfMonth()+"일 "
                        +localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
                        + "은 등교일이 아닙니다.");
    }

}