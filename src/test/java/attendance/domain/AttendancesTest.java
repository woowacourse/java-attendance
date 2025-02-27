package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    Attendances attendances;
    Attendance attendance1;

    @BeforeEach
    void setUp() {
        attendance1 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 0)));
        attendances = new Attendances(new ArrayList<>());
        attendances.add(attendance1);
    }

    @DisplayName("출석을 추가한다.")
    @Test
    void 출석을_추가한다() {

        // given
        Attendance attendance2 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 28, 10, 0)));

        // when & then
        assertThat(attendances.add(attendance2)).isTrue();
    }

    @DisplayName("크루 이름과 년월일로 출석 기록을 찾는다.")
    @Test
    void 크루_이름과_년월일로_출석_기록을_찾는다() {

        // given
        LocalDate localDate = LocalDate.of(2025, 2, 27);

        // when
        Attendance attendance2 = attendances.findByCrewNameAndLocalDate("체체", localDate);

        // then
        assertThat(attendance1).isEqualTo(attendance2);
    }
}
