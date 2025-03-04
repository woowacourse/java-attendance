package attendance.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceStateViewTest {

    @Test
    @DisplayName("이름과 동일한 출력문을 반환한다")
    void 이름과_동일한_출력문을_반환한다() {
        // given
        String name = "ATTENDANCE";

        // when
        AttendanceStateView result = AttendanceStateView.findByName(name);

        // then
        assertThat(result.getName())
                .isEqualTo("출석");
    }
}
