package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceRecordTest {
    @DisplayName("LocalDateTime을 받아 기록 객체를 생성할 수 있다.")
    @Test
    void instanceTest() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 0);

        // then
        assertThatNoException().isThrownBy(() -> new AttendanceRecord(dateTime));
    }

    @DisplayName("LocalDate을 받아 결석으로 처리된 객체를 생성할 수 있다.")
    @Test
    void absentInstanceTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 2);

        // when
        AttendanceStatus expectedValue = AttendanceStatus.ABSENT;

        // then
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> new AttendanceRecord(date)),
                () -> assertThat(new AttendanceRecord(date).getAttendanceStatus()).isEqualTo(expectedValue)
        );
    }

    @DisplayName("출석을 기록하려는 날짜가 등교일이 아닐 경우 예외가 발생한다.")
    @Test
    void validateDateTest() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 25, 10, 0);

        // then
        assertThatThrownBy(() -> new AttendanceRecord(dateTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("등교 시간에 따른 출석 상태를 저장한다.")
    @Test
    void attendanceStatusTest() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 10);
        AttendanceRecord attendanceRecord = new AttendanceRecord(dateTime);

        // when
        AttendanceStatus expectedValue = AttendanceStatus.TARDY;
        AttendanceStatus actualValue = attendanceRecord.getAttendanceStatus();

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
