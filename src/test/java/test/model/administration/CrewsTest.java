package test.model.administration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import model.admininstration.Crews;
import model.attendance.Crew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    @DisplayName("크루 객체들을 포장한 객체를 생성한다.")
    @Test
    void success_createCrews() {
        //given
        List<String> combinedData = List.of(
                "쿠키", "빙봉", "이든", "빙티"
        );

        //when
        Crews crews = Crews.from(combinedData);

        //then
        assertThat(crews).isEqualTo(new Crews(List.of(
                new Crew("쿠키"),
                new Crew("빙봉"),
                new Crew("빙티"),
                new Crew("이든")
        )));
    }

    @DisplayName("입력한 닉네임에 맞는 크루 정보를 가져온다.")
    @Test
    void success_findCrewFromCrewsByName() {
        //given
        String name = "빙티";
        Crews crews = new Crews(List.of(
                new Crew("쿠키"),
                new Crew("빙봉"),
                new Crew("빙티"),
                new Crew("이든")
        ));

        //when
        Optional<Crew> crew = crews.findCrewByName(name);

        //then
        assertThat(crew.get()).isEqualTo(new Crew(name));
    }



}
