package domain;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @Test
    void 주말에_출석을_생성할_경우_예외가_발생한다() {
        //given
        String nickname = "pobi";
        LocalDateTime saturday = LocalDateTime.of(2024, 12, 14, 10, 1);

        //when
        Assertions.assertThatThrownBy(() -> new Attendance(nickname, saturday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말에는 출석할 수 없습니다.");
    }

    @Test
    void 공휴일에_출석_객체를_생성하는_경우_예외가_발생한다() {
        //given
        String nickname = "pobi";
        LocalDateTime christmas = LocalDateTime.of(2024, 12, 25, 10, 1);

        //when then
        Assertions.assertThatThrownBy(() -> new Attendance(nickname, christmas))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공휴일에는 출석할 수 없습니다.");
    }

    @Test
    void 출석의_상태를_확인할_수_있다() {
        //given
        String nickname = "pobi";
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 6);
        Attendance attendance = new Attendance(nickname, attendanceDateTime);

        //when
        AttendanceStatus result = attendance.getAttendanceStatus();

        //then
        Assertions.assertThat(result).isEqualTo(AttendanceStatus.LATE);
    }
}
