import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.Crew;
import domain.Crews;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CrewsTest {

    @DisplayName("크루 출석 데이터를 초기화한다.")
    @Test
    void test2() {
        Crews crews = new Crews();
        crews.saveCrew("폰트", "2024-12-13 10:08");
        crews.saveCrew("폰트", "2024-12-12 10:01");
        crews.saveCrew("슬링키", "2024-12-09 13:03");

        assertThat(crews.findByNickname("폰트").getName()).isEqualTo("폰트");
    }

    @DisplayName("닉네임으로 크루를 찾는다.")
    @ParameterizedTest
    @CsvSource({"폰트,폰트", "슬링키,슬링키"})
    void test1(String nickname, String expected) {
        Crews crews = new Crews();
        crews.saveCrew("폰트", "2024-12-13 10:08");
        crews.saveCrew("슬링키", "2024-12-09 13:03");

        Crew crew = crews.findByNickname(nickname);

        assertThat(crew.getName()).isEqualTo(expected);
    }

    @DisplayName("제적된 크루를 반환한다.")
    @Test
    void test7() {
        Crews crews = new Crews();
        crews.saveCrew("폰트", "2024-12-13 10:08");
        crews.saveCrew("슬링키", "2024-12-09 13:03");

        List<Crew> dismissalCrews = crews.getDangerousCrews("제적");

        assertThat(dismissalCrews.size()).isEqualTo(2);
    }

    @DisplayName("등록되지 않은 닉네임을 입력하면 예외를 발생시킨다.")
    @Test
    void test9() {
        Crews crews = new Crews();
        crews.saveCrew("폰트", "2024-12-13 10:08");
        crews.saveCrew("슬링키", "2024-12-09 13:03");
        crews.saveCrew("포비", "2024-12-09 13:03");

        assertThatThrownBy(() -> crews.findByNickname("벨로"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
