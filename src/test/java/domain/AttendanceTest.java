package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static util.Constants.ERROR_HEADER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.InputValidator;

public class AttendanceTest {
    LocalDate validDate = LocalDate.of(2024, 12, 3);
    LocalTime validTime = LocalTime.of(10, 0);

    @DisplayName("출석 날짜와 시간을 입력하여 출석 기록을 저장한다.")
    @Test
    void test1() {
        assertDoesNotThrow(() -> new Attendance(
                LocalDateTime.of(validDate, validTime)));

    }

    @DisplayName("캠퍼스 운영시간 전이면 예외가 발생한다.")
    @Test
    void test2() {
        LocalTime beforeRunningTime = LocalTime.of(7, 59);

        assertThrowsIllegalArgumentException(
                () -> new Attendance(validDate, beforeRunningTime));
    }

    @DisplayName("캠퍼스 운영시간 후이면 예외가 발생한다.")
    @Test
    void test3() {
        LocalTime afterRunningTime = LocalTime.of(7, 59);

        assertThrowsIllegalArgumentException(
                () -> new Attendance(validDate, afterRunningTime));
    }

    @DisplayName("주말을 입력할 경우 예외가 발생한다.")
    @Test
    void test4() {
        LocalDate weekend = LocalDate.of(2024, 12, 7);

        assertThrowsIllegalArgumentException(
                () -> new Attendance(weekend, validTime));
    }

    @DisplayName("공휴일을 입력할 경우 예외가 발생한다.")
    @Test
    void test5() {
        LocalDate holiday = LocalDate.of(2024, 12, 25);

        assertThrowsIllegalArgumentException(
                () -> new Attendance(holiday, validTime));
    }

    void assertThrowsIllegalArgumentException(ThrowingCallable throwingCallable) {
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ERROR_HEADER);
    }
}
