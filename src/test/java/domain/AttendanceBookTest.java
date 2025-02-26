package domain;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Test
    void 이미_해당_날짜에_출석한_경우_다시_출석할때_예외가_발생한다() {
        //given
        String nickname = "pobi";
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 1);
        AttendanceBook attendanceBook = new AttendanceBook(new Attendance(nickname, attendanceDateTime));

        //when
        Assertions.assertThatThrownBy(() -> attendanceBook.attend(new Attendance(nickname, attendanceDateTime)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석한 경우 다시 출석할 수 없습니다.");
    }
}
