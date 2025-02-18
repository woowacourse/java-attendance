package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CheckInTimeTest {

    @Test
    @DisplayName("주말에 출석할 경우 예외 발생")
    void weekendCheckInThrowException() {
        LocalDateTime time = LocalDateTime.of(2024, 12, 28, 11, 11);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CheckInTime.of(time))
                .withMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("공휴일에 출석할 경우 예외 발생")
    void publicHolidayCheckInThrowException() {
        LocalDateTime time = LocalDateTime.of(2024, 12, 25, 11, 11);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CheckInTime.of(time))
                .withMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("출석 시간으로 출석 판단")
    void decidePresenceTest() {
        LocalDateTime time = LocalDateTime.of(2024, 12, 10, 9, 50);
        CheckInTime checkInTime = CheckInTime.of(time);

        AttendanceStatus status = checkInTime.getAttendanceStatus();

        assertThat(status).isEqualTo(AttendanceStatus.PRESENCE);
    }

    @Test
    @DisplayName("출석 시간으로 지각 판단")
    void decideLateTest() {
        LocalDateTime time = LocalDateTime.of(2024, 12, 10, 10, 6);
        CheckInTime checkInTime = CheckInTime.of(time);

        AttendanceStatus status = checkInTime.getAttendanceStatus();

        assertThat(status).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("출석 시간으로 결석 판단")
    void decideAbsenceTest() {
        LocalDateTime time = LocalDateTime.of(2024, 12, 10, 10, 31);
        CheckInTime checkInTime = CheckInTime.of(time);

        AttendanceStatus status = checkInTime.getAttendanceStatus();

        assertThat(status).isEqualTo(AttendanceStatus.ABSENCE);
    }
}
