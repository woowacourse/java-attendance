import domain.Crew;
import utils.CrewAttendanceFileReader;
import domain.Crews;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CrewsTest {
    @DisplayName("크루들의 출석 데이터를 받아와 크루를 생성할 수 있다")
    @Test
    void test1() {
        List<String> crewAttendanceResource = CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(crewAttendanceResource);
        assertThat(crews.getCrews().size()).isGreaterThan(0);
    }

    @DisplayName("크루들의 출석 데이터를 받아올때 한 크루가 여러번 들어가지 않는다")
    @Test
    void test2() {
        List<String> crewAttendanceResource = CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(crewAttendanceResource);
        assertThat(crews.getCrews().stream().filter(crew -> crew.getNickname().equals("쿠키")).toList().size()).isEqualTo(1);
    }

    @DisplayName("크루의 이름을 통해서 크루를 찾을 수 있다")
    @Test
    void test3(){
        List<String> crewAttendanceResource = CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(crewAttendanceResource);
        Crew crew = crews.findCrewByNickname("쿠키").orElseThrow(()->new IllegalArgumentException("없는 닉네임입니다"));
        assertThat(crew.getNickname()).isEqualTo("쿠키");
    }
    @DisplayName("크루의 이름을 통해서 크루를 찾을 수 있다")
    @Test
    void test4(){
        List<String> crewAttendanceResource = CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(crewAttendanceResource);
        Crew crew = crews.findCrewByNickname("이든").orElseThrow(()->new IllegalArgumentException("없는 닉네임입니다"));
        assertThat(crew.getNickname()).isEqualTo("이든");
    }

    @DisplayName("제적 대상자 크루들 찾을 수 있다")
    @Test
    void test5() {
        List<String> crewAttendanceResource = CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(crewAttendanceResource);
        List<Crew> dismissalCrews = crews.findDismissalCrews();
        assertThat(dismissalCrews.size()).isEqualTo(5);
    }

    @DisplayName("제적 대상자 크루들을 정렬된 상태로 찾을 수 있다")
    @Test
    void test6(){
        List<String> crewAttendanceResource = CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(crewAttendanceResource);
        List<Crew> dismissalCrews=crews.findDismissalCrewsByImportance();
        assertThat(dismissalCrews.size()).isEqualTo(5);
    }
}
