package attendance.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 테스트")
class AttendanceTest {

    @DisplayName("주말인 경우 출석을 생성할때 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenWeekendAttendance() {
        Crew crew = new Crew("포비");
        LocalDateTime sunday = LocalDateTime.parse("2024-12-01 11:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Assertions.assertThatThrownBy(() -> new Attendance(crew, sunday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말인 경우 출석할 수 없습니다.");
    }

    @DisplayName("법정 공휴일인 경우 출석을 생성할때 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenHolidayAttendance() {
        Crew crew = new Crew("포비");
        LocalDateTime christmas = LocalDateTime.parse("2024-12-25 11:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Assertions.assertThatThrownBy(() -> new Attendance(crew, christmas))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("법정 공휴일에는 출석할 수 없습니다.");
    }
}
