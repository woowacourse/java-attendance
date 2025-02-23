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
        Crew crew = new Crew("훌라",
                new Attendances(LocalDate.of(2024,12,17), List.of(LocalDateTime.of(2024, 12, 16, 11, 0))));

        assertThatThrownBy(() -> crew.existInAttendances(LocalDate.of(2024,12,16)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미_출석하지않은_경우_예외_반환X() {
        Crew crew = new Crew("훌라",
                new Attendances(LocalDate.of(2024,12,11), List.of(LocalDateTime.of(2024, 12, 16, 11, 0))));

        assertThatCode(() -> crew.existInAttendances(LocalDate.of(2024,12,17))).doesNotThrowAnyException();
    }

    @Test
    void 결석_2회_이상_경고_대상자() {
        Crew crew = new Crew("훌라",
                new Attendances(LocalDate.of(2024,12,5),
                        List.of(LocalDateTime.of(2024, 12, 2, 11, 0))));
        assertThat(crew.checkWarning()).isEqualTo(Warning.WARN);
    }

    @Test
    void 결석_3회_이상_면담_대상자() {
        Crew crew = new Crew("훌라", new Attendances(LocalDate.of(2024,12,6),
                        List.of(LocalDateTime.of(2024, 12, 2, 11, 0),
                                LocalDateTime.of(2024, 12, 3, 11, 0))));

        assertThat(crew.checkWarning()).isEqualTo(Warning.INTERVIEW);
    }

    @Test
    void 결석_5회_이상_제적_대상자() {
        Crew crew = new Crew("훌라", new Attendances(LocalDate.of(2024,12,21),
                List.of(LocalDateTime.of(2024, 12, 16, 14, 0))));

        assertThat(crew.checkWarning()).isEqualTo(Warning.EXPULSION);
    }

    @Test
    void 지각_결석_5회_이상_제적_대상자() {
        Crew crew = new Crew("훌라", new Attendances(LocalDate.of(2024,12,21),
                List.of(LocalDateTime.of(2024, 12, 16, 14, 0),
                        LocalDateTime.of(2024, 12, 17, 11, 0),
                        LocalDateTime.of(2024, 12, 18, 11, 0),
                        LocalDateTime.of(2024, 12, 19, 11, 0),
                        LocalDateTime.of(2024, 12, 20, 11, 0))));

        assertThat(crew.checkWarning()).isEqualTo(Warning.EXPULSION);
    }

    @Test
    void 정상_출석이면_대상자가_아니다() {
        Crew crew = new Crew("훌라", new Attendances(LocalDate.of(2024,12,6),
                List.of(LocalDateTime.of(2024, 12, 2, 9, 0),
                        LocalDateTime.of(2024, 12, 3, 9, 0),
                        LocalDateTime.of(2024, 12, 4, 9, 0),
                        LocalDateTime.of(2024, 12, 5, 9, 0),
                        LocalDateTime.of(2024, 12, 6, 9, 0))));

        assertThat(crew.checkWarning()).isEqualTo(Warning.NONE);
    }

    @Test
    void 지각_3회_결석_1회는_경고_대상자() {
        Crew crew = new Crew("훌라", new Attendances(LocalDate.of(2024,12,6),
                List.of(LocalDateTime.of(2024, 12, 2, 13, 6),
                        LocalDateTime.of(2024, 12, 3, 10, 6),
                        LocalDateTime.of(2024, 12, 4, 10, 6),
                        LocalDateTime.of(2024, 12, 5, 11, 0))));

        assertThat(crew.checkWarning()).isEqualTo(Warning.WARN);
    }

    @Test
    void 출석_수정을_성공하면_수정한_기록을_받는다() {
        Crew crew = new Crew("훌라", new Attendances(LocalDate.of(2024,12,21),
                List.of(LocalDateTime.of(2024, 12, 16, 9, 0),
                        LocalDateTime.of(2024, 12, 17, 9, 0),
                        LocalDateTime.of(2024, 12, 18, 9, 0),
                        LocalDateTime.of(2024, 12, 19, 9, 0),
                        LocalDateTime.of(2024, 12, 20, 9, 0))));

        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 20, 11, 0);

        Attendance attendance = crew.updateAttendance(localDateTime);
        assertThat(attendance.getDateTime()).isEqualTo(localDateTime);
    }
}
