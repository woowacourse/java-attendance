package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Test
    void 등록된_닉네임이_아닌경우_출석시_예외가_발생한다() {
        //given
        String nickname = "pobi";
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 1);
        AttendanceBook attendanceBook = new AttendanceBook(new Crews());

        //when
        assertThatThrownBy(() -> attendanceBook.attend(nickname, attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(nickname + "은 등록되지 않은 닉네임입니다.");
    }

    @Test
    void 이미_해당_날짜에_출석한_경우_다시_출석할때_예외가_발생한다() {
        //given
        String nickname = "pobi";
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 1);
        AttendanceBook attendanceBook = new AttendanceBook(new Crews(nickname),
                new Attendance(nickname, attendanceDateTime));

        //when
        assertThatThrownBy(() -> attendanceBook.attend(nickname, attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석한 경우 다시 출석할 수 없습니다.");
    }

    @Test
    void 출석을_할_수_있다() {
        //given
        String nickname = "pobi";
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 1);
        AttendanceBook attendanceBook = new AttendanceBook(new Crews(nickname));

        //when
        attendanceBook.attend(nickname, attendanceDateTime);

        //then
        assertThat(attendanceBook)
                .isEqualTo(new AttendanceBook(new Crews(nickname), new Attendance(nickname, attendanceDateTime)));
    }

    @Test
    void 출석을_수정할때_해당하는_크루가_없다면_예외가_발생한다() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook(new Crews());

        //when
        assertThatThrownBy(() -> attendanceBook.updateAttendance(
                "pobi",
                LocalDateTime.of(2024, 12, 13, 11, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("pobi은 등록되지 않은 닉네임입니다.");
    }

    @Test
    void 출석을_수정할_수_있다() {
        //given
        String nickname = "pobi";
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 1);
        AttendanceBook attendanceBook = new AttendanceBook(new Crews(nickname),
                new Attendance(nickname, attendanceDateTime));

        //when
        attendanceBook.updateAttendance(
                "pobi",
                LocalDateTime.of(2024, 12, 13, 11, 1)
        );

        //then
        assertThat(attendanceBook).isEqualTo(new AttendanceBook(
                new Crews(nickname),
                new Attendance(
                        "pobi",
                        LocalDateTime.of(2024, 12, 13, 11, 1)
                )
        ));
    }

    @Test
    void 해당_날의_출석이_없는_경우에도_출석을_수정할_수_있다() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook(new Crews("pobi"));

        //when
        attendanceBook.updateAttendance(
                "pobi",
                LocalDateTime.of(2024, 12, 13, 11, 1)
        );

        //then
        assertThat(attendanceBook).isEqualTo(new AttendanceBook(
                new Crews("pobi"),
                new Attendance(
                        "pobi",
                        LocalDateTime.of(2024, 12, 13, 11, 1)
                )
        ));
    }

    @Test
    void 크루의_출석_기록을_조회할때_해당하는_크루가_없다면_예외가_발생한다() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook(new Crews("neo"));

        //when & then
        assertThatThrownBy(() -> attendanceBook.findAttendancesByNickname("pobi"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("pobi은 등록되지 않은 닉네임입니다.");

    }

    @Test
    void 크루의_출석_기록을_조회할_수_있다() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook(
                new Crews("pobi", "neo"),
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

    @Test
    void 크루원_마다의_출석_기록을_모두_생성할_수_있다() {
        //given
        Attendance pobiAttendance = new Attendance("pobi", LocalDateTime.of(2024, 11, 25, 10, 1));
        Attendance neoAttendance = new Attendance("neo", LocalDateTime.of(2024, 12, 2, 13, 1));
        Attendance surfAttendance = new Attendance("surf", LocalDateTime.of(2024, 12, 3, 10, 1));
        AttendanceBook attendanceBook = new AttendanceBook(
                new Crews("pobi", "neo", "surf"),
                pobiAttendance,
                neoAttendance,
                surfAttendance);

        //when
        CrewAttendances crewAttendances = attendanceBook.createCrewAttendances();

        //then
        assertThat(crewAttendances.getCrewAttendances())
                .containsAll(
                        List.of(
                                new CrewAttendance("pobi", pobiAttendance),
                                new CrewAttendance("neo", neoAttendance),
                                new CrewAttendance("surf", surfAttendance)
                        )
                );
    }

    @Test
    void 이름과_출석날짜를_통해_출석을_찾을_수_있다() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook(
                new Crews("pobi"),
                new Attendance("pobi", LocalDateTime.of(2024, 12, 2, 10, 1))
        );

        //when
        Optional<Attendance> result = attendanceBook.findAttendance("pobi", LocalDate.of(2024, 12, 2));

        //then
        Assertions.assertThat(result)
                .isPresent()
                .isEqualTo(Optional.of(
                        new Attendance("pobi", LocalDateTime.of(2024, 12, 2, 10, 1))
                ));
    }

    @Test
    void 이름과_출석날짜를_통해_출석을_찾을때_없을때도_Optional로_감싸_반환한다() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook(
                new Crews("pobi")
        );

        //when
        Optional<Attendance> result = attendanceBook.findAttendance("pobi", LocalDate.of(2024, 12, 2));

        //then
        Assertions.assertThat(result).isEmpty();
    }
}
