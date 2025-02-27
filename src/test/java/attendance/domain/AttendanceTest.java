package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    Attendance attendance1;
    Attendance attendance2;

    @BeforeEach
    void setUp() {
        attendance1 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 0)));
        attendance2 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 0)));
    }

    @DisplayName("크루 이름이 같고 같은 날의 출석인 경우 예외가 발생한다")
    @Test
    void 크루_이름이_같고_같은_날의_출석인_경우_예외가_발생한다() {

        // given

        // when & then
        assertThatThrownBy(() -> {
            attendance1.isSameLocalDate(attendance2);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석 기록이 존재합니다. 수정 기능을 이용해 주세요.");

        // then
    }

    @DisplayName("출석 시간을 수정한다.")
    @Test
    void 출석_시간을_수정한다() {

        // given
        LocalTime modifyTime = LocalTime.of(10, 5);

        // when
        Attendance modifyAttendance = attendance1.modifyAttendanceTime(modifyTime);

        // then
        assertThat(attendance1).isEqualTo(modifyAttendance);

    }
}
