package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.CrewAttendance;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Test
    void 이미_해당_날짜에_출석한_경우_다시_출석할때_예외가_발생한다() {
        //given
        String nickname = "pobi";
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 1);
        AttendanceBook attendanceBook = new AttendanceBook(new Attendance(nickname, attendanceDateTime));

        //when
        assertThatThrownBy(() -> attendanceBook.attend(new Attendance(nickname, attendanceDateTime)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석한 경우 다시 출석할 수 없습니다.");
    }

    @Test
    void 출석을_할_수_있다() {
        //given
        String nickname = "pobi";
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 1);
        AttendanceBook attendanceBook = new AttendanceBook();

        //when
        attendanceBook.attend(new Attendance(nickname, attendanceDateTime));

        //then
        assertThat(attendanceBook)
                .isEqualTo(new AttendanceBook(new Attendance(nickname, attendanceDateTime)));
    }

    @Test
    void 출석을_수정할_수_있다() {
        //given
        String nickname = "pobi";
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 1);
        AttendanceBook attendanceBook = new AttendanceBook(new Attendance(nickname, attendanceDateTime));

        //when
        attendanceBook.updateAttendance(
                "pobi",
                LocalDateTime.of(2024, 12, 13, 11, 1)
        );

        //then
        assertThat(attendanceBook).isEqualTo(new AttendanceBook(
                new Attendance(
                        "pobi",
                        LocalDateTime.of(2024, 12, 13, 11, 1)
                )
        ));
    }

    @Test
    void 해당_날의_출석이_없는_경우에도_출석을_수정할_수_있다() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();

        //when
        attendanceBook.updateAttendance(
                "pobi",
                LocalDateTime.of(2024, 12, 13, 11, 1)
        );

        //then
        assertThat(attendanceBook).isEqualTo(new AttendanceBook(
                new Attendance(
                        "pobi",
                        LocalDateTime.of(2024, 12, 13, 11, 1)
                )
        ));
    }

    @Test
    void 크루의_출석_기록을_조회할_수_있다() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook(
                new Attendance("pobi", LocalDateTime.of(2024, 11, 25, 10, 1)),
                new Attendance("pobi", LocalDateTime.of(2024, 12, 2, 13, 1)),
                new Attendance("neo", LocalDateTime.of(2024, 12, 3, 10, 1))
        );

        //when
        CrewAttendance attendances = attendanceBook.findAttendancesByNickname("pobi");

        //then
        assertThat(attendances)
                .isEqualTo(new CrewAttendance(
                        "pobi",
                        List.of(
                                new Attendance("pobi", LocalDateTime.of(2024, 11, 25, 10, 1)),
                                new Attendance("pobi", LocalDateTime.of(2024, 12, 2, 13, 1))
                        )
                ));
    }
}
