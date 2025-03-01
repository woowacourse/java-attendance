import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendCount;
import domain.WarningCrew;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WarningCrewTest {

    @Test
    @DisplayName("주어진 값을 조건에 맞게 정렬한다")
    void sort() {
        //given
        List<WarningCrew> warningCrews = List.of(
                new WarningCrew("빙봉", new AttendCount(0, 6, 1)),
                new WarningCrew("쿠키", new AttendCount(0, 3, 2)),
                new WarningCrew("짱수", new AttendCount(0, 6, 0)),
                new WarningCrew("이든", new AttendCount(0, 5, 2)),
                new WarningCrew("빙티", new AttendCount(0, 4, 3))
        );

        //when
        List<WarningCrew> actual = WarningCrew.sort(warningCrews);

        //then
        List<WarningCrew> expected = List.of(
                new WarningCrew("빙티", new AttendCount(0, 4, 3)),
                new WarningCrew("이든", new AttendCount(0, 5, 2)),
                new WarningCrew("빙봉", new AttendCount(0, 6, 1)),
                new WarningCrew("쿠키", new AttendCount(0, 3, 2)),
                new WarningCrew("짱수", new AttendCount(0, 6, 0))
        );

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("출석 상태가 같으면 닉네임으로 오름차순 정렬한다")
    void sortSameCount() {
        //given
        List<WarningCrew> warningCrews = List.of(
                new WarningCrew("라", new AttendCount(0, 1, 1)),
                new WarningCrew("다", new AttendCount(0, 1, 1)),
                new WarningCrew("나", new AttendCount(0, 1, 1)),
                new WarningCrew("가", new AttendCount(0, 1, 1)),
                new WarningCrew("마", new AttendCount(0, 1, 1))
        );

        //when
        List<WarningCrew> actual = WarningCrew.sort(warningCrews);

        //then
        List<WarningCrew> expected = List.of(
                new WarningCrew("가", new AttendCount(0, 1, 1)),
                new WarningCrew("나", new AttendCount(0, 1, 1)),
                new WarningCrew("다", new AttendCount(0, 1, 1)),
                new WarningCrew("라", new AttendCount(0, 1, 1)),
                new WarningCrew("마", new AttendCount(0, 1, 1))
        );

        assertThat(actual).containsExactlyElementsOf(expected);
    }
}
