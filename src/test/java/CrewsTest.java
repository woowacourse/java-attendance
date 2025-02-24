import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendTime;
import domain.Crew;
import domain.Crews;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CrewsTest {

    @DisplayName("닉네임으로 크루를 찾는다.")
    @ParameterizedTest
    @CsvSource({"폰트,폰트", "슬링키,슬링키"})
    void test1(String nickname, String expected) {
        Crews crews = new Crews();
        crews.loadCrews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03"));
        Crew crew = crews.findCrew(nickname);

        assertThat(crew.getName()).isEqualTo(expected);
    }

    @DisplayName("출석 데이터를 추가한다.")
    @Test
    void test3() {
        Crews crews = new Crews();
        crews.loadCrews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03"));

        crews.initializeAttendTime("슬링키", "2024-12-09 13:03");

        assertThat(crews.findCrew("슬링키").getAttendTimes().size())
                .isEqualTo(2);
    }

    @Test
    void test4() {
        Crews crews = new Crews();
        crews.loadCrews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03"));

        crews.initializeAttendTime("포비", "2024-12-09 13:03");

        assertThat(crews.getCrews().size())
                .isEqualTo(3);
    }

    @DisplayName("해당 날짜의 출석 데이터를 삭제한다.")
    @Test
    void test5() {
        Crews crews = new Crews();
        crews.loadCrews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03"));
        AttendTime attendTime = crews.deleteAttendance("슬링키", 9);

        assertThat(attendTime.getAttendTime().getHour()).isEqualTo(13);
        assertThat(attendTime.getAttendTime().getMinute()).isEqualTo(3);
    }

    @DisplayName("제적된 크루를 반환한다.")
    @Test
    void test7() {
        Crews crews = new Crews();
        crews.loadCrews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03"));

        List<Crew> dismissalCrews = crews.getDangerousCrews("제적");

        assertThat(dismissalCrews.size()).isEqualTo(2);
    }

    @DisplayName("등록되지 않은 닉네임을 입력하면 예외를 발생시킨다.")
    @Test
    void test9() {
        Crews crews = new Crews();
        crews.loadCrews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03", "포비,2024-12-09 13:03"));

        assertThatThrownBy(() -> crews.ifFindNameAddTime("벨로"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
