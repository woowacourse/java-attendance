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
        LocalDateTime christmas = LocalDateTime.parse("2024-12-25 11:01",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Assertions.assertThatThrownBy(() -> new Attendance(crew, christmas))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("법정 공휴일에는 출석할 수 없습니다.");
    }

    @DisplayName("이미 출석했는지 알 수 있다.")
    @Test
    void equalsTest() {
        Attendance attendance1 = new Attendance(new Crew("포비"), LocalDateTime.of(2024, 12, 13, 11, 1));
        Attendance attendance2 = new Attendance(new Crew("포비"), LocalDateTime.of(2024, 12, 13, 12, 1));

        boolean result = attendance1.isAlreadyAttendance(attendance2);

        Assertions.assertThat(result).isTrue();
    }
}
