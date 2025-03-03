package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 닉네임으로_크루를_조회한다() {
        //given
        Crew crew = new Crew("쿠키");
        Crews crews = new Crews(Set.of(crew));
        //when
        Crew actual = crews.findByNickname("쿠키");
        //then
        assertThat(actual).isEqualTo(crew);
    }

    @Test
    void 닉네임을_찾을수_없으면_예외를_발생시킨다() {
        //given
        Crew crew = new Crew("쿠키");
        Crews crews = new Crews(Set.of(crew));
        //when & then
        assertThatThrownBy(() -> crews.findByNickname("쿠기"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 닉네임의 크루가 존재하지 않습니다.");
    }

    @Test
    void 제적위험_크루를_조회한다() {
        //given
        Crew crew1 = new Crew("쿠키");
        Crew crew2 = new Crew("빙봉");
        Crews crews = new Crews(Set.of(crew1, crew2));

        LocalDateTime time1 = LocalDateTime.of(2024, 12, 2, 12, 30);
        AttendanceTime attendanceTime1 = new AttendanceTime(time1);
        Attendance attendance1 = Attendance.of(crew1, attendanceTime1);

        LocalDateTime time2 = LocalDateTime.of(2024, 12, 3, 9, 30);
        AttendanceTime attendanceTime2 = new AttendanceTime(time2);
        Attendance attendance2 = Attendance.of(crew1, attendanceTime2);

        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance1, attendance2)));

        Crews expected = new Crews(Set.of(crew2));
        //when
        Crews actual = crews.findDangerCrews(attendances, LocalDate.of(2024, 12, 4));
        //then
        assertThat(actual).isEqualTo(expected);
    }
}