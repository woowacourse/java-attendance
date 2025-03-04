import domain.AttendCount;
import domain.Nickname;
import domain.WarningCrew;
import domain.WarningStatus;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WarningCrewTest {
    private static List<WarningCrew> createWarningCrew(List<Nickname> names) {
        AttendCount attendCount = new AttendCount(0, 1, 1);
        return names.stream()
                .map(name -> new WarningCrew(name, attendCount, WarningStatus.PASS))
                .toList();
    }


    @Test
    @DisplayName("주어진 값을 조건에 맞게 정렬한다")
    void sort() {
        //given
        List<WarningCrew> warningCrews = List.of(
                new WarningCrew(new Nickname("빙봉"), new AttendCount(0, 6, 1), WarningStatus.INTERVIEW),
                new WarningCrew(new Nickname("쿠키"), new AttendCount(0, 3, 2), WarningStatus.INTERVIEW),
                new WarningCrew(new Nickname("짱수"), new AttendCount(0, 6, 0), WarningStatus.WARNING),
                new WarningCrew(new Nickname("이든"), new AttendCount(0, 5, 2), WarningStatus.INTERVIEW),
                new WarningCrew(new Nickname("빙티"), new AttendCount(0, 4, 3), WarningStatus.INTERVIEW)
        );

        //when
        List<WarningCrew> actual = WarningCrew.sort(warningCrews);

        //then
        List<WarningCrew> expected = List.of(
                new WarningCrew(new Nickname("빙티"), new AttendCount(0, 4, 3), WarningStatus.INTERVIEW),
                new WarningCrew(new Nickname("이든"), new AttendCount(0, 5, 2), WarningStatus.INTERVIEW),
                new WarningCrew(new Nickname("빙봉"), new AttendCount(0, 6, 1), WarningStatus.INTERVIEW),
                new WarningCrew(new Nickname("쿠키"), new AttendCount(0, 3, 2), WarningStatus.INTERVIEW),
                new WarningCrew(new Nickname("짱수"), new AttendCount(0, 6, 0), WarningStatus.WARNING)
        );

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    private static List<Nickname> createNickname(List<String> names) {
        return names.stream()
                .map(Nickname::new)
                .toList();
    }

    @Test
    @DisplayName("출석 상태가 같으면 닉네임으로 오름차순 정렬한다")
    void sortSameCount() {
        //given
        List<String> names = List.of("라", "다", "나", "가", "마");
        List<Nickname> nicknames = createNickname(names);
        List<WarningCrew> warningCrews = createWarningCrew(nicknames);

        //when
        List<WarningCrew> actual = WarningCrew.sort(warningCrews);

        //then
        List<String> sortedNames = List.of("가", "나", "다", "라", "마");
        List<Nickname> sortedNicknames = createNickname(sortedNames);
        List<WarningCrew> expected = createWarningCrew(sortedNicknames);

        assertThat(actual).containsExactlyElementsOf(expected);
    }
}
