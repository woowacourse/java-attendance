import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceBook;
import domain.AttendanceHistory;
import domain.Crew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("크루에 해당하는 출석 기록을 반환한다.")
        @Test
        public void findByCrew() throws Exception {
            // given
            final var crew = new Crew("헤일러");
            final var attendanceBook = new AttendanceBook();
            attendanceBook.registerCrew(crew);

            // when
            final AttendanceHistory actual = attendanceBook.findByCrew(crew);

            // then
            assertThat(actual.getCrew()).isEqualTo(crew);
        }
    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {
    }
}
