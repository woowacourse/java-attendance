package attendance;

import attendance.domain.Attendance;
import attendance.domain.Crew;
import attendance.domain.Warning;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CrewTest {

    @Test
    void 이미_출석한_경우_예외_반환() {
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 16, 11, 0));
        Crew crew = new Crew("훌라", List.of(attendance));

        assertThatThrownBy(() -> crew.existInAttendances(LocalDate.of(2024,12,16)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미_출석하지않은_경우_예외_반환X() {
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 16, 11, 0));
        Crew crew = new Crew("훌라", List.of(attendance));

        assertThatCode(() -> crew.existInAttendances(LocalDate.of(2024,12,17))).doesNotThrowAnyException();
    }

    @Test
    void 결석_2회_이상_경고_대상자() {
        List<Attendance> attendances = List.of(new Attendance(LocalDateTime.of(2024, 12, 16, 14, 0)),
                new Attendance(LocalDateTime.of(2024, 12, 17, 11, 0)));
        Crew crew = new Crew("훌라", attendances);

        assertThat(crew.checkWarning()).isEqualTo(Warning.WARN);
    }

    @Test
    void 결석_3회_이상_면담_대상자() {
        List<Attendance> attendances = List.of(new Attendance(LocalDateTime.of(2024, 12, 16, 14, 0)),
                new Attendance(LocalDateTime.of(2024, 12, 17, 11, 0)),
                new Attendance(LocalDateTime.of(2024, 12, 18, 11, 0)));
        Crew crew = new Crew("훌라", attendances);

        assertThat(crew.checkWarning()).isEqualTo(Warning.INTERVIEW);
    }

    @Test
    void 결석_5회_이상_제적_대상자() {
        List<Attendance> attendances = List.of(new Attendance(LocalDateTime.of(2024, 12, 16, 14, 0)),
                new Attendance(LocalDateTime.of(2024, 12, 17, 11, 0)),
                new Attendance(LocalDateTime.of(2024, 12, 18, 11, 0)),
                new Attendance(LocalDateTime.of(2024, 12, 19, 11, 0)),
                new Attendance(LocalDateTime.of(2024, 12, 20, 11, 0)));
        Crew crew = new Crew("훌라", attendances);

        assertThat(crew.checkWarning()).isEqualTo(Warning.EXPULSION);
    }

    @Test
    void 정상_출석이면_대상자가_아니다() {
        List<Attendance> attendances = List.of(new Attendance(LocalDateTime.of(2024, 12, 16, 9, 0)),
                new Attendance(LocalDateTime.of(2024, 12, 17, 10, 0)),
                new Attendance(LocalDateTime.of(2024, 12, 18, 10, 0)),
                new Attendance(LocalDateTime.of(2024, 12, 19, 10, 0)),
                new Attendance(LocalDateTime.of(2024, 12, 20, 10, 0)));
        Crew crew = new Crew("훌라", attendances);

        assertThat(crew.checkWarning()).isEqualTo(Warning.NONE);
    }

    @Test
    void 지각_3회_결석_1회는_경고_대상자() {
        List<Attendance> attendances = List.of(new Attendance(LocalDateTime.of(2024, 12, 16, 13, 7)),
                new Attendance(LocalDateTime.of(2024, 12, 17, 10, 7)),
                new Attendance(LocalDateTime.of(2024, 12, 18, 10, 7)),
                new Attendance(LocalDateTime.of(2024, 12, 20, 11, 7)));

        Crew crew = new Crew("훌라", attendances);

        assertThat(crew.checkWarning()).isEqualTo(Warning.WARN);
    }
}
