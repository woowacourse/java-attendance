package model;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    @DisplayName("주말 및 공휴일에는 출석할 수 없다.")
    void test2() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 14, 9, 35);

        //when & then
        Assertions.assertThatThrownBy(() -> Attendance.of(crew, checkInTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 및 공휴일에는 출석할 수 없습니다.");
    }
}
