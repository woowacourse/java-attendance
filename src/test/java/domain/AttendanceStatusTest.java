package domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {
    @DisplayName("월요일의 출석 상태를 구할 수 있다.")
    @Test
    void test1() {
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 9, 13, 5));

        assertThat(AttendanceStatus.from(attendance)).isEqualTo(AttendanceStatus.ATTEND);
    }

    @DisplayName("화~금요일의 출석 상태를 구할 수 있다.")
    @Test
    void test2() {
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 10, 9, 59));

        assertThat(AttendanceStatus.from(attendance)).isEqualTo(AttendanceStatus.ATTEND);
    }
}
