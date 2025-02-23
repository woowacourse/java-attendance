package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AttendancesTest {

    @DisplayName("출석을 추가할 수 있다.")
    @Test
    void 출석을_추가할_수_있다() {
        // given
        Attendances attendances = new Attendances();
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 5, 10, 0);

        // when
        Attendance result = attendances.addAttendance(dateTime);

        // than
        assertThat(result.getDateTime()).isEqualTo(dateTime);
        assertThat(result.getStatus()).isEqualTo(AttendanceStatusType.ATTENDANCE);
    }

    @DisplayName("해당 날짜의 출석을 찾을 수 있다.")
    @Test
    void 해당_날짜의_출석을_찾을_수_있다() {
        // given
        Attendances attendances = new Attendances();

        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        attendances.addAttendance(dateTime);

        // when
        Attendance result = attendances.find(dateTime.toLocalDate());

        // then
        assertThat(result.getDateTime()).isEqualTo(dateTime);
    }

    @Test
    void 이미_출석한_크루의_출석_확인할_경우_에러가_발생한다() {
        // given
        Attendances attendances = new Attendances();

        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 5, 10, 0, 0);
        attendances.addAttendance(dateTime);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> attendances.validateAlreadyAttendance(dateTime.toLocalDate()))
                .withMessage("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
    }
}
