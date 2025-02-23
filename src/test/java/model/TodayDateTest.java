package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TodayDateTest {
    @Test
    @DisplayName("오늘이 주말 및 공휴일인지 확인하는 메서드 테스트")
    void test1() {
        TodayDate todayDate = new TodayDate(LocalDate.of(2024,12,14));
        Assertions.assertThatThrownBy(todayDate::isHoliday)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 14일 토요일은 등교일이 아닙니다.");
    }

}