package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @DisplayName("크루 이름을 통해 해당 크루의 출석 기록을 모두 가져온다.")
    @Test
    void 크루_이름을_통해_해당_크루의_출석_기록을_모두_가져온다() {

        // given
        String crewName = "체체";

        // when
        List<Attendance> currentAttendances = attendances.findAttendancesByCrewName(crewName, 2025, 2);

        // then
        assertThat(currentAttendances.size()).isEqualTo(1);
    }

    @DisplayName("크루 이름을 통해 해당 크루의 출석 기록을 모두 가져온다.")
    @ParameterizedTest
    @CsvSource(value = {
            "ATTEND,1", "LATE,1", "ABSENT,1"
    })
    void 크루_이름을_통해_해당_월의_출석_상태를_가져온다(AttendanceStatus status, int result) {

        // given
        String crewName = "체체";
        Attendance attendance2 = new Attendance(crewName, new Time(LocalDateTime.of(2025, 2, 26, 10, 6)));
        Attendance attendance3 = new Attendance(crewName, new Time(LocalDateTime.of(2025, 2, 25, 10, 31)));

        attendances.add(attendance2);
        attendances.add(attendance3);

        // when
        long statusCount = attendances.getStatusCount(status, crewName, 2025, 2, 28);

        // then
        assertThat(statusCount).isEqualTo(result);
    }

    @DisplayName("한 크루의 특정 출석 상태의 횟수를 구한다.")
    @Test
    void 한_크루의_특정_출석_상태의_횟수를_구한다() {

        // given
        String crewName = "체체";
        Attendance attendance2 = new Attendance(crewName, new Time(LocalDateTime.of(2025, 2, 26, 10, 31)));
        Attendance attendance3 = new Attendance(crewName, new Time(LocalDateTime.of(2025, 2, 25, 10, 31)));
        Attendance attendance4 = new Attendance(crewName, new Time(LocalDateTime.of(2025, 2, 24, 10, 31)));
        Attendance attendance5 = new Attendance(crewName, new Time(LocalDateTime.of(2025, 2, 21, 10, 31)));
        attendances.add(attendance2);
        attendances.add(attendance3);
        attendances.add(attendance4);
        attendances.add(attendance5);

        // when
        long count = attendances.getStatusCount(AttendanceStatus.ABSENT, crewName, 2025, 2, 25);

        // then
        assertThat(count).isEqualTo(4);

    }

}
