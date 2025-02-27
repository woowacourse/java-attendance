package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    Attendances attendances;
    Attendance attendance1;

    @BeforeEach
    void setUp() {
        attendance1 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 0)));
        attendances = new Attendances(new HashSet<>());
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

    @DisplayName("크루 이름이 같고 같은 날의 출석인 경우 예외가 발생한다")
    @Test
    void 크루_이름이_같고_같은_날의_출석인_경우_예외가_발생한다() {

        // given
        Attendance attendance2 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 5)));

        // when & then
        assertThatThrownBy(() -> attendances.add(attendance2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석 기록이 존재합니다. 수정 기능을 이용해 주세요.");
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

    @DisplayName("크루 이름과 년월일로 출석 기록을 검색시, 없으면 예외가 발생한다.")
    @Test
    void 크루_이름과_년월일로_출석_기록을_검색시_없으면_예외가_발생한다() {

        // given
        LocalDate localDate = LocalDate.of(2025, 2, 27);

        // when & then
        assertThatThrownBy(() -> attendances.findByCrewNameAndLocalDate("체체2", localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 기록이 존재하지 않습니다.");
    }
}
