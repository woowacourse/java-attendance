package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatusTest {

    @ParameterizedTest
    @CsvSource({
        "2025,02,03,13,05,ATTENDANCE",
        "2025,02,03,13,06,LATE",
        "2025,02,03,13,31,ABSENT",
    })
    @DisplayName("출석 시간에 따라 올바른 출석 상태를 반환한다.")
    void ofTest(int year, int month, int day, int hour, int minute, String statusName) {
        // when
        AttendanceStatus status = AttendanceStatus.of(LocalDate.of(year, month, day), LocalTime.of(hour, minute));

        // then
        assertThat(status.name()).isEqualTo(statusName);
    }
}
