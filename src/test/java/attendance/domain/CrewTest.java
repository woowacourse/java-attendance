package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class CrewTest {

    @Test
    void 이미_출석한_경우_예외_반환() {
        Attendance attendance = Attendance.from(LocalDateTime.of(2024, 12, 16, 11, 0));
        Crew crew = new Crew("훌라", List.of(attendance));

        assertThatThrownBy(() -> crew.existInAttendances(LocalDate.of(2024,12,16)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미_출석하지않은_경우_예외_반환X() {
        Attendance attendance = Attendance.from(LocalDateTime.of(2024, 12, 16, 11, 0));
        Crew crew = new Crew("훌라", List.of(attendance));

        assertThatCode(() -> crew.existInAttendances(LocalDate.of(2024,12,17))).doesNotThrowAnyException();
    }

    @Test
    void 경고_대상자를_판별한다() {
        List<Attendance> attendances = List.of(Attendance.from(LocalDateTime.of(2024, 12, 16, 14, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 17, 11, 0)));
        Crew crew = new Crew("훌라", attendances);

        assertThat(crew.checkWarning()).isEqualTo(Warning.WARN);
    }

    @Test
    void 면담_대상자를_판별한다() {
        List<Attendance> attendances = List.of(Attendance.from(LocalDateTime.of(2024, 12, 16, 14, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 17, 11, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 18, 11, 0)));
        Crew crew = new Crew("훌라", attendances);

        assertThat(crew.checkWarning()).isEqualTo(Warning.INTERVIEW);
    }

    @Test
    void 제적_대상자를_판별한다() {
        List<Attendance> attendances = List.of(Attendance.from(LocalDateTime.of(2024, 12, 16, 14, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 17, 11, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 18, 11, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 19, 11, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 20, 11, 0)));
        Crew crew = new Crew("훌라", attendances);

        assertThat(crew.checkWarning()).isEqualTo(Warning.EXPULSION);
    }

    @Test
    void 정상_출석이면_대상자가_아니다() {
        List<Attendance> attendances = List.of(Attendance.from(LocalDateTime.of(2024, 12, 16, 9, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 17, 10, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 18, 10, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 19, 10, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 20, 10, 0)));
        Crew crew = new Crew("훌라", attendances);

        assertThat(crew.checkWarning()).isEqualTo(Warning.NONE);
    }

    @Test
    void 지각_3회_결석_1회는_경고_대상자() {
        List<Attendance> attendances = List.of(Attendance.from(LocalDateTime.of(2024, 12, 16, 13, 7)),
                Attendance.from(LocalDateTime.of(2024, 12, 17, 10, 7)),
                Attendance.from(LocalDateTime.of(2024, 12, 18, 10, 7)),
                Attendance.from(LocalDateTime.of(2024, 12, 20, 11, 7)));

        Crew crew = new Crew("훌라", attendances);

        assertThat(crew.checkWarning()).isEqualTo(Warning.WARN);
    }

    @Test
    void 출석_수정을_성공하면_수정한_기록을_받는다() {
        List<Attendance> attendances = List.of(Attendance.from(LocalDateTime.of(2024, 12, 16, 9, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 17, 10, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 18, 10, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 19, 10, 0)),
                Attendance.from(LocalDateTime.of(2024, 12, 20, 10, 0)));
        Crew crew = new Crew("훌라", attendances);

        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 20, 11, 0);

        Attendance attendance = crew.updateAttendance(localDateTime);
        assertThat(attendance.getDateTime()).isEqualTo(localDateTime);
    }
}
