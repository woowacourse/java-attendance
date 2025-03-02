package attendance.view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStateViewTest {

    @Test
    @DisplayName("이름과 동일한 출력문을 반환한다")
    void 이름과_동일한_출력문을_반환한다() {
        // given
        String name = "ATTENDANCE";

        // when
        AttendanceStateView result = AttendanceStateView.findByName(name);

        // then
        Assertions.assertThat(result.getName())
                .isEqualTo("출석");
    }
}
