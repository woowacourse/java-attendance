package test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Crew;
import model.CrewGenerator;
import model.Crews;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @DisplayName("중복 없이 크루 이름을 읽어온다.")
    @Test
    void test1() {
        String crewInput = """
                쿠키,2024-12-13 10:08
                빙봉,2024-12-13 10:07
                이든,2024-12-13 10:07
                빙봉,2024-12-12 11:11
                빙티,2024-12-12 10:07
                이든,2024-12-12 10:06
                이든,2024-12-11 10:10
                """;
        List<String> crewNames = CrewGenerator.findCrewNames(crewInput);
        assertThat(crewNames).containsAll(Arrays.asList("쿠키", "빙봉", "빙티", "이든"));
    }

    @DisplayName("닉네임을 바탕으로 크루 객체를 생성한다.")
    @Test
    void test2() {
        List<String> crewNames = List.of("쿠키", "빙봉", "빙티", "이든");
        List<Crew> crews = CrewGenerator.registerCrew(crewNames);
        assertThat(crews).containsAll(Arrays.asList(
                new Crew("쿠키"),
                new Crew("빙봉"),
                new Crew("빙티"),
                new Crew("이든")
        ));
    }

    @DisplayName("크루 객체들을 포장한 객체를 생성한다.")
    @Test
    void test3() {
        //given
        List<Crew> crewsInput = List.of(
                new Crew("쿠키"),
                new Crew("빙봉"),
                new Crew("빙티"),
                new Crew("이든")
        );
        Crews crews = CrewGenerator.wrapCrews(crewsInput);
        assertThat(crews).isSameAs(new Crews(crewsInput));
    }

//    @DisplayName("입력한 닉네임에 맞는 크루 정보를 가져온다.")
//    @Test
//    void test3() {
//        //given
//        List<String> crewNames = List.of("쿠키", "빙봉", "빙티", "이든");
//        String name = "빙티";
//        Crews crews = CrewGenerator.generateCrews()
//        Crew crew = crews.findCrewByName(name);
//        assertThat(crew).isSameAs(new Crew(name));
//    }
}
