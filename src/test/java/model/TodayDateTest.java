package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.InputView;

class TodayDateTest {
    @Test
    @DisplayName("시간을 입력받아 LocalDateTime 으로 변환하는 메서드 테스트")
    void test1(){
        String time = "09:59";
        TodayDate todayDate = new TodayDate(LocalDate.of(2024, 12, 13));
        LocalDateTime localDateTime = InputView.makeLocalDateToLocalDateTime(LocalDate.of(2024,12,13));
        LocalDateTime expect = LocalDateTime.of(2024, 12, 13, 9, 59);
        Assertions.assertTrue(localDateTime.isEqual(expect));
    }

    @Test
    @DisplayName("형식이 잘못된 시간을 입력하면 예외 발생 테스트")
    void test2() {
        String time = "09:71";
        TodayDate todayDate = new TodayDate(LocalDate.of(2024, 12, 13));
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> InputView.makeLocalDateToLocalDateTime(LocalDate.of(2024,12,13)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시간 형식에 맞지 않습니다.");
    }
}