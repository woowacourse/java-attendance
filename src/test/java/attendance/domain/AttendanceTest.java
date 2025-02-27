package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @DisplayName("크루 이름이 같고 같은 날의 출석인 경우 예외가 발생한다")
    @Test
    void 크루_이름이_같고_같은_날의_출석인_경우_예외가_발생한다() {

        // given
        Attendance attendance1 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 0)));
        Attendance attendance2 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 0)));

        // when & then
        assertThatThrownBy(() -> {
            attendance1.isSameLocalDate(attendance2);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석 기록이 존재합니다. 수정 기능을 이용해 주세요.");

        // then
    }
}
