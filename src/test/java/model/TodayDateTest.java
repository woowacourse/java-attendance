package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TodayDateTest {
    private final TodayDate todayDate = new TodayDate(LocalDate.of(2024, 12, 1));

    @Test
    @DisplayName("오늘 날짜의 요일 받기")
    void test1() {
        Assertions.assertTrue(todayDate.getTodayDayName().equals("일요일"));
    }

    @Test
    @DisplayName("오늘이 휴일인지 확인하는 테스트")
    void test2() {
        Assertions.assertTrue(todayDate.isHoliday());
    }

    @Test
    @DisplayName("AttendanceDateTime 객체로 반환하는 테스트")
    void test3() {
        LocalDateTime expectedDateTime = LocalDateTime.of(2024, 12, 1, 0, 0);
        LocalDateTime actualDateTime = todayDate.toAttendanceDateTime().getAttendanceDateTime();

        Assertions.assertTrue(expectedDateTime.equals(actualDateTime));
    }
}