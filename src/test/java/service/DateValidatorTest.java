package service;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DateValidatorTest {
    DateValidator dateValidator = new DateValidator();

    @DisplayName("출석 변경 날짜가 평일이고 미래가 아닌지 검사합니다.")
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 23, 24})
    void validate(int date) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 24, 10, 0);
        Assertions.assertDoesNotThrow(() -> dateValidator.validateAttendanceChangeDate(date, today));
    }

    @DisplayName("출석 변경 날짜가 휴일이면 예외가 발생합니다.")
    @ParameterizedTest
    @ValueSource(ints = {25, 1, 7})
    void validateChangeDateHolidayTest(int date) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 24, 10, 0);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dateValidator.validateAttendanceChangeDate(date, today));
    }

    @DisplayName("출석 변경 날짜가 미래이면 예외가 발생합니다")
    @ParameterizedTest
    @ValueSource(ints = {26, 27, 31})
    void validateChangeDateFutureTest(int date) {
        LocalDateTime today = LocalDateTime.of(2024, 12, 24, 10, 0);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dateValidator.validateAttendanceChangeDate(date, today));
    }

    @DisplayName("출석 확인 날짜가 휴일이 아닌지 검사합니다.")
    @Test
    void validateCheckDateTest() {
        LocalDateTime today = LocalDateTime.of(2024, 12, 2, 10, 0);
        Assertions.assertDoesNotThrow(() -> dateValidator.validateAttendanceCheckDate(today));

    }

    @DisplayName("출석 확인 날짜가 휴일이면 예외가 발생합니다.")
    @Test
    void validateCheckDateFutureTest() {
        LocalDateTime today = LocalDateTime.of(2024, 12, 25, 10, 0);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dateValidator.validateAttendanceCheckDate(today));
    }
}