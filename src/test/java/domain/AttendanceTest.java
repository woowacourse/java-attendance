package domain;

import attendance.domain.Attendance;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @Test
    void 주말에_출석을_할_경우_예외가_발생한다() {
        //given
        String nickname = "pobi";
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 14, 10, 1); // 토요일

        //when
        Assertions.assertThatThrownBy(() -> new Attendance(nickname, attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말에는 출석할 수 없습니다.");
    }
}
