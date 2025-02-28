package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {
    @Test
    @DisplayName("출석 기록의 닉네임은 빈 값일 수 없다.")
    void validateNicknameExceptionTest() {
        // given
        String nickname = "";
        LocalDate date = LocalDate.of(2025, 2, 3);
        LocalTime time = LocalTime.of(13, 5);
        AttendanceStatus status = AttendanceStatus.ATTENDANCE;

        // when && then
        Assertions.assertThatThrownBy(() -> {
            new AttendanceRecord(nickname, date, time, status);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출석 기록 날짜가 주말 혹은 공휴일이면 예외가 발생한다")
    void validateOffDateExceptionTest() {
        // given
        String nickname = "name";
        LocalDate saturday = LocalDate.of(2025, 2, 1);
        LocalTime time = LocalTime.of(13, 5);
        AttendanceStatus status = AttendanceStatus.ATTENDANCE;

        // when && then
        Assertions.assertThatThrownBy(() -> {
            new AttendanceRecord(nickname, saturday, time, status);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출석 기록 시간이 캠퍼스 운영 시간이 아니면 예외가 발생한다")
    void validateCampusTimeExceptionTest() {
        // given
        String nickname = "name";
        LocalDate date = LocalDate.of(2025, 2, 3);
        LocalTime overCampusCloseTime = LocalTime.of(23, 5);
        AttendanceStatus status = AttendanceStatus.of(date, overCampusCloseTime);

        // when && then
        Assertions.assertThatThrownBy(() -> {
            new AttendanceRecord(nickname, date, overCampusCloseTime, status);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출석 상태가 null이면 예외가 발생한다")
    void validateStatusExceptionTest() {
        // given
        String nickname = "name";
        LocalDate date = LocalDate.of(2025, 2, 3);
        LocalTime time = LocalTime.of(23, 5);
        AttendanceStatus status = null;

        // when && then
        Assertions.assertThatThrownBy(() -> {
            new AttendanceRecord(nickname, date, time, status);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}